package edu.ita.honorio.facerecognitionclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class RecognizeFragment extends CommonFragment {
    public interface OnRecognizeFragmentListener {
        void onRecognizeFragmentClose();
    }

    private static WeakReference<RecognizeResponse> mResponse;

    private OnRecognizeFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public RecognizeFragment() { }

    public static RecognizeFragment newInstance() {
        return new RecognizeFragment();
    }

    public static void setResponse(RecognizeResponse response) {
        mResponse = response != null ? new WeakReference<>(response) : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recognize, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.recognizeOkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseFragment();
            }
        });

        RecognizeResponse response = mResponse == null ? null : mResponse.get();
        if (response != null) {
            ((TextView) view.findViewById(R.id.recognizeNombreTextView)).setText(getString(R.string.recognize_fragment_prefix_nomre) + response.firstName);
            ((TextView) view.findViewById(R.id.recognizePaternoTextView)).setText(getString(R.string.recognize_fragment_prefix_paterno) + response.lastName);
            ((TextView) view.findViewById(R.id.recognizeMaternoTextView)).setText(getString(R.string.recognize_fragment_prefix_materno) + response.secondLastName);
            ((TextView) view.findViewById(R.id.recognizeControlTextView)).setText(getString(R.string.recognize_fragment_prefix_control) + response.control);
            ((TextView) view.findViewById(R.id.recognizeScoreTextView)).setText(getString(R.string.recognize_fragment_prefix_score) + response.score);

            if (response.thumbnail != null) {
                ((ImageView) view.findViewById(R.id.recognizeFotoImageView)).setImageBitmap(response.thumbnail);
            }
            else {
                Utils.showAlert(getActivity(), getString(R.string.common_title_attention), getString(R.string.common_message_error_bitmap_decode));
            }
        }
        else {
            Utils.showAlert(getActivity(), getString(R.string.common_title_error), getString(R.string.recognize_fragment_message_error_response), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    onCloseFragment();
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecognizeFragmentListener) {
            mListener = (OnRecognizeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnRecognizeFragmentListener");
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

    private void onCloseFragment() {
        mListener.onRecognizeFragmentClose();
    }
}
