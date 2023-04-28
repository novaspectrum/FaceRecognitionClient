package edu.ita.honorio.facerecognitionclient;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class EnrollFragment extends CommonFragment implements View.OnClickListener {
    public interface OnEnrollFragmentListener {
        void onEnrollFragmentClose();
    }

    private static final String TAG = EnrollFragment.class.getName();

    private Bitmap mBitmap = null;
    private TextInputLayout mNombreTextInputLayout;
    private TextInputLayout mPaternoTextInputLayout;
    private TextInputLayout mMaternoTextInputLayout;
    private TextInputLayout mControlTextInputLayout;
    private ImageView mFotoImageView;
    private OnEnrollFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public EnrollFragment() { }

    public static EnrollFragment newInstance() {
        return new EnrollFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enroll, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.enrollOkButton).setOnClickListener(this);
        view.findViewById(R.id.enrollCancelButton).setOnClickListener(this);
        mNombreTextInputLayout = view.findViewById(R.id.enrollNombreTextInputLayout);
        mPaternoTextInputLayout = view.findViewById(R.id.enrollPaternoTextInputLayout);
        mMaternoTextInputLayout = view.findViewById(R.id.enrollMaternoTextInputLayout);
        mControlTextInputLayout = view.findViewById(R.id.enrollControlTextInputLayout);
        mFotoImageView = view.findViewById(R.id.enrollFotoImageView);
        mFotoImageView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEnrollFragmentListener) {
            mListener = (OnEnrollFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnEnrollFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBackPressed() {
        onCloseFragment();
    }

    @Override
    public void onClick(View v) {
        if (!validateLastClick()) return;

        switch (v.getId()) {
            case R.id.enrollOkButton:
                onEnrollRequest();
                break;

            case R.id.enrollCancelButton:
                onCloseFragment();
                break;

            case R.id.enrollFotoImageView:
                onShowCamera();
                break;
        }
    }

    private void onCloseFragment() {
        mListener.onEnrollFragmentClose();
    }

    private void onShowCamera() {
        CameraManager.showCamera(getActivity(), new CameraManager.OnCameraManagerListener() {
            @Override
            public void onCameraManagerCancel() {
                Log.w(TAG, "onCameraManagerCancel");
            }

            @Override
            public void onCameraManagerError(String error) {
                Utils.showAlert(getActivity(), getString(R.string.common_title_error_camera), error);
            }

            @Override
            public void onCameraManagerCapture(Bitmap bitmap) {
                if (mBitmap != null) {
                    mBitmap.recycle();
                    mBitmap = null;
                }

                mBitmap = bitmap;
                mFotoImageView.setImageDrawable(new BitmapDrawable(getResources(), mBitmap));
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void onEnrollRequest() {
        if (mBitmap == null) {
            Utils.showAlert(getActivity(), getString(R.string.common_title_attention), getString(R.string.enroll_fragment_error_foto));
            return;
        }

        String firstName = mNombreTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(firstName, mNombreTextInputLayout, getString(R.string.enroll_fragment_error_nombre))) {
            return;
        }

        String lastName = mPaternoTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(lastName, mPaternoTextInputLayout, getString(R.string.enroll_fragment_error_apellido))) {
            return;
        }

        String secondLastName = mMaternoTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(secondLastName, mMaternoTextInputLayout, getString(R.string.enroll_fragment_error_apellido))) {
            return;
        }

        String control = mControlTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(control, mControlTextInputLayout, getString(R.string.enroll_fragment_error_control))) {
            return;
        }

        EnrollRequest request = new EnrollRequest();
        request.firstName = firstName;
        request.lastName = lastName;
        request.secondLastName = secondLastName;
        request.control = control;
        request.image = mBitmap;

        showProgressDialog(getString(R.string.enroll_fragment_title_enroll), getString(R.string.common_message_wait));
        FaceRecognition.enroll(getActivity(), request, new FaceRecognition.OnEnrollResponseListener() {
            @Override
            public void onResponse(EnrollResponse response) {
                hideProgressDialog();
                onEnrollResponse(response);
            }
        });
    }

    private void onEnrollResponse(EnrollResponse response) {
        if (response.code == Enums.FR_OK) {
            Utils.showAlert(getActivity(), getString(R.string.common_title_attention), Enums.FR_SERVER_ENROLL_SUCCESS_KEY, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    onCloseFragment();
                }
            });
        }
        else {
            Utils.showAlert(getActivity(), getString(R.string.common_title_error), response.message);
        }
    }
}
