package wanek.average;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.TooltipCompat;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class FragmentRuCalculatorWithOne extends FragmentCaclulator {


    private MaterialButton button_5;
    private MaterialButton button_4;
    private MaterialButton button_3;
    private MaterialButton button_2;
    private MaterialButton button_1;
    private TextView tvCountFiveForFive;
    private TextView tvCountFiveForFour;
    private TextView tvCountFourForFour;
    private TextView tvForFive;
    private TextView tvForFour;
    private TextView tvOr;

    public void onCreate(Bundle bundle) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        double roundFive;
        double roundFour;
        try {
            roundFive = Double.valueOf(sharedPreferences.getString("average_round_five","4.5"));
        } catch (NumberFormatException ex) {
            roundFive = 4.5;
        }try {
            roundFour = Double.valueOf(sharedPreferences.getString("average_round_four","3.5"));
        } catch (NumberFormatException ex) {
            roundFour = 3.5;
        }
        handleNotes = new HandleNotes(roundFive,roundFour);
        super.onCreate(bundle);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.culculator51_fragment,container,false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        tvScore = view.findViewById(R.id.tvScore);
        tvCountFiveForFive = view.findViewById(R.id.tvFiveForFive);
        tvCountFiveForFour = view.findViewById(R.id.tvFiveForFour);
        tvCountFourForFour= view.findViewById(R.id.tvFourForFour);
        tvOr = view.findViewById(R.id.tvOr);
        button_5 = view.findViewById(R.id.button_5);
        button_4 = view.findViewById(R.id.button_4);
        button_3 = view.findViewById(R.id.button_3);
        button_2 = view.findViewById(R.id.button_2);
        button_1 = view.findViewById(R.id.button_1);
        btnDel = view.findViewById(R.id.btnDel);
        btnDown = view.findViewById(R.id.btnDown);
        tvForFive = view.findViewById(R.id.tvForFive);
        tvForFour = view.findViewById(R.id.tvForFour);
        viewLeft = view.findViewById(R.id.view1);
        viewRight = view.findViewById(R.id.view2);
        btnComment = view.findViewById(R.id.commentBtn);
        btnSettings = view.findViewById(R.id.btnSettings);
        tvAds21 = view.findViewById(R.id.btn21);
        TooltipCompat.setTooltipText(tvForFive,"Показывает сколько нужно получить пятерок, чтобы вышла 5ка");

        button_5.setOnTouchListener(onTouchListenerBtnNote);
        button_4.setOnTouchListener(onTouchListenerBtnNote);
        button_3.setOnTouchListener(onTouchListenerBtnNote);
        button_2.setOnTouchListener(onTouchListenerBtnNote);
        button_1.setOnTouchListener(onTouchListenerBtnNote);

        super.onCreateView(inflater,container,bundle);

        return view;
    }
    View.OnTouchListener onTouchListenerBtnNote = new View.OnTouchListener() { // обработчик касания для кнопок-оценок
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleXBy(1).scaleX(0.9f).scaleYBy(1).scaleY(0.9f).setDuration(30).start();
                    v.animate().alphaBy(1.0f).alpha(0.9f).setDuration(80).start();
                        v.setBackgroundColor(getResources().getColor(R.color.bottomBackColorPressed));
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
                    v.animate().alphaBy(0.9f).alpha(1.0f).setDuration(80).start();
                        v.setBackgroundColor(getResources().getColor(R.color.bottomBackColor));
                    if (v.getId() == R.id.button_5) {
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
                    viewRight.setText(handleNotes.getNotesString());
                    visibilityLayout(handleNotes.getAscoreNotes());
                    break;
            }
            return true;
        }
    };
    void visibilityLayout(double score) { // анимация
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animfortop);
        if (score == 0) {
            if(tvForFive.getVisibility() == View.VISIBLE) {
                tvForFive.setVisibility(View.INVISIBLE);
                tvCountFiveForFive.setVisibility(View.INVISIBLE);
                /*line2.setVisibility(View.INVISIBLE);
                line3.startAnimation(animation);*/
                tvForFive.startAnimation(animation);
                tvCountFiveForFive.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animforbottom);

            if(tvOr.getVisibility() == View.VISIBLE) {
                tvCountFourForFour.setVisibility(View.INVISIBLE);
                tvOr.setVisibility(View.INVISIBLE);
                tvCountFiveForFour.setVisibility(View.INVISIBLE);
                tvForFour.setVisibility(View.INVISIBLE);
                /*line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);*/
                tvCountFourForFour.startAnimation(animation); //bottomLayout.startAnimation(animation);
                tvCountFiveForFour.startAnimation(animation);
                tvOr.startAnimation(animation);
                tvForFour.startAnimation(animation);
                //line3.startAnimation(animation);
            }
        } /*else if(score >= handleNotes.getRoundFive()) {
            tvForFive.setVisibility(View.INVISIBLE);
            tvCountFiveForFive.setVisibility(View.INVISIBLE);
            tvCountFourForFour.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountFiveForFour.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
        } else if(score < handleNotes.getRoundFive() && score >= handleNotes.getRoundFour()) {
            tvForFour.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
            tvCountFourForFour.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountFiveForFour.setVisibility(View.INVISIBLE);
            tvForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(textToSpannedWithUnderline(handleNotes.getFiveWithFive()));
        } else if (score < handleNotes.getRoundFour()) {
            tvForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            tvCountFourForFour.setVisibility(View.VISIBLE);
            tvOr.setVisibility(View.VISIBLE);
            tvCountFiveForFour.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(textToSpannedWithUnderline(handleNotes.getFiveWithFive()));
            tvCountFiveForFour.setText(textToSpannedWithUnderline(handleNotes.getFourWithFive()));
            tvCountFourForFour.setText(textToSpannedWithUnderline(handleNotes.getFourWithFour()));
            tvForFour.setVisibility(View.VISIBLE);
        }*/
    }
}

