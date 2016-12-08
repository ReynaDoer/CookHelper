package com.cookhelper.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import io.realm.annotations.PrimaryKey;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ViewRecipe extends AppCompatActivity {
    Realm realm;
    Recipe recipe;
    String recName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Realm.init(this);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        this.recName = bundle.getString("name");


        RealmResults<Recipe> query = realm.where(Recipe.class).equalTo("name", recName).findAll();
        recipe = query.first();

        //Fill in the widgets on the View Recipe page
        TextView rName = (TextView) findViewById(R.id.nameOfDish);

        rName.setText(recipe.name);

        EditText rIng = (EditText) findViewById(R.id.listIng);

        String s = "";
        FoodItem temp = recipe.items.first();
        int i = 0;
        while(i < recipe.items.size()){
            temp = recipe.items.get(i);
            s = s + temp.name + " : " + temp.amount + "\n";
            i++;
        }
        rIng.setText(s);



        EditText rDir = (EditText) findViewById(R.id.listDirections);
        rDir.setText(recipe.instructions);

        EditText rInfo = (EditText) findViewById(R.id.listInfo);
        rInfo.setText("category : " + recipe.category  + "\n" +
                "type : " + recipe.type + "\n" +
                "portion size : " + recipe.portionSize + "\n" +
                "calories : " + recipe.calories + "\n" +
                "notes : " + recipe.notes);
        //System.out.println(query.toString());

        ImageView imgView = (ImageView) findViewById(R.id.imgViewRecipe);
        Bitmap bitmapImage= BitmapFactory.decodeByteArray(recipe.image, 0, recipe.image.length);
        imgView.setImageBitmap(bitmapImage);


        realm.commitTransaction();
    }

    //Code to be excuted whend delete button is pressed
    public void onClickDeleteRecipe(View view) {

        //Name of the recipe that we are interested in deleting
        //Needs to come from search result page
        String recipeName = recName;
        Realm realm = Realm.getDefaultInstance(); // Create a new realm instance

        // Build the query looking at all recipes:
        RealmQuery<Recipe> query = realm.where(Recipe.class);

        // Add query conditions:
        // Looking for all queries for the desired recipeName
        query.equalTo("name", recipeName);

        // Execute the query. Should only have 1 exact hit:
        final RealmResults<Recipe> results = query.findAll();

        //Deletion of a Realm object
        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Delete all matches, which will end up being the 1 hit
                //results.deleteAllFromRealm();

                //Delete first match, will be the only hit available
                results.deleteFirstFromRealm();
            }
        });

        Intent searchIntent = new Intent(this, MainActivity.class);
        startActivity(searchIntent);
    }



}
