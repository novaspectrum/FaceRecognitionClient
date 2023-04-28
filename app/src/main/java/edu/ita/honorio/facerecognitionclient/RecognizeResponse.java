package edu.ita.honorio.facerecognitionclient;

import android.graphics.Bitmap;

import org.json.JSONObject;

@SuppressWarnings("WeakerAccess")
public class RecognizeResponse {
    public int code;
    public String message;
    public float score;
    public String control;
    public String firstName;
    public String lastName;
    public String secondLastName;
    public Bitmap thumbnail;

    public RecognizeResponse() {
        this.code = Enums.FR_UNEXPECTED_ERROR;
        this.message = null;
        this.score = 0.f;
        this.control = null;
        this.firstName = null;
        this.lastName = null;
        this.secondLastName = null;
        this.thumbnail = null;
    }

    public RecognizeResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.score = 0.f;
        this.control = null;
        this.firstName = null;
        this.lastName = null;
        this.secondLastName = null;
        this.thumbnail = null;
    }

    public static RecognizeResponse fromJSON(JSONObject json) throws Exception {
        RecognizeResponse response = new RecognizeResponse();
        response.code = json.getInt("Code");
        response.message = json.getString("Message");
        response.score = Float.valueOf(json.getString("Score"));
        response.control = json.getString("Control");
        response.firstName = json.getString("FirstName");
        response.lastName = json.getString("LastName");
        response.secondLastName = json.getString("SecondLastName");
        response.thumbnail = Utils.decodeBitmap(json.getString("Thumbnail"));
        return response;
    }
}
