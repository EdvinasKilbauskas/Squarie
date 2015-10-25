package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import java.util.ArrayList;

/**
 * Created by pufix on 6/23/13.
 */
public class Pool<T> {
    private ArrayList<T> objects;
    private Class<T> clazz;
    private int maxObjects;

    public Pool(Class<T> clazz, int maxObjects){
        this.clazz = clazz;
        this.maxObjects = maxObjects;
        objects = new ArrayList<T>(maxObjects);
    }

    public T getObject(){
        if(objects.size() == 0){
            try{
                return clazz.newInstance();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else
            return objects.remove(objects.size()-1);

        return null;
    }

    public void giveBackObject(T object){
        if(objects.size() < maxObjects){
            objects.add(object);
        }
    }
}
