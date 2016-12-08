package com.cookhelper.cookhelper;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.RealmQuery;

public class AddRecipe extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Realm realm;
    Recipe newRecipe;
    RealmList<FoodItem> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Realm.init(this);

        realm = Realm.getDefaultInstance();
        tempList = new RealmList<FoodItem>();
        //realm.beginTransaction();


        buttonClickListener();
    }

    void createRecipe(){
        realm.beginTransaction();
        EditText recipeName = (EditText) findViewById(R.id.recipeName);

        newRecipe = realm.createObject(Recipe.class,recipeName.getText().toString());

        Spinner spinner = (Spinner)findViewById(R.id.Categories);
        String cate = spinner.getSelectedItem().toString();
        Spinner spinner1 = (Spinner)findViewById(R.id.Types);
        String ty = spinner1.getSelectedItem().toString();
        EditText et = (EditText) findViewById(R.id.addInstruct);
        String instruct = et.getText().toString();

        if (!cate.equals("Any")){
            newRecipe.category = cate;
        }
        if (!ty.equals("Any")){
            newRecipe.type = ty;
        }
        if (instruct.trim().length() != 0){
            newRecipe.instructions = instruct;
        }

        newRecipe.items = tempList;

        System.out.println(newRecipe.toString());
        realm.commitTransaction();
    }

    @Override
    public void onBackPressed(){
        //realm.commitTransaction();
        realm.close();
        finish();
    }
    public void openAddMore(View view) {
        EditText recipeName = (EditText) findViewById(R.id.recipeName);
        String recipeNameString = recipeName.getText().toString();

        //checks if recipe name is entered
        if (recipeName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Recipe Name", Toast.LENGTH_LONG).show();
        }

        //recipe name is entered
        else {

            //checks if recipe name already exists
            RealmQuery<Recipe> query = realm.where(Recipe.class);
            query.equalTo("name", recipeNameString );
            RealmResults<Recipe> result = query.findAll();

            //recipe does not already exist
            if (result.size() == 0) {

                createRecipe();
                Intent addMoreScreenIntent;
                addMoreScreenIntent = new Intent(this, AddMore.class);
                addMoreScreenIntent.putExtra("recipeName", newRecipe.name);
                //realm.commitTransaction();
                //realm.close();
                startActivity(addMoreScreenIntent);

            }
            //recipe already exists
            else {
                Toast.makeText(this, "This Recipe Name already Exists", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void openCreated(View view) {
        EditText recipeName = (EditText) findViewById(R.id.recipeName);
        String recipeNameString = recipeName.getText().toString();

        Spinner spinner = (Spinner)findViewById(R.id.Categories);
        Spinner spinner1 = (Spinner)findViewById(R.id.Types);
        EditText et = (EditText) findViewById(R.id.addInstruct);

        //checks if recipe name is not entered
        if (recipeName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Recipe Name", Toast.LENGTH_LONG).show();
        }

        else {

            //checks if recipe already exists
            RealmQuery<Recipe> query = realm.where(Recipe.class);
            query.equalTo("name", recipeNameString);
            RealmResults<Recipe> result = query.findAll();

            if (result.size() == 0) {

                if (spinner.getSelectedItem().toString().equals("Any")){
                    Toast.makeText(this, "Select A Category", Toast.LENGTH_LONG).show();
                }
                else if (spinner1.getSelectedItem().toString().equals("Any")){
                    Toast.makeText(this, "Select A Type", Toast.LENGTH_LONG).show();
                }
                else if (et.getText().toString().trim().length() == 0){
                    Toast.makeText(this, "Enter Instructions", Toast.LENGTH_LONG).show();
                }
                else {
                    createRecipe();
                    //realm.commitTransaction();
                    realm.close();
                    Intent createdScreenIntent = new Intent(this, RecipeCreated.class);
                    startActivity(createdScreenIntent);
                }
            }
            else {
                Toast.makeText(this, "This Recipe Name already Exists", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void buttonClickListener(){
        Button btn = (Button) findViewById(R.id.btnAddIng);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                realm.beginTransaction();
                EditText ingredient = (EditText) findViewById(R.id.addIngredients);
                EditText amnt = (EditText) findViewById(R.id.addIngredientsAmount);

                FoodItem item = realm.createObject(FoodItem.class);

                item.name = ingredient.getText().toString();
                item.amount = amnt.getText().toString();
                tempList.add(item);

                final RealmResults<FoodItem> items = realm.where(FoodItem.class).findAll();
                System.out.println(items.toString());

                amnt.setText("");
                ingredient.setText("");
                realm.commitTransaction();
            }
        });

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddRecipe Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
