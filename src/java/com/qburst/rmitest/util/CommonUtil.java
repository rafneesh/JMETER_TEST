package com.qburst.rmitest.util;

import com.ibl.remote.user.Password;

public class CommonUtil {

	public static Password getPassword(String password){
		return new Password(password);
	}
	
}
