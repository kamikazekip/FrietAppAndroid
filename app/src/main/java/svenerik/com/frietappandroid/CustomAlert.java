package svenerik.com.frietappandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomAlert{

    private Context context;

    public CustomAlert(Context context){
        this.context = context;
    }

    public void showAlert(String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
