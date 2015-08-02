package com.dpc.dchacks2015.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dpc.dchacks2015.R;

/**
 * Created by Daniel on 8/1/2015.
 */
public class CardiacArrestDetectedDialog extends AlertDialog.Builder {

   public CardiacArrestDetectedDialog(Context context) {
       super(context);

       setTitle(context.getString(R.string.cardiacArrestDetected));

       setPositiveButton(context.getString(R.string.imOk), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });

       setNegativeButton(context.getString(R.string.iNeedHelp), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });

       AlertDialog dialog = create();
       dialog.setView(LayoutInflater.from(context).inflate(R.layout.dialog_detect_emergency, null));
       dialog.show();
   }

}
