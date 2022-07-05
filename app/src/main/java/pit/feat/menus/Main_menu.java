package pit.feat.menus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import pit.feat.OpeningActivity;
import pit.feat.R;
import pit.feat.User;
import pit.feat.WelcomeScreen;
import pit.feat.chat.FeatsChats;

/**
 * Created by Usuário on 19/05/2016.
 */
public class Main_menu extends Fragment {

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private LinearLayout menu_body;
    private LinearLayout menu_bar;
    private LinearLayout button_help;

    private LoginManager loginManager;


    private ImageView menu_button;
    private ImageView chat_button;


    private TextView username;
    private TextView fetiches;
    private ProfilePictureView user_pic;

    private String userfacebookid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //INSTANTIATIONS

        //Instantiate the animation. Gets it from the assets.
        final Animation animation = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(), R.anim.menu_animation);

        //Inflate the menuLayout, so that we can have access to its Views.
        View view = inflater.inflate(R.layout.main_menu_layout, container, false);


        //Get font from Assets
        Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "CaviarDreams.ttf");
        Bitmap pic= null;

        //Get user reference
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Get database reference
        firebaseDatabase = FirebaseDatabase.getInstance();

        menu_bar = (LinearLayout) view.findViewById(R.id.menu_bar);
        menu_body = (LinearLayout) view.findViewById(R.id.menu_body);
        menu_button = (ImageView) view.findViewById(R.id.menu_button);
        chat_button = (ImageView) view.findViewById(R.id.main_menu_chaticon);

        button_help = (LinearLayout) view.findViewById(R.id.menu_help_button);

        username = (TextView) view.findViewById(R.id.user_name_textview);
        fetiches = (TextView) view.findViewById(R.id.user_fetiches_textview);
        user_pic = (ProfilePictureView) view.findViewById(R.id.user_profile_pic);

        //END OF INSTANTIATIONS

        //CONFIGURATIONS

        username.setTypeface(font);
        fetiches.setTypeface(font);

        username.setText(user.getDisplayName().toString());

        menu_body.setVisibility(View.INVISIBLE);
        menu_bar.setVisibility(View.VISIBLE);

        //END OF CONFIGURATIONS


        //LISTENERS AND LOGICS

        //See if user is null
        if(user == null){
           goToWelcomeScreen();
        }


        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatsScreen();
            }
        });


        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                goToWelcomeScreen();
            }
        });






        //fetiches.setText("Adicione seus fetiches ;)");

        /*
        if(user.getProviderId().equalsIgnoreCase("facebook")){

        }
        */

        firebaseDatabase.getReference().child("Users").child(user.getUid()).child("facebook_id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userfacebookid = dataSnapshot.getValue().toString();
                user_pic.setProfileId(userfacebookid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //user_pic.setProfileId(User.getInstance().getUser_profile_id());



        //menu_bar.bringToFront();

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu_body.getVisibility() == View.INVISIBLE) {
                    menu_body.setVisibility(View.VISIBLE);
                    animation.setDuration(250);
                    menu_body.setAnimation(animation);
                    menu_body.animate();
                    animation.start();
                    menu_body.bringToFront();

                } else {
                    menu_body.setVisibility(View.INVISIBLE);
                }
            }
        });






        return view;
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


    private void goToOpeningScreen() {
        Intent intent = new Intent(this.getContext(), OpeningActivity.class);
        //intent.
        startActivity(intent);
    }

    public void goToWelcomeScreen(){
        Intent intent = new Intent(this.getContext(), WelcomeScreen.class);
        //intent.
        startActivity(intent);
    }


    public void goToChatsScreen(){
        Intent intent = new Intent(this.getContext(), FeatsChats.class);
        //intent.
        startActivity(intent);
    }
}
