package edu.ita.honorio.facerecognitionclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;

import static android.content.Context.MODE_PRIVATE;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class Utils {
    private static final String TAG = Utils.class.getName();

    public static boolean validateText(String text, TextInputLayout inputLayout, String error) {
        if (text.length() > 0) {
            inputLayout.setError(null);
            return true;
        }

        inputLayout.setError(error);
        return false;
    }

    public static boolean validateIP(String text, TextInputLayout inputLayout, String error) {
        Matcher matcher = Patterns.IP_ADDRESS.matcher(text);
        if (matcher.matches()) {
            inputLayout.setError(null);
            return true;
        }

        inputLayout.setError(error);
        return false;
    }

    public static ServerAddress getServerAddress(Context context) {
        try
        {
            SharedPreferences prefs = context.getSharedPreferences(Enums.FR_PREFERENCES_KEY, MODE_PRIVATE);
            String ip = prefs.getString(Enums.FR_SERVER_IP_KEY, Enums.FR_SERVER_IP_VALUE);
            String port = prefs.getString(Enums.FR_SERVER_PORT_KEY, Enums.FR_SERVER_PORT_VALUE);
            return new ServerAddress(ip, port);
        }
        catch (ClassCastException ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            return new ServerAddress(Enums.FR_SERVER_IP_VALUE, Enums.FR_SERVER_PORT_VALUE);
        }
    }

    public static void setServerAddress(Context context, String ip, String port) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Enums.FR_PREFERENCES_KEY, MODE_PRIVATE).edit();
        editor.putString(Enums.FR_SERVER_IP_KEY, ip);
        editor.putString(Enums.FR_SERVER_PORT_KEY, port);
        editor.apply();
    }

    public static String encodeBitmap(Bitmap bitmap) throws Exception {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)) {
                return null;
            }
            byte[] bytes = baos.toByteArray();
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (AssertionError | NullPointerException | IllegalArgumentException ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            throw new Exception(Enums.FR_ENCODE_BITMAP_KEY);
        }
    }

    public static Bitmap decodeBitmap(String base64) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            return null;
        }
    }

    public static void showAlert(Context context, String title, String message) {
        showAlert(context, title, message, null);
    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        if (listener == null) {
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.common_btn_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        else {
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.common_btn_ok), listener);
        }

        alertDialog.show();
    }
}
