package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OrderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    public void setText(String titel) {
        TextView view = (TextView) getView().findViewById(R.id.titel);
        view.setText(titel);
    }
}