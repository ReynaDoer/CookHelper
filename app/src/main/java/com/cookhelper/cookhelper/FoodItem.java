package com.cookhelper.cookhelper;

/**
 * Created by calvin on 2016-12-04.
 */

import io.realm.RealmObject;

public class FoodItem extends RealmObject{

    String name;
    String amount;

    public  FoodItem(){

    }

    public FoodItem(String name, String amount){
        this.name = name;
        this.amount = amount;
        System.out.println(this.name + " created");

    }

}
