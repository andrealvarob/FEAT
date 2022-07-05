package pit.feat;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pit.feat.insideApp.MainUserView;
import pit.feat.models.User_model;
import pit.feat.loginEmailPassword.FirstTimeLoginEmailPassword;
import pit.feat.loginEmailPassword.SignUpEmailPassword;
import pit.feat.loginFacebook.FirstTimeLoginFacebook;
import pit.feat.utility.CryptoUtils;

public class MainActivity extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private static final String TAG = "EmailPassword";

    private GoogleApiClient client;

    private CryptoUtils crypto;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;

    private ProgressBar progressBar;
    private LinearLayout main_activity_Linear;


    private LoginButton loginButton;
    private Button myLoginButton;

    private EditText user_email;
    private EditText password;

    private TextView or_word;
    private TextView signup;

    Profile profile;

    private CallbackManager mCallbackManager;
    //Facebook CallBack. When an authentication with facebook is attempted this Callback is called
    //It has 3 methods, each one of them corresponds to one possible output of the authentication try

    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
        @Override
        //In case the authentication is successful
        public void onSuccess(LoginResult loginResult) {
            main_activity_Linear.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.VISIBLE);
            progressBar.bringToFront();
            //Get the token given by the LoginResult
            AccessToken accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();

            //User.getInstance().setUser_profile(profile);
            //Self explanatory
            handleFacebookAccessToken(loginResult.getAccessToken());
            //handleFacebookAccessToken(loginResult.getAccessToken());
            //profile.getId();
        }

        //If it's canceled
        @Override
        public void onCancel() {

        }

        //If there's an error
        @Override
        public void onError(FacebookException error) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //AuthStateListener is an Object that "listens" to any change on the Authentication state.
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            //When the state changes.
            @Override
            public void onAuthStateChanged(final FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();



                if (user != null) {
                    //goToFirstTimeLoginEmailPassword();
                    // User is signed in




                    //Check node existence :DDDD
                    //Get a reference to the user id node on the Database
                    DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
                    //Add a listener to the reference.
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        //When data changes, this code is executed. But it's executed on the first run as well.
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //If the User id exists, proceed to the MainUserView
                            if(dataSnapshot.exists() == true){
                                goToMainUserView();
                            }
                            //If not, register it on the database
                            else{
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                firebaseDatabase.getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("facebook_id").setValue(Profile.getCurrentProfile().getId());
                                firebaseDatabase.getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("name").setValue(Profile.getCurrentProfile().getName());
                                //Go to the Activity which gets the user data.
                                goToFirstLoginFacebook();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    //goToFirstLoginFacebook();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };






        mCallbackManager = CallbackManager.Factory.create();
        //Get the font CaviarDreams
        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");

        //Instantiate all the views
        loginButton = (LoginButton) findViewById(R.id.login_button);
        myLoginButton = (Button) findViewById(R.id.myloginButton);
        user_email = (EditText) findViewById(R.id.user_email);
        password = (EditText) findViewById(R.id.password);

        or_word = (TextView) findViewById(R.id.line_or_line2);
        signup = (TextView) findViewById(R.id.signup_text);

        progressBar = (ProgressBar) findViewById(R.id.main_activity_progressbar);

        main_activity_Linear = (LinearLayout) findViewById(R.id.main_activity_Linear);

        //Set the permissions asked to facebook user
        loginButton.setReadPermissions("user_friends", "email", "public_profile");
        //Register the previously created CallBackManager, and CallBack
        loginButton.registerCallback(mCallbackManager, mCallback);


        loginButton.setTypeface(font);

        myLoginButton.setTypeface(font);

        myLoginButton.setTranslationX(10);


        //Was user for email+password login. Not needed anymore. DELETE IT
        myLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = user_email.getText().toString();
                String pass = crypto.generateHash(password.getText().toString());
                //loginEmailPassword(email, pass);


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToSignUpEmailPassword();
            }
        });

        ////END OF DELETE IT

        user_email.setTypeface(font);
        password.setTypeface(font);

        or_word.setTypeface(font);
        signup.setTypeface(font);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }

    public void goToFirstLoginFacebook(){
        Intent intent = new Intent(this, FirstTimeLoginFacebook.class);
        //intent.
        startActivity(intent);

    }

    public void goToWelcomeActivity(){
        Intent intent = new Intent(this, WelcomeScreen.class);
        //intent.
        startActivity(intent);
    }

    public void goToMainUserView(){
        Intent intent = new Intent(this, MainUserView.class);
        //intent.
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //OnStart of the activity
    @Override
    public void onStart() {
        super.onStart();

        //Add the AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://pit.feat/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    //When the activity is stopped
    @Override
    public void onStop() {
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://pit.feat/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void goToFirstTimeLoginFacebook(){
        Intent intent = new Intent(this.getApplicationContext(), FirstTimeLoginFacebook.class);
        startActivity(intent);
    }

    public void goToSignUpEmailPassword(){
        Intent intent = new Intent(this.getApplicationContext(), SignUpEmailPassword.class);
        startActivity(intent);
    }

    public void goToFirstTimeLoginEmailPassword(){
        Intent intent = new Intent(this.getApplicationContext(), FirstTimeLoginEmailPassword.class);
        startActivity(intent);
    }


    //Check if the accessToken is ok
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void loginEmailPassword(String email, String pass){

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            goToFirstTimeLoginEmailPassword();
                        }
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }


}