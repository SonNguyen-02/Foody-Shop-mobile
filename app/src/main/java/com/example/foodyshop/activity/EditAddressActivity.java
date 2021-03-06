package com.example.foodyshop.activity;

import static com.example.foodyshop.config.Const.KEY_ADDRESS;
import static com.example.foodyshop.config.Const.KEY_NAME;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyshop.R;
import com.example.foodyshop.dialog.ConfirmDialog;
import com.example.foodyshop.dialog.ToastCustom;
import com.example.foodyshop.helper.Helper;

public class EditAddressActivity extends AppCompatActivity {

    private String address;
    private EditText edtAddress;
    private long lastClickSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        ImageView imgBack = findViewById(R.id.img_back);
        TextView tvSave = findViewById(R.id.tv_save);
        edtAddress = findViewById(R.id.edt_address);
        address = getIntent().getStringExtra(KEY_ADDRESS);
        if (address != null) {
            edtAddress.setText(address);
            edtAddress.requestFocus(address.length());
            Helper.showKeyboard(this);
        }
        tvSave.setOnClickListener(v -> {
            if (System.currentTimeMillis() - lastClickSave < 1000) {
                return;
            }
            lastClickSave = System.currentTimeMillis();
            String newAddress = edtAddress.getText().toString().trim();
            if (newAddress.isEmpty()) {
                ToastCustom.notice(getApplicationContext(), "Địa chỉ không được bỏ trống!", ToastCustom.WARNING).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(KEY_ADDRESS, newAddress);
            setResult(RESULT_OK, intent);
            if (isKeyboardOpen(edtAddress)) {
                Helper.hideKeyboard(getApplicationContext(), edtAddress);
            }
            finish();
        });
        imgBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Helper.hideKeyboard(this, edtAddress);
        String address = edtAddress.getText().toString().trim();
        if (!address.equals(this.address)) {
            ConfirmDialog.newInstance(this, "Hủy thay đổi?", confirmDialog -> {
                confirmDialog.dismiss();
                super.onBackPressed();
            }).show();
        } else {
            super.onBackPressed();

        }
    }

    private boolean isKeyboardOpen(@NonNull View view) {
        Rect visibleBounds = new Rect();
        view.getRootView().getWindowVisibleDisplayFrame(visibleBounds);
        int heightDiff = view.getRootView().getHeight() - visibleBounds.height();
        int marginOfError = Math.round(convertDpToPx(50F));
        return heightDiff > marginOfError;
    }

    private float convertDpToPx(Float dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}