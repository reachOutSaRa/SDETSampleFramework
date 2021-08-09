package com.sdet.automationFramework.services.SampleService.model.response;

import com.axp.automationFramework.services.MABAllocationAlgorithm.model.request.Loop;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

	@JsonProperty("mabFeedbackLoop")
	private Loop mabFeedbackLoop;

	@Builder.Default
	private Map<String, Object> additionalParam = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalParam() {
		return additionalParam;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalParam.put(name, value);
	}

}