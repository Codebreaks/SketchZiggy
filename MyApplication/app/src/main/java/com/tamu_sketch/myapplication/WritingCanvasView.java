package com.tamu_sketch.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;
/**
 * Created by xien on 2/2/2018.
 */
//data structure for collecting sketch data. timestamp and stroke data
class dataCollect implements Serializable {
    String timeSample;
    String sketchColor;
    String numStroke;
    float x,y,xVel,yVel;

}

public class WritingCanvasView extends View {
    public int width = 10;
    public int height= 10;
    public static int aid = 0;
    public dataCollect data = new dataCollect();

    ArrayList<dataCollect> sketchData = new ArrayList<dataCollect>();
    public static ArrayList<dataCollect> sketchAlgo = new ArrayList<dataCollect>();
    static ArrayList<Pair<Path,Paint>> paths = new ArrayList<Pair<Path, Paint>>();
    ArrayList<Pair<Path,Paint>> starryPaths = new ArrayList<Pair<Path, Paint>>();
    Long tsLong;
    String ts;
    private VelocityTracker mVelocityTracker = null;
    private float brushSize, lastBrushSize;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    public static Path mPath;
    private boolean erase = false;
    Context context;
    public static Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private HomeActivity mActivity;
    public int strokeCount=0;
    public long startTime = 0;
    public long endTime = 0;
    public String call = "stopAid";
    public WritingCanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        // we set a new Path
        mPath = new Path();
        paths.clear();
        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(brushSize);


        mCanvas = new Canvas();
//        mBitmap= Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
//        mCanvas.setBitmap(mBitmap);

    }
    public ArrayList<dataCollect> getList(){
        return sketchData;
    }
    public static ArrayList<dataCollect> getAlgoList() {return sketchAlgo; }
    public int aidingEnd(){return aid;}
    public void setBrushSize(float newSize){
    //update size
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        Log.i("HERE", Float.toString(pixelAmount));
        brushSize=pixelAmount;
        mPaint.setStrokeWidth(brushSize);
    }
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void startNew() {
        //mCanvas = new Canvas();
        mCanvas.drawColor(0, PorterDuff.Mode.MULTIPLY);
        mPath.reset();
        //mPaint.reset();
        paths.clear();
        invalidate();
    }
    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }
    public Canvas getCanvas(){
        return mCanvas;
    }
//    private dataCollect getCanvasSize(){
//        mCanvas;
//    }
    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        for (Pair<Path, Paint> p : paths) {
            canvas.drawPath(p.first, p.second);
        }
        canvas.drawPath(mPath, mPaint);
    }
    static String realMatch;
    public void starAiding(String match){
        sketchAlgo = new ArrayList<dataCollect>();
        realMatch =match;
        call = "startAid";
    }
    public void setErase(boolean isErase){
    //set erase true or false
        erase = isErase;
//        if(erase) mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        else mPaint.setXfermode(null);
    }
    // when ACTION_DOWN start touch according to the x,y values
    public static int firstRun = 0;
    //dollarP.point startPoint = new dollarP.point();
    static double startx=0;
    static double starty=0;

    dollarP.point endPoint = new dollarP.point();
    //threshold
    public int th = 15;
    private void startTouch(float x, float y, String color) {
        mPath.reset();
        mPath.moveTo(x, y);

        mX = x;
        mY = y;

        tsLong = System.currentTimeMillis(); // milliseconds
        ts = tsLong.toString();
        dataCollect data = new dataCollect();
        data.timeSample = ts;
        data.x = mX;
        data.y = mY;
        data.xVel = 0;
        data.yVel = 0;
        data.sketchColor = color;
        data.numStroke = "stroke" + strokeCount;
        if(call == "startAid"){
            if (firstRun < 1) {

                firstRun=500;
                startTime = System.currentTimeMillis()/1000;
                //startPoint = new dollarP.point(mX, mY, "none");
                startx = mX;
                starty = mY;
            }
            sketchAlgo.add(data);

        }
        sketchData.add(data);
    }
    private void releaseTouch(){

    }
    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y, float xVel, float yVel, String color) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            tsLong = System.currentTimeMillis()/1000;
            ts = tsLong.toString();
            dataCollect data = new dataCollect();
            data.timeSample = ts;
            data.x = mX;
            data.y = mY;
            data.xVel = xVel;
            data.yVel = yVel;
            data.sketchColor = color;
            data.numStroke = Float.toString(strokeCount);
            //Log.e("Here", "DATA:" + data.sketchColor);
            if(call == "startAid"){
                endPoint = new dollarP.point(mX,mY,"none");

                sketchAlgo.add(data);
                endTime = System.currentTimeMillis()/1000;
                long elapseTime = endTime - startTime;
                if(elapseTime >= 2){
                    if((endPoint.x >= startx - th) &&
                            (endPoint.x <= startx + th) &&
                            (endPoint.y >= starty - th) &&
                            (endPoint.y <= starty + th)){
                        HomeActivity.stopAid(context, realMatch);
                        firstRun = 0;
                        startx = 0;
                        starty =0;
                    }
                }
            }
            sketchData.add(data);


           // Log.i("Here", "DATA: " +data.x + " " + data.y);
        }
    }

    public void setColor(String newColor){
    //set color
        invalidate();
    }

    public void clearCanvas() {
        //add strokes to a data structure

        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
        HomeActivity val = new HomeActivity();

        Paint newPaint = new Paint(mPaint); // Clones the mPaint object
        paths.add(new Pair<Path, Paint>(mPath, newPaint));
        //val.createNews(paths);
        mPath = new Path();

    }
//    public void onClickUndo() {
//
//        Log.e("", "pathsize:::" + paths.size());
//        Log.e("", "undonepathsize:::" + undonePaths.size());
//        if (paths.size() > 0) {
//            undonePaths.add(paths.remove(paths.size() - 1));
//            invalidate();
//        } else {
//
//        }
//        // toast the user
//    }
//
//    public void onClickRedo() {
//
//        Log.e("", "pathsize:::" + paths.size());
//        Log.e("", "undonepathsize:::" + undonePaths.size());
//        if (undonePaths.size() > 0) {
//            paths.add(undonePaths.remove(undonePaths.size() - 1));
//            invalidate();
//        } else {
//
//        }
//        // toast the user
//    }


    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //MainActivity val = new MainActivity();
        float x = event.getX();
        float y = event.getY();
        int index = event.getActionIndex();
        //int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //begin stroke

                startTouch(x, y, HomeActivity.getContextColor());
                if(erase){
                    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                }
                else{
                    mPaint.setXfermode(null);
                }
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the
                    // velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y, VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                        pointerId),VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                        pointerId), mActivity.getContextColor());
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //end
                strokeCount++;
                upTouch();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                //end stroke
                mVelocityTracker.recycle();
                break;
        }
        return true;
    }
}
