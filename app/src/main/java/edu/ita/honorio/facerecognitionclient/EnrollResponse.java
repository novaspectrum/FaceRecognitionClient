package edu.ita.honorio.facerecognitionclient;

import org.json.JSONObject;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class EnrollResponse {
    public int code;
    public String message;

    public EnrollResponse() {
        this.code = Enums.FR_UNEXPECTED_ERROR;
        this.message = null;
    }

    public EnrollResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static EnrollResponse fromJSON(JSONObject json) throws Exception {
        EnrollResponse response = new EnrollResponse();
        response.code = json.getInt("Code");
        response.message = json.getString("Message");
        return response;
    }
}
