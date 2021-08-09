package com.sdet.automationFramework.services.SampleService.model.request;

import com.axp.automationFramework.utilities.JSONData;
import com.axp.automationFramework.utilities.JSONDataManager;
import com.axp.automationFramework.utilities.excelManager.ExcelParserMain;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString
public class RequestInstance implements JSONData {

    private String dataId;
    private String dataDescription;

    @JsonProperty("mabRequest")
    private Request mabRequest;

    @Builder.Default
    private Map<String, Object> additionalParam = new HashMap<>();

    /**
     * Empty MGMPZNRequest Constructor
     */
    public RequestInstance(){

    }

    /**
     * MGMPZNRequest Constructor
     * @param requestDescription
     * @throws Exception
     */
    public RequestInstance(String requestDescription) throws Exception{

        ExcelParserMain.main(new String[]{
                "src/main/resources/testDataSheet/MABAllocationAlgorithm/" +
                        "MABAllocationAlgorithmRequest/MABAllocationAlgorithmRequest.xlsx",
                "data", "false"});
        final String requestResourcePath = "testDataSheet/MABAllocationAlgorithm" +
                "/MABAllocationAlgorithmRequest/MABAllocationAlgorithmRequest.json";

        RequestInstance instance = (RequestInstance)
                JSONDataManager.getInstance(
                        RequestInstance.class,
                        requestResourcePath,
                        requestDescription);

        assignProperties(instance);
    }

    /**
     * Assigns properties from the input JSON
     * @param request
     */
    private void assignProperties(RequestInstance request) {
        this.dataId = request.getDataId();
        this.dataDescription = request.getDataDescription();
        this.mabRequest = request.getMabRequest();
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
