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
    String instructions;

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
}
