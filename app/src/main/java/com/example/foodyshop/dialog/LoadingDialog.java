package com.example.foodyshop.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.foodyshop.R;

public class LoadingDialog {

    private final Activity activity;
    private final View view;
    private AlertDialog dialog;
    private String message;
    private TextView tvMessLoad;

    public LoadingDialog(Activity activity) {
        this(activity, null);
    }

    public LoadingDialog(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
    }

    public void setMessage(String message) {
        this.message = message;
        if (tvMessLoad == null) {
            tvMessLoad = view.findViewById(R.id.tv_message);
        }
        tvMessLoad.setText(message);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (message != null && !message.trim().isEmpty()) {
            tvMessLoad = view.findViewById(R.id.tv_message);
            tvMessLoad.setText(message);
        }
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
