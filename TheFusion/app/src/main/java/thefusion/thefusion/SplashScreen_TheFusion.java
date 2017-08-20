package thefusion.thefusion;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

public class SplashScreen_TheFusion extends AppCompatActivity {





        @Override
        public void onCreate(Bundle savedInstanceState) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen__the_fusion);
            getSupportActionBar().hide();
            TextView textView=(TextView)findViewById(R.id.textView_thefusion);
            textView.setGravity(Gravity.CENTER);
            new CountDownTimer(3000,1000) {

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getBaseContext(), MainActivity_TheFusion.class);
                    startActivity(intent);
                    finish();

                }


                @Override
                public void onTick(long millisUntilFinished) {

                }
            }.start();

        }
    }


