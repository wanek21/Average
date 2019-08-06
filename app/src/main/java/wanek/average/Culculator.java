package wanek.average;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import io.github.tonnyl.whatsnew.WhatsNew;
import io.github.tonnyl.whatsnew.item.WhatsNewItem;


public class Culculator extends AppCompatActivity{ // ca-app-pub-1507333058310304~5364129323 // новый - ca-app-pub-9940096249715523~4395113228
                                                   // ca-app-pub-1507333058310304/6657740130 - идент. рекламного блока
                                                   // device ID CC3FD7DFEF4BA9E928027A5C27443739

    Toolbar toolbar;
    ConstraintLayout mainLayout;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    MyPageAdapter myPageAdapter;
    LinearLayout culcLayout;
    int COLOR_ITEM = 0;
    Window window;
    final String[] chooseColor = {"Тема 1","Тема 2","Тема 3","Тема 4", "Тема 5", "Тема 6","Тема 7","Тема 8"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culculator);

        viewPager = findViewById(R.id.pager);
        mainLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Калькулятор оценок");
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPageAdapter);
        viewPager.setCurrentItem(1);

        // что нового
        WhatsNew whatsNew = WhatsNew.newInstance(
                new WhatsNewItem("Правила вывода оценок в аттестат", "Смахните экран вправо, чтобы узнать по какому принципу выставляются оценки"),
                new WhatsNewItem("Изменение темы","Теперь вы можете поменять цветовую тему, для этого нажмите на меню вверху справа и выберите 'сменить тему'"),
                new WhatsNewItem("Мелкие исправления и изменения","")
        );
        whatsNew.setTitleText("Что нового?");
        whatsNew.setButtonText("Продолжить");
        whatsNew.presentAutomatically(Culculator.this);

        window = this.getWindow();

        getColorItem();
        setColorTop();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_review:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
                case R.id.action_settings:
                    showDialog(0);
                    break;
        }

        return super.onOptionsItemSelected(item);
    }
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Culculator.this);
        builder.setTitle("Тема оформления")
                .setNeutralButton("Назад",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        })
                .setSingleChoiceItems(chooseColor, getColorItem(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                COLOR_ITEM = item;
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("colorItem",item);
                                editor.apply();
                                setColorItem(); // изменение цвета  всех элементов
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }
    private int getColorItem() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        COLOR_ITEM = sharedPreferences.getInt("colorItem",0);
        return COLOR_ITEM;
    }
    private void setColorItem() {
        switch (COLOR_ITEM) {
            case 0:
                setColorTop();
                setColorViews();
                break;
            case 1:
                setColorTop();
                setColorViews();
                break;
            case 2:
                setColorTop();
                setColorViews();
                break;
            case 3:
                setColorTop();
                setColorViews();
                break;
            case 4:
                setColorTop();
                setColorViews();
                break;
            case 5:
                setColorTop();
                setColorViews();
                break;
            case 6:
                setColorTop();
                setColorViews();
                break;
            case 7:
                setColorTop();
                setColorViews();
                break;
        }
    }
    public void setColorViews() { // установка цвета для фона и кнопок
        View viewOfFragmentCulc = myPageAdapter.getRegisteredFragment(1).getView();
        View viewOfFragmentInfo = myPageAdapter.getRegisteredFragment(0).getView();

        switch(COLOR_ITEM) {
            case 0: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_0));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_0));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_0));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_0));
                    break;
            case 1: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                           .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark1));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark1));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_1));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_1));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_1));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_1));
                    break;
            case 2: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_2));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_2));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_2));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_2));
                    break;
            case 3: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark3));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark3));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_3));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_3));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_3));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_3));
                    break;
            case 4: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark4));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark4));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_4));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_4));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_4));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_4));
                    break;
            case 5: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark5));
                    viewOfFragmentInfo.findViewById(R.id.infoLayout)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark5));
                    viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_5));
                    viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_5));
                    viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_5));
                    viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_5));
                    break;
            case 6: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark6));
                viewOfFragmentInfo.findViewById(R.id.infoLayout)
                        .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark6));
                viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_6));
                viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_6));
                viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_6));
                viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_6));
                break;
            case 7: viewOfFragmentCulc.findViewById(R.id.culcLayout)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark7));
                viewOfFragmentInfo.findViewById(R.id.infoLayout)
                        .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark7));
                viewOfFragmentCulc.findViewById(R.id.button_2).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.button_3).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.button_4).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.button_5).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.btnDel).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.btnDown).setBackground(ContextCompat.getDrawable(this,R.drawable.btn_back_7));
                viewOfFragmentCulc.findViewById(R.id.line1).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_7));
                viewOfFragmentCulc.findViewById(R.id.line2).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_7));
                viewOfFragmentCulc.findViewById(R.id.line3).setBackground(ContextCompat.getDrawable(this,R.drawable.gradient_7));
                break;
        }
    }
    public void setColorTop() { // установка цвета для статус-бара и toolbar
        switch (COLOR_ITEM) {
            case 0: toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary0));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark0));
                }
                break;
            case 1: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark1));
                }
                break;
            case 2: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary2));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark2));
                }
                break;
            case 3: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary3));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark3));
                }
                break;
            case 4: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary4));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark4));
                }
                break;
            case 5: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary5));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark5));
                }
                break;
            case 6: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary6));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark6));
                }
                break;
            case 7: toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary7));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark7));
                }
                break;
        }
    }
}

