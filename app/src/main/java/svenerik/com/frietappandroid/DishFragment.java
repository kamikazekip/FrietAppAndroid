package svenerik.com.frietappandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import svenerik.com.frietappandroid.adapters.DishListAdapter;
import svenerik.com.frietappandroid.models.Dish;

public class DishFragment extends Fragment {

    private CustomAlert alertHandler;
    private String user;
    private ArrayList<Dish> dishes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);
        alertHandler = new CustomAlert(this.getActivity());
        dishes = new ArrayList<Dish>();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void populateListView(String dishesString, String user){
        this.user = user;

        JSONArray dishesJSON;
        try {
            dishesJSON = new JSONArray(dishesString);

            for (int i = 0; i < dishesJSON.length(); i++) {
                JSONObject dish = dishesJSON.getJSONObject(i);
                this.addDish(dish);
            }
            makeListView();
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public void addDish(JSONObject dishJSON){
        try{
            String dish = dishJSON.getString("dish");
            String order_id = dishJSON.getString("order_id");
            String date = dishJSON.getString("date");
            String _id = dishJSON.getString("_id");
            String creator = dishJSON.getString("creator");

            Dish newDish = new Dish(dish, order_id, date, _id, creator, this.user);
            newDish.setDishFragment(this);
            this.dishes.add(newDish);
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public void makeListView(){
        Log.i("HALLO - MakeListView: ", "" + this.dishes.size());
        ListView dishesList = ((ListView) this.getActivity().findViewById(R.id.dishListView));
        DishListAdapter adapter = new DishListAdapter(this.getActivity(), R.layout.dish_row, this.dishes);
        dishesList.setAdapter(adapter);
    }



    public void success(String orders){
        alertHandler.stopActivityIndicator();
    }

    public void fail(){
        alertHandler.stopActivityIndicator();
        alertHandler.showAlert("Oeps!", "Er ging iets mis tijdens het ophalen van de sessies!");
    }
}