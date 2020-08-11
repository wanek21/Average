package wanek.average;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.view.Window;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    ConstraintLayout mainLayout;
    FrameLayout frameLayout;

    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    Fragment fragmentCulculator;
    FragmentTransaction fragmentTransaction;
    Window window;
    DialogFragment commentDialogFragment;

    private AdView adView;
    final static String COMMENT_DIALOG_TAG = "comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culculator);

        MobileAds.initialize(this);

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mainLayout = findViewById(R.id.main);
        frameLayout = findViewById(R.id.container);

        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        setPointSystem();
        askingToComment(7);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setPointSystem();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setPointSystem(); // при изменении бальной системы в настройках меняем на главной активити
    }

    private void setPointSystem() { // установка бальной системы на экране
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getSupportFragmentManager();
        String system = prefs.getString("switch_system","5-бальная");
        if(system.contains("5")) {
            if(prefs.getBoolean("there_is_one",false)) {
                fragmentCulculator = new RuCalculatorWithOneFragment();
            } else fragmentCulculator = new RuCalculatorFragment();
        } else if(system.contains("12")) {
            fragmentCulculator = new UkCalculatorFragment();
        }  else if(system.contains("10")) {
            fragmentCulculator = new BeCalculatorFragment();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentCulculator);
        fragmentTransaction.commit();
    }

    private void askingToComment(int onCountLaunch) { // метод отвечает за вывод просьбы написать отзыв
        sharedPreferences = getSharedPreferences("launch",MODE_PRIVATE);
        int countLaunch = sharedPreferences.getInt("countLaunch2",0);
        if(countLaunch < onCountLaunch) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch2",sharedPreferences.getInt("countLaunch2",0) + 1);
            editor.apply();
        } else if(countLaunch == onCountLaunch){
            commentDialogFragment = new MessageDialog(MessageDialog.REVIEW_DIALOG,getPackageName());
            commentDialogFragment.show(fragmentManager,COMMENT_DIALOG_TAG);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch2",sharedPreferences.getInt("countLaunch2",0) + 1);
            editor.apply();
        }
    }
}

