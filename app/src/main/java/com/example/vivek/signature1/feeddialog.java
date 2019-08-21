package com.example.vivek.signature1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.vivek.signature1.R;
import com.example.vivek.signature1.review;

public class feeddialog  extends DialogFragment {
    private boolean wantToCloseDialog = false;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.feedbackdilog,null);
        ImageView feedbackview = view.findViewById(R.id.feedbackview);
        final AlertDialog.Builder builder1 = builder.setView(view);



        return builder.create();

    }
}
