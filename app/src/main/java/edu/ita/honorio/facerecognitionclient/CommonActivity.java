package edu.ita.honorio.facerecognitionclient;

import android.app.ProgressDialog;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

@SuppressWarnings("WeakerAccess")
public abstract class CommonActivity extends AppCompatActivity {
    private long mLastClickTime = 0;
    private ProgressDialog mProgressDialog = null;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean validateLastClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) return false;
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    protected void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
