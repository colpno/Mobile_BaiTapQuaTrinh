package com.example.modernartui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;

import androidx.fragment.app.DialogFragment;

public class DialogFrag extends DialogFragment {
    String message = "Xuân Đức - 3120410138" + "<br>" +
            "Lê Tùng Lâm - 3118410216" + "<br>" +
            "Nguyễn Tiến Duẩn - 3121410106" + "<br>" +
            "Tạ Gia Vinh - 3119410492" + "<br>" +
            "Lê Bửu Trí - 3121410521";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Thông tin về nhóm").setMessage(Html.fromHtml(message))
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

        return builder.create();
    }
}
