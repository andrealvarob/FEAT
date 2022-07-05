package pit.feat.signUp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import pit.feat.R;
import pit.feat.insideApp.MainUserView;
import pit.feat.utility.SecretTextView;

public class ChooseFilters extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;


    private SecretTextView oqueprocura_textview;
    private SecretTextView aquedistancia_textview;


    private CircleView circleView;
    private SeekBar seekBar;

    private CheckBox homens;
    private CheckBox mulheres;




    private Button continueButton;


    private int distance_radius;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_filters);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");

        oqueprocura_textview = (SecretTextView) findViewById(R.id.choose_filters_oqueprocura_textview);
        aquedistancia_textview = (SecretTextView) findViewById(R.id.choose_filters_aquedistancia_textview);

        circleView = (CircleView) findViewById(R.id.choose_filters_circleview);

        seekBar = (SeekBar) findViewById(R.id.choose_filters_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String pos;
                String title;

                float rad;
                float prog;



                pos = Integer.toString(progress+20);

                rad = (((float)progress + 20)/100);

                title = pos + " km";
                distance_radius = progress+20;
                circleView.setFillRadius(rad);
                circleView.setTitleText(title);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        homens = (CheckBox) findViewById(R.id.choose_filters_homem_checkbox);
        mulheres = (CheckBox) findViewById(R.id.choose_filters_mulher_checkbox);

        //distance_seekbar = (SeekBar) findViewById(R.id.choose_filters_distanceseekbar);
        //distance_tv = (TextView) findViewById(R.id.choose_filters_seekbar_textview);

        continueButton = (Button) findViewById(R.id.choose_filters_continuebutton);

        //Seekbar is the bar that defines the range of distance the person wants to find people




        homens.setTypeface(font);
        mulheres.setTypeface(font);

        continueButton.setTypeface(font);



        oqueprocura_textview.setTypeface(font);
        aquedistancia_textview.setTypeface(font);



        oqueprocura_textview.show();
        aquedistancia_textview.show();


        //Listener for click on the Continue Button.
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //seeks
                //Verify which ones are checked






                //Set the distance radius
                //distance radius
                firebaseDatabase.getReference().child("Users").child(user.getUid()).child("distance_radius").setValue(distance_radius);




                //with_who
                //Verify which ones are checked
                boolean shomem = homens.isChecked();
                boolean smulher = mulheres.isChecked();
                //Add to Database
                firebaseDatabase.getReference().child("Users").child(user.getUid()).child("with_who").child("homem").setValue(shomem);
                firebaseDatabase.getReference().child("Users").child(user.getUid()).child("with_who").child("mulher").setValue(smulher);

                goToMainUserView();

            }
        });





    }

    public void goToMainUserView(){
        Intent intent = new Intent(this, MainUserView.class);
        //intent.
        startActivity(intent);
    }
}
