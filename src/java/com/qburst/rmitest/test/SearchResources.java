package com.qburst.rmitest.test;

import com.ibl.client.SchedulingClient;
import com.ibl.remote.clinic.ClinicLocation;
import com.ibl.remote.resource.Resource;
import com.ibl.remote.resource.ResourceCRG;
import com.ibl.remote.util.Address;
import com.ibl.remote.util.ClientRequest;
import com.ibl.remote.util.EntityName;
import com.ibl.remote.util.RequestResponse;

public class SearchResources{

	public static RequestResponse searchResources() {
		final ClinicLocation cliniclocation = SchedulingClient.getScheduleDisplayProfile().getCurrentLocation();
		
		Resource mResourceWithValues = new Resource();
		mResourceWithValues.setAddress(new Address());
		EntityName entityName = new EntityName();
		mResourceWithValues.setEntityName(entityName);		
		mResourceWithValues.getEntityName().setSimple(null);	
		mResourceWithValues.getAddress().setProvince("");
		mResourceWithValues.setTypeKey(null);		
	
		Resource mResourceWithFilters = new Resource();
		
		
		mResourceWithFilters.setPerson(true);
		mResourceWithValues.setPerson(true);
		ClientRequest request = new ResourceCRG.SearchResourcesCR(mResourceWithValues, mResourceWithFilters);
		RequestResponse requestResponse = SchedulingClient.issueRequest(request);
		return requestResponse;
	}
	
	
}
