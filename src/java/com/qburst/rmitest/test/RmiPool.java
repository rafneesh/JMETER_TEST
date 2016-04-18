package com.qburst.rmitest.test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import com.ibl.remote.ISessionManager;
import com.ibl.remote.util.ReconnectCR;
import com.ibl.remote.util.RequestResponse;

public class RmiPool {

    // Public static variables
    public static String HOST_NAME;
    public static String BINDING_NAME;
    public static int PORT;
    public static int POOL_SIZE;

    // Default capacity
    private static final int DEFAULT_POOL_SIZE = 300;

    // Singleton object
    private static RmiPool instance;

    private List<RmiConnection> pool;

    /**
     * Constructor
     *
     * @throws RmiException RMI exception
     */
    public RmiPool() throws RmiException {
        this(HOST_NAME, PORT, BINDING_NAME, POOL_SIZE);
    }

    /**
     * Private constructor
     *
     * @param hostName    Host name
     * @param port        Port
     * @param bindingName Binding name
     * @param capacity    Pool size
     * @throws RmiException RMI exception
     */
    private RmiPool(String hostName, int port, String bindingName, int capacity) throws RmiException {
        init(hostName, port, bindingName, capacity);

    }


    /**
     * Initialization
     *
     * @param hostName    Host name or IP address
     * @param port        Port
     * @param bindingName Binding name
     * @param capacity    Capacity
     * @throws RmiException RMI exception
     */
    private void init(String hostName, int port, String bindingName, int capacity) throws RmiException {
    	
    	System.out.println("Connecting");
        this.pool = new ArrayList<RmiConnection>(capacity);
        try {
            for (int i = 0; i < capacity; i++) {
                Registry registry = LocateRegistry.getRegistry(hostName,port);
                //Remote remote = registry.lookup(bindingName);
                
                ISessionManager sessionManager = (ISessionManager) registry.lookup(bindingName);
                System.out.println("Connected to RMI AND Looked up the bind"+hostName+":"+port+" Bind:"+bindingName);
                
                ReconnectCR reconnectCR = new ReconnectCR("ssjsj");
    			//RequestResponse response = sessionManager.reconnect(reconnectCR);
                
                
               // RmiConnection conn = new RmiConnection(remote);
             //   pool.add(conn);
            }
        } catch (RemoteException e) {
        	e.printStackTrace();
            System.out.println("Error connecting to " + hostName + " at " + port + ":" + e.getMessage());
            throw new RmiException("Error connecting to " + hostName + " at " + port + ":" + e.getMessage(), e);
        } catch (NotBoundException e) {
        	e.printStackTrace();
            System.out.println("Error connecting to " + hostName + " at " + port + ":" + e.getMessage());
            throw new RmiException("Error connecting to " + hostName + " at " + port + ":" + e.getMessage(), e);
        }
    }

    /**
     * Get a RMI connection
     *
     * @return RMI connection
     * @throws Exception Exception
     */
    public RmiConnection getConnection() throws Exception {

        while (true) {
            if (pool.size() > 0) {
                RmiConnection conn = pool.remove(0);
                conn.setInUse(true);
                return conn;
            }
            System.out.println("WAITING for connection");
            Thread.sleep(30);
        }

    }

    /**
     * Release the RMI connection
     *
     * @param conn Connection object
     */
    public void releaseConnection(RmiConnection conn) {
        conn.setInUse(false);
        pool.add(conn);
    }


    /**
     * Singleton access method
     *
     * @return Singleton object
     * @throws RmiException RMI exception
     */
    public static RmiPool getInstance() throws RmiException {
        if (instance == null)
            instance = new RmiPool();
        return instance;
    }


    public static boolean reload() {
        try {
            RmiPool newInstance = new RmiPool();
            RmiPool oldInstance = instance;
            instance = newInstance;
            oldInstance = null;
            return true;
        } catch (RmiException e) {
            return false;
        }

    }

}
