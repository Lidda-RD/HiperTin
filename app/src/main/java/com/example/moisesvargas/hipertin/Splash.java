package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv=(ImageView) findViewById(R.id.logo_iv);
        Animation mi_animacion = AnimationUtils.loadAnimation(this,R.anim.mi_animacion);

        iv.startAnimation(mi_animacion);
        final Intent _secundario=new Intent(this,MainActivity.class);
        Thread timer = new Thread()
        {
            public void run ()
            {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(_secundario);
                    finish();
                }
            }

        };
        timer.start();
    }
}
