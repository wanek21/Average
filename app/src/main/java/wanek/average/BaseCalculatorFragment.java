package wanek.average;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spanned;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

/* Базовый фрагмент для всех бальных систем */

public abstract class BaseCalculatorFragment extends Fragment {

    MotionLayout mainLayout;
    MotionLayout mlTopNote;
    MotionLayout mlBottomNote;
    ImageView btnComment;
    ImageView btnSettings;
    ImageView viewTop;
    TextView tvBottom;
    TextView tvScore;
    Button btnDel;
    Button btnDown;

    NotesControl notesControl;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("launch", MODE_PRIVATE);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // этот метод родителя испольуется в конце данного переопределнного метода

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment commentDialogFragment = new MessageDialog(MessageDialog.REVIEW_DIALOG, getActivity().getPackageName());
                commentDialogFragment.show(getFragmentManager(), MainActivity.COMMENT_DIALOG_TAG);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(getContext(), SettingsActivity.class);
                startActivityForResult(intentSettings, 1);
            }
        });
        btnDel.setOnTouchListener(onTouchListenerDelDown);
        btnDown.setOnTouchListener(onTouchListenerDelDown);

        mainLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) { }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) { }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if(i == R.id.end) {
                    viewTop.animate().rotation(180);
                } else if(i == R.id.start) {
                    viewTop.animate().rotation(0);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) { }
        });
        if (sharedPreferences.getBoolean("isComment", false)) { // если пользователь уже оставил отзыв, то скрываем кнопку
            btnComment.setVisibility(View.INVISIBLE);
        }
        sharedPreferences = getActivity().getSharedPreferences("launch",MODE_PRIVATE);
        int countLaunch = sharedPreferences.getInt("countLaunch2",0);
        if(countLaunch == 1) {
            tvBottom.setText(R.string.swipe_hint);
        }
        return null;
    }

    Spanned textToSpannedWithUnderline(String text) {
        return android.text.Html.fromHtml("<u>" + text + "</u>");
    }

    abstract void visibilityLayout(double score);

    View.OnTouchListener onTouchListenerDelDown = new View.OnTouchListener() { // обработчик касания для кнопок стирания и удаления оценок

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
                        if (notesControl.getCurrentAscoreNotes() > 0) {
                            tvScore.setText(String.valueOf(notesControl.deleteOne()));
                        }
                    } else if (v.getId() == R.id.btnDel) {
                        tvScore.setText(String.valueOf(notesControl.deleteAll()));
                    }
                    tvBottom.setText(notesControl.getNotesToString());
                    visibilityLayout(notesControl.getCurrentAscoreNotes());
                    break;
            }
            return true;
        }
    };
}
