package com.cookhelper.cookhelper;

import java.util.ArrayList;
import java.util.LinkedList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by calvin on 2016-12-04.
 */

public class Recipe extends RealmObject {

    @PrimaryKey
    String name;
    String type;
    String category;
    String instructions;
    int portionSize;
    int calories;
    String notes;
    RealmList<FoodItem> items;

    public Recipe(){
    }

    String getName(){
        return this.name;
    }


    String getType(){
        return this.type;
    }

    String getCategory(){
        return this.category;
    }

    String getInstructions(){
        return  this.instructions;
    }

    int getPortionSize(){
        return this.portionSize;
    }

    int getCalories(){
        return this.calories;
    }

    String getNotes(){
        return this.notes;
    }

    RealmList<FoodItem> getItems(){
        return this.items;
    }
}
