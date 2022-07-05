package pit.feat;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.w3c.dom.Text;

import pit.feat.utility.SecretTextView;

public class WelcomeScreen extends AppCompatActivity {

    private ImageView circleSearch;
    private ImageView circlePepper;
    private ImageView circleKey;


    private LinearLayout centerLinearLayout;
    private ImageView circleCenter;
    private SecretTextView textViewCenter;

    private Animation pulse;
    private Animation pulse_up;

    private Button continueButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        FirebaseAuth.getInstance().signOut();

        //Instantiate


        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        pulse_up = AnimationUtils.loadAnimation(this, R.anim.pulse_up);

        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");

        continueButton = (Button) findViewById(R.id.welcomescreen_continuebutton);


        circleSearch = (ImageView) findViewById(R.id.welcomescreen_circle_search);
        circlePepper = (ImageView) findViewById(R.id.welcomescreen_circle_pepper);
        circleKey = (ImageView) findViewById(R.id.welcomescreen_circle_key);


        centerLinearLayout = (LinearLayout) findViewById(R.id.welcomescreen_center_LinearLayout);
        circleCenter = (ImageView) findViewById(R.id.welcomescreen_centercircle);
        circleCenter.setScaleY(0);
        circleCenter.setScaleX(0);

        textViewCenter = (SecretTextView) findViewById(R.id.welcomescreen_center_TextView);
        textViewCenter.setVisibility(View.INVISIBLE);
        textViewCenter.setTypeface(font);
        textViewCenter.setTextColor(ContextCompat.getColor(this.getApplicationContext(), R.color.button_color_1));

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen();
            }
        });




        circleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circleSearch.startAnimation(pulse);

                circleSearch.setClickable(false);
                circlePepper.setClickable(false);
                circleKey.setClickable(false);

                new CountDownTimer(1000, 300){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circleSearch.setScaleY(0);
                        circleSearch.setScaleX(0);
                        circleCenter.setImageResource(R.drawable.icon_circle_search);
                        circleCenter.startAnimation(pulse_up);
                        textViewCenter.setText("Search Icon");
                        textViewCenter.setVisibility(View.VISIBLE);
                        textViewCenter.show();
                        circleCenter.setScaleX(1);
                        circleCenter.setScaleY(1);
                    }
                }.start();

                new CountDownTimer(8000, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        textViewCenter.hide();
                        textViewCenter.setVisibility(View.INVISIBLE);
                        circleCenter.startAnimation(pulse);

                    }
                }.start();

                new CountDownTimer(9000,100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circleCenter.setScaleX(0);
                        circleCenter.setScaleY(0);
                        circleSearch.startAnimation(pulse_up);
                        circleSearch.setScaleY(1);
                        circleSearch.setScaleX(1);
                        circleSearch.setClickable(true);
                        circlePepper.setClickable(true);
                        circleKey.setClickable(true);
                    }
                }.start();



            }
        });


        circlePepper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circlePepper.startAnimation(pulse);
                circleSearch.setClickable(false);
                circlePepper.setClickable(false);
                circleKey.setClickable(false);


                new CountDownTimer(1000, 300){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circlePepper.setScaleY(0);
                        circlePepper.setScaleX(0);
                        circleCenter.setImageResource(R.drawable.icon_circle_pepper);
                        circleCenter.startAnimation(pulse_up);
                        textViewCenter.setText("Pepper Icon");
                        textViewCenter.setVisibility(View.VISIBLE);
                        textViewCenter.show();
                        circleCenter.setScaleX(1);
                        circleCenter.setScaleY(1);
                    }
                }.start();

                new CountDownTimer(7500, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        textViewCenter.hide();
                        textViewCenter.setVisibility(View.INVISIBLE);
                        circleCenter.startAnimation(pulse);

                    }
                }.start();

                new CountDownTimer(8500,100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circleCenter.setScaleX(0);
                        circleCenter.setScaleY(0);
                        circlePepper.startAnimation(pulse_up);
                        circlePepper.setScaleY(1);
                        circlePepper.setScaleX(1);
                        circleSearch.setClickable(true);
                        circlePepper.setClickable(true);
                        circleKey.setClickable(true);
                    }
                }.start();




            }
        });


        circleKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circleKey.startAnimation(pulse);
                circleSearch.setClickable(false);
                circlePepper.setClickable(false);
                circleKey.setClickable(false);


                new CountDownTimer(1000, 300){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circleKey.setScaleY(0);
                        circleKey.setScaleX(0);
                        circleCenter.setImageResource(R.drawable.icon_circle_key);
                        circleCenter.startAnimation(pulse_up);
                        textViewCenter.setText("Key Icon");
                        textViewCenter.setVisibility(View.VISIBLE);
                        textViewCenter.show();
                        circleCenter.setScaleX(1);
                        circleCenter.setScaleY(1);
                    }
                }.start();

                new CountDownTimer(7500, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        textViewCenter.hide();
                        textViewCenter.setVisibility(View.INVISIBLE);
                        circleCenter.startAnimation(pulse);

                    }
                }.start();

                new CountDownTimer(8500,100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        circleCenter.setScaleX(0);
                        circleCenter.setScaleY(0);
                        circleKey.startAnimation(pulse_up);
                        circleKey.setScaleY(1);
                        circleKey.setScaleX(1);
                        circleSearch.setClickable(true);
                        circlePepper.setClickable(true);
                        circleKey.setClickable(true);
                    }
                }.start();


            }
        });



    }



    public void goToLoginScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }





}
