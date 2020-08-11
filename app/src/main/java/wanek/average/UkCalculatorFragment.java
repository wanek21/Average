package wanek.average;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UkCalculatorFragment extends BaseCalculatorFragment {

    private TextView tvCountElevenForEleven;
    private TextView tvCountElevenForEight;
    private TextView tvCountEightForEight;
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
        notesControl = new NotesControl();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.culculator_uk_fragment, container, false);

        mainLayout = view.findViewById(R.id.mlMain);
        btnComment = view.findViewById(R.id.btnComment);
        btnSettings = view.findViewById(R.id.btnSettings);
        viewTop = view.findViewById(R.id.imgTop);
        tvBottom = view.findViewById(R.id.tvBottom);

        mlTopNote = view.findViewById(R.id.mlForEleven);
        mlBottomNote = view.findViewById(R.id.mlForEight);
        tvCountElevenForEight = view.findViewById(R.id.tvElevenForEight);
        tvCountEightForEight = view.findViewById(R.id.tvEightForEight);
        tvCountElevenForEleven = view.findViewById(R.id.tvElevenForEleven);
        tvScore = view.findViewById(R.id.tvScore);
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
        if (score == 0) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score >= 10.5) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score < 10.5 && score >= 7.5) {
            mlBottomNote.transitionToStart();
            mlTopNote.transitionToEnd();
            tvCountElevenForEleven.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(11,11),11)));
        } else if (score < 7.5) {
            mlTopNote.transitionToEnd();
            mlBottomNote.transitionToEnd();
            tvCountElevenForEleven.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(11,11),11)));
            tvCountElevenForEight.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(11,8),11)));
            tvCountEightForEight.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(8,8),8)));
        }
    }

    private String textNote(int note, int noteText) {
        if(note == 1 || ((note % 10 == 1) && (note != 11))) {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерка";
            }
        } else if ((note % 10 == 2 || note % 10 == 3 || note % 10 == 4) && (note - note % 10 != 10)) {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерки";
            }
        } else {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерок";
            }
        }
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
                        tvScore.setText(String.valueOf(notesControl.addNote(12)));
                    } else if (v.getId() == R.id.button_11) {
                        tvScore.setText(String.valueOf(notesControl.addNote(11)));
                    } else if (v.getId() == R.id.button_10) {
                        tvScore.setText(String.valueOf(notesControl.addNote(10)));
                    } else if (v.getId() == R.id.button_9) {
                        tvScore.setText(String.valueOf(notesControl.addNote(9)));
                    } else if (v.getId() == R.id.button_8) {
                        tvScore.setText(String.valueOf(notesControl.addNote(8)));
                    } else if (v.getId() == R.id.button_7) {
                        tvScore.setText(String.valueOf(notesControl.addNote(7)));
                    } else if (v.getId() == R.id.button_6) {
                        tvScore.setText(String.valueOf(notesControl.addNote(6)));
                    } else if (v.getId() == R.id.button_5) {
                        tvScore.setText(String.valueOf(notesControl.addNote(5)));
                    } else if (v.getId() == R.id.button_4) {
                        tvScore.setText(String.valueOf(notesControl.addNote(4)));
                    } else if (v.getId() == R.id.button_3) {
                        tvScore.setText(String.valueOf(notesControl.addNote(3)));
                    } else if (v.getId() == R.id.button_2) {
                        tvScore.setText(String.valueOf(notesControl.addNote(2)));
                    } else if (v.getId() == R.id.button_1) {
                        tvScore.setText(String.valueOf(notesControl.addNote(1)));
                    }
                    tvBottom.setText(notesControl.getNotesToString());
                    visibilityLayout(notesControl.getCurrentAscoreNotes());
                    break;
            }
            return true;
        }
    };
}
