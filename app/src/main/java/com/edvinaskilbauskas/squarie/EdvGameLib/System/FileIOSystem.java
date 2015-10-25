package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pufix on 6/24/13.
 */
public class FileIOSystem {
    private GameSystem gameSystem;

    public FileIOSystem(GameSystem gameSystem){
        this.gameSystem = gameSystem;

    }

    public FileInputStream openLocalFileInput(String filePath){
        try{
            return gameSystem.openFileInput(filePath);
        }catch(FileNotFoundException e){
            // if file was not found, open output stream (which will create a file)
            openLocalFileOutput(filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public FileOutputStream openLocalFileOutput(String file){
        try{
            return gameSystem.openFileOutput(file, Context.MODE_PRIVATE);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public InputStream openAsset(String file){
        try{
            return gameSystem.getAssets().open(file);
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}

