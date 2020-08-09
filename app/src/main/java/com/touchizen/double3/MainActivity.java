package com.touchizen.double3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.touchizen.double3.transition3d.ActivitySwitcher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String YOUTUBE_CHANNEL = "https://www.youtube.com/channel/UCHTNaLtro_1I6Y3SSywo3Cg";
    public static final String GOOGLE_PLAYSTORE = "http://play.google.com/store/apps/details?id=";
    public static final String MARKET_URI = "market://details?id=";

    public ElegantNumberButton numberButtonX;
    public ElegantNumberButton numberButtonY;
    public ImageButton playButton;
    public ImageButton rateButton;
    public ImageButton subsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initXY();
        initButtons();
    }

    private void initXY() {
        numberButtonX = findViewById(R.id.number_buttonX);
        numberButtonY = findViewById(R.id.number_buttonY);

        numberButtonX.setRange(2, 9);
        numberButtonX.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String number = numberButtonX.getNumber();
        });

        /*  useless parts
        numberButtonX.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
        });
        */

        numberButtonY.setRange(2, 9);
        numberButtonY.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String number = numberButtonY.getNumber();
        });

        loadXY();
    }

    private void initButtons() {
        playButton = findViewById(R.id.button_play);
        rateButton = findViewById(R.id.button_rate);
        subsButton = findViewById(R.id.button_subscriptions);

        playButton.setOnClickListener(this);
        rateButton.setOnClickListener(this);
        subsButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        // animateIn this activity
        //ActivitySwitcher.animationIn(findViewById(R.id.main_container), getWindowManager());
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_play: goToPlay(); break;
            case R.id.button_rate: goToPlayStore(); break;
            case R.id.button_subscriptions: goToYoutube(); break;
            default: break;
        }
    }

    public void goToPlay() {

        int row = Integer.parseInt(numberButtonX.getNumber());
        int col = Integer.parseInt(numberButtonY.getNumber());

        Intent playIntent = new Intent(getApplicationContext(),PlayActivity.class);
        playIntent.putExtra(PlayActivity.ROW, row);
        playIntent.putExtra(PlayActivity.COL, col);
        startActivity(playIntent);
        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
        finish();
        /*
        final Intent playIntent = new Intent(getApplicationContext(), PlayActivity.class);
        // disable default animation for new intent
        playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivitySwitcher.animationOut(findViewById(R.id.main_container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                startActivity(playIntent);
            }
        });
         */
    }

    public void goToPlayStore() {
        Uri uri = Uri.parse(MARKET_URI + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GOOGLE_PLAYSTORE + getPackageName())));
        }
    }

    public void goToYoutube() {
        startActivity(new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(YOUTUBE_CHANNEL)) // edit this url
                .setPackage("com.google.android.youtube"));	// do not edit
    }

    private void loadXY() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        numberButtonX.setNumber(""+settings.getInt(PlayActivity.WIDTH, 4));
        numberButtonY.setNumber(""+settings.getInt(PlayActivity.HEIGHT, 4));
    }
}