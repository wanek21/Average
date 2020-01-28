package wanek.average;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class FragmentUkCulculator extends Fragment {

    private ImageView btnComment;
    private ImageView btnSettings;
    private TextView tvScore;
    private TextView tvCountElevenForEleven;
    private TextView tvCountElevenForEight;
    private TextView tvCountEightForEight;
    private TextView tvForEleven;
    private TextView tvForEight;
    private TextView tvOr;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_10;
    private Button button_11;
    private Button button_12;
    private ImageView viewLeft;
    private TextView viewRight;
    private TextView tvAds21;
    private Button btnDel;
    private Button btnDown;

    private ConstraintLayout.LayoutParams notesLeftParams;
    private ConstraintLayout.LayoutParams notesRightParams;

    private HandleNotes handleNotes = new HandleNotes();
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.culculator_uk_fragment,container,false);

        btnComment = view.findViewById(R.id.btnComment);
        tvAds21 = view.findViewById(R.id.btn21);
        btnSettings = view.findViewById(R.id.btnSettings);
        viewLeft = view.findViewById(R.id.view1);
        viewLeft.setOnTouchListener(noteOnTouchListener);
        viewRight = view.findViewById(R.id.view2);
        viewRight.setOnTouchListener(noteOnTouchListener);

        tvCountElevenForEight = view.findViewById(R.id.tvElevenForEight);
        tvCountEightForEight = view.findViewById(R.id.tvEightForEight);
        tvCountElevenForEleven = view.findViewById(R.id.tvElevenForEleven);
        tvForEleven = view.findViewById(R.id.tvForEleven);
        tvForEight = view.findViewById(R.id.tvForEight);
        tvScore = view.findViewById(R.id.tvScore);
        tvOr = view.findViewById(R.id.tvOr);
        button_1 = view.findViewById(R.id.button_1);
        button_2 = view.findViewById(R.id.button_2);
        button_3 = view.findViewById(R.id.button_3);
        button_4 = view.findViewById(R.id.button_4);
        button_5 = view.findViewById(R.id.button_5);
        button_6 = view.findViewById(R.id.button_6);
        button_7 = view.findViewById(R.id.button_7);
        button_8 = view.findViewById(R.id.button_8);
        button_9 = view.findViewById(R.id.button_9);
        button_10 = view.findViewById(R.id.button_10);
        button_11 = view.findViewById(R.id.button_11);
        button_12 = view.findViewById(R.id.button_12);
        btnDel = view.findViewById(R.id.btnDel);
        btnDown = view.findViewById(R.id.btnDown);

        tvAds21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("launch", MODE_PRIVATE);
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
                DialogFragment commentDialogFragment = new MessageDialogFragent(MessageDialogFragent.REVIEW_DIALOG,"wanek.average");
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
        button_1.setOnTouchListener(btnOnTouchListener);
        button_2.setOnTouchListener(btnOnTouchListener);
        button_3.setOnTouchListener(btnOnTouchListener);
        button_4.setOnTouchListener(btnOnTouchListener);
        button_5.setOnTouchListener(btnOnTouchListener);
        button_6.setOnTouchListener(btnOnTouchListener);
        button_7.setOnTouchListener(btnOnTouchListener);
        button_8.setOnTouchListener(btnOnTouchListener);
        button_9.setOnTouchListener(btnOnTouchListener);
        button_10.setOnTouchListener(btnOnTouchListener);
        button_11.setOnTouchListener(btnOnTouchListener);
        button_12.setOnTouchListener(btnOnTouchListener);
        btnDel.setOnTouchListener(btnOnTouchListener);
        btnDown.setOnTouchListener(btnOnTouchListener);

        notesLeftParams = (ConstraintLayout.LayoutParams) viewLeft.getLayoutParams();
        notesRightParams = (ConstraintLayout.LayoutParams) viewRight.getLayoutParams();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        width = width - 32;
        notesLeftParams.width = width / 9;
        notesLeftParams.height = height / 10;
        notesRightParams.width = width - notesLeftParams.width - 9;
        notesRightParams.height = height / 12;

        viewLeft.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
        viewRight.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            MessageDialogFragent messageDialogFragent = new MessageDialogFragent(MessageDialogFragent.ADS_DIALOG);
            messageDialogFragent.show(getFragmentManager(),"ADS");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("adsIsShowed",true);
            editor.apply();
        }
        if(!adsIsPressed) {
            tvAds21.setVisibility(View.VISIBLE);
        }
    }

    private Spanned textToSpannedWithUnderline(String text) {
        return android.text.Html.fromHtml("<u>" + text + "</u>");
    }
    private void visibilityLayout(double score) { // анимация
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animfortop);
        if (score == 0) {
            if(tvForEleven.getVisibility() == View.VISIBLE) {
                tvForEleven.setVisibility(View.INVISIBLE);
                tvCountElevenForEleven.setVisibility(View.INVISIBLE);
                tvForEleven.startAnimation(animation);
                tvCountElevenForEleven.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animforbottom);

            if(tvOr.getVisibility() == View.VISIBLE) {
                tvCountEightForEight.setVisibility(View.INVISIBLE);
                tvOr.setVisibility(View.INVISIBLE);
                tvCountElevenForEight.setVisibility(View.INVISIBLE);
                tvForEight.setVisibility(View.INVISIBLE);
                tvCountEightForEight.startAnimation(animation);
                tvCountElevenForEight.startAnimation(animation);
                tvOr.startAnimation(animation);
                tvForEight.startAnimation(animation);
            }
        } else if(score >= 10.5) {
            tvForEleven.setVisibility(View.INVISIBLE);
            tvCountElevenForEleven.setVisibility(View.INVISIBLE);
            tvCountEightForEight.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountElevenForEight.setVisibility(View.INVISIBLE);
            tvForEight.setVisibility(View.INVISIBLE);
        } else if(score < 10.5 && score >= 7.5) {
            tvForEight.setVisibility(View.INVISIBLE);
            tvForEight.setVisibility(View.INVISIBLE);
            tvCountEightForEight.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountElevenForEight.setVisibility(View.INVISIBLE);
            tvForEleven.setVisibility(View.VISIBLE);
            tvCountElevenForEleven.setVisibility(View.VISIBLE);
            tvCountElevenForEleven.setText(textToSpannedWithUnderline(handleNotes.getElevenWithEleven()));
        } else if (score < 7.5) {
            tvForEleven.setVisibility(View.VISIBLE);
            tvCountElevenForEleven.setVisibility(View.VISIBLE);
            tvCountEightForEight.setVisibility(View.VISIBLE);
            tvOr.setVisibility(View.VISIBLE);
            tvCountElevenForEight.setVisibility(View.VISIBLE);
            tvCountElevenForEleven.setText(textToSpannedWithUnderline(handleNotes.getElevenWithEleven()));
            tvCountElevenForEight.setText(textToSpannedWithUnderline(handleNotes.getEightWithEleven()));
            tvCountEightForEight.setText(textToSpannedWithUnderline(handleNotes.getEightWithEight()));
            tvForEight.setVisibility(View.VISIBLE);
        }
    }
    View.OnTouchListener btnOnTouchListener = new View.OnTouchListener() { // обработчик касания для кнопок-оценок

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleXBy(1).scaleX(0.9f).scaleYBy(1).scaleY(0.9f).setDuration(30).start();
                    v.animate().alphaBy(1.0f).alpha(0.9f).setDuration(80).start();
                    if (!(v.getId() == R.id.btnDel || v.getId() == R.id.btnDown)) {
                        v.setBackground(getResources().getDrawable(R.drawable.btn_note_back_pressed));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
                    v.animate().alphaBy(0.9f).alpha(1.0f).setDuration(80).start();
                    if (!(v.getId() == R.id.btnDel || v.getId() == R.id.btnDown)) {
                        v.setBackground(getResources().getDrawable(R.drawable.btn_note_back));
                    }
                    if(v.getId() == R.id.button_12) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(12)));
                    }
                    else if (v.getId() == R.id.button_11) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(11)));
                    }
                    else if (v.getId() == R.id.button_10) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(10)));
                    }
                    else if (v.getId() == R.id.button_9) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(9)));
                    }
                    else if (v.getId() == R.id.button_8) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(8)));
                    }
                    else if (v.getId() == R.id.button_7) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(7)));
                    }
                    else if (v.getId() == R.id.button_6) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(6)));
                    }
                    else if (v.getId() == R.id.button_5) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(5)));
                    } else if (v.getId() == R.id.button_4) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(4)));
                    } else if (v.getId() == R.id.button_3) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(3)));
                    } else if (v.getId() == R.id.button_2) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(2)));
                    }
                    else if (v.getId() == R.id.button_1) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(1)));
                    }else if (v.getId() == R.id.btnDown) {
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
                        Log.d("my","Ошибка");
                    }
                    break;
            }
            return true;
        }
    };
}
