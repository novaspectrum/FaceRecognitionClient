package edu.ita.honorio.facerecognitionclient;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends CommonActivity implements MainFragment.OnMainFragmentListener, EnrollFragment.OnEnrollFragmentListener, RecognizeFragment.OnRecognizeFragmentListener, SettingsFragment.OnSettingsFragmentListener {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMainFragment();
    }

    @Override
    public void onMainFragmentEnroll() {
        showEnrollFragment();
    }

    @Override
    public void onMainFragmentRecognize() {
        onRecognizeRequest();
    }

    @Override
    public void onMainFragmentSettings() {
        showSettingsFragment();
    }

    @Override
    public void onEnrollFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onRecognizeFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onSettingsFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onBackPressed() {
        if (tellFragments()) {
            super.onBackPressed();
        }
    }

    private boolean tellFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0) {
            return true;
        }

        for (Fragment f : fragments){
            if (f != null && f instanceof CommonFragment) {
                ((CommonFragment) f).onBackPressed();
            }
        }
        return false;
    }

    private void showMainFragment() {
        showFragment(MainFragment.newInstance());
    }

    private void showEnrollFragment() {
        showFragment(EnrollFragment.newInstance());
    }

    private void showRecognizeFragment(RecognizeResponse response) {
        RecognizeFragment.setResponse(response);
        showFragment(RecognizeFragment.newInstance());
    }

    private void showSettingsFragment() {
        showFragment(SettingsFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
    }

    private void onRecognizeRequest() {
        CameraManager.showCamera(this, new CameraManager.OnCameraManagerListener() {
            @Override
            public void onCameraManagerCancel() {
                Log.w(TAG, "onCameraManagerCancel");
            }

            @Override
            public void onCameraManagerError(String error) {
                Utils.showAlert(MainActivity.this, getString(R.string.common_title_error_camera), error);
            }

            @Override
            public void onCameraManagerCapture(Bitmap bitmap) {
                RecognizeRequest request = new RecognizeRequest(bitmap);
                showProgressDialog(getString(R.string.main_activity_title_recognize), getString(R.string.common_message_wait));
                FaceRecognition.recognize(MainActivity.this, request, new FaceRecognition.OnRecognizeResponseListener() {
                    @Override
                    public void onResponse(RecognizeResponse response) {
                        hideProgressDialog();
                        onRecognizeResponse(response);
                    }
                });
            }
        });
    }

    private void onRecognizeResponse(RecognizeResponse response) {
        if (response.code == Enums.FR_OK) {
            showRecognizeFragment(response);
        }
        else {
            String message = response.message;
            if (response.code == Enums.FR_USER_NOT_FOUND) {
                message += ("\nScore: " + response.score);
            }
            Utils.showAlert(this, getString(R.string.common_title_error), message);
        }
    }
}
