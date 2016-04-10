package pnu.ntc.b.appapinya.apinyaburisri.kinggyrestautant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.NfcEvent;

/**
 * Created by apinyaburisri on 4/10/2016 AD.
 */
public class MyAlert {

    public void myDialog(Context context,
                         String strTitle,
                         String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.icon_question);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


}// Main Class
