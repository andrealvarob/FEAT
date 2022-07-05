package pit.feat.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pit.feat.R;

/**
 * Created by Kamilla on 8/15/2016.
 */
public class FeatsListViewAdapter extends ArrayAdapter<String> {

    private final Context context;

    private FirebaseDatabase firebaseDatabase;

    private ValueEventListener featsListener;
    private ValueEventListener dataListener;

    private ArrayList<String> featsUsersArray;
    private ArrayList<String> featsPicsArray;


    public FeatsListViewAdapter(Context context, ArrayList<String> mfeatsUsersArray, ArrayList<String> mfeatsPicsArray) {
        super(context, -1);
        this.context = context;
        featsUsersArray = mfeatsUsersArray;
        featsPicsArray = mfeatsPicsArray;
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.feats_list_group, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.feats_list_group_TextView);
        ProfilePictureView profilePictureView = (ProfilePictureView) rowView.findViewById(R.id.feats_list_group_ProfilePictureView);

        textView.setText(featsUsersArray.get(position));
        profilePictureView.setProfileId(featsPicsArray.get(position));

        return rowView;
    }

    @Override
    public int getCount() {
        return featsUsersArray.size();
    }
}
