package hasan.app.com.ngodemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //A Handler allows you to send and process Message and Runnable objects associated
        // with a thread's MessageQueue. Each Handler instance is associated with a single
        // thread and that thread's message queue. When you create a new Handler, it is
        // bound to the thread / message queue of the thread that is creating it -- from
        // that point on, it will deliver messages and runnables to that message queue
        // and execute them as they come out of the message queue.

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent startActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startActivityIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}