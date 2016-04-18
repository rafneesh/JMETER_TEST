package com.qburst.rmitest.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;

import com.ibl.remote.appointment.AppointmentType;
import com.ibl.remote.clinic.ClinicLocationDBKey;
import com.ibl.remote.patient.Procedure;
import com.ibl.remote.patient.TreatmentPlanDBKey;
import com.ibl.remote.resource.RepeatPattern;
import com.ibl.remote.resource.ResourceDBKey;
import com.ibl.remote.resource.ScheduleAnnotation;
import com.ibl.remote.util.DayDate;
import com.ibl.remote.workflow.TaskDBKey;

public class ObjectReader {
	
	
	public static void readAndSetObjectsFromFile(String objetcsFileLoc) {
		
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/planKey.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.planKey  = (TreatmentPlanDBKey) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/type.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.type = (AppointmentType) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/procedures.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.procedures = (Procedure[]) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/taskKeys.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.taskKeys = (Collection<TaskDBKey>) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/location.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.location = (ClinicLocationDBKey) ois.readObject();
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/proKey.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.proKey = (ResourceDBKey) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/otherProKey.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.otherProKey = (ResourceDBKey) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/recallDay.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.recallDay = (DayDate) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/pattern.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.pattern = (RepeatPattern) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/annotation.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.annotation = (ScheduleAnnotation) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileInputStream fin = new FileInputStream(objetcsFileLoc+"/resourceDbKey.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CreateAppointment.resourceDbKey = (ResourceDBKey) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Object reading from Files completed");
	}

}
