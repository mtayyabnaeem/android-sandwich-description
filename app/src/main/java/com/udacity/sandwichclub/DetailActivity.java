package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView getDescription, getOrigin,getAlsoKnownAs,ingredientsTv;
    Sandwich sandwich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        getDescription = findViewById(R.id.description_tv);
        getOrigin = findViewById(R.id.origin_tv);
        getAlsoKnownAs = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        getDescription.setText(sandwich.getDescription());
        getOrigin.setText(sandwich.getPlaceOfOrigin());

        displayList(getAlsoKnownAs,sandwich.getAlsoKnownAs());
        displayList(ingredientsTv,sandwich.getIngredients());

    }


    /**
     * This displayList method use two input parameters; i.e. textview and the list of type String
     * @param textView
     * @param extractedListString
     * @precondition
     * extractedListString != null;
     * @postcondition
     * append textView to extractedListString
     */
    public void displayList(TextView textView, List<String> extractedListString){
        // n <- extractedListString.size
        int n = extractedListString.size();
        if(n == 0)
            textView.append("no info"); // when there is no data in extracted string.
        for(int i = 0; i < n; i++){
            if(i == n - 1){
                textView.append(extractedListString.get(i));
            }else {
                textView.append(extractedListString.get(i) + ", ");
            }

        }

    }

}
