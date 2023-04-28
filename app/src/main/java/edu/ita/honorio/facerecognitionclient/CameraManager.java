package edu.ita.honorio.facerecognitionclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

@SuppressWarnings("SameReturnValue")
public class CameraManager {
    public interface OnCameraManagerListener {
        void onCameraManagerCancel();
        void onCameraManagerError(String error);
        void onCameraManagerCapture(Bitmap bitmap);
    }

    private OnCameraManagerListener mListener = null;
    private static final CameraManager mInstance = new CameraManager();

    private CameraManager() { }

    private static CameraManager getInstance() {
        return mInstance;
    }

    private void setInnerListener(OnCameraManagerListener listener) {
        mListener = listener;
    }

    private OnCameraManagerListener getInnerListener() {
        return mListener;
    }

    public static void showCamera(FragmentActivity activity, OnCameraManagerListener listener) {
        CameraManager.getInstance().setInnerListener(listener);
        Intent intent = new Intent(activity, CameraActivity.class);
        activity.startActivity(intent);
    }

    public static OnCameraManagerListener getListener() {
        return CameraManager.getInstance().getInnerListener();
    }
}
