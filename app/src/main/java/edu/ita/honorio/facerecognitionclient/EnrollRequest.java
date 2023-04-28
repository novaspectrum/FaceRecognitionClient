package edu.ita.honorio.facerecognitionclient;

import android.graphics.Bitmap;

import org.json.JSONObject;

@SuppressWarnings("WeakerAccess")
public class EnrollRequest {
    public String control;
    public String firstName;
    public String lastName;
    public String secondLastName;
    public Bitmap image;

    public EnrollRequest() {
        this.control = null;
        this.firstName = null;
        this.lastName = null;
        this.secondLastName = null;
        this.image = null;
    }

    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Control", control);
        jsonObject.put("FirstName", firstName);
        jsonObject.put("LastName", lastName);
        jsonObject.put("SecondLastName", secondLastName);
        jsonObject.put("Image", Utils.encodeBitmap(image));
        return jsonObject;
    }
}
