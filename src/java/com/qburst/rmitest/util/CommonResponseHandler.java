package com.qburst.rmitest.util;

import org.apache.jmeter.samplers.SampleResult;

import com.ibl.remote.util.RequestResponse;

public class CommonResponseHandler {

	public static void endSamplerForResponseFailure(SampleResult results, RequestResponse requestResponse) {
		if (requestResponse.getResultCode() != 0){
			results.setResponseCode("" + requestResponse.getResultCode());
			results.setResponseMessage("Failed");
			results.setSuccessful(false);
			results.sampleEnd();
		}
	}
	
	public static void endSamplerForResponseSuccess(SampleResult results, RequestResponse requestResponse) {
		if (requestResponse.getResultCode() == 0){
			results.setResponseCode("" + requestResponse.getResultCode());
			results.setResponseMessage("Success");
			results.setSuccessful(true);
			results.sampleEnd();
		}
	}

	public static SampleResult endSampler(SampleResult results, RequestResponse requestResponse){
		endSamplerForResponseFailure(results, requestResponse);
		endSamplerForResponseSuccess(results, requestResponse);
		return results;
	}
}
