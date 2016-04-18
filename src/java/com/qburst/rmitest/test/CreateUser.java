package com.qburst.rmitest.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.ibl.client.SchedulingClient;
import com.ibl.remote.clinic.ClinicLocationDBKey;
import com.ibl.remote.resource.ResourceExtendedDBKey;
import com.ibl.remote.user.AuthPrincipal;
import com.ibl.remote.user.Password;
import com.ibl.remote.user.UserCRG;
import com.ibl.remote.user.UserCRG.CreateUserAccountCR;
import com.ibl.remote.util.RequestResponse;

public class CreateUser extends AbstractJavaSamplerClient{
	
	static ResourceExtendedDBKey[] resourceDBKeys;	
	static HashMap<ClinicLocationDBKey, String[]> principalMap = new HashMap<ClinicLocationDBKey, String[]>();
	int counter;

	static String adminUserName = "will.trivett@clinicserver.com";
	static String adminPassword = "temptemp";
    private static transient Logger log = LoggingManager.getLoggerForClass();
    
	private String getEmail(){
		return "user" + counter + "@clinicserver.com";
	}
	private String getFirstName(){
		return "User" + counter ;
	}
	private String getLastName(){
		return counter + "User" ;
	}
	private Password getPassword(){
		return new Password("temp");
	}
	
	static{
		Arguments params = new Arguments();
		params.addArgument("HostName", "10.9.0.158");
		params.addArgument("Port", "10000");
		params.addArgument("BindingName", "cslocalserver");
		params.addArgument("Username", adminUserName);
		params.addArgument("Password", adminPassword);
		log.info("Doing RMI logon");
		new UserLogon().doRMILogon(new JavaSamplerContext(params));
		RequestResponse requestResponse = SearchResources.searchResources();
		resourceDBKeys = ((ResourceExtendedDBKey[]) requestResponse.getDetail());
		
		final List<String> permissions = new ArrayList<String>();
		getPermissions(permissions);
		ClinicLocationDBKey clinicLocationDBKey = new ClinicLocationDBKey();
		clinicLocationDBKey.setID(-1);
		principalMap.put(clinicLocationDBKey, permissions.toArray(new String[0]));
	}

	private static void getPermissions(final List<String> permissions) {
		permissions.add(AuthPrincipal.IBL_ADMIN_FINANCE);
		permissions.add(AuthPrincipal.LOGON);
		permissions.add(AuthPrincipal.LOGON_MOBILE);
		permissions.add(AuthPrincipal.ADMIN_BILLING);
		permissions.add(AuthPrincipal.ADMIN_APPOINTMENT);
		permissions.add(AuthPrincipal.ADMIN_APPT_BILLING);
		permissions.add(AuthPrincipal.ADMIN_CLINIC);
		permissions.add(AuthPrincipal.ADMIN_ECLAIMS);
		permissions.add(AuthPrincipal.ADMIN_FORM);
		permissions.add(AuthPrincipal.ADMIN_PROCEDURES);
		permissions.add(AuthPrincipal.ADMIN_RESOURCE);
		permissions.add(AuthPrincipal.ADMIN_RESOURCE_COMPENSATION_RATES);
		permissions.add(AuthPrincipal.ADMIN_RESOURCE_COMPENSATION_STATEMENTS);
		permissions.add(AuthPrincipal.ADMIN_SYSTEM);
		permissions.add(AuthPrincipal.ADMIN_USER);
		permissions.add(AuthPrincipal.ADMIN_WORK_FLOW);
		permissions.add(AuthPrincipal.BILLING_QUERIES);
		permissions.add(AuthPrincipal.PATIENT_BILLING);
		permissions.add(AuthPrincipal.PATIENT_DOCUMENTS);
		permissions.add(AuthPrincipal.PATIENT_FORM);
		permissions.add(AuthPrincipal.PATIENT_HIST_APPT);
		permissions.add(AuthPrincipal.PATIENT_HISTORY_WORK_FLOW);
		permissions.add(AuthPrincipal.PATIENT_PATIENT_PROFILE);
		permissions.add(AuthPrincipal.PATIENT_QUERY);
		permissions.add(AuthPrincipal.APPOINTMENT_BOOK);
		permissions.add(AuthPrincipal.APPOINTMENT_BOOK_RESOURCES);
		permissions.add(AuthPrincipal.APPOINTMENT_QUERY);
		permissions.add(AuthPrincipal.APPOINTMENT_PROCEDURE);
		permissions.add(AuthPrincipal.APPT_PROCEDURE);
		permissions.add(AuthPrincipal.SCHEDULE_REVISION);
		permissions.add(AuthPrincipal.WORK_FLOW_QUERIES);
		permissions.add(AuthPrincipal.WORK_FLOW_PROCEDURE);
	}
	
	private HashMap<ClinicLocationDBKey, String[]> getPrincipalMap(){
		return principalMap;
	}
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("HostName", "10.9.0.158");
		params.addArgument("Port", "10000");
		params.addArgument("BindingName", "cslocalserver");
		params.addArgument("Username", "will.trivett@clinicserver.com");
		params.addArgument("Password", "temptemp");
		params.addArgument("Counter", "counter");
		return params;
	}
	
	
	public void setupTest(JavaSamplerContext context){
		counter = Integer.parseInt(context.getParameter("Counter"));
	}
	
	
	public SampleResult runTest(JavaSamplerContext context) {
		log.info("Starting test..");
		SampleResult results = new SampleResult();
		results.setSampleLabel("CreateUser");
		results.sampleStart();
		CreateUserAccountCR cr =  new UserCRG.CreateUserAccountCR(getEmail(), getFirstName(), getLastName(), getEmail(), getPassword(), true, null, resourceDBKeys, null, false, true, getPrincipalMap());
		RequestResponse responseApp = SchedulingClient.issueRequest(cr);
		System.out.println(responseApp);
		
		
		
/*
	
		
	
			 */
		/*	
			
			CreateAppointmentCR cr = new AppointmentCRG.CreateAppointmentCR(
					planKey, resourceDbKey, type, apptTime,
					overBook == 1 ? true : false, memo, location,
					taskKeys.toArray(new TaskDBKey[taskKeys.size()]), price,
					isHasDefaultPrice, procedures, recallDay,
					AppointmentSource.CS_MAIN, annotation);

			System.out.println("Newly Set Start Time: " + cr.getStartTime());

			RequestResponse responseApp = SchedulingClient.issueRequest(cr);

			System.out.println("Create Appointment:" + responseApp.toString());

			System.out.println("Create Appointment Result Code:"
					+ responseApp.getResultCode());

			if (responseApp.getResultCode() == 0) {
				results.setResponseCode("OK");
				results.setResponseMessage("Success");
				results.setSuccessful(true);
				results.sampleEnd();
				

			} else {
				endSamplerForResponseFailure(results, responseApp);

			}

		} catch (Exception ex) {

			System.out.println("Exception during test: " + ex);
			ex.printStackTrace();

			results.setResponseCode("ERR");
			results.setResponseMessage(ex.getMessage());
			results.setSuccessful(false);
			results.sampleEnd();

		}

		System.out.println(whoAmI() + "\trunTest()" + "\tTime:\t"
				+ results.getTime());
		listParameters(context);*/

		return results;
	}




	public static void main(String[] args) {
		CreateUser sample = new CreateUser();
		JavaSamplerContext context = new JavaSamplerContext(sample.getDefaultParameters());
		sample.setupTest(context);
		sample.runTest(context);
	}

}
