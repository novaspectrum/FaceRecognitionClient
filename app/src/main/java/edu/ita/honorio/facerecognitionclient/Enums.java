package edu.ita.honorio.facerecognitionclient;

@SuppressWarnings("unused")
public class Enums {
    public static final int FR_OK                     = 0;
    public static final int FR_DATABASE_ERROR         = -1;
    public static final int FR_EMPTY_DATABASE         = -2;
    public static final int FR_USER_EXISTS            = -3;
    public static final int FR_USER_NOT_FOUND         = -4;
    public static final int FR_EMPTY_IMAGE            = -5;
    public static final int FR_FACE_NOT_FOUND         = -6;
    public static final int FR_CANNOT_SAVE_TEMPLATE   = -7;
    public static final int FR_CANNOT_LOAD_TEMPLATE   = -8;
    public static final int FR_CANNOT_SAVE_THUMBNAIL  = -9;
    public static final int FR_CANNOT_LOAD_LIBRARY    = -10;
    public static final int FR_CANNOT_EXECUTE_LIBRARY = -11;
    public static final int FR_UNEXPECTED_ERROR       = -12;

    public static final int FR_SERVER_TIMEOUT                    = 5000; // Milisegundos
    public static final String FR_ENCODE_BITMAP_KEY              = "Error al codificar la imagen";
    public static final String FR_PREFERENCES_KEY                = "PREFERENCES";
    public static final String FR_SERVER_IP_KEY                  = "IP";
    public static final String FR_SERVER_IP_VALUE                = "127.0.0.1";
    public static final String FR_SERVER_PORT_KEY                = "PORT";
    public static final String FR_SERVER_PORT_VALUE              = "8080";
    public static final String FR_SERVER_URL_SUFFIX_KEY          = "/FaceRecognitionServer";
    public static final String FR_SERVER_ENROLL_METHOD_KEY       = "/enroll";
    public static final String FR_SERVER_RECOGNIZE_METHOD_KEY    = "/recognize";
    public static final String FR_SERVER_ENROLL_SUCCESS_KEY      = "Usuario registrado correctamente";
}
