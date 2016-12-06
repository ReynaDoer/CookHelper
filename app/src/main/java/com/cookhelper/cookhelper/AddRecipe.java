package com.cookhelper.cookhelper;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddRecipe extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        buttonClickListener();
    }

    @Override
    public void onBackPressed(){
        realm.commitTransaction();
        realm.close();
        finish();
    }
    public void openAddMore(View view) {
        realm.commitTransaction();
        realm.close();
        Intent addMoreScreenIntent = new Intent(this, AddMore.class);
        startActivity(addMoreScreenIntent);
    }

    public void openCreated(View view) {
        realm.commitTransaction();
        realm.close();
        Intent createdScreenIntent = new Intent(this, RecipeCreated.class);
        startActivity(createdScreenIntent);
    }

    public void buttonClickListener(){
        Button btn = (Button) findViewById(R.id.btnAddIng);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                EditText ingredient = (EditText) findViewById(R.id.addIngredients);


                FoodItem item = realm.createObject(FoodItem.class);

                item.name = ingredient.getText().toString();
                item.amount = null;

                final RealmResults<FoodItem> items = realm.where(FoodItem.class).findAll();
                System.out.println(items.toString());


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
