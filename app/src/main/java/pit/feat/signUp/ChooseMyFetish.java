package pit.feat.signUp;

import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pit.feat.MainActivity;
import pit.feat.R;
import pit.feat.insideApp.MainUserView;
import pit.feat.utility.ExpandListAdapter;
import pit.feat.utility.ExpandListChild;
import pit.feat.utility.ExpandListGroup;
import pit.feat.utility.SecretTextView;
import pit.feat.utility.TotalListener;

public class ChooseMyFetish extends AppCompatActivity implements TotalListener{


    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;

    private ValueEventListener fetish_listener;
    private DatabaseReference fetish_listRef;


    private SecretTextView textView;

    private ExpandableListView fetish_expandableListView;

    private Button continueButton;

    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;

    ArrayList<String> fetiches;
    ArrayList<String> descricoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_my_fetish);


        //INSTANTIATIONS

        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Textview

        textView = (SecretTextView) findViewById(R.id.choose_my_fetish_textView);
        continueButton = (Button) findViewById(R.id.choose_my_fetish_button);

        //Arrays
        fetiches = new ArrayList<String>();



        descricoes = new ArrayList<String>();

        //ExpandableListView instantiation. It's used inside the ScrollView.
        //ExpandableListView is used for the "dropdown" effect. So when we click the fetish name,
        //the description appears below
        fetish_expandableListView = (ExpandableListView) findViewById(R.id.choose_my_fetish_ExpandableListView);

        //ExpListItems = loadFetishList();
        //loadFetishList();
        //ExpandListAdapter Instantiation. Needed to control the ExpandableListView behavior
        ExpAdapter = new ExpandListAdapter(this, fetiches, descricoes);


        //fetish_list Reference instantiation
        fetish_listRef = firebaseDatabase.getReference().child("Fetish_List");

        //END OF INSTANTIATIONS

        //CONFIGURATIONS

        textView.setTypeface(font);
        textView.show();

        //If one value isn't added at the instantiation, it will crash on RunTime
        fetiches.add("loading");

        //If one value isn't added at the instantiation, it will crash on RunTime
        descricoes.add("loading");

        continueButton.setTypeface(font);


        //END OF CONFIGURATIONS

        //LISTENERS


        //Add ClickListener to Continue Button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ArrayLists to hold the values.
                ArrayList<Boolean> checkboxes = ExpAdapter.getSelectedParentCheckBoxesState();
                ArrayList<String> fetish_names = ExpAdapter.getTestgroupData();
                ArrayList<String> user_fetish = new ArrayList<String>();

                //Check which CheckBoxes are checked, and add their respective name to user_fetish array
                for(int i = 0; i < fetish_names.size(); i++){
                    if(checkboxes.get(i) == true){
                        user_fetish.add(fetish_names.get(i));
                    }

                }
                //Add all the chosen values to the user "my_fetish" node
                for(int j = 0; j <user_fetish.size(); j++) {

                    firebaseDatabase.getReference().child("Users").child(user.getUid()).child("my_fetish").child(user_fetish.get(j)).setValue(true);

                }
               goToChooseOthersFetish();
            }

        });





        //ValueEventListener. Listens to one value on the Database.
        fetish_listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                fetiches = new ArrayList<String>();
                descricoes = new ArrayList<String>();



                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    fetiches.add(dsp.getKey()); //add result into array list
                    descricoes.add(dsp.child("description").getValue().toString());



                }


                ExpAdapter = new ExpandListAdapter(getApplicationContext(), fetiches, descricoes);
                ExpAdapter.setmListener(ChooseMyFetish.this);
                fetish_expandableListView.setAdapter(ExpAdapter);
                setListViewHeight(fetish_expandableListView, 0);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //Add Listener to the database Reference
        fetish_listRef.addListenerForSingleValueEvent(fetish_listener);



        //Set the ExpandListAdapter
        ExpAdapter.setmListener(this);

        fetish_expandableListView.setAdapter(ExpAdapter);
        setListViewHeight(fetish_expandableListView, 0);

        //Add Listener to act when a group of the list is selected.
        fetish_expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }

    //Method that expands the list Height. Because the height is variable, since the List is Expandable and changes its
    //size each time it is expanded or reduced.
    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 100;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }




    /////////////////////Method not used, caused crash.

    public void loadFetishList() {


        DatabaseReference fetish_listRef = firebaseDatabase.getReference().child("Fetish_List");

        firebaseDatabase.getReference().child("Fetish_List").child("BBW").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetiches.add(dataSnapshot.getValue().toString());
                descricoes.add(dataSnapshot.child("description").toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

                fetish_listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> lst = new ArrayList<String>(); // Result will be holded Here
                        Toast myToast = Toast.makeText(getApplicationContext(), "on data change ", Toast.LENGTH_LONG);
                        myToast.show();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            fetiches.add(dsp.getValue().toString()); //add result into array list
                            descricoes.add(dsp.child("description").getValue().toString());
                            Toast myToast2 = Toast.makeText(getApplicationContext(), "Pegou Key: " + dsp.getKey(), Toast.LENGTH_LONG);
                            myToast2.show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast myToast = Toast.makeText(getApplicationContext(), "Database error ", Toast.LENGTH_LONG);
                        myToast.show();
                    }
                });

    }



    ///////////


    @Override
    protected void onStop() {
        super.onStop();
        fetish_listRef.removeEventListener(fetish_listener);
    }

    @Override
    public void onTotalChanged(int sum) {

    }

    @Override
    public void expandGroupEvent(int groupPosition, boolean isExpanded) {
        if(isExpanded)
            fetish_expandableListView.collapseGroup(groupPosition);
        else
            fetish_expandableListView.expandGroup(groupPosition);
    }


    public void goToChooseOthersFetish(){
        Intent intent = new Intent(this, ChooseOthersFetish.class);
        //intent.
        startActivity(intent);
    }

}
