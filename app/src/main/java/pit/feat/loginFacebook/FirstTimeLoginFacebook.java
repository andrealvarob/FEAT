package pit.feat.loginFacebook;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import pit.feat.R;
import pit.feat.User;
import pit.feat.WelcomeScreen;
import pit.feat.insideApp.MainUserView;
import pit.feat.signUp.ChooseMyFetish;
import pit.feat.utility.SecretTextView;

//TESTE

public class FirstTimeLoginFacebook extends AppCompatActivity {

    LinearLayout menu;
    LinearLayout menuFragment;


    Firebase firebase;


    TextView username;
    TextView fetiches;

    private SecretTextView welcomeText;
    private SecretTextView welcomeText2;
    private SecretTextView welcomeText3;

    private TextView gender_textview;

    private EditText user_name;
    private EditText user_age;
    private RadioGroup user_gender_radioGroup;
    private RadioButton gender_masculino;
    private RadioButton gender_feminino;
    private RadioButton gender_outros;




    private Button continueButton;


    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login_facebook);

        //Firebase

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();



        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
        //if(user == null){
        //    goToWelcomeScreen();
        //}



        //Textos iniciais
        welcomeText = (SecretTextView) findViewById(R.id.first_time_login_welcome1);
        welcomeText2 = (SecretTextView) findViewById(R.id.first_time_login_welcome2);
        welcomeText3 = (SecretTextView) findViewById(R.id.first_time_login_welcome3);

        // Editboxes
        user_name = (EditText) findViewById(R.id.first_time_login_username_text);
        user_age = (EditText) findViewById(R.id.first_time_login_age_text);

        //RadioGroup

        user_gender_radioGroup = (RadioGroup) findViewById(R.id.first_time_login_gender_radio_group);

        //RadioButtons

        gender_masculino = (RadioButton) findViewById(R.id.first_time_login_gender_radio_button_masculino);
        gender_feminino = (RadioButton) findViewById(R.id.first_time_login_gender_radio_button_feminino);
        gender_outros = (RadioButton) findViewById(R.id.first_time_login_gender_radio_button_outros);

        //Button

        continueButton = (Button) findViewById(R.id.first_time_login_button);

        gender_textview = (TextView) findViewById(R.id.first_time_login_gender_text);




        //Set fonts
        welcomeText.setTypeface(font);
        welcomeText2.setTypeface(font);
        welcomeText3.setTypeface(font);




        user_name.setTypeface(font);
        user_age.setTypeface(font);

        gender_masculino.setTypeface(font);
        gender_feminino.setTypeface(font);
        gender_outros.setTypeface(font);

        gender_textview.setTypeface(font);

        continueButton.setTypeface(font);

        //Listeners

        user_gender_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                int age;
                String gender;
                RadioButton checked_gender;

                checked_gender = (RadioButton) findViewById(user_gender_radioGroup.getCheckedRadioButtonId());

                if (user_name.getText() == null || user_age.getText() == null) {

                } else {

                    name = user_name.getText().toString();
                    age = Integer.parseInt(user_age.getText().toString());
                    gender = checked_gender.getText().toString();

                    firebaseDatabase.getReference().child("Users").child(user.getUid()).child("name").setValue(name);
                    firebaseDatabase.getReference().child("Users").child(user.getUid()).child("age").setValue(age);
                    firebaseDatabase.getReference().child("Users").child(user.getUid()).child("gender").setValue(gender);

                    goToChooseMyFetish();
                }
            }
        });

        //Database data access



        firebaseDatabase.getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("name").exists() == true) {
                    String n = dataSnapshot.child("name").getValue().toString();
                    user_name.setText(n);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        /////


        welcomeText.show();

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                welcomeText2.show();
            }
        }.start();

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                welcomeText3.show();
            }
        }.start();

    }


    public void goToWelcomeScreen(){
        Intent intent = new Intent(this, WelcomeScreen.class);
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

}
