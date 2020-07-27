package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        final String keyName = "name";
        final String keyPlaceOfOrigin = "placeOfOrigin";
        final String keyDescription = "description";
        final String keyImage = "image";
        final String keyMainName = "mainName";
        final String keyIngredients = "ingredients";
        final String keyAlsoKnowAs= "alsoKnownAs";

        JSONObject sandwichObject = new JSONObject(json);
        //get the name JSONObject
        JSONObject name = sandwichObject.getJSONObject(keyName);

        //get origin
        String origin = sandwichObject.getString(keyPlaceOfOrigin);
        //get description
        String description = sandwichObject.getString(keyDescription);
        //get image
        String image = sandwichObject.getString(keyImage);
        //get nameString
        String nameString = name.getString(keyMainName);

        //i will create method getArrayObject since our data structure is in a List of type string to get the ingredients and alsoknowas.
        List<String> ingredients = getArrayObject(sandwichObject.getJSONArray(keyIngredients));

        List<String> asKnowAsName = getArrayObject(name.getJSONArray(keyAlsoKnowAs));

        // return the Sandwich class which takes six parameters.
        return new Sandwich(nameString, asKnowAsName, origin, description, image, ingredients);

    }

    private static List<String> getArrayObject(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                list.add(jsonArray.getString(i));
            }

        } catch (JSONException e ){

            Log.e(TAG,"Error parsing",e);

        }
        return list;
    }



}
