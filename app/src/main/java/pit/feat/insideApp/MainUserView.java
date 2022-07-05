package pit.feat.insideApp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pit.feat.R;


public class MainUserView extends AppCompatActivity {


    private LocationManager locationManager;
    private LocationListener locationListenerGPS;
    private Looper loop;

    private boolean isLocationReady;


    private ImageView button_dislike;
    private ImageView button_like;


    private ProgressBar loadingCircle;
    private LinearLayout mainUserScreen;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference locationReference;
    private Firebase firebase;
    private FirebaseUser user;

    private GeoFire geoFire;

    private  ArrayList<String> usersAvailable;

    String username;
    String userage;
    String user_facebook_id;

    private ProfilePictureView user_profile_pic;
    private TextView userdata_textview;

    //SEARCH VIDEO OBJECTS
    private FrameLayout videoFrame;
    private VideoView videoView;

    private ImageView eaIcon;



    String latitude;
    String longitude;

    private boolean checkLocation;


    LinearLayout menuLayout;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_view);

        firebase = new Firebase("https://feat-cef62.firebaseio.com/Users_location");

        Typeface font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
        usersAvailable = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();


        user = FirebaseAuth.getInstance().getCurrentUser();

        geoFire = new GeoFire(firebase);


        isLocationReady = false;

        loadingCircle = (ProgressBar) findViewById(R.id.main_user_view_progressbar);
        mainUserScreen = (LinearLayout) findViewById(R.id.main_user_view_screen);

        menuLayout = (LinearLayout) findViewById(R.id.main_user_view_menu_linear);
        menuLayout.bringToFront();

        button_dislike = (ImageView) findViewById(R.id.main_user_view_dislike_image);
        button_like = (ImageView) findViewById(R.id.main_user_view_like_image);

        user_profile_pic = (ProfilePictureView) findViewById(R.id.main_user_view_target_data_profile_image);
        userdata_textview = (TextView) findViewById(R.id.main_user_view_target_data_text);

        /// SEARCH ANIMATION FRAME
        videoFrame = (FrameLayout) findViewById(R.id.main_user_view_videoFrame);
        videoView = (VideoView) findViewById(R.id.main_user_view_videoView);

        eaIcon = (ImageView) findViewById(R.id.main_user_view_eaIcon);

        //START SEARCH ANIMATION
        loadingCircle.setVisibility(View.INVISIBLE);
        videoFrame.setVisibility(View.VISIBLE);
        videoFrame.bringToFront();
        eaIcon.bringToFront();
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mediaController.setAnchorView(videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.searchvideo);

        videoView.setVideoURI(video);

        //featLogo.setBackgroundColor(Color.TRANSPARENT);
        //featLogo.setBackgroundColor(Color.parseColor("#404059"));
        //featLogo.setZOrderOnTop(true);
        videoView.setMediaController(mediaController);

        //videoView.setBackgroundColor(Color.parseColor("#404059"));
        //videoView.setZOrderOnTop(true);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setBackgroundColor(Color.TRANSPARENT);

                videoView.start();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setBackgroundColor(Color.TRANSPARENT);
                //videoView.setBackgroundColor(Color.parseColor("#404059"));
                videoView.start();
            }
        });


        //EA ANIMATION

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(eaIcon,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(600);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();




        userdata_textview.setTypeface(font);






        //loadingCircle.bringToFront();

        button_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




        button_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast myToast = Toast.makeText(getApplicationContext(), "Dislike", Toast.LENGTH_LONG);
                myToast.show();


                DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(usersAvailable.get(1));

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        username = dataSnapshot.child("name").getValue().toString();
                        userage = dataSnapshot.child("age").getValue().toString();
                        user_facebook_id = dataSnapshot.child("facebook_id").getValue().toString();

                        user_profile_pic.setProfileId(user_facebook_id);
                        userdata_textview.setText(username + ", " + userage);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*
                geoFire.setLocation("Satelite", new GeoLocation((-5.855100), (-35.247874)));
                geoFire.setLocation("Prudente", new GeoLocation(-5.869083, -35.243184));
                geoFire.setLocation("Parnamirim", new GeoLocation(-5.908747, -35.270981));
                geoFire.setLocation("Panificadora Bomfim", new GeoLocation(-5.836843, -35.219089));
                geoFire.setLocation("Midway", new GeoLocation(-5.811242, -35.206041));
                geoFire.setLocation("Forte Reis Magos", new GeoLocation(-5.756475, -35.194691));
                */
            }
        });


        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /*
        locationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());

                geoFire.setLocation(user.getUid(), new GeoLocation(location.getLatitude(), location.getLongitude()));

                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(Double.parseDouble(latitude), Double.parseDouble(longitude)), 100);


                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        if(key != user.getUid()) {

                            usersAvailable.add(key);
                            //Set first user to shusersAvailable.get(0);
                            // usersAvailable.get(0)


                            if (usersAvailable.size() <= 1) {
                                DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(usersAvailable.get(0));

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        username = dataSnapshot.child("name").getValue().toString();
                                        userage = dataSnapshot.child("age").getValue().toString();
                                        user_facebook_id = dataSnapshot.child("facebook_id").getValue().toString();

                                        user_profile_pic.setProfileId(user_facebook_id);
                                        userdata_textview.setText(username + ", " + userage);


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onKeyExited(String key) {

                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(FirebaseError error) {

                    }
                });




                loadingCircle.setVisibility(View.INVISIBLE);

                mainUserScreen.setVisibility(View.VISIBLE);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }

        locationManager.requestLocationUpdates("gps", 5000, 10, locationListenerGPS);




        //locationManager.requestLocationUpdates("gps", 5000, 30, locationListenerGPS);

        */


    }



    private ArrayList<String> usersAvailable(){






        return  null;
    }





    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public void toggleGPSUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }


    public boolean checkLocation(){
        return checkLocation;
    }


}
