package com.edvinaskilbauskas.squarie;

import android.util.Log;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.FileIOSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by pufix-pc on 2/10/14.
 */
public class ScoreManager {
    private FileIOSystem ioSystem;
    private String savePath = "data.dat";
    private int currentScore  = 0;
    private int highScore  = 0;

    public ScoreManager(GameSystem gameSystem){
        this.ioSystem = gameSystem.getFileIOSystem();

    }

    public void restartScore(){
        currentScore = 0;
    }

    public void incrementScore(int score){
        currentScore += score;
    }

    public int getHighScore(){
        loadHighScore();
        return highScore;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void saveHighScore(int score){
        FileOutputStream file = ioSystem.openLocalFileOutput(savePath);
        if(file == null) return;
        try{
            FileWriter writer = new FileWriter(file.getFD());
            writer.write(Integer.toString(score));
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void loadHighScore(){

        FileInputStream file = ioSystem.openLocalFileInput(savePath);
        if(file == null) return;
        String string = null;
        try{
            FileReader reader = new FileReader(file.getFD());
            BufferedReader bufferedReader = new BufferedReader(reader);

            string = bufferedReader.readLine();



        }catch(IOException e){
            e.printStackTrace();
        }

        if(string == null) return;

        highScore = Integer.parseInt(string);

    }
}
