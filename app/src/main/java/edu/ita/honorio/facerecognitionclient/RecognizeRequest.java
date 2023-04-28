package edu.ita.honorio.facerecognitionclient;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("WeakerAccess")
public class RecognizeRequest {
    public Bitmap image;

    public RecognizeRequest(Bitmap image) {
        this.image = image;
    }

    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Image", Utils.encodeBitmap(image));
        return jsonObject;
    }
}
