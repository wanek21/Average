package wanek.average;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentCulculator extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    TextView button_5;
    TextView button_4;
    TextView button_3;
    TextView button_2;
    Button btnDel;
    Button btnDown;
    TextView tvScore;
    TextView tvCountFiveForFive;
    TextView tvCountFiveForFour;
    TextView tvCountFourForFour;
    TextView tvForFive;
    TextView tvForFour;
    TextView tvOr;
    ImageView viewLeft;
    TextView viewRight;
    LinearLayout buttonsLayout;
    LinearLayout bottomLayout;
    View line1;
    View line2;
    View line3;
    LinearLayout culcLayout;
    LinearLayout.LayoutParams buttonsLayoutParams;
    ConstraintLayout.LayoutParams notesLeftParams;
    ConstraintLayout.LayoutParams notesRightParams;
    LinearLayout.LayoutParams button_5Params;
    LinearLayout.LayoutParams button_4Params;
    LinearLayout.LayoutParams button_3Params;
    LinearLayout.LayoutParams button_2Params;
    LinearLayout.LayoutParams btnDelParams;
    LinearLayout.LayoutParams btnDownParams;

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
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        setRetainInstance(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.culculator_fragment,container,false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"main.ttf");
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        tvScore = view.findViewById(R.id.tvScore);
        tvCountFiveForFive = view.findViewById(R.id.tvCountFiveForFive);
        tvCountFiveForFour = view.findViewById(R.id.tvCountFiveForFour);
        tvCountFourForFour= view.findViewById(R.id.tvCountFourForFour);
        culcLayout = view.findViewById(R.id.culcLayout);
        tvOr = view.findViewById(R.id.tvOr);
        button_5 = view.findViewById(R.id.button_5);
        button_4 = view.findViewById(R.id.button_4);
        button_3 = view.findViewById(R.id.button_3);
        button_2 = view.findViewById(R.id.button_2);
        btnDel = view.findViewById(R.id.btnDel);
        btnDown = view.findViewById(R.id.btnDown);
        tvForFive = view.findViewById(R.id.tvForFive);
        tvForFour = view.findViewById(R.id.tvForFour);
        buttonsLayout = view.findViewById(R.id.buttonsLayout);
        bottomLayout = view.findViewById(R.id.layourBottom);
        viewLeft = view.findViewById(R.id.view1);
        viewRight = view.findViewById(R.id.view2);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line3 = view.findViewById(R.id.line3);

        button_5.setOnTouchListener(btnOnTouchListener);
        button_4.setOnTouchListener(btnOnTouchListener);
        button_3.setOnTouchListener(btnOnTouchListener);
        button_2.setOnTouchListener(btnOnTouchListener);
        btnDel.setOnTouchListener(btnOnTouchListener);
        btnDown.setOnTouchListener(btnOnTouchListener);
        viewLeft.setOnTouchListener(noteOnTouchListener);
        viewRight.setOnTouchListener(noteOnTouchListener);
        buttonsLayoutParams = (LinearLayout.LayoutParams) buttonsLayout.getLayoutParams();
        notesLeftParams = (ConstraintLayout.LayoutParams) viewLeft.getLayoutParams();
        notesRightParams = (ConstraintLayout.LayoutParams) viewRight.getLayoutParams();
        button_2Params = (LinearLayout.LayoutParams) button_2.getLayoutParams();
        button_3Params = (LinearLayout.LayoutParams) button_3.getLayoutParams();
        button_4Params = (LinearLayout.LayoutParams) button_4.getLayoutParams();
        button_5Params = (LinearLayout.LayoutParams) button_5.getLayoutParams();
        btnDelParams = (LinearLayout.LayoutParams) btnDel.getLayoutParams();
        btnDownParams = (LinearLayout.LayoutParams) btnDown.getLayoutParams();

        notesLeftParams.width = width / 9;
        notesLeftParams.height = height / 10;
        notesRightParams.width = width - notesLeftParams.width - 9;
        notesRightParams.height = height / 12;

        btnDelParams.leftMargin = width / 30;
        btnDelParams.rightMargin = width / 30;
        btnDelParams.bottomMargin = height / 115;

        btnDownParams.leftMargin = width / 30;
        btnDownParams.rightMargin = width / 30;
        btnDownParams.bottomMargin = height / 115;

        button_2Params.leftMargin = width / 23;
        button_2Params.rightMargin = width / 23;
        button_2Params.bottomMargin = height / 55;

        button_3Params.leftMargin = width / 23;
        button_3Params.rightMargin = width / 23;
        button_3Params.bottomMargin = height / 55;

        button_4Params.leftMargin = width / 23;
        button_4Params.rightMargin = width / 23;
        button_4Params.bottomMargin = height / 55;

        button_5Params.leftMargin = width / 23;
        button_5Params.rightMargin = width / 23;
        button_5Params.bottomMargin = height / 55;

        viewLeft.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
        viewRight.animate().translationXBy(0).translationX(notesRightParams.width + 9).start();
        line1.animate().scaleXBy(0.0f).scaleX(width).setDuration(1200).start();

        tvForFive.setTypeface(type);
        tvForFour.setTypeface(type);

        setColorBackAndButton();

        return view;
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
    private void setColorBackAndButton() {
        int colorItem = sharedPreferences.getInt("colorItem",0);
        switch (colorItem) {
            case 0: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_0));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_0));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_0));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_0));
                break;
            case 1: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark1));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_1));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_1));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_1));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_1));
                break;
            case 2: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_2));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_2));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_2));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_2));
                break;
            case 3: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark3));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_3));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_3));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_3));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_3));
                break;
            case 4: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark4));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_4));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_4));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_4));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_4));
                break;
            case 5: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark5));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_5));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_5));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_5));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_5));
                break;
            case 6: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark6));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_6));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_6));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_6));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_6));
                break;
            case 7: culcLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark7));
                btnDel.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                btnDown.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                button_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                button_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                button_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                button_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_back_7));
                line1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_7));
                line2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_7));
                line3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_7));
                break;
        }
    }
    View.OnTouchListener btnOnTouchListener = new View.OnTouchListener() { // обработчик касания для кнопок-оценок
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleXBy(1).scaleX(0.9f).scaleYBy(1).scaleY(0.9f).setDuration(30).start();
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleXBy(0.9f).scaleX(1).scaleYBy(0.9f).scaleY(1).setDuration(80).start();
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
                line2.setVisibility(View.INVISIBLE);
                line3.startAnimation(animation);
                tvForFive.startAnimation(animation);
                tvCountFiveForFive.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animforbottom);

            if(bottomLayout.getVisibility() == View.VISIBLE) {
                bottomLayout.setVisibility(View.INVISIBLE);
                tvForFour.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                bottomLayout.startAnimation(animation);
                tvForFour.startAnimation(animation);
                line3.startAnimation(animation);
            }
        } else if(score >= 4.5) {
            tvForFive.setVisibility(View.INVISIBLE);
            tvCountFiveForFive.setVisibility(View.INVISIBLE);
            bottomLayout.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
        } else if(score < 4.5 && score >= 3.5) {
            bottomLayout.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);
            tvForFour.setVisibility(View.INVISIBLE);

            tvForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(handleNotes.getFiveWithFive());
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.INVISIBLE);
        } else if (score < 3.5) {
            tvForFive.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            tvCountFiveForFive.setText(handleNotes.getFiveWithFive());
            tvCountFiveForFive.setVisibility(View.VISIBLE);
            tvCountFiveForFour.setText(handleNotes.getFourWithFive());
            tvCountFourForFour.setText(handleNotes.getFourWithFour());
            line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.VISIBLE);
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

