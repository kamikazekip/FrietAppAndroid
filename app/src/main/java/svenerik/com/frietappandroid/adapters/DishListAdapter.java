package svenerik.com.frietappandroid.adapters;

/**
 * Created by Erik on 28-5-2015.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import svenerik.com.frietappandroid.R;
import svenerik.com.frietappandroid.models.Dish;

public class DishListAdapter extends ArrayAdapter<Dish> {
    private ArrayList<Dish> dishes;

    public DishListAdapter(Context context, int textViewResourceId, ArrayList<Dish> dishes) {
        super(context, textViewResourceId, dishes);
        this.dishes = dishes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.dish_row, null);
        }


        Dish i = this.dishes.get(position);

        if (i != null) {
            TextView creatorView = (TextView) v.findViewById(R.id.creator);
            TextView dishView = (TextView) v.findViewById(R.id.dish);

            if (creatorView != null){
                creatorView.setText(i.creator);
            }
            if (dishView != null){
                dishView.setText(i.dish);
            }

        }

        return v;
    }
}