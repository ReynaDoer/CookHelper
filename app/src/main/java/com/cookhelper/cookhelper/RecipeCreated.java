package com.cookhelper.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by James on 27/11/2016.
 */

public class RecipeCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_created);
    }

    public void openHome(View view) {
        Intent homeScreenIntent = new Intent(this, MainActivity.class);
        startActivity(homeScreenIntent);
    }

    public void openAdd(View view) {
        Intent addScreenIntent = new Intent(this, AddRecipe.class);
        startActivity(addScreenIntent);
    }
}
