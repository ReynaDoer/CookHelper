package com.cookhelper.cookhelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AddMore extends AppCompatActivity {

    public static final int IMG_REQUEST_CODE = 0;
    public static final int IMG_REQUEST_CAPTURE = 1;
    private ImageView imgSelect;
    Realm realm;
    String recipeName;
    Recipe recipe;
    Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        setContentView(R.layout.activity_add_more);
        imgSelect = (ImageView) findViewById(R.id.imgSelect);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("recipeName");
        System.out.println(message);

        final Recipe recipies = realm.where(Recipe.class).equalTo("name", message).findFirst();
        System.out.println(recipies.toString());
        recipe = recipies;

        realm.commitTransaction();

    }
    @Override
    public void onBackPressed(){
        //realm.commitTransaction();
        realm.close();
        finish();
    }

    //called to state intent access to camera on Access Camera Button
    public void callCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, IMG_REQUEST_CAPTURE);
        }

        else {
            Toast.makeText(this, "Error Accessing Camera", Toast.LENGTH_LONG).show();
        }
    }

    public void getRecipeName(String name){
        this.recipeName = name;
    }

    public void getImageFromGallery (View view) {

        //states intent to call to select an item of data and return it
        Intent getImageIntent = new Intent(Intent.ACTION_PICK);

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String directoryPath = directory.getPath();

        Uri data = Uri.parse(directoryPath);

        getImageIntent.setDataAndType (data, "image/*");

        startActivityForResult(getImageIntent, IMG_REQUEST_CODE);

    }

    //Takes selected image from gallery and decodes it to use in CookHelper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ) {

            imageData = data.getData();

            if (requestCode == IMG_REQUEST_CODE) {

                Uri image = data.getData();

                try {
                    InputStream imageStream = getContentResolver().openInputStream(image);

                    Bitmap bitmapImage = BitmapFactory.decodeStream(imageStream);

                    imgSelect.setImageBitmap(bitmapImage);

                }
                //catches error in opening image
                catch (FileNotFoundException e) {
                    e.printStackTrace();

                    //displays one of those popups announcing error
                    Toast.makeText(this, "Error Opening Image", Toast.LENGTH_LONG).show();

                }

            }
            //if access camera camera button is pressed
            if (requestCode == IMG_REQUEST_CAPTURE) {

                Bundle extras = data.getExtras();
                Bitmap bitmapImage = (Bitmap) extras.get("data");
                imgSelect.setImageBitmap(bitmapImage);
            }

        }

    }

    //open Recipe Created Activity on Save Button click
    public void openRecipeCreated (View view) {

        realm.beginTransaction();

        EditText portionSize = (EditText) findViewById(R.id.editPortionSize);
        EditText calories = (EditText) findViewById(R.id.editCalories);
        EditText notes = (EditText) findViewById(R.id.editNotes);

        if (portionSize.getText().toString().trim().length() !=0 ) {
            recipe.portionSize = Integer.parseInt(portionSize.getText().toString());
        }
        if (calories.getText().toString().trim().length() != 0 ) {
            recipe.calories = Integer.parseInt(calories.getText().toString());
        }
        if (notes.getText().toString().trim().length() != 0 ) {
            recipe.notes = notes.getText().toString();
        }
        if (imageData != null ) {
            recipe.image = imageData.toString();
        }

        System.out.println(recipe.toString());
        realm.commitTransaction();

        Intent intentRecipeCreated = new Intent(this, RecipeCreated.class) ;
        startActivity(intentRecipeCreated);
    }
}
