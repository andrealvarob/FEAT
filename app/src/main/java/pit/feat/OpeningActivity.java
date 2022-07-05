package pit.feat;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sprylab.android.widget.TextureVideoView;

import pit.feat.insideApp.MainUserView;
import pit.feat.loginFacebook.FirstTimeLoginFacebook;
import pit.feat.signUp.ChooseFilters;
import pit.feat.signUp.ChooseMyFetish;
import pit.feat.utility.FirebaseTest;

public class OpeningActivity extends AppCompatActivity {

    private LinearLayout devicescreen;
    private VideoView featLogo;


    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private String facebookid;
    private Boolean isLogged = false;
    private CountDownTimer countDownTimer;

    AccessTokenTracker accessTokenTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        //Initialize Facebook SDK so we can use it
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Get token, update if there's a new one. Not sure what is it for, but ptobably
        //is used to get track of the user permission to the Profile
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        Animation tween = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.tween);

        //Get the reference from the LinearLayout of the OpeningActivity, which corresponds
        //to the entire screen view
        devicescreen = (LinearLayout) findViewById(R.id.device_screen);
        //MediaController
        //MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mediaController.setAnchorView(featLogo);
        featLogo = (VideoView) findViewById(R.id.openingActivity_LogoImageView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.featlogo);

        featLogo.setVideoURI(video);

        //featLogo.setBackgroundColor(Color.TRANSPARENT);
        //featLogo.setBackgroundColor(Color.parseColor("#404059"));
        //featLogo.setZOrderOnTop(true);
        featLogo.setMediaController(mediaController);

        featLogo.setBackgroundColor(Color.parseColor("#404059"));
        featLogo.setZOrderOnTop(true);
        featLogo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                featLogo.setBackgroundColor(Color.TRANSPARENT);
                featLogo.start();
            }
        });



        /*
        final ObjectAnimator objAnim;

        objAnim= ObjectAnimator.ofPropertyValuesHolder(featLogo, PropertyValuesHolder.ofFloat("scaleX", 1.1f), PropertyValuesHolder.ofFloat("scaleY", 1.1f));
        objAnim.setDuration(500);

        objAnim.setRepeatCount(ObjectAnimator.INFINITE);

        objAnim.setDuration(1000);
        objAnim.setRepeatMode(ObjectAnimator.REVERSE);
        objAnim.start();
        */

        //Get the user logged in. It gets the FirebaseAuth Class, which manages all the Auth operations
        //get the instance of it, and get the current user logged in
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Get the FirebaseDatabase instance, so that we can insert and retrieve data from the DB.
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Get the FirebaseAuth instance.
        firebaseAuth = FirebaseAuth.getInstance();




        //Timer to go to the next activity. Added because people might think that the opening screen was a
        //loading one, and would wait too much to click on it. Leading to a not so good experience about the app
        new CountDownTimer(3000, 300) {

            public void onTick(long millisUntilFinished) {
                // Does nothing on Tick (100ms tick)
                //featLogo.animate();
                //objAnim.start();
            }

            public void onFinish() {
                //Does this when finished. (3000ms passed)
                if(isLogged == false) {

                    //If there isnt a user logged in
                    goToWelcomeActivity();
                    //goToMainUserView();
                    //goToFirstTimeLoginFacebook();
                    //goToChooseMyFetish();
                }
                else{
                    //if user is logged in
                    goToMainUserView();
                }
            }

        };

        //Set a Click Listener for the LinearLayout, so that when the user click on the screen, this code is executed
        devicescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                goToWelcomeActivity();
                //goToChooseFilters();

                /*
                if(isLogged == false) {


                    goToWelcomeActivity();
                    //goToMainUserView();
                    //goToFirstTimeLoginFacebook();
                    //goToChooseMyFetish();
                }
                else{
                    goToMainUserView();
                }
                */



            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        featLogo.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        featLogo.pause();
    }

    public void goToLoginScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void goToFirstTimeLoginFacebook(){
        Intent intent = new Intent(this, FirstTimeLoginFacebook.class);
        startActivity(intent);
    }

    public void goToWelcomeActivity(){
        Intent intent = new Intent(this, WelcomeScreen.class);
        //intent.
        startActivity(intent);
    }

    public void goToFirebaseTest(){
        Intent intent = new Intent(this, FirebaseTest.class);
        //intent.
        startActivity(intent);
    }

    public void goToMainUserView(){
        Intent intent = new Intent(this, MainUserView.class);
        //intent.
        startActivity(intent);
    }

    public void goToChooseMyFetish(){
        Intent intent = new Intent(this, ChooseMyFetish.class);
        //intent.
        startActivity(intent);
    }

    public void goToChooseFilters(){
        Intent intent = new Intent(this, ChooseFilters.class);
        //intent.
        startActivity(intent);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        FirebaseUser user;
        if (currentAccessToken != null) {

            //MainUserView
            user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                //goToMainUserView();
                isLogged = true;
            }
            else{
                isLogged = false;
                //goToLoginScreen();
            }

        } else {
            //login
            isLogged = false;
            //goToLoginScreen();
        }
    }

}
