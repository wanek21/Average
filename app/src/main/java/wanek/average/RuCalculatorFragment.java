package wanek.average;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class RuCalculatorFragment extends BaseCalculatorFragment {

    private MaterialButton button_5;
    private MaterialButton button_4;
    private MaterialButton button_3;
    private MaterialButton button_2;
    private TextView tvCountFiveForFive;
    private TextView tvCountFiveForFour;
    private TextView tvCountFourForFour;


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
        notesControl = new NotesControl(roundFive,roundFour);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.culculator5_fragment,container,false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mainLayout = view.findViewById(R.id.mlMain);
        tvScore = view.findViewById(R.id.tvScore);
        mlTopNote = view.findViewById(R.id.mlForFive);
        tvCountFiveForFive = view.findViewById(R.id.tvFiveForFive);
        mlBottomNote = view.findViewById(R.id.mlForFour);
        tvCountFiveForFour = view.findViewById(R.id.tvFiveForFour);
        tvCountFourForFour= view.findViewById(R.id.tvFourForFour);
        button_5 = view.findViewById(R.id.button_5);
        button_4 = view.findViewById(R.id.button_4);
        button_3 = view.findViewById(R.id.button_3);
        button_2 = view.findViewById(R.id.button_2);
        btnDel = view.findViewById(R.id.btnDel);
        btnDown = view.findViewById(R.id.btnDown);
        viewTop = view.findViewById(R.id.imgTop);
        tvBottom = view.findViewById(R.id.tvBottom);
        btnComment = view.findViewById(R.id.commentBtn);
        btnSettings = view.findViewById(R.id.btnSettings);

        button_5.setOnTouchListener(onTouchListenerBtnNote);
        button_4.setOnTouchListener(onTouchListenerBtnNote);
        button_3.setOnTouchListener(onTouchListenerBtnNote);
        button_2.setOnTouchListener(onTouchListenerBtnNote);

        super.onCreateView(inflater,container,bundle);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                        tvScore.setText(String.valueOf(notesControl.addNote(5)));
                    } else if (v.getId() == R.id.button_4) {
                        tvScore.setText(String.valueOf(notesControl.addNote(4)));
                    } else if (v.getId() == R.id.button_3) {
                        tvScore.setText(String.valueOf(notesControl.addNote(3)));
                    } else if (v.getId() == R.id.button_2) {
                        tvScore.setText(String.valueOf(notesControl.addNote(2)));
                    }
                    tvBottom.setText(notesControl.getNotesToString());
                    visibilityLayout(notesControl.getCurrentAscoreNotes());
                    break;
            }
            return true;
        }
    };

    void visibilityLayout(double score) { // анимация
        if (score == 0) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score >= notesControl.getRoundFive()) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score < notesControl.getRoundFive() && score >= notesControl.getRoundFour()) {
            mlBottomNote.transitionToStart();
            mlTopNote.transitionToEnd();
            tvCountFiveForFive.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(5,5),5)));
        } else if (score < notesControl.getRoundFour()) {
            mlTopNote.transitionToEnd();
            mlBottomNote.transitionToEnd();
            tvCountFiveForFive.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(5,5),5)));
            tvCountFiveForFour.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(5,4),5)));
            tvCountFourForFour.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(4,4),4)));
        }
    }

    public String textNote(int countNote, int noteText) {
        if(countNote == 1 || ((countNote % 10 == 1) && (countNote != 11))) {
            if(noteText == 4) {
                return countNote + " четверка";
            } else {
                return countNote + " пятерка";
            }
        } else if ((countNote % 10 == 2 || countNote % 10 == 3 || countNote % 10 == 4) && (countNote - countNote % 10 != 10)) {
            if(noteText == 4) {
                return countNote + " четверки";
            } else {
                return countNote + " пятерки";
            }
        } else {
            if(noteText == 4) {
                return countNote + " четверок";
            } else {
                return countNote + " пятерок";
            }
        }
    }

}

