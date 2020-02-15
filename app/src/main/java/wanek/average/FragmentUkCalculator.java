package wanek.average;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentUkCalculator extends FragmentCaclulator {

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handleNotes = new HandleNotes();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.culculator_uk_fragment, container, false);

        mainLayout = view.findViewById(R.id.mlMain);
        btnComment = view.findViewById(R.id.btnComment);
        tvAds21 = view.findViewById(R.id.btn21);
        btnSettings = view.findViewById(R.id.btnSettings);
        viewTop = view.findViewById(R.id.imgTop);
        tvBottom = view.findViewById(R.id.tvBottom);

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

        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    void visibilityLayout(double score) { // анимация
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animfortop);
        if (score == 0) {
            if (tvForEleven.getVisibility() == View.VISIBLE) {
                tvForEleven.setVisibility(View.INVISIBLE);
                tvCountElevenForEleven.setVisibility(View.INVISIBLE);
                tvForEleven.startAnimation(animation);
                tvCountElevenForEleven.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animforbottom);

            if (tvOr.getVisibility() == View.VISIBLE) {
                tvCountEightForEight.setVisibility(View.INVISIBLE);
                tvOr.setVisibility(View.INVISIBLE);
                tvCountElevenForEight.setVisibility(View.INVISIBLE);
                tvForEight.setVisibility(View.INVISIBLE);
                tvCountEightForEight.startAnimation(animation);
                tvCountElevenForEight.startAnimation(animation);
                tvOr.startAnimation(animation);
                tvForEight.startAnimation(animation);
            }
        } /*else if(score >= 10.5) {
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
        }*/
    }

    View.OnTouchListener btnOnTouchListener = new View.OnTouchListener() { // обработчик касания для кнопок-оценок

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleXBy(1).scaleX(0.9f).scaleYBy(1).scaleY(0.9f).setDuration(30).start();
                    v.animate().alphaBy(1.0f).alpha(0.9f).setDuration(80).start();
                    v.setBackground(getResources().getDrawable(R.drawable.btn_note_back_pressed));
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
                    v.animate().alphaBy(0.9f).alpha(1.0f).setDuration(80).start();
                    v.setBackground(getResources().getDrawable(R.drawable.btn_note_back));
                    if (v.getId() == R.id.button_12) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(12)));
                    } else if (v.getId() == R.id.button_11) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(11)));
                    } else if (v.getId() == R.id.button_10) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(10)));
                    } else if (v.getId() == R.id.button_9) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(9)));
                    } else if (v.getId() == R.id.button_8) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(8)));
                    } else if (v.getId() == R.id.button_7) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(7)));
                    } else if (v.getId() == R.id.button_6) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(6)));
                    } else if (v.getId() == R.id.button_5) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(5)));
                    } else if (v.getId() == R.id.button_4) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(4)));
                    } else if (v.getId() == R.id.button_3) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(3)));
                    } else if (v.getId() == R.id.button_2) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(2)));
                    } else if (v.getId() == R.id.button_1) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(1)));
                    }
                    tvBottom.setText(handleNotes.getNotesString());
                    visibilityLayout(handleNotes.getAscoreNotes());
                    break;
            }
            return true;
        }
    };
}
