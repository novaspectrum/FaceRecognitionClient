package edu.ita.honorio.facerecognitionclient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("ConstantConditions")
public class SettingsFragment extends CommonFragment implements View.OnClickListener {
    public interface OnSettingsFragmentListener {
        void onSettingsFragmentClose();
    }

    private OnSettingsFragmentListener mListener;
    private TextInputLayout mIPTextInputLayout;
    private TextInputLayout mPortTextInputLayout;

    public SettingsFragment() { }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.settingsOkButton).setOnClickListener(this);
        view.findViewById(R.id.settingsCancelButton).setOnClickListener(this);

        mIPTextInputLayout = view.findViewById(R.id.settingsIPTextInputLayout);
        mPortTextInputLayout = view.findViewById(R.id.settingsPortTextInputLayout);

        ServerAddress address = Utils.getServerAddress(getActivity());
        mIPTextInputLayout.getEditText().setText(address.ip);
        mPortTextInputLayout.getEditText().setText(address.port);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsFragmentListener) {
            mListener = (OnSettingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSettingsFragmentListener");
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
            case R.id.settingsOkButton:
                onSaveAddress();
                break;

            case R.id.settingsCancelButton:
                onCloseFragment();
                break;
        }
    }

    private void onCloseFragment() {
        mListener.onSettingsFragmentClose();
    }

    private void onSaveAddress() {
        String ip = mIPTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateIP(ip, mIPTextInputLayout, getString(R.string.settings_fragment_error_ip))) {
            return;
        }

        String port = mPortTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(port, mPortTextInputLayout, getString(R.string.settings_fragment_error_port))) {
            return;
        }

        Utils.setServerAddress(getActivity(), ip, port);
        onCloseFragment();
    }
}
