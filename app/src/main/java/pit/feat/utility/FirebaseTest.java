package pit.feat.utility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pit.feat.models.User_model;
import pit.feat.R;

public class FirebaseTest extends AppCompatActivity {

    private EditText ID;
    private EditText name;
    private EditText email;
    private EditText age;
    private EditText gender;
    private EditText gender_preference;

    private Button submitButton;
    private Button checkButton;

    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        firebaseDatabase = FirebaseDatabase.getInstance();
        Firebase.setAndroidContext(this);

        ID = (EditText) findViewById(R.id.firebasetest_id);
        name = (EditText) findViewById(R.id.firebasetest_name);

        age = (EditText) findViewById(R.id.firebasetest_age);
        gender = (EditText) findViewById(R.id.firebasetest_gender);
        gender_preference = (EditText) findViewById(R.id.firebasetest_gender_preference);

        submitButton = (Button) findViewById(R.id.firebasetest_button);
        checkButton = (Button) findViewById(R.id.firebasetest_button2);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = ID.getText().toString();
                String user_name = name.getText().toString();


                String user_gender = gender.getText().toString();
                String user_gender_preferences = gender_preference.getText().toString();
                User_model user = new User_model(user_id, user_name, 18, user_gender, user_gender_preferences);
                DatabaseReference UsersRef = firebaseDatabase.getReference().child("Users").getRef();
                UsersRef.child(user_id);
                DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(user_id).getRef();

                userRef.setValue(user);

                Toast myToast = Toast.makeText(getApplicationContext(), "Dados enviados!", Toast.LENGTH_LONG);
                myToast.show();
            }
        });


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DatabaseReference ref = firebaseDatabase.getReference().child("Users").getRef();

            }
        });



    }
}
