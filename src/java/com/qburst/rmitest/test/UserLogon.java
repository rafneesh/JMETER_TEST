package com.qburst.rmitest.test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.ibl.client.SchedulingClient;
import com.ibl.remote.user.Password;
import com.ibl.remote.util.RequestResponse;
import com.qburst.rmitest.util.CommonResponseHandler;
import com.qburst.rmitest.util.CommonUtil;

public class UserLogon extends AbstractJavaSamplerClient {
	private static transient Logger log = LoggingManager.getLoggerForClass();
	
	// RMI information
	private String hostName;
	private int port;
	private String bindingName;

	// UserInfo
	private String userName;
	private String passwordStr;

	private RequestResponse doRMILogon() {
		Password password = CommonUtil.getPassword(passwordStr);
		RequestResponse resp = SchedulingClient.logonRMI(hostName, port, bindingName, userName, password, password,
				null, false);
		log.info("RMILogin done for user " + userName + "...status = " + resp.getResultCode());
		return resp;
	}

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("HostName", "10.9.0.158");
		params.addArgument("Port", "10000");
		params.addArgument("BindingName", "cslocalserver");
		params.addArgument("Username", "will.trivett@clinicserver.com");
		params.addArgument("Password", "temptemp");
		return params;
	}

	public void setupTest(JavaSamplerContext context) {
		hostName = context.getParameter("HostName");
		port = context.getIntParameter("Port");
		bindingName = context.getParameter("BindingName");
		userName = context.getParameter("Username");
		passwordStr = context.getParameter("Password");
	}
	

	public SampleResult runTest(JavaSamplerContext context) {
		log.info("Running Test ..");
		SampleResult results = new SampleResult();
		results.setSampleLabel("CreateUser");
		results.sampleStart();
		setupTest(context);
		RequestResponse requestResponse = doRMILogon();
		return CommonResponseHandler.endSampler(results, requestResponse);
	}

	
	public RequestResponse doRMILogon(JavaSamplerContext context) {
		setupTest(context);
		return doRMILogon();
	}
	
	public static void main(String[] args) {
		UserLogon sample = new UserLogon();
		JavaSamplerContext context = new JavaSamplerContext(sample.getDefaultParameters());
		sample.runTest(context);
	}
	
}
