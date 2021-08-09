package com.sdet.automationFramework.services.SampleService.model.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Dist
{

	private String lower;
	private String upper;

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