package com.learnlife.learnlife.crosslayers.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.learnlife.learnlife.R;

public class Dialog {

    public static void showErrorMessageDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_error))
                .setMessage(message)
                .setNeutralButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        android.app.Dialog dialog = builder.create();
        dialog.show();
    }
}
