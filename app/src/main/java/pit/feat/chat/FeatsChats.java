package pit.feat.chat;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pit.feat.R;

public class FeatsChats extends AppCompatActivity {

    private TextView featsTextView;
    private TextView chatsTextView;

    private ListView featsListView;
    private ListView chatsListView;

    private ArrayList<String> featsUsersArray;

    private ArrayList<String> featsUsersIdArray;
    private ArrayList<String> featsPicsArray;

    private FirebaseDatabase firebaseDatabase;

    private FeatsListViewAdapter featsListViewAdapter;

    private ValueEventListener featsListener;
    private ValueEventListener dataListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feats_chats);


        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");

        featsTextView = (TextView) findViewById(R.id.activity_feats_chats_FeatsTextView);
        chatsTextView = (TextView) findViewById(R.id.activity_feats_chats_ChatsTextView);

        featsListView = (ListView) findViewById(R.id.activity_feats_FeatsListView);
        chatsListView = (ListView) findViewById(R.id.activity_feats_ChatsListView);

        featsTextView.setTypeface(font);
        chatsTextView.setTypeface(font);

        final ArrayList<String> featsUsersArray = new ArrayList<String>();
        final ArrayList<String> featsUsersIdArray = new ArrayList<String>();
        final ArrayList<String> featsPicsArray = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        //featsListView.setAdapter(featsListViewAdapter);


        //textView.setText(values[position]);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference feats = firebaseDatabase.getReference().child("Users").child(user.getUid()).child("feats");
        final DatabaseReference usersReference = firebaseDatabase.getReference().child("Users");

        featsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    featsUsersArray.add(dsp.getKey());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //feats.addValueEventListener(featsListener);
        feats.addListenerForSingleValueEvent(featsListener);


        dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(int i = 0; i < featsUsersArray.size(); i++){
                    String userName;
                    String userPic;

                    userName = dataSnapshot.child(featsUsersArray.get(i)).child("name").getValue().toString();
                    userPic = dataSnapshot.child(featsUsersArray.get(i)).child("facebook_id").getValue().toString();

                    featsUsersIdArray.add(userName);
                    featsPicsArray.add(userPic);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };






        //usersReference.addValueEventListener(dataListener);
        usersReference.addListenerForSingleValueEvent(dataListener);

        //featsUsersIdArray.add("IWgAziJaM2ZH9OBK2YIrXRBPSb72");
        //featsPicsArray.add("131419693969986");

        featsListViewAdapter = new FeatsListViewAdapter(this.getApplicationContext(), featsUsersIdArray, featsPicsArray);

        featsListView.setAdapter(featsListViewAdapter);






    }
}
