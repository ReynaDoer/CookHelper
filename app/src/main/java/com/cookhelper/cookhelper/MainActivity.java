package com.cookhelper.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    RealmResults<Recipe> recipies;
    RealmResults<Recipe> tempList;
    Recipe tempRecipe;
    RealmList<FoodItem> tempItems;
    FoodItem tempItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        realm = Realm.getDefaultInstance();
    }

    public void btnAddClick(View view){
        Intent startAdd = new Intent(this, AddRecipe.class);
        startActivity(startAdd);
    }

    //open help screen on Help Button click
    public void openHelpScreen (View view) {
        Intent helpScreenIntent = new Intent(this, HelpScreen.class);
        startActivity(helpScreenIntent);
    }

    //open Add Recipe screen on Add+ Button click
    public void openAddRecipeScreen (View view) {
        Intent addRecipeIntent = new Intent(this, AddRecipe.class) ;
        startActivity (addRecipeIntent);
    }

    public void openList(View view){

        realm.beginTransaction();

        recipies = realm.where(Recipe.class).findAll();
        System.out.println(recipies.toString());

        EditText search = (EditText) findViewById(R.id.RecipeSearch);
        System.out.println(search.getText().toString());
        String searchTxt = search.getText().toString();

        int i = 0;
        tempList = recipies;
        tempRecipe = tempList.first();




    }
}




