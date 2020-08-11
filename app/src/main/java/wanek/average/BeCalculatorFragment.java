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


public class BeCalculatorFragment extends BaseCalculatorFragment {

    private TextView tvCountNineForNine;
    private TextView tvCountNineForSeven;
    private TextView tvCountSevenForSeven;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        notesControl = new NotesControl();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.culculator_be_fragment, container, false);

        mainLayout = view.findViewById(R.id.mlMain);
        btnComment = view.findViewById(R.id.btnComment);
        btnSettings = view.findViewById(R.id.btnSettings);
        viewTop = view.findViewById(R.id.imgTop);
        tvBottom = view.findViewById(R.id.tvBottom);

        mlTopNote = view.findViewById(R.id.mlForNine);
        mlBottomNote = view.findViewById(R.id.mlForSeven);
        tvCountNineForSeven = view.findViewById(R.id.tvNineForSeven);
        tvCountSevenForSeven = view.findViewById(R.id.tvSevenForSeven);
        tvCountNineForNine = view.findViewById(R.id.tvNineForNine);
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

        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    void visibilityLayout(double score) {
        if (score == 0) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score >= 8.5) {
            mlTopNote.transitionToStart();
            mlBottomNote.transitionToStart();
        } else if(score < 8.5 && score >= 6.5) {
            mlBottomNote.transitionToStart();
            mlTopNote.transitionToEnd();
            tvCountNineForNine.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(9,9),9)));
        } else if (score < 6.5) {
            mlTopNote.transitionToEnd();
            mlBottomNote.transitionToEnd();
            tvCountNineForNine.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(9,9),9)));
            tvCountNineForSeven.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(9,7),9)));
            tvCountSevenForSeven.setText(textToSpannedWithUnderline(textNote(notesControl.getHowManyNotes(7,7),7)));
        }
    }

    public String textNote(int countNote, int noteText) {
        if(countNote == 1 || ((countNote % 10 == 1) && (countNote != 11))) {
            if(noteText == 7) {
                return countNote + " семёрка";
            } else {
                return countNote + " девятка";
            }
        } else if ((countNote % 10 == 2 || countNote % 10 == 3 || countNote % 10 == 4) && (countNote - countNote % 10 != 10)) {
            if(noteText == 7) {
                return countNote + " семёрки";
            } else {
                return countNote + " девятки";
            }
        } else {
            if(noteText == 7) {
                return countNote + " семёрок";
            } else {
                return countNote + " девяток";
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
                    if (v.getId() == R.id.button_10) {
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
