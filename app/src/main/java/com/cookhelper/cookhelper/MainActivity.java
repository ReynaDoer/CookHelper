package com.cookhelper.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Button btnAdd = (Button)findViewById(R.id.Add);

    btnAdd.setOnClickListener(new View.OnClickListener() {*/
        public void btnAddClick(View view){
            Intent startAdd = new Intent(this, AddRecipe.class);
            startActivity(startAdd);
        }
    //open help screen on Help Button click
    public void openHelpScreen (View view) {
        Intent helpScreenIntent = new Intent(this, HelpScreen.class);
        startActivity(helpScreenIntent);
    }
    public void openList(View view){
        Intent startList = new Intent(this , RecipeList.class);
        startActivity(startList);
    }


}

