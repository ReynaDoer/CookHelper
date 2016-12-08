package com.cookhelper.cookhelper;

import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Bundle bundle = getIntent().getExtras();

        String ingredientSearch = bundle.getString("RecipeSearch");
        System.out.println("at list "+ingredientSearch);
        String[] splited = ingredientSearch.split(",");




        // Get ListView object from xml layout
        ListView listView = (ListView) findViewById(R.id.list);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmQuery<Recipe> ingredientQuery = realm.where(Recipe.class);
        RealmResults<Recipe> ingredientResult = ingredientQuery.findAll();//Test execution

        System.out.println("Full recipe list "+ingredientResult.toString()); //Test

        ingredientQuery.contains("ingredients",splited[0]);
        ingredientResult = ingredientQuery.findAll();





        for(int i = 1 ; i< splited.length ; i = i+2){
            if(splited[i].contains("AND")){
                ingredientQuery.contains("ingredients", splited[i+1]);


            }
            if(splited[i].contains("OR")){
                ingredientQuery.or().contains("ingredients", splited[i+1]);

            }
            if(splited[i].contains("NOT")){
                ingredientQuery.not().contains("ingredients",splited[i+1]);

            }

        }

        ingredientResult = ingredientQuery.findAll();



        String[] values = new String[ingredientResult.size()];
        for(int i = 0 ; i<ingredientResult.size() ; i++){
            values[i] = ingredientResult.get(i).getName();
        }

        realm.commitTransaction();
        realm.close();

//Defining Array values to show in ListView
        //  String[] values = new String[] {
        //          "Item","Item 02","Item 03","Item 04","Item 05","Item 06","Item 07","Item 08","item 09", "item 10", "item 11"
        //  };
        Icon[] images = new Icon[values.length]  ;
//Converting Array to ArrayList
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<Icon> listIm = new ArrayList<Icon>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
            listIm.add(images[i]);
        }
//Create an ArrayAdapter and Set it on the ListView
        TeamArrayAdapter adapter = new TeamArrayAdapter(this, values, images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
//Do something with the string that you just got!
            }
        });


    }




}