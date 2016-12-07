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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AddRecipe extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Realm realm;
    Recipe newRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Realm.init(this);


        realm = Realm.getDefaultInstance();
        //realm.beginTransaction();

        buttonClickListener();
    }

    void createRecipe(){
        realm.beginTransaction();
        EditText recipeName = (EditText) findViewById(R.id.recipeName);
        newRecipe = realm.createObject(Recipe.class,recipeName.getText().toString());
        Spinner spinner = (Spinner)findViewById(R.id.Categories);
        String cate = spinner.getSelectedItem().toString();
        newRecipe.category = cate;
        Spinner spinner1 = (Spinner)findViewById(R.id.Types);
        String ty = spinner1.getSelectedItem().toString();
        newRecipe.type = ty;
        EditText et = (EditText) findViewById(R.id.addInstruct);
        String instruct = et.getText().toString();
        newRecipe.instructions = instruct;
        System.out.println(newRecipe.toString());
        realm.commitTransaction();
    }

    @Override
    public void onBackPressed(){
        realm.close();
        finish();
    }
    public void openAddMore(View view) {
        createRecipe();

        Intent addMoreScreenIntent;
        addMoreScreenIntent = new Intent(this, AddMore.class);
        addMoreScreenIntent.putExtra("recipeName", newRecipe.name);
        //realm.close();
        startActivity(addMoreScreenIntent);
    }

    public void openCreated(View view) {
        createRecipe();
        Intent createdScreenIntent = new Intent(this, RecipeCreated.class);
        //realm.close();
        startActivity(createdScreenIntent);
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
                item.recipe = newRecipe;

                final RealmResults<FoodItem> items = realm.where(FoodItem.class).findAll();
                System.out.println(items.toString());

                amnt.setText("");
                ingredient.setText("");
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
