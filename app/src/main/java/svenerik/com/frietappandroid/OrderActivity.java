package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class OrderActivity extends ActionBarActivity {
    public static final String EXTRA_TITEL = "Titel";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        setContentView(R.layout.activity_order);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String titel = extras.getString(EXTRA_TITEL);
            OrderFragment detailFragment = (OrderFragment) getFragmentManager().findFragmentById(R.id.orderFragment);
            detailFragment.setText(titel);
        }
    }
}