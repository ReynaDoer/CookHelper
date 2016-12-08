package com.cookhelper.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
}
