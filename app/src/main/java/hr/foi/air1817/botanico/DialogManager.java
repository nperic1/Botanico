package hr.foi.air1817.botanico;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogManager {
    static DialogManager instance = null;

    private DialogManager() {
    }

    public static DialogManager getInstance(){
        if (instance == null){
            instance = new DialogManager();
        }
        return instance;
    }

    public void showDialogOK(Activity context, int dialogTitle, int dialogMsg){
        final Dialog dialogOk = new Dialog(context);
        dialogOk.setContentView(R.layout.wifi_dialog);

        TextView title = dialogOk.findViewById(R.id.dialog_title);
        TextView msg = dialogOk.findViewById(R.id.dialog_message);
        title.setText(context.getString(dialogTitle));
        msg.setText(context.getString(dialogMsg));

        Button dialogButton = (Button) dialogOk.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOk.dismiss();
            }
        });

        dialogOk.show();
    }
}
