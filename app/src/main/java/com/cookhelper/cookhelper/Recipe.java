package com.cookhelper.cookhelper;

import java.util.ArrayList;
import java.util.LinkedList;

import io.realm.RealmObject;

/**
 * Created by calvin on 2016-12-04.
 */

public class Recipe extends RealmObject {

    String name;
    String type;
    String category;

   //ArrayList<> items;


    String directions;


    public Recipe(){
    }

    public Recipe(String name, String type, String category, String directions){
        this.name = name;
        this.type = type;
        this.category = category;
        this.directions = directions;
        //this.items = items;
    }

}
