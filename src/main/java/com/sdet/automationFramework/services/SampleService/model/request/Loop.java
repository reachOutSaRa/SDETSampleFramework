package com.sdet.automationFramework.services.SampleService.model.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString
public class Loop {

    private String market;
    private String partner;
    private String explnMsg;
    private String explnCode;
    private String statusMsg;
    private String timestamp;
    private String scheduleId;
    private String statusCode;
    private String feedbackSignals;
    private String noVisitorPenalty;

    @JsonProperty("mabConfig")
    private ArrayList<Configs> mabConfigs;

    @JsonProperty("distThreshold")
    private Dist distThreshold;

    @Builder.Default
    private Map<String, Object> additionalParam = new HashMap<>();

    /**
     * Empty MGMPZNRequest Constructor
     */
    public Loop(){

    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalParam() {
        return additionalParam;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalParam.put(name, value);
    }

}
