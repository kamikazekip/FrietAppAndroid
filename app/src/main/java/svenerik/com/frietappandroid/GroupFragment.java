package svenerik.com.frietappandroid;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import svenerik.com.frietappandroid.R;
import svenerik.com.frietappandroid.models.Group;

public class GroupFragment extends Fragment {

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        final Button button = (Button) view.findViewById(R.id.groupButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders(button);
            }
        });

        return view;
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(String titel);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    public void createTableView(Group[] groups){
        for (int i = 0; i < groups.length; i++) {
            Button newButton = new Button(getActivity());
            newButton.setText(groups[i].name);
            newButton.setHeight(50);
            newButton.setWidth(200);
            newButton.setTag(groups[i]);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("HALLO", "LELELELELEL");
                }
            });
        }
        Log.i("HALLO", "LOLOLOLOLOL");

    }

    public void goToOrders(Button button) {
        String titel = button.getText().toString();
        listener.onItemSelected(titel);
    }
}

