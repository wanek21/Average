package wanek.average;

import android.content.SharedPreferences;

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

import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Culculator extends AppCompatActivity {

    ConstraintLayout mainLayout;
    SharedPreferences sharedPreferences;
    FrameLayout frameLayout;
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
                Log.d("my","onLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("my","onFailedToLoad: " + String.valueOf(errorCode));
            }
        });

        mainLayout = findViewById(R.id.main);
        frameLayout = findViewById(R.id.container);

        fragmentManager = getSupportFragmentManager();
        fragmentCulculator = new FragmentCulculator();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,fragmentCulculator);
        fragmentTransaction.commit();

        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        appealingToComment();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void appealingToComment() { // метод отвечает за вывод просьбы написать отзыв
        sharedPreferences = getSharedPreferences("launch",MODE_PRIVATE);
        int countLaunch = sharedPreferences.getInt("countLaunch",0);
        if(countLaunch < 9) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch",sharedPreferences.getInt("countLaunch",0) + 1);
            editor.commit();
        } else if(countLaunch == 9){
            commentDialogFragment = new CommentDialogFragment(getPackageName());
            commentDialogFragment.show(fragmentManager,COMMENT_DIALOG_TAG);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("countLaunch",sharedPreferences.getInt("countLaunch",0) + 1);
            editor.commit();
            return;
        }
    }
}

