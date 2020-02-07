package wanek.average;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public abstract class FragmentCaclulator extends Fragment {

    private MotionLayout mainLayout;
    private ImageView btnComment;
    private ImageView btnSettings;
    private ImageView viewLeft;
    private TextView viewRight;
    private TextView tvAds21;
    private TextView tvScore;
    private Button btnDel;
    private Button btnDown;

    private HandleNotes handleNotes = new HandleNotes();
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // этот метод родителя испольуется в конце данного переопределнного метода
        tvAds21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("adsIsPressed",true);
                editor.apply();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "martian.mystery")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "martian.mystery")));
                }
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment commentDialogFragment = new MessageDialog(MessageDialog.REVIEW_DIALOG,"wanek.average");
                commentDialogFragment.show(getFragmentManager(), MainActivity.COMMENT_DIALOG_TAG);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(getContext(),SettingsActivity.class);
                startActivityForResult(intentSettings,1);
            }
        });
        btnDel.setOnClickListener(onClickListenerDelDown);
        btnDown.setOnClickListener(onClickListenerDelDown);
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        appealingToAds();
    }

    private void appealingToAds() {
        sharedPreferences = getActivity().getSharedPreferences("launch",MODE_PRIVATE);
        boolean adsIsShowed = sharedPreferences.getBoolean("adsIsShowed",false);
        boolean adsIsPressed = sharedPreferences.getBoolean("adsIsPressed",false);
        if(!adsIsShowed) {
            MessageDialog messageDialog = new MessageDialog(MessageDialog.ADS_DIALOG);
            messageDialog.show(getFragmentManager(),"ADS");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("adsIsShowed",true);
            editor.apply();
        }
        if(!adsIsPressed) {
            tvAds21.setVisibility(View.VISIBLE);
        }
    }
    static FragmentRuCalculator newInstance(int page) {
        FragmentRuCalculator pageFragment = new FragmentRuCalculator();
        Bundle arguments = new Bundle();
        arguments.putInt("arg", page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    View.OnClickListener onClickListenerDelDown = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDel: {
                    tvScore.setText(String.valueOf(handleNotes.clickDeleteAll()));
                    break;
                }case R.id.btnDown: {
                    if (handleNotes.getAscoreNotes() > 0) {
                        tvScore.setText(String.valueOf(handleNotes.clickDeleteOne()));
                    }
                    break;
                }
            }
        }
    };
    private Spanned textToSpannedWithUnderline(String text) {
        return android.text.Html.fromHtml("<u>" + text + "</u>");
    }
    abstract void visibilityLayout(double score);
}
