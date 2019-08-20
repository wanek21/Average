package wanek.average;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.TooltipCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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

public class FragmentCulculator extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    TextView button_5;
    TextView button_4;
    TextView button_3;
    TextView button_2;
    Button btnDel;
    Button btnDown;
    ImageView commentBtn;
    TextView tvScore;
    TextView tvCountFiveForFive;
    TextView tvCountFiveForFour;
    TextView tvCountFourForFour;
    TextView tvForFive;
    TextView tvForFour;
    TextView tvOr;
    ImageView viewLeft;
    TextView viewRight;
    View line1;
    ConstraintLayout.LayoutParams notesLeftParams;
    ConstraintLayout.LayoutParams notesRightParams;

    HandleNotes handleNotes;
    SharedPreferences sharedPreferences;

    public String notes = "";
    int width;
    int height;

    static FragmentCulculator newInstance(int page) {
        FragmentCulculator pageFragment = new FragmentCulculator();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handleNotes = new HandleNotes();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        sharedPreferences = getActivity().getSharedPreferences("launch",Context.MODE_PRIVATE);
        setRetainInstance(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.culculator5_fragment,container,false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        tvScore = view.findViewById(R.id.tvScore);
        tvCountFiveForFive = view.findViewById(R.id.tvFiveForFive);
        tvCountFiveForFour = view.findViewById(R.id.tvFiveForFour);
        tvCountFourForFour= view.findViewById(R.id.tvFourForFour);
        //culcLayout = view.findViewById(R.id.culcLayout);
        tvOr = view.findViewById(R.id.tvOr);
        button_5 = view.findViewById(R.id.button_5);
        button_4 = view.findViewById(R.id.button_4);
        button_3 = view.findViewById(R.id.button_3);
        button_2 = view.findViewById(R.id.button_2);
        btnDel = view.findViewById(R.id.btnDel);
        btnDown = view.findViewById(R.id.btnDown);
        tvForFive = view.findViewById(R.id.tvForFive);
        tvForFour = view.findViewById(R.id.tvForFour);
        line1 = view.findViewById(R.id.line1);
        viewLeft = view.findViewById(R.id.view1);
        viewRight = view.findViewById(R.id.view2);
        commentBtn = view.findViewById(R.id.commentBtn);
        TooltipCompat.setTooltipText(tvForFive,"Показывает сколько нужно получить пятерок, чтобы вышла 5ка");

        button_5.setOnTouchListener(btnOnTouchListener);
        button_4.setOnTouchListener(btnOnTouchListener);
        button_3.setOnTouchListener(btnOnTouchListener);
        button_2.setOnTouchListener(btnOnTouchListener);
        btnDel.setOnTouchListener(btnOnTouchListener);
        btnDown.setOnTouchListener(btnOnTouchListener);
        viewLeft.setOnTouchListener(noteOnTouchListener);
        viewRight.setOnTouchListener(noteOnTouchListener);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment commentDialogFragment = new CommentDialogFragment("wanek.average");
                commentDialogFragment.show(getFragmentManager(),Culculator.COMMENT_DIALOG_TAG);
            }
        });
        notesLeftParams = (ConstraintLayout.LayoutParams) viewLeft.getLayoutParams();
        notesRightParams = (ConstraintLayout.LayoutParams) viewRight.getLayoutParams();

        width = width - 32;
        notesLeftParams.width = width / 9;
        notesLeftParams.height = height / 10;
        notesRightParams.width = width - notesLeftParams.width - 9;
        notesRightParams.height = height / 12;

        viewLeft.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
        viewRight.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();

        if(sharedPreferences.getBoolean("isComment",false) == true) { // если пользователь уже оставил отзыв, то скрываем кнопку
            commentBtn.setVisibility(View.INVISIBLE);
        }

        return view;
    }
    private Spanned textToSpannedWithUnderline(String text) {
        return android.text.Html.fromHtml("<u>" + text + "</u>");
    }
    private String setTextNote(int note) { // вывод введенных оценок (никогда не трогать, без понятия как это работает)
        switch (note) { // 0 - стереть все, 1 - стереть одну
            case 0: notes = "";
                    return "";
            case 1:
                if (notes.length() > 5) {
                   if(handleNotes.getCountNotes() < 9) notes = notes.substring(0, notes.length() - 7) + ")/" + String.valueOf(handleNotes.getCountNotes());
                   else notes = notes.substring(0, notes.length() - 8) + ")/" + String.valueOf(handleNotes.getCountNotes());
                } else { notes = ""; }
                return notes;
            case 2:
                if(notes.equals("")) {notes = "(2)/1";}
                else {
                    if(handleNotes.getCountNotes() < 11) notes = notes.substring(0, notes.length() - 3) + " + 2)/" + String.valueOf(handleNotes.getCountNotes());
                    else notes = notes.substring(0, notes.length() - 4) + " + 2)/" + String.valueOf(handleNotes.getCountNotes());
                }
                return notes;
            case 3:
                if(notes.equals("")) {notes = "(3)/1";}
                else {
                    if(handleNotes.getCountNotes() < 11) notes = notes.substring(0, notes.length() - 3) + " + 3)/" + String.valueOf(handleNotes.getCountNotes());
                    else notes = notes.substring(0, notes.length() - 4) + " + 3)/" + String.valueOf(handleNotes.getCountNotes());
                }
                return notes;
            case 4:
                if(notes.equals("")) {notes = "(4)/1";}
                else {
                    if(handleNotes.getCountNotes() < 11) notes = notes.substring(0, notes.length() - 3) + " + 4)/" + String.valueOf(handleNotes.getCountNotes());
                    else notes = notes.substring(0, notes.length() - 4) + " + 4)/" + String.valueOf(handleNotes.getCountNotes());
                }
                return notes;
            case 5:
                if(notes.equals("")) {notes = "(5)/1";}
                else {
                    if(handleNotes.getCountNotes() < 11) notes = notes.substring(0, notes.length() - 3) + " + 5)/" + String.valueOf(handleNotes.getCountNotes());
                    else notes = notes.substring(0, notes.length() - 4) + " + 5)/" + String.valueOf(handleNotes.getCountNotes());
                }
                return notes;
            default: return notes;
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
                        v.setBackgroundColor(getResources().getColor(R.color.bottomBackColorPressed));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
                    v.animate().alphaBy(0.9f).alpha(1.0f).setDuration(80).start();
                    if (!(v.getId() == R.id.btnDel || v.getId() == R.id.btnDown)) {
                        v.setBackgroundColor(getResources().getColor(R.color.bottomBackColor));
                    }
                    if (v.getId() == R.id.button_5) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(5)));
                        setTextNote(5);
                    } else if (v.getId() == R.id.button_4) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(4)));
                        setTextNote(4);
                    } else if (v.getId() == R.id.button_3) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(3)));
                        setTextNote(3);
                    } else if (v.getId() == R.id.button_2) {
                        tvScore.setText(String.valueOf(handleNotes.clickNote(2)));
                        setTextNote(2);
                    } else if (v.getId() == R.id.btnDown) {
                        if (handleNotes.getAscoreNotes() > 0) {
                            tvScore.setText(String.valueOf(handleNotes.clickDeleteOne()));
                        }
                        setTextNote(1);
                    } else if (v.getId() == R.id.btnDel) {
                        tvScore.setText(String.valueOf(handleNotes.clickDeleteAll()));
                        setTextNote(0);
                    }
                    viewRight.setText(notes);
                    visibilityLayout(handleNotes.getAscoreNotes());
                    break;
            }
            return true;
        }
    };
    private void visibilityLayout(double score) { // анимация
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
        } else if(score >= 4.5) {
            tvForFive.setVisibility(View.INVISIBLE);
            tvCountFiveForFive.setVisibility(View.INVISIBLE);
            tvCountFourForFour.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountFiveForFour.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
            /*line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);*/
        } else if(score < 4.5 && score >= 3.5) {
            tvForFour.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
            tvCountFourForFour.setVisibility(View.INVISIBLE);
            tvOr.setVisibility(View.INVISIBLE);
            tvCountFiveForFour.setVisibility(View.INVISIBLE);
            tvForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(textToSpannedWithUnderline(handleNotes.getFiveWithFive()));
            /*line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.INVISIBLE);*/
        } else if (score < 3.5) {
            tvForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            tvCountFourForFour.setVisibility(View.VISIBLE);
            tvOr.setVisibility(View.VISIBLE);
            tvCountFiveForFour.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(textToSpannedWithUnderline(handleNotes.getFiveWithFive()));
            tvCountFiveForFour.setText(textToSpannedWithUnderline(handleNotes.getFourWithFive()));
            tvCountFourForFour.setText(textToSpannedWithUnderline(handleNotes.getFourWithFour()));
            /*line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.VISIBLE);*/
            tvForFour.setVisibility(View.VISIBLE);
        }
    }
    View.OnTouchListener noteOnTouchListener = new View.OnTouchListener() { // обработчик касания для вывода оценок
        boolean flag = false;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_UP:
                    try{
                        if(!flag) {
                            ImageView vNext = (ImageView) v;
                            viewRight.setText(notes);
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

