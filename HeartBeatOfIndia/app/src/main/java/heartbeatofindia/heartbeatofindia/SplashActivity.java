package heartbeatofindia.heartbeatofindia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.sharedpreference.PrefKeys;
import heartbeatofindia.heartbeatofindia.sharedpreference.PreferenceManger;

public class SplashActivity extends AppCompatActivity {
    Animation animation;
    @BindView(R.id.app_name)
    AppCompatTextView tvAppName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        animation= AnimationUtils.loadAnimation(this,R.anim.zoom);
        tvAppName.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceManger.getPreferenceManger().getBoolean(PrefKeys.ISLOGIN))
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));

                finish();
            }
        },5500);
    }
}
