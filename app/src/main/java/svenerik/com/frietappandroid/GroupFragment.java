package svenerik.com.frietappandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import svenerik.com.frietappandroid.R;
import svenerik.com.frietappandroid.models.Group;

public class GroupFragment extends Fragment {

    private GroupActivity listener;
    private CustomAlert alertHandler;
    private String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        alertHandler = new CustomAlert(this.getActivity());
        return view;
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(String titel);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (GroupActivity) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    public void createTableView(Group[] groups, String user){
        this.user = user;
        for (int i = 0; i < groups.length; i++) {
            Log.i("HALLO", groups[i]._id);
            Button newButton = new Button(getActivity());
            groups[i].setGroupFragment(this);
            newButton.setText(groups[i].name);
            newButton.setTag(groups[i]);
            newButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Group clickedGroup = (Group) v.getTag();
                    clickedGroup.getSessions();
                    alertHandler.startActivityIndicator("Sessies ophalen");
                }
            });
            LayoutParams rl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            rl.topMargin = 20;
            newButton.setLayoutParams(rl);
            ((LinearLayout) listener.findViewById(R.id.linear)).addView(newButton);
        }
    }

    public void success(String orders){
        alertHandler.stopActivityIndicator();
        GroupActivity activ = (GroupActivity) this.getActivity();
        activ.onItemSelected(orders);
    }

    public void fail(){
        alertHandler.stopActivityIndicator();
        Log.i("MIS", "MIS");
        alertHandler.showAlert("Oeps!", "Er ging iets mis tijdens het ophalen van de sessies!");
    }
}

