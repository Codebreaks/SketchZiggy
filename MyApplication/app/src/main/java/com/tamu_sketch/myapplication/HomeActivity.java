package com.tamu_sketch.myapplication;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.lang.Object;

//amazon web services
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class HomeActivity extends Activity {

    //Surface view
    static WritingCanvasView mainview;
    Pop popup;

    //AWS variables
    public static PinpointManager pinpointManager;
    DynamoDBMapper dynamoDBMapper;

    private Button currPaint;
    private Button black;
    private Button gray;
    private Button white;
    private Button pink;
    private Button violet;
    private Button blue;

    private ImageButton download,slot;
    private ImageButton eraser, drawBtn, smallBtn, mediumBtn, largeBtn, newBtn, newAYS, starAid;
    static int count = 0;
    private Button turc;
    private Button green;
    private Button yellow;
    private Button orange;
    private Button red;
    private Button burgandy;
    public int continu;
    public static String continueColor;
    public String age;
    public String userName;
    private WritingCanvasView drawView;
    private float smallBrush, mediumBrush, largeBrush;
    //final MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.sampleGood);
    static dollarP.dollarPRecognizer pAlgo = new dollarP.dollarPRecognizer();

    static dollarP.result outPut = new dollarP.result("nothing",0,0);
    // seek bar (thumb bar)
    // get progress of seek_bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        seekBar();
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        continueColor = "#FF000000";

        age = "not18";
        userName = "user";
        //drawView.setBrushSize(mediumBrush);
//        Log.e("HERE", Double.toString(pAlgo.pointClouds.get(0));
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setContentView(R.layout.brush_chooser);
        //Establish AWS session
        //public class aws_session extends MultidexApplication.startSession()
//        AWSMobileClient.getInstance().initialize(this).execute();

//        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
//                getApplication(),
//                AWSMobileClient.getInstance().getCredentialsProvider(),
//                AWSMobileClient.getInstance().getConfiguration());
//
//        pinpointManager = new PinpointManager(pinpointConfig);
//
//        //start a session with Pinpoint
//        pinpointManager.getSessionClient().startSession();
//
//        //stop the session and submit the default app started event
//        pinpointManager.getSessionClient().stopSession();
//        pinpointManager.getAnalyticsClient().submitEvents();
//
//        //AWS Database
//        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
//        this.dynamoDBMapper = DynamoDBMapper.builder()
//                .dynamoDBClient(dynamoDBClient)
//                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                .build();

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();



        //drawView = (WritingCanvasView)findViewById(R.id.surfaceView);
        mainview = findViewById(R.id.surfaceView);
        mainview.setBackgroundResource(R.drawable.white);



        //slot = findViewById(R.id.paint_colorsOne);


        //Black button
        black = findViewById(R.id.blackColor);
        black.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF000000"));
                continueColor = "#FF000000";
                mainview.setErase(false);
                startAnimation(black,0xFF000000);

            }

        });
//        black.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    // change color
//                }
//            }
//        });
        //gray button
        gray = (Button) findViewById(R.id.gray);
        gray.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF787878"));
                continueColor = "#FF787878";
                mainview.setErase(false);
                startAnimation(gray, 0xFF787878);
            }
        });
        //white button
        white = (Button) findViewById(R.id.white);
        white.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFFFFFF"));
                continueColor = "#FFFFFFFF";
                //mainview.setErase(false);
                startAnimation(white, 0xFFFFFFFF);
            }
        });

        //pink button
        pink = (Button) findViewById(R.id.pink);
        pink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFF6666"));
                continueColor = "#FFFF6666";
                mainview.setErase(false);
                startAnimation(pink, 0xFFFF6666);
            }
        });
        //violet button
        violet = (Button) findViewById(R.id.violet);
        violet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF990099"));
                continueColor = "#FF990099";
                mainview.setErase(false);
                startAnimation(violet, 0xFF990099);
            }
        });
        //blue button
        blue = (Button) findViewById(R.id.blue);
        blue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF0000FF"));
                continueColor = "#FF0000FF";
                mainview.setErase(false);
                startAnimation(blue, 0xFF0000FF);
            }
        });
        //turc button
        turc = (Button) findViewById(R.id.turc);
        turc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF009999"));
                continueColor = "#FF009999";
                mainview.setErase(false);
                startAnimation(turc, 0xFF009999);
            }
        });
        //green button
        green = (Button) findViewById(R.id.green);
        green.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF009900"));
                continueColor = "#FF009900";
                mainview.setErase(false);
                startAnimation(green, 0xFF009900);
            }
        });
        //green button
        yellow = (Button) findViewById(R.id.yellow);
        yellow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFFCC00"));
                continueColor = "#FFFFCC00";
                mainview.setErase(false);
                startAnimation(yellow, 0xFFFFCC00);
            }
        });
        //orange button
        orange = (Button) findViewById(R.id.orange);
        orange.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFF6600"));
                continueColor = "#FFFF6600";
                mainview.setErase(false);
                startAnimation(orange, 0xFFFF6600);
            }
        });
        //red button
        red = (Button) findViewById(R.id.red);
        red.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFF0000"));
                continueColor = "#FFFF0000";
                mainview.setErase(false);
                startAnimation(red, 0xFFFF0000);
            }
        });
        //burgandy button
        burgandy = (Button) findViewById(R.id.burgandy);
        burgandy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FF660000"));
                continueColor = "#FF660000";
                mainview.setErase(false);
                startAnimation(burgandy, 0xFF660000);
            }
        });
        //download button

        download = (ImageButton) findViewById(R.id.save_btn);
        download.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String uniqueID = UUID.randomUUID().toString();
//                takeScreenshot();
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {


                        ActivityCompat.requestPermissions(HomeActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                        );
                    }
                }


                //ask if the user over 18 years or not
//                AlertDialog.Builder eighteen;
//                eighteen = new AlertDialog.Builder(HomeActivity.this);
//                eighteen.setTitle("18+");
//                eighteen.setMessage("Hello! Are you 18 and older?");
//                eighteen.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        age = "18years";
//                    }
//                });
//                eighteen.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                eighteen.show();


                final AlertDialog.Builder saveDialog;
                saveDialog = new AlertDialog.Builder(HomeActivity.this);
                saveDialog.setTitle("Save drawing");
                saveDialog.setMessage("Save drawing to device Gallery?");
                saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save drawing
                        mainview.setDrawingCacheEnabled(true);

                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), mainview.getDrawingCache(),
                                userName + UUID.randomUUID().toString() + ".png", "drawing");
                        if (imgSaved != null) {
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            continu = 1;
                            savedToast.show();
                            //mainview.startNew();

                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        mainview.destroyDrawingCache();
                        count = count +1;
                        doInBackground("sketch" + userName + count + uniqueID, mainview.getList(), age);
                    }
                });
                saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // show pop up window for name
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                final EditText et = new EditText(HomeActivity.this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(et);
                // set dialog message
                alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userName = String.valueOf(et.getText());
                        saveDialog.show();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                //Log.i("Here", "DATA: " + mainview.paths.get(0));
                //createNews(mainview.sketchData.get(0));

                //cont(continu);
            }

        });

        //Aiden star
//        starAid = (ImageButton) findViewById(R.id.starAid);
//        starAid.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                //save sketches already made
//                final String uniqueID = UUID.randomUUID().toString();
//
//                mainview.setDrawingCacheEnabled(true);
//
//                String imgSaved = MediaStore.Images.Media.insertImage(
//                        getContentResolver(), mainview.getDrawingCache(),
//                        userName + UUID.randomUUID().toString() + ".png", "drawing");
//                if (imgSaved != null) {
//                    Toast savedToast = Toast.makeText(getApplicationContext(),
//                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
//                    continu = 1;
//                    savedToast.show();
//                    mainview.startNew();
//
//                } else {
//                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
//                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
//                    unsavedToast.show();
//                }
//                // I think this delete
//                mainview.destroyDrawingCache();
//                count = count +1;
//                doInBackground("sketch" + userName + count + uniqueID, mainview.getList(), age);
//
//                starAid.setBackgroundResource(R.drawable.star_back2);
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        starAid.setBackgroundResource(R.drawable.star_back);
//                    }
//                }, 2000);
//
//                //delete previous sketches
//                mainview.mPath = new Path();
//                mainview.paths.clear();
//                //make pop up box
//                final Dialog brushDialog = new Dialog(HomeActivity.this);
//                brushDialog.setTitle("android shapes window:");
//                brushDialog.setContentView(R.layout.option_menu);
//                mainview.setBrushSize(mediumBrush);
//                mainview.setLastBrushSize(mediumBrush);
//                ImageButton starButton = (ImageButton)brushDialog.findViewById(R.id.imageStar);
//                ImageButton squareButton = (ImageButton)brushDialog.findViewById(R.id.imageSquare);
//                ImageButton triangleButton = (ImageButton)brushDialog.findViewById(R.id.imageTriangle);
//                ImageButton trapezoidButton = (ImageButton)brushDialog.findViewById(R.id.imageTrapezoid);
//
//                starButton.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //change background into a star
//                        mainview.startNew();
//                        mainview.setBackgroundResource(R.drawable.star_back2);
//                        mainview.starAiding("five-point star");
//                        brushDialog.dismiss();
//                    }
//                });
//                //change background
//                //mainview.setBackgroundResource(R.drawable.star_back2);
//                squareButton.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //change background into a star
//                        mainview.startNew();
//                        mainview.setBackgroundResource(R.drawable.square_draw );
//                        mainview.starAiding("square");
//                        brushDialog.dismiss();
//                    }
//                });
////                pAlgo= new dollarP.dollarPRecognizer();
//                triangleButton.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //change background into a star
////                        mainview.startNew();
//                        mainview.setBackgroundResource(R.drawable.triangle_draw );
//                        mainview.starAiding("triangle");
//                        brushDialog.dismiss();
//                    }
//                });
//                trapezoidButton.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //change background into a star
//                        mainview.startNew();
//                        mainview.setBackgroundResource(R.drawable.trapezoid_draw);
//                        mainview.starAiding("trapezoid");
//                        brushDialog.dismiss();
//                    }
//                });
//                brushDialog.show();
//
//
//            }
//        });

        //new AYS button
        newAYS = (ImageButton)findViewById(R.id.new_AYS_btn);
        newAYS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final String uniqueID = UUID.randomUUID().toString();

                final AlertDialog.Builder saveDialogAYS;
                saveDialogAYS = new AlertDialog.Builder(HomeActivity.this);
                saveDialogAYS.setTitle("Clear drawing");
                saveDialogAYS.setMessage("Are you sure you want to clear your progress?");
                saveDialogAYS.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save drawing
                        mainview.setDrawingCacheEnabled(true);

                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), mainview.getDrawingCache(),
                                userName + UUID.randomUUID().toString() + ".png", "drawing");
                        if (imgSaved != null) {
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Successfully cleared", Toast.LENGTH_SHORT);
                            continu = 1;
                            savedToast.show();
                            mainview.startNew();

                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be cleared.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        mainview.destroyDrawingCache();
                        count = count + 1;
                        //doInBackground("sketch" + userName + count + uniqueID, mainview.getList(), age);
                    }
                });
                saveDialogAYS.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                saveDialogAYS.show();
                WritingCanvasView.mPaint.setColor(Color.parseColor(continueColor));


            }
        });
        //new button
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final String uniqueID = UUID.randomUUID().toString();

                //save view
                mainview.setDrawingCacheEnabled(true);

                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), mainview.getDrawingCache(),
                        userName + UUID.randomUUID().toString() + ".png", "drawing");
                if (imgSaved != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    continu = 1;
                    savedToast.show();
                    //reset view
                    mainview.startNew();

                } else {
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                mainview.destroyDrawingCache();
                count = count +1;
                doInBackground("sketch" + userName + uniqueID, mainview.getList(), age);
                WritingCanvasView.mPaint.setColor(Color.parseColor(continueColor));


            }
        });
        //drawing button
        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //Change brush size
                //startActivity(new Intent(MainActivity.this, Pop.class));
                final Dialog brushDialog = new Dialog(HomeActivity.this);
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.brush_chooser);
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(smallBrush);
                        mainview.setLastBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(mediumBrush);
                        mainview.setLastBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(largeBrush);
                        mainview.setLastBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });
                brushDialog.show();
                WritingCanvasView.mPaint.setColor(Color.parseColor(continueColor));


            }
        });

        //eraser button
        eraser = (ImageButton)findViewById(R.id.erase_btn);
        eraser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Change brush size
                //startActivity(new Intent(MainActivity.this, Pop.class));
                //Change brush size
                //startActivity(new Intent(MainActivity.this, Pop.class));
                final Dialog brushDialog = new Dialog(HomeActivity.this);
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.brush_chooser);
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(smallBrush);
                        mainview.setLastBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(mediumBrush);
                        mainview.setLastBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mainview.setBrushSize(largeBrush);
                        mainview.setLastBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });
                brushDialog.show();
                WritingCanvasView.mPaint.setColor(Color.parseColor("#FFFFFFFF"));

            }
        });
        smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                mainview.setErase(true);
                mainview.setBrushSize(smallBrush);
                brushDialog.dismiss();


            }

        });
        mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                mainview.setErase(true);
                mainview.setBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });
        largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                mainview.setErase(true);
                mainview.setBrushSize(largeBrush);
                brushDialog.dismiss();
                //brushDialog.show();
            }
        });
    }
    static ArrayList<dataCollect> dataP = new ArrayList<dataCollect>();
    static outcomePop pop = new outcomePop();
    //    String imgSaved = MediaStore.Images.Media.insertImage(
//            getContentResolver(), mainview.getDrawingCache(),
//            userName + UUID.randomUUID().toString() + ".png", "drawing");
    public static void stopAid(Context context, String ToMatch){

        String age = "not18";
        //save sketches already made
        final String uniqueID = UUID.randomUUID().toString();

        mainview.setDrawingCacheEnabled(true);

        // I think this delete
        mainview.destroyDrawingCache();
        count = count +1;
        doInBackground("sketch" + ToMatch + count + uniqueID, mainview.getList(), age);
        //end button

        dataP = WritingCanvasView.getAlgoList();

        ArrayList<dollarP.point> newInput = new ArrayList<dollarP.point>();

        for(int i =0; i < dataP.size(); i++){
            dollarP.point input = new dollarP.point();
            input.x = dataP.get(i).x;
            input.y = dataP.get(i).y;
            input.id = dataP.get(i).numStroke;

            newInput.add(input);
        }
        outPut = pAlgo.recognize(newInput);
        Log.e("Here", outPut.Name);
        Log.e("Here", Double.toString(outPut.Score));
        //mainview = (WritingCanvasView) findViewById(R.id.surfaceView);

        pop.resultOut(outPut);
        Intent i = new Intent(context,outcomePop.class);
        //show text
        String outcomeText;
        //context.startActivity(i);

        if(outPut.Name == ToMatch)
            outcomeText= "You created a "+ ToMatch + "!";
        else
            outcomeText= "You did not create a " + ToMatch;
//        startActivity
        //
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                mainview.setBackgroundResource(R.drawable.white);
//                mainview.startNew();
//                //Toast.makeText(c, "check", Toast.LENGTH_SHORT).show();
//                handler.postDelayed(this, 2000);
//            }
//        }, 1500);

        final Dialog brushDialog = new Dialog(context);
        brushDialog.setTitle("pop-up window:");
        brushDialog.setContentView(R.layout.correct_pop_window);
        TextView okButton = (TextView)brushDialog.findViewById(R.id.okText);
        TextView correctText = (TextView)brushDialog.findViewById(R.id.correctThumbText);
        correctText.setText(outcomeText);
        okButton.setText("OK");
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainview.setBackgroundResource(R.drawable.white);
                mainview.startNew();
                brushDialog.dismiss();
            }
        });

        brushDialog.show();
        WritingCanvasView.mPaint.setColor(Color.parseColor(continueColor));
    }
    // This function is used to start the fadding affect when click on a color button
    public Void startAnimation(View v, int colorEnd){
        int colorStart=v.getSolidColor();
        //int colorEnd = 0xFF660000;

        ValueAnimator colorAnim = ObjectAnimator.ofInt(v,
                "backgroundColor", colorStart, colorEnd);
        colorAnim.setDuration(2000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
        return null;
    }
    public Void cont(int continu){
        //make a new drawing
        if(continu == 1) {
            AlertDialog.Builder newDialog;
            newDialog = new AlertDialog.Builder(HomeActivity.this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        return null;
    }
    public static Void doInBackground(String values, ArrayList<dataCollect> data, String age) {
//        FileOutputStream outputStream;
//        String cache_directories  = Environment.getDataDirectory();
//        cache_directories  = Environment.getDataDirectory();
//        cache_directories  = Environment.DIRECTORY_DOCUMENTS;
        //cache_directories.listFiles()
        File path1 = Environment.getExternalStorageDirectory();

        BufferedWriter buffOut = null;
        try {
            File file_location = new File(path1 + File.separator + "sample", "");
            if (!file_location.exists()) {
                file_location.mkdir();
            }
            file_location = new File(path1 + File.separator + "sample", values + ".txt");
            if (!file_location.exists()) {
                file_location.createNewFile();
            }
            int datas =20;
            buffOut = new BufferedWriter(new FileWriter(file_location));
            //for(dataCollect line : data)
            //String eol = System.getProperty("line.separator");
            for(int i =0; i < data.size(); i++){
                buffOut.write(data.get(i).timeSample);
                buffOut.write(", ");
                buffOut.write(data.get(i).sketchColor);
                buffOut.write(", ");
                buffOut.write(data.get(i).numStroke);
                buffOut.write(", ");
                buffOut.write(Float.toString(data.get(i).x));
                buffOut.write(", ");
                buffOut.write(Float.toString(data.get(i).y));
                buffOut.write(", ");
//                buffOut.write(continueColor);
                buffOut.write(Float.toString(data.get(i).xVel));
                buffOut.write(", ");
                buffOut.write(Float.toString(data.get(i).yVel));
                buffOut.append("\r\n");
                //Log.i("Here", "DATA: " + data.get(i).x);

            }

            buffOut.flush();
            buffOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //Toast.makeText(context, "didn't work", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }
    public static String getContextColor(){
        return continueColor;
    }
    public void createNews(dataCollect data) {
        final SketchDataDO newsItem = new SketchDataDO();

        //newsItem.setUserId(identityManager.getCachedUserID());

        newsItem.setUserId("user1");
        ArrayList<Double> coord = new ArrayList<>();
        coord.add(0.0);
//        Log.i("Here", "DATA: " + coord);
        newsItem.setSketchCoordinates(coord);


        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(newsItem);
                // Item saved
            }
        }).start();
    }
    public void readNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                SketchDataDO newsItem = dynamoDBMapper.load(
                        SketchDataDO.class,
                        //identityManager.getCachedUserID(),
                        "Article1");

                // Item read
                // Log.d("News Item:", newsItem.toString());
            }
        }).start();
    }
    public void updateNews() {
        final SketchDataDO newsItem = new SketchDataDO();

        //newsItem.setUserId(identityManager.getCachedUserID());

        newsItem.setUserId("user1");
        ArrayList<Double> coord = new ArrayList<Double>();
        coord.add(0.0);
        newsItem.setSketchCoordinates(coord);

        new Thread(new Runnable() {
            @Override
            public void run() {

                dynamoDBMapper.save(newsItem);

                // Item updated
            }
        }).start();
    }
    public void deleteNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                SketchDataDO newsItem = new SketchDataDO();

                //newsItem.setUserId(identityManager.getCachedUserID());    //partition key

                newsItem.setUserId("user1");  //range (sort) key

                dynamoDBMapper.delete(newsItem);

                // Item deleted
            }
        }).start();
    }
    public void logEvent() {
        pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                pinpointManager.getAnalyticsClient().createEvent("Xien's Event")
                        .withAttribute("DemoAttribute1", "DemoAttributeValue1")
                        .withAttribute("DemoAttribute2", "DemoAttributeValue2")
                        .withMetric("DemoMetric1", Math.random());

        pinpointManager.getAnalyticsClient().recordEvent(event);
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();
    }
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //Toast.makeText(this, Float.toString(touchX) + " , " + Float.toString(touchY), Toast.LENGTH_SHORT).show();
//detect user touch
        return true;
    }
}
