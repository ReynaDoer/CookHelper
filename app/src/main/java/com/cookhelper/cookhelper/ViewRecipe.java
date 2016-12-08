package com.cookhelper.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
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

public class ViewRecipe extends AppCompatActivity {
    Realm realm;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Realm.init(this);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("name");
        //System.out.println(message);

        RealmResults<Recipe> query = realm.where(Recipe.class).equalTo("name", message).findAll();
        recipe = query.first();

        TextView rName = (TextView) findViewById(R.id.nameOfDish);

        rName.setText(recipe.name);

        EditText rIng = (EditText) findViewById(R.id.listIng);
        rIng.setText(recipe.items.toString());

        EditText rDir = (EditText) findViewById(R.id.listDirections);
        rDir.setText(recipe.instructions);

        EditText rInfo = (EditText) findViewById(R.id.listInfo);
        rInfo.setText(recipe.category  + "\n" + recipe.type + "\n" + recipe.portionSize + "\n" + recipe.calories + "\n" + recipe.notes);
        //System.out.println(query.toString());


        realm.commitTransaction();
    }

    //Code to be excuted whend delete button is pressed
    public void onClickDeleteRecipe(View view) {

        //Name of the recipe that we are interested in deleting
        //Needs to come from search result page
        String recipeName="";
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

        Intent searchIntent = new Intent(this, RecipeList.class);
        startActivity(searchIntent);


    }

}
