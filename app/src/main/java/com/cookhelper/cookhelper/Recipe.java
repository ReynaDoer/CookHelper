package com.cookhelper.cookhelper;

import io.realm.RealmObject;

/**
 * Created by calvin on 2016-12-04.
 */

public class Recipe extends RealmObject {

    String name;
    String type;
    String category;
    FoodItem[] items;
    String directions;

    public Recipe(String name, String type,String category, FoodItem[] items,String directions){
        this.name = name;
        this.type = type;
        this.category = category;
        this.items = items;
        this.directions = directions;
    }

}
