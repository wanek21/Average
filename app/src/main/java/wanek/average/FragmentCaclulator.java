package wanek.average;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public abstract class FragmentCaclulator extends Fragment {

    MotionLayout mainLayout;
    ImageView btnComment;
    ImageView btnSettings;
    ImageView viewLeft;
    TextView viewRight;
    TextView tvAds21;
    TextView tvScore;
    Button btnDel;
    Button btnDown;

    private int widthScreen;
    private int heightScreen;

    private ConstraintLayout.LayoutParams notesLeftParams;
    private ConstraintLayout.LayoutParams notesRightParams;

    HandleNotes handleNotes;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        widthScreen = display.getWidth();
        heightScreen = display.getHeight();
        sharedPreferences = getActivity().getSharedPreferences("launch", MODE_PRIVATE);
        setRetainInstance(true);
    }

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
        btnDel.setOnTouchListener(onTouchListenerDelDown);
        btnDown.setOnTouchListener(onTouchListenerDelDown);
        viewLeft.setOnTouchListener(noteOnTouchListener);
        viewRight.setOnTouchListener(noteOnTouchListener);

        notesLeftParams = (ConstraintLayout.LayoutParams) viewLeft.getLayoutParams();
        notesRightParams = (ConstraintLayout.LayoutParams) viewRight.getLayoutParams();

        widthScreen = widthScreen - 32;
        notesLeftParams.width = widthScreen / 9;
        notesLeftParams.height = heightScreen / 10;
        notesRightParams.width = widthScreen - notesLeftParams.width - 9;
        notesRightParams.height = heightScreen / 12;
        if(sharedPreferences.getBoolean("isComment",false)) { // если пользователь уже оставил отзыв, то скрываем кнопку
            btnComment.setVisibility(View.INVISIBLE);
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        appealingToAds();
    }

    void appealingToAds() {
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

    View.OnTouchListener onTouchListenerDelDown = new View.OnTouchListener() { // обработчик касания для кнопок-оценок

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleXBy(1).scaleX(0.9f).scaleYBy(1).scaleY(0.9f).setDuration(30).start();
                    v.animate().alphaBy(1.0f).alpha(0.9f).setDuration(80).start();
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
                    v.animate().alphaBy(0.9f).alpha(1.0f).setDuration(80).start();

                    if (v.getId() == R.id.btnDown) {
                        if (handleNotes.getAscoreNotes() > 0) {
                            tvScore.setText(String.valueOf(handleNotes.clickDeleteOne()));
                        }
                    } else if (v.getId() == R.id.btnDel) {
                        tvScore.setText(String.valueOf(handleNotes.clickDeleteAll()));
                    }
                    viewRight.setText(handleNotes.getNotesString());
                    visibilityLayout(handleNotes.getAscoreNotes());
                    break;
            }
            return true;
        }
    };
    View.OnTouchListener noteOnTouchListener = new View.OnTouchListener() { // обработчик касания для вывода оценок
        boolean flag = false;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_UP:
                    try{
                        if(!flag) {
                            ImageView vNext = (ImageView) v;
                            viewRight.setText(handleNotes.getNotesString());
                            v.animate().translationXBy(0).translationX(-5).start();
                            viewRight.animate().translationXBy(0).translationX(-5).start();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                vNext.setImageDrawable(getResources().getDrawable(R.drawable.right));
                            }
                            flag = true;
                        } else {
                            if(v.getId() == R.id.view2) {
                                v.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
                                viewLeft.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    viewLeft.setImageDrawable(getResources().getDrawable(R.drawable.left));
                                }
                                flag = false;
                                break;
                            }
                            v.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
                            viewRight.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                viewLeft.setImageDrawable(getResources().getDrawable(R.drawable.left));
                            }
                            flag = false;
                        }
                    } catch (ClassCastException ex) {
                    }
                    break;
            }
            return true;
        }
    };
    Spanned textToSpannedWithUnderline(String text) {
        return android.text.Html.fromHtml("<u>" + text + "</u>");
    }
    abstract void visibilityLayout(double score);
}
