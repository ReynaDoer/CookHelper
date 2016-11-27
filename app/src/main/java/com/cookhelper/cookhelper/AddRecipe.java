package com.cookhelper.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void openAddMore (View view) {
        Intent addMoreScreenIntent = new Intent(this, AddMore.class);
        startActivity(addMoreScreenIntent);
    }

    public void openCreated (View view) {
        Intent createdScreenIntent = new Intent(this, RecipeCreated.class);
        startActivity(createdScreenIntent);
    }

}
