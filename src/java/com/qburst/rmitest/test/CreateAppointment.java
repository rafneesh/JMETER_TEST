package com.qburst.rmitest.test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.ibl.client.SchedulingClient;
import com.ibl.client.clinic.ClinicClient;
import com.ibl.client.lookandfeel.EntityNameTableCellRenderer;
import com.ibl.client.resource.ResourceClient;
import com.ibl.remote.appointment.AppointmentCRG;
import com.ibl.remote.appointment.AppointmentCRG.CreateAppointmentCR;
import com.ibl.remote.appointment.AppointmentSource;
import com.ibl.remote.appointment.AppointmentType;
import com.ibl.remote.clinic.Clinic;
import com.ibl.remote.clinic.ClinicLocation;
import com.ibl.remote.clinic.ClinicLocationDBKey;
import com.ibl.remote.patient.Procedure;
import com.ibl.remote.patient.TreatmentPlanDBKey;
import com.ibl.remote.resource.RepeatPattern;
import com.ibl.remote.resource.RepeatPatternWeeklyDay;
import com.ibl.remote.resource.Resource;
import com.ibl.remote.resource.ResourceDBKey;
import com.ibl.remote.resource.ResourceTypeDBKey;
import com.ibl.remote.resource.ScheduleAnnotation;
import com.ibl.remote.user.ClinicUser;
import com.ibl.remote.user.Password;
import com.ibl.remote.util.DayDate;
import com.ibl.remote.util.RequestResponse;
import com.ibl.remote.workflow.TaskDBKey;

/**
 * 
 * @author rafneesh
 *
 */
public class CreateAppointment extends AbstractJavaSamplerClient implements
		Serializable {

	
	// RMI information
	private String hostName;
	private int port;
	private String bindingName;

	// Appo Info
	private int overBook;
	private int date;
	private int hour;
	private int minutes;
	private String memo;
	private int duration;
	private int occurences;
	private int price;

	// UserInfo
	private String username;
	private String password;

	// SerialisedObjLoc
	private String objetcsFileLoc;

	// ObjectsReqdForAppo
	static Password pas = null;
	static TreatmentPlanDBKey planKey = null;
	static AppointmentType type = null;
	static GregorianCalendar apptTime = new GregorianCalendar();
	static Procedure[] procedures = null;
	static Collection<TaskDBKey> taskKeys = null;
	static ClinicLocationDBKey location = null;
	static ResourceDBKey proKey = null;
	static ResourceDBKey otherProKey = null;
	static DayDate recallDay = null;
	static RepeatPattern pattern = null;
	static GregorianCalendar selectStartDay = new GregorianCalendar();
	static DayDate startDay = new DayDate(new GregorianCalendar());
	static GregorianCalendar selectEndDay = new GregorianCalendar();
	static DayDate endDay = null;
	static ScheduleAnnotation annotation = null;
	static ResourceDBKey resourceDbKey = null;
	static boolean isHasDefaultPrice = false;
	
	{ hostName = "10.9.0.173"; port = 10000; bindingName = "cslocalserver";
	 overBook = 1;
	  date = 8; hour = 17; minutes = 30; memo = "test"; duration = 1; occurences = 1; price = 1; objetcsFileLoc =
	  "/home/rafneesh/Downloads/Untitled Folder"; username =
	 "rafneesh@qburst.com"; password = "qburst"; 
	 isHasDefaultPrice = false;

		pas = new Password(password);

		ObjectReader.readAndSetObjectsFromFile(objetcsFileLoc);
		
	
	}

	public CreateAppointment() {
	}

	public void setupTest(JavaSamplerContext context) {

		System.setProperty("release.version", "12.8.2.HEAD");
		System.out.println("Test Set Up Completed, properties set");

	}

	private void setupValues(JavaSamplerContext context) {
		
		hostName = context.getParameter("HostName");
		port = context.getIntParameter("Port");
		bindingName = context.getParameter("BindingName");
		
		objetcsFileLoc = context.getParameter("FileLocation");
		
		overBook = context.getIntParameter("OverBook");
		date = context.getIntParameter("AppointmentDate(DD)");
		
		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		try {
			hour = timeFormat.parse(
					context.getParameter("AppointmentTime(HH/mm)")).getHours();
			minutes = timeFormat.parse(
					context.getParameter("AppointmentTime(HH/mm)"))
					.getMinutes();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Parse Exception for time");
			e.printStackTrace();
		}
		
		memo = context.getParameter("Memo");
		duration = context.getIntParameter("Duration");
		occurences = context.getIntParameter("Occurences");
		price = context.getIntParameter("Price");

		username = context.getParameter("Username");
		password = context.getParameter("Password");
		
		Timestamp tm = new Timestamp(new Date().getTime());
		
		isHasDefaultPrice = false;

		pas = new Password(password);

		ObjectReader.readAndSetObjectsFromFile(objetcsFileLoc);
		
		RequestResponse respo = SchedulingClient.logonRMI(hostName, port,
				bindingName, username, pas, pas, null, false);
		
		System.out.println("Login Result Code:" + respo.getResultCode());

		System.out.println("Set Up Values Completed");
	}

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("HostName", "10.9.0.173");
		params.addArgument("Port", "10000");
		params.addArgument("BindingName", "cslocalserver");

		params.addArgument("FileLocation",
				"/home/rafneesh/Downloads/Untitled Folder");

		params.addArgument("OverBook", "1");

		params.addArgument("AppointmentDate(DD)", "8");
		params.addArgument("AppointmentTime(HH/mm)", "9:00");
		params.addArgument("Memo", "Created from jMeter");
		params.addArgument("Duration", "1");
		params.addArgument("Occurences", "1");
		params.addArgument("Price", "1");

		params.addArgument("Username", "will.trivett@clinicserver.com");
		params.addArgument("Password", "temptemp");
		return params;
	}

	public SampleResult runTest(JavaSamplerContext context) {

		System.out.println("Running Test ..");
		SampleResult results;

		// Comment when running through main
		//setupValues(context);

		results = new SampleResult();

		results.setSampleLabel("CreateAppointment");

		results.sampleStart();
		
		RequestResponse respo = SchedulingClient.logonRMI(hostName, port,
				bindingName, username, pas, pas, null, false); 
		
		final ClinicLocation cliniclocation = SchedulingClient.getScheduleDisplayProfile().getCurrentLocation();
		ClinicUser user = SchedulingClient.getUser();
		//Clinic clinic = ClinicClient.getClinicLocationKeys();
		
		System.out.println("==>"+cliniclocation);

		try {

			if (selectEndDay != null) {
				endDay = new DayDate(selectEndDay);
			} else {
				endDay = new DayDate(pattern.getLastDateByOccurrences(
						startDay.toCalendar(), occurences));
				selectEndDay = endDay.toCalendar();
			}
			boolean singleAppointment = false;

			if (pattern instanceof RepeatPatternWeeklyDay) {
				if (selectEndDay != null
						&& ((startDay.getDay() == endDay.getDay())
								&& (startDay.getMonth() == endDay.getMonth()) && (startDay
								.getYear() == endDay.getYear()))) {
					singleAppointment = true;
				} else if (occurences == 1) {
					singleAppointment = true;
				}
			}

/*			if (respo.getResultCode() != 0) {
				results.setResponseCode("" + respo.getResultCode());
				results.setResponseMessage("Login Failed");
				results.setSuccessful(false);
				results.sampleEnd();
				
				return results;
			}
*/
			System.out.println("SingleAppointment?:" + singleAppointment);

			if (singleAppointment) {
				// single appointment
				try {
					RequestResponse response = prepareAppointmentType(type,
							proKey, otherProKey, duration);

					System.out.println("PrepareAppointmentType Result Code:"
							+ response.getResultCode());

					if (!response.isOkay()) {
						// ExceptionHandler.showMessage(CreateAppointmentDialog.this,
						// response);
						return null;
					}
					type = (AppointmentType) response.getDetail();

				} catch (IllegalArgumentException e) {
					// ExceptionHandler.showMessage(CreateAppointmentDialog.this,
					// e.getMessage());
					return null;
				}

			}

			apptTime.set(GregorianCalendar.DATE, date);
			apptTime.set(GregorianCalendar.HOUR_OF_DAY, hour);
			apptTime.set(GregorianCalendar.MINUTE, minutes);
			apptTime.set(GregorianCalendar.SECOND, 00);

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
				results.setResponseCode("" + responseApp.getResultCode());
				results.setResponseMessage("Failed");
				results.setSuccessful(false);
				results.sampleEnd();

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
		listParameters(context);

		return results;
	}

	public void teardownTest(JavaSamplerContext context) {
		System.out.println(whoAmI() + "\tteardownTest()");
		listParameters(context);
	}

	private void listParameters(JavaSamplerContext context) {
		String name;
		for (Iterator argsIt = context.getParameterNamesIterator(); argsIt
				.hasNext(); System.out.println(name + "="
				+ context.getParameter(name))) {
			name = (String) argsIt.next();
		}
	}

	private String whoAmI() {
		StringBuffer sb = new StringBuffer();
		sb.append(Thread.currentThread().toString());
		sb.append("@");
		sb.append(Integer.toHexString(hashCode()));
		return sb.toString();
	}

	public void myTest() throws Exception {
		RmiPool rmiPool = RmiPool.getInstance();
		RmiConnection rmiConnection = null;
		try {
			rmiConnection = rmiPool.getConnection();

			// NOTE Write your RMI test here

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (rmiConnection != null) {
				rmiPool.releaseConnection(rmiConnection);
			}
		}

	}

	private RequestResponse prepareAppointmentType(AppointmentType type,
			ResourceDBKey proKey, ResourceDBKey otherProKey, int duration) {
		AppointmentType typeCopy = (AppointmentType) type.clone();

		if (proKey != null) {
			typeCopy = customizeAppointmentType(typeCopy, proKey);
		}

		if (otherProKey != null) {
			typeCopy = customizeAppointmentType(typeCopy, otherProKey);
		}

		typeCopy.setDuration(duration);
		return new RequestResponse(typeCopy);
	}

	private AppointmentType customizeAppointmentType(AppointmentType type,
			ResourceDBKey resourceKey) throws IllegalArgumentException {
		if (type.requires(resourceKey)) {
			return type;
		}

		Resource resource = ResourceClient.getLocalResource(resourceKey);
		ResourceTypeDBKey resourceTypeKey = resource.getTypeKey();

		if (type.requires(resourceTypeKey)) {
			ArrayList<ResourceTypeDBKey> requiredTypeKeys = new ArrayList<ResourceTypeDBKey>(
					Arrays.asList(type.getRequiredResourceTypeKeys()));
			ArrayList<ResourceDBKey> requiredResourceKeys = new ArrayList<ResourceDBKey>(
					Arrays.asList(type.getRequiredResourceKeys()));

			for (Iterator<ResourceTypeDBKey> it = requiredTypeKeys.iterator(); it
					.hasNext();) {
				ResourceTypeDBKey next = (ResourceTypeDBKey) it.next();

				if (resourceTypeKey.equals(next)) {
					// remove the resource type from the required array
					requiredTypeKeys.remove(next);
					ResourceTypeDBKey[] typeKeys = (ResourceTypeDBKey[]) requiredTypeKeys
							.toArray(new ResourceTypeDBKey[requiredTypeKeys
									.size()]);
					type.setRequiredResourceTypeKeys(typeKeys);

					// add the resource to the required array
					requiredResourceKeys.add(resourceKey);
					ResourceDBKey[] resourceKeys = (ResourceDBKey[]) requiredResourceKeys
							.toArray(new ResourceDBKey[requiredResourceKeys
									.size()]);
					type.setRequiredResourceKeys(resourceKeys);
					return type;
				}
			}
		}
		throw new IllegalArgumentException(
				EntityNameTableCellRenderer.renderName(
						resource.getEntityName(), false, false)
						+ " does not provide this type of appointment");
	}

	public static void main(String[] args) {

		CreateAppointment sample = new CreateAppointment();

		JavaSamplerContext context = new JavaSamplerContext(new Arguments());
		sample.setupTest(context);
		sample.runTest(context);
	}

}
