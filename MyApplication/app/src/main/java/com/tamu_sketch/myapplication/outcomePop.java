package com.tamu_sketch.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xien on 7/20/2018.
 */
public class outcomePop extends Activity{
    HomeActivity main;
    private static SeekBar seek_bar;
    private static TextView text_view;
    public int progressive;
    static dollarP.result res;
    public void correctScreen(){

        text_view = (TextView)findViewById(R.id.correctThumbText);
        text_view.setText("I see a STAR! correct!");

    }
    public void incorrectScreen(){
        text_view = (TextView)findViewById(R.id.correctThumbText);
        text_view.setText("I don't see a STAR! incorrect!");
    }
    public void resultOut(dollarP.result input){
        //getting the output of the recognizer
        res =input;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_pop_window);
        if(res.Name == "five-point star")
            correctScreen();
        else
            incorrectScreen();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.3));

    }
}
