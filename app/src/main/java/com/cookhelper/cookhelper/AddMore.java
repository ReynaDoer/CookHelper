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
import java.io.ByteArrayOutputStream;


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
    Bitmap imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        //finds the recipe to be edited
        setContentView(R.layout.activity_add_more);
        imgSelect = (ImageView) findViewById(R.id.imgSelect);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("recipeName");
        System.out.println(message);

        final Recipe recipies = realm.where(Recipe.class).equalTo("name", message).findFirst();
        System.out.println(recipies.toString());
        recipe = recipies;

        realm.commitTransaction();

        //fills any existing intformation into the appropriate widgets
        if (recipe.image != null) {
            Bitmap bitmapImage= BitmapFactory.decodeByteArray(recipe.image, 0, recipe.image.length);
            imgSelect.setImageBitmap(bitmapImage);
        }
        if (recipe.portionSize >0 ) {
            EditText portionSize = (EditText) findViewById(R.id.editPortionSize);
            String number = Integer.toString(recipe.getPortionSize());
            portionSize.setText(number);
        }
        if (recipe.calories >0) {
            EditText calories = (EditText) findViewById(R.id.editCalories);
            String number1 = Integer.toString(recipe.getCalories());
            calories.setText(number1);
        }
        if (recipe.notes != null){
            EditText notes = (EditText) findViewById(R.id.editNotes);
            notes.setText(recipe.getNotes());
        }

    }

    //on click for back button, saves any changes to recipe and closes activity
    @Override
    public void onBackPressed(){

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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageData.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageByte = stream.toByteArray();
            recipe.image = imageByte;
        }

        System.out.println(recipe.toString());
        realm.commitTransaction();

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

//calles to state intent to access gallery and request access and a returned file
    public void getImageFromGallery (View view) {

        //states intent to call to select an item of data and return it
        Intent getImageIntent = new Intent(Intent.ACTION_PICK);

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String directoryPath = directory.getPath();

        Uri data = Uri.parse(directoryPath);

        getImageIntent.setDataAndType (data, "image/*");

        startActivityForResult(getImageIntent, IMG_REQUEST_CODE);

    }

    //Takes selected image from gallery or camera and decodes it to use in CookHelper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ) {

            if (requestCode == IMG_REQUEST_CODE) {

                Uri image = data.getData();

                try {
                    InputStream imageStream = getContentResolver().openInputStream(image);

                    Bitmap bitmapImage = BitmapFactory.decodeStream(imageStream);
                    imageData = bitmapImage;

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
                imageData = bitmapImage;
                imgSelect.setImageBitmap(bitmapImage);
            }

        }

    }

    //open Recipe Created Activity on Save Button click - checks to make sure all manditory information is added
    public void openRecipeCreated (View view) {

        if (recipe.category == null){
            Toast.makeText(this, "Select A Category", Toast.LENGTH_LONG).show();
        }
        else if (recipe.type == null){
            Toast.makeText(this, "Select A Type", Toast.LENGTH_LONG).show();
        }
        else if (recipe.instructions == null){
            Toast.makeText(this, "Enter Instructions", Toast.LENGTH_LONG).show();
        }
        else if (recipe.items.size() == 0){
            Toast.makeText(this, "Enter an Ingredient", Toast.LENGTH_LONG).show();
        }
        else {
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageData.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageByte = stream.toByteArray();
                recipe.image = imageByte;
            }

            System.out.println(recipe.toString());
            realm.commitTransaction();

            Intent intentRecipeCreated = new Intent(this, RecipeCreated.class) ;
            startActivity(intentRecipeCreated);
        }



    }
}
