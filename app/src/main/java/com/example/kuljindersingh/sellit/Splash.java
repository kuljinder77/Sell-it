package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
    ImageView logo , back;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.logo);
        back = (ImageView) findViewById(R.id.back);
        appname = (TextView)findViewById(R.id.sellit);
        front_animation();
    }

    private void front_animation() {

        Animation anim_fade_in , anim_left , anim_upper;
        anim_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_from_left);
        anim_fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        anim_upper = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_from_up);

        appname.setAnimation(anim_left);
        logo.setAnimation(anim_upper);
        back.setAnimation(anim_fade_in);
        Thread splashthread = new Thread()

        {
            public void run() {
                try {
                    int waited = 0;
                    sleep(4000);
                    Intent intent = new Intent(Splash.this,
                            Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splash.this.finish();
                }

            }

        };
        splashthread.start();
    }
}
