package edu.ita.honorio.facerecognitionclient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends CommonFragment implements View.OnClickListener {
    public interface OnMainFragmentListener {
        void onMainFragmentEnroll();
        void onMainFragmentRecognize();
        void onMainFragmentSettings();
    }

    private OnMainFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public MainFragment() { }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.mainEnrollButton).setOnClickListener(this);
        view.findViewById(R.id.mainRecognizeButton).setOnClickListener(this);
        view.findViewById(R.id.mainSettingsButton).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragmentListener) {
            mListener = (OnMainFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (!validateLastClick()) return;

        switch (v.getId()) {
            case R.id.mainEnrollButton:
                mListener.onMainFragmentEnroll();
                break;

            case R.id.mainRecognizeButton:
                mListener.onMainFragmentRecognize();
                break;

            case R.id.mainSettingsButton:
                mListener.onMainFragmentSettings();
                break;
        }
    }
}
