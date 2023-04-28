package edu.ita.honorio.facerecognitionclient;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

public class CameraActivity extends CommonActivity implements View.OnClickListener {
    private static final String TAG = CameraActivity.class.getName();
    private static final int MAX_WIDTH = 1280;
    private static final int MAX_HEIGHT = 720;

    private ImageView mCloseButton;
    private ImageView mCaptureButton;
    private ImageView mSwitchButton;
    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCloseButton = findViewById(R.id.cameraCloseButton);
        mCaptureButton = findViewById(R.id.cameraCaptureButton);
        mSwitchButton = findViewById(R.id.cameraSwitchButton);

        mCloseButton.setOnClickListener(this);
        mCaptureButton.setOnClickListener(this);
        mSwitchButton.setOnClickListener(this);

        mCameraView = findViewById(R.id.cameraView);
        mCameraView.setLifecycleOwner(this);
        mCameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                Log.w(TAG, "onCameraOpened");
            }

            @Override
            public void onCameraClosed() {
                Log.w(TAG, "onCameraClosed");
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                Log.e(TAG, "onCameraError", exception);
                CameraManager.getListener().onCameraManagerError(exception.toString());
                finish();
            }

            @Override
            public void onPictureTaken(byte[] picture) {
                CameraUtils.decodeBitmap(picture, MAX_WIDTH, MAX_HEIGHT, new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        if (bitmap == null) {
                            CameraManager.getListener().onCameraManagerError(getString(R.string.camera_activity_message_error_capture));
                        } else {
                            CameraManager.getListener().onCameraManagerCapture(bitmap);
                        }
                        finish();
                    }
                });
            }

            @Override
            public void onOrientationChanged(int orientation) {
                Log.w(TAG, "onOrientationChanged: " + orientation);
            }
        });
    }

    @Override
    public void onBackPressed() { }

    @Override
    public void onClick(View v) {
        if (!validateLastClick()) return;

        switch (v.getId()) {
            case R.id.cameraCloseButton:
                onClose();
                break;

            case R.id.cameraCaptureButton:
                onCapture();
                break;

            case R.id.cameraSwitchButton:
                onSwitch();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean valid = true;
        for (int grantResult : grantResults) {
            valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
        }

        if (valid) {
            if (!mCameraView.isStarted()) {
                mCameraView.start();
            }
        }
        else {
            CameraManager.getListener().onCameraManagerError(getString(R.string.camera_activity_message_error_permissions));
            finish();
        }
    }

    private void onClose() {
        enableButtons(false);
        CameraManager.getListener().onCameraManagerCancel();
        finish();
    }

    private void onCapture() {
        enableButtons(false);
        mCameraView.captureSnapshot();
    }

    private void onSwitch() {
        enableButtons(false);
        mCameraView.toggleFacing();
        enableButtons(true);
    }

    private void enableButtons(boolean enabled) {
        mCloseButton.setEnabled(enabled);
        mCaptureButton.setEnabled(enabled);
        mSwitchButton.setEnabled(enabled);
    }
}
