package health.singular.viewer3cr.android.sdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FrontEndPayload {

    @JsonProperty("Interface")
    private FrontEndInterfaces frontEndInterface;

    @JsonProperty("Action")
    private String action;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Version")
    private String version;

    // Default constructor for Jackson
    public FrontEndPayload() {
    }

    // Constructor
    public FrontEndPayload(FrontEndInterfaces frontEndInterface, String action, String message, String version) {
        this.frontEndInterface = frontEndInterface;
        this.action = action;
        this.message = message;
        this.version = version;
    }

    // Getters and Setters
    public FrontEndInterfaces getFrontEndInterface() {
        return frontEndInterface;
    }

    public void setFrontEndInterface(FrontEndInterfaces frontEndInterface) {
        this.frontEndInterface = frontEndInterface;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}