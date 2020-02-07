package wanek.average;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {

    String appPackageName;
    int typeMessage;

    static final int REVIEW_DIALOG = 1;
    static final int ADS_DIALOG = 2;
    SharedPreferences sharedPreferences;

    public MessageDialog(int typeMessage) {
        this.typeMessage = typeMessage;
    }
    public MessageDialog(int typeMessage, String packageName) {
        this.typeMessage = typeMessage;
        this.appPackageName = packageName;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(typeMessage == REVIEW_DIALOG) {
            builder.setMessage(R.string.comment_dialog)
                    .setPositiveButton(R.string.comment_dialog_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            sharedPreferences = getContext().getSharedPreferences("launch", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isComment",true);
                            editor.commit();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })
                    .setNegativeButton(R.string.comment_dialog_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        } else if(typeMessage == ADS_DIALOG) {
            builder.setMessage(R.string.ads_dialog)
                    .setPositiveButton(R.string.ads_dialog_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "martian.mystery")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "martian.mystery")));
                            }
                        }
                    })
                    .setNegativeButton(R.string.comment_dialog_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
