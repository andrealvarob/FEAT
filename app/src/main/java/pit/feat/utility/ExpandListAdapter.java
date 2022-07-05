package pit.feat.utility;

import android.graphics.Typeface;
import android.util.Log;


        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseExpandableListAdapter;
        import android.widget.CheckBox;
        import android.widget.TextView;

        import java.util.ArrayList;

import pit.feat.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ABHISHEK on 5/12/2015.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroupList = new ArrayList<>();

    /*
     *  Raw Data
     */
    //String[] testChildData =  {"10","20","30", "40", "50"};
    //String[] testgroupData =  {"Apple","Banana","Mango", "Orange", "Pineapple", "Strawberry"};
    Context mContext;
    ArrayList<String> testChildData;
    ArrayList<String> testgroupData;
    ArrayList<Boolean> selectedParentCheckBoxesState = new ArrayList<>();
    TotalListener mListener;

    public void setmListener(TotalListener mListener) {
        this.mListener = mListener;
    }

    public void setmGroupList(ArrayList<ArrayList<String>> mGroupList) {
        this.mGroupList = mGroupList;
    }

    class ViewHolder {
        public CheckBox groupName;
        public TextView dummyTextView; // View to expand or shrink the list
        public TextView childTextView;
    }

    public ExpandListAdapter(Context context, ArrayList<String> _testgroupData, ArrayList<String> _testChildData) {
        mContext = context;

        this.testgroupData = _testgroupData;
        this.testChildData = _testChildData;
        //Add raw data into Group List Array
        for(int i = 0; i < testgroupData.size(); i++) {
            ArrayList<String> prices = new ArrayList<>();
            prices.add(testChildData.get(i));
            mGroupList.add(i, prices);
        }

        //initialize default check states of checkboxes
        initCheckStates(false);
    }


    public void setLists(ArrayList<String> _testgroupData, ArrayList<String> _testChildData){


        this.testgroupData = _testgroupData;
        this.testChildData = _testChildData;
        //Add raw data into Group List Array
        for(int i = 0; i < testgroupData.size(); i++) {
            ArrayList<String> prices = new ArrayList<>();
            prices.add(testChildData.get(i));
            mGroupList.add(i, prices);
        }



    }


    /**
     * Called to initialize the default check states of items
     * @param defaultState : false
     */
    private void initCheckStates(boolean defaultState) {
        for(int i = 0 ; i < mGroupList.size(); i++){
            selectedParentCheckBoxesState.add(i, defaultState);
            ArrayList<Boolean> childStates = new ArrayList<>();



        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Typeface font = Typeface.createFromAsset(parent.getContext().getAssets(), "CaviarDreams.ttf");

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_layout, null);
            holder = new ViewHolder();
            holder.groupName = (CheckBox) convertView.findViewById(R.id.checkbox_group);
            holder.dummyTextView = (TextView) convertView.findViewById(R.id.textview_group);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dummyTextView.setText(testgroupData.get(groupPosition));
        holder.dummyTextView.setTypeface(font);
        if(selectedParentCheckBoxesState.size() <= groupPosition){
            selectedParentCheckBoxesState.add(groupPosition, false);
        }else {
            holder.groupName.setChecked(selectedParentCheckBoxesState.get(groupPosition));
        }



        holder.groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Callback to expansion of group item
                //if(!isExpanded)
                //    mListener.expandGroupEvent(groupPosition, isExpanded);

                boolean state = selectedParentCheckBoxesState.get(groupPosition);
                Log.d("TAG", "STATE = " + state);
                selectedParentCheckBoxesState.remove(groupPosition);
                selectedParentCheckBoxesState.add(groupPosition, state ? false : true);


                notifyDataSetChanged();

            }
        });


        //callback to expand or shrink list view from dummy text click
        holder.dummyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Callback to expansion of group item
                mListener.expandGroupEvent(groupPosition, isExpanded);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder;

        Typeface font = Typeface.createFromAsset(parent.getContext().getAssets(), "CaviarDreams.ttf");
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child_layout, null);
            holder = new ViewHolder();
            holder.childTextView = (TextView) convertView.findViewById(R.id.textview_child);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.childTextView.setText(mGroupList.get(groupPosition).get(childPosition));
        holder.childTextView.setTypeface(font);


        return convertView;
    }

    /**
     * Called to reflect the sum of checked prices
     * @param groupPosition : group position of list
     */


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public ArrayList<Boolean> getSelectedParentCheckBoxesState(){
        return this.selectedParentCheckBoxesState;
    }

    public ArrayList<String> getTestgroupData(){
        return this.testgroupData;
    }

}
