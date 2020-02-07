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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;

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
    private AppUpdateManager appUpdateManager;
    final static String COMMENT_DIALOG_TAG = "comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culculator);

        appUpdateManager = AppUpdateManagerFactory.create(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        });

        mainLayout = findViewById(R.id.main);
        frameLayout = findViewById(R.id.container);

        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        setSystem();
        appealingToComment(7);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setSystem();
    }

    private void setSystem() {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getSupportFragmentManager();
        String system = prefs.getString("switch_system","5-бальная");
        if(system.contains("5")) {
            if(prefs.getBoolean("there_is_one",false)) {
                fragmentCulculator = new FragmentRuCalculatorWithOne();
            } else fragmentCulculator = new FragmentRuCalculator();
        } else if(system.contains("12")) {
            fragmentCulculator = new FragmentUkCalculator();
        }  else fragmentCulculator = new FragmentUkCalculator();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentCulculator);
        fragmentTransaction.commit();
    }
    private void appealingToComment(int onCountLaunch) { // метод отвечает за вывод просьбы написать отзыв
        sharedPreferences = getSharedPreferences("launch",MODE_PRIVATE);
        int countLaunch = sharedPreferences.getInt("countLaunch",0);
        if(countLaunch < onCountLaunch) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch",sharedPreferences.getInt("countLaunch",0) + 1);
            editor.commit();
        } else if(countLaunch == onCountLaunch){
            commentDialogFragment = new MessageDialog(MessageDialog.REVIEW_DIALOG,getPackageName());
            commentDialogFragment.show(fragmentManager,COMMENT_DIALOG_TAG);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch",sharedPreferences.getInt("countLaunch",0) + 1);
            editor.commit();
            return;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setSystem(); // при изменении бальной системы в настройках меняем на главной активити
    }
}

