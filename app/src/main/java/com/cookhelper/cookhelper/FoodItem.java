package com.cookhelper.cookhelper;

/**
 * Created by calvin on 2016-12-04.
 */

import io.realm.RealmObject;

public class FoodItem extends RealmObject{

    String name;
    String amount;
    Recipe recipe;

    public  FoodItem(){

    }

    Recipe getRecipe(){
        return this.recipe;
    }

    String getName(){
        return name;
    }

    void setName(String n){
        this.name = n;
    }

    String getAmount(){
        return amount;
    }

    void setAmount(String a){
        this.amount = a;
    }

}
