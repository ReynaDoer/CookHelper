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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfiguration); // Clean slate
        Realm.setDefaultConfiguration(realmConfiguration); // Make this Realm the default*/
    }

    //Button btnAdd = (Button)findViewById(R.id.Add);

    //btnAdd.setOnClickListener(new View.OnClickListener() {*/
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
        Intent startList = new Intent(this , RecipeList.class);
        startActivity(startList);
    }


}




