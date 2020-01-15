package com.tamu_sketch.myapplication;

import java.util.*;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xien on 6/21/2018.
 * This is the android develpoment of &P
 * Point-cloud multistroke recognizer - for recognizing multistroke gestures as point-clouds
 */
public class dollarP {
    //point class
    public static int NumPointClouds = 16;
    public static int NumPoints = 32;
    public static int NumP = 32;
    public static point origin = new point(0,0,"0");

    public static class point{ //constructor
        public double x,y;
        public String id;
        public point(){
            x = 0;
            y = 0;
            id = "";
        }
        public point(double xVal,double yVal,String idVal){
            x = xVal;
            y = yVal;
            id = idVal;
        }

    }
    //point cloud template
    public static class pointCloud{
        public String Name;
        public ArrayList<point> Points;
        pointCloud(String name, ArrayList<point> points){
            Name =name;

            //Log.e("HERE", Double.toString(Points.size()));

            Points = scale(points);
            //Log.e("HERE", Double.toString(Points.size()));
            Points = translateTo(Points, origin);
            Points = resample(Points, NumPoints);
//            Points = resample(Points, NumPoints);
            //Log.e("HERE", Double.toString(Points.size()));
//             for (int i=0; i<Points.size(); i++)
//            Log.e("HERE", Double.toString(Points.get(5).x));
        }
    }
    //result class
    public static class result{
        public String Name;
        public double Score;
        public double Time;
        public String ToMatch;
        result(String name, double score, double ms){
            this.Name= name;
            this.Score= score;
            this.Time= ms;
            this.ToMatch = "none";
        }
    }
    public static class dollarPRecognizer{
        ArrayList<pointCloud> pointClouds = new ArrayList<pointCloud>();
        //point pointer = new point();
        dollarPRecognizer(){

            //Star
            ArrayList<point> fiveStar = new ArrayList<point>();

//            fiveStar.add(new point(170,340,"1"));
//            fiveStar.add(new point(250,260,"1"));
//            fiveStar.add(new point(189,420,"1"));
//            fiveStar.add(new point(189,260,"1"));
//            fiveStar.add(new point(250,400,"1"));
//            fiveStar.add(new point(172,340, "1"));

//            fiveStar.add(new point(100,102,"1"));
//            fiveStar.add(new point(120,160,"1"));
//            fiveStar.add(new point(80,222,"1"));
//            fiveStar.add(new point(150,240,"1"));
//            fiveStar.add(new point(183,299,"1"));
//            fiveStar.add(new point(217,240,"1"));
//            fiveStar.add(new point(308,222,"1"));
//            fiveStar.add(new point(240,160,"1"));
//            fiveStar.add(new point(262,102,"1"));
//            fiveStar.add(new point(185,125,"1"));
//            fiveStar.add(new point(110, 100, "1"));
//            //STAR
            fiveStar.add(new point(177, 396, "1"));
            fiveStar.add(new point(223,299,"1"));
            fiveStar.add(new point(262,396,"1"));
            fiveStar.add(new point(168,332,"1"));
            fiveStar.add(new point(278,332,"1"));
            fiveStar.add(new point(184,397,"1"));

            //double kamp = fiveStar.get(1).x;
            this.pointClouds.add(new pointCloud("five-point star", fiveStar));

            ArrayList<point> arrowhead = new ArrayList<point>();
            //Arrow head
            arrowhead.add(new point(506,349,"1"));
            arrowhead.add(new point(574,349,"1"));
            arrowhead.add(new point(525,306,"2"));
            arrowhead.add(new point(584,349,"2"));
            arrowhead.add(new point(525,388,"2"));
            this.pointClouds.add(new pointCloud("arrowHead", arrowhead));
//            SQUARE
            ArrayList<point> square = new ArrayList<point>();
            square.add(new point(188,137,"1"));
            square.add(new point(188,240,"1"));
            square.add(new point(241,240,"1"));
            square.add(new point(241,137,"1"));
            square.add(new point(180,137,"1"));
            this.pointClouds.add(new pointCloud("square", square));

//            TRIANGLE
            ArrayList<point> triangle = new ArrayList<point>();
            triangle.add(new point(150,300,"1"));
            triangle.add(new point(250,100,"1"));
            triangle.add(new point(350,300,"1"));
            triangle.add(new point(160,300,"1"));
            this.pointClouds.add(new pointCloud("triangle", triangle));

//            TRAPEZOID
            ArrayList<point> trapezoid = new ArrayList<point>();
            trapezoid.add(new point(120,400,"1"));
            trapezoid.add(new point(200,200,"1"));
            trapezoid.add(new point(400,200,"1"));
            trapezoid.add(new point(480,400,"1"));
            trapezoid.add(new point(150,400,"1"));
            this.pointClouds.add(new pointCloud("trapezoid", trapezoid));

//            ArrayList<point> letterT = new ArrayList<point>();
//            letterT.add(new point(10,7,"1"));
//            letterT.add(new point(123,7,"1"));
//            letterT.add(new point(66,7,"2"));
//            letterT.add(new point(66,107,"2"));
//            this.pointClouds.add(new pointCloud("Letter T", letterT));
        }
        //
        // The $P Point-Cloud Recognizer API begins here -- 3 methods: Recognize(), AddGesture(), DeleteUserGestures()
        //
        public result recognize(ArrayList<point> points){
            double t0 = System.currentTimeMillis();

            points = scale(points);
            points = translateTo(points, origin);

            points = resample(points, NumP);

//            points = resample(points, NumP);
            //Log.e("HERE", Double.toString(points.size()));
            double b = Math.pow(2,23)-1; //infinite
            int u = -1;
            ArrayList<point> PTemp = new ArrayList<point>();
//
            for (int i = 0; i < this.pointClouds.size(); i++) // for each point-cloud template
            {
                PTemp = this.pointClouds.get(i).Points;
                //reduce the chance of minus one error
                while(points.size() != PTemp.size()){
                    if(points.size()<PTemp.size())
                        points.add(new point(points.get(points.size()-1).x,points.get(points.size()-1).y,points.get(points.size()-1).id));
                    if(points.size()>PTemp.size())
                        this.pointClouds.get(i).Points.add(new point(PTemp.get(PTemp.size()-1).x,PTemp.get(PTemp.size()-1).y,PTemp.get(PTemp.size()-1).id));
                }
                double d = greedyCloudMatch(points, this.pointClouds.get(i));
                if(this.pointClouds.get(i).Name.equals("square"))
                    Log.e("HERE", Double.toString(d) + " FOR square");
                if (d < b) {
                    b = d; // best (least) distance
                    u = i; // point-cloud index
                }
            }
            double t1 = System.currentTimeMillis();
            Log.e("HERE", Double.toString(b));
            return (u == -1) ? new result("No match.", 0.0, t1-t0) : new result(this.pointClouds.get(u).Name, Math.max((2.0 - b) / 2.0, 0.0), t1-t0);
        }
        public double addGesture(String name, ArrayList<point> points){
            this.pointClouds.set(this.pointClouds.size(), new pointCloud(name, points));
            int num = 0;
            for (int i = 0; i < this.pointClouds.size(); i++) {
                if (this.pointClouds.get(i).Name.equals(name))
                    num++;
            }
            return num;
        }
    }

    public static double greedyCloudMatch(ArrayList<point> points, pointCloud P){
        double e = 0.50;
        int step = (int)Math.floor(Math.pow(points.size(), 1.0 - e));
        double min =Float.MAX_VALUE;
        for (int i = 0; i < points.size(); i += step) {
            double d1 = cloudDistance(points, P.Points, i);
            double d2 = cloudDistance(P.Points, points, i);
            min = Math.min(min, Math.min(d1, d2)); // min3
        }
        return min;

        //c# code from website and given parameters(Point[] points1, Point[] points2)
//        int n = points1.Length; // the two clouds should have the same number of points by now
//        float eps = 0.5f;       // controls the number of greedy search trials (eps is in [0..1])
//        int step = (int)Math.Floor(Math.Pow(n, 1.0f - eps));
//        float minDistance = float.MaxValue;
//        for (int i = 0; i < n; i += step)
//        {
//            float dist1 = CloudDistance(points1, points2, i);   // match points1 --> points2 starting with index point i
//            float dist2 = CloudDistance(points2, points1, i);   // match points2 --> points1 starting with index point i
//            minDistance = Math.Min(minDistance, Math.Min(dist1, dist2));
//        }
//        return minDistance;
    }
    public static double cloudDistance(ArrayList<point> pts1, ArrayList<point> pts2, int start){
//        ArrayList<Boolean> matched = new ArrayList<Boolean>(pts1.size()); // pts1.length == pts2.length
//        bool[] matched = new bool[n]; // matched[i] signals whether point i from the 2nd cloud has been already matched
        boolean[] matched = new boolean[pts1.size()];
//        for (int k = 0; k < pts1.size(); k++)
//            matched.add(false);
        //Array.Clear(matched, 0, n);   // no points are matched at the beginning
        double sum = 0;
        int i = start;
        do
        {
            int index = -1;
            double min = Float.MAX_VALUE;

            for (int j = 0; j < pts1.size(); j++)
            {
                if (!matched[j]) {
                    double d = Distance(pts1.get(i), pts2.get(j));
                    if (d < min) {
                        min = d;
                        index = j;
                    }
                }
            }
//            if(index != -1)
//                matched.set(index,true);
            matched[index] =true;
            double weight = 1.0f - ((i - start + pts1.size()) % pts1.size()) / (1.0f * pts1.size());
            sum += weight * min;
            i = (i + 1) % pts1.size();
        } while (i != start);
        return sum;
    }
    public static ArrayList<point> translateTo(ArrayList<point> points, point pt){
        point c = centroid(points);
        ArrayList<point> newpoints = new ArrayList<point>();
        for (int i = 0; i < points.size(); i++) {
//            double qx = points.get(i).x + pt.x - c.x;
//            double qy = points.get(i).y + pt.y - c.y;
            newpoints.add(new point(points.get(i).x - c.x, points.get(i).y - c.y, points.get(i).id));
        }
        return newpoints;
    }
    public static point centroid(ArrayList<point> points){
        double x = 0.0, y = 0.0;
        for (int i = 0; i < points.size(); i++) {
            x += points.get(i).x;
            y += points.get(i).y;
        }
        x /= points.size();
        y /= points.size();
        return new point(x, y, "0");
    }
    public static ArrayList<point> scale(ArrayList<point> points){
        double minX = Math.pow(2,23)-1; //infinite
        double maxX = 0;
        double minY = Math.pow(2,23)-1; //infinite
        double maxY = 0;
        for (int i = 0; i < points.size(); i++) { //FIND MIN AND MAX VALUE
            minX = Math.min(minX, points.get(i).x);
            minY = Math.min(minY, points.get(i).y);
            maxX = Math.max(maxX, points.get(i).x);
            maxY = Math.max(maxY, points.get(i).y);
        }
        double size = Math.max(maxX - minX, maxY - minY);
        ArrayList<point> newpoints = new ArrayList<point>();
        for (int i = 0; i < points.size(); i++) {
//            Log.e("HERE",Double.toString(points.get(i).x));
            double qx = (points.get(i).x - minX) / size;
            double qy = (points.get(i).y - minY) / size;
            point point = new point(qx, qy, points.get(i).id);
            //Log.e("HERE",Double.toString(qx));
            newpoints.add(point);
            //newpoints.size();
        }
        return newpoints;
    }
    public static ArrayList<point> resample(ArrayList<point> points, int n){

        double I = PathLength(points) / (n-1);
        double D = 0.0;
        int numPoints = 1;
        ArrayList<point> newpoints = new ArrayList<point>();
        newpoints.add(points.get(0));
        for (int i = 1; i < points.size(); i++)
        {
            if (points.get(i).id.equals(points.get(i-1).id))
            {
                double d = Distance(points.get(i - 1), points.get(i));
//                Log.e("HERE", Double.toString(d));

                if ((D + d) >= I)
                {
                    point firstPoint = points.get(i-1);
                    while (D + d >= I){
                        // add interpolated point
                        float t = (float)Math.min(Math.max((I - D) / d, 0.0f), 1.0f);
                        if (Float.isNaN(t)) t = 0.5f;
                        numPoints++;
                        point q = new point(
                                (1.0f - t) * firstPoint.x + t * points.get(i).x ,
                                (1.0f - t) * firstPoint.y + t * points.get(i).y ,
                                points.get(i).id
                        );
                        newpoints.add(q); // append new point 'q'
                        // update partial length
                        d = D + d - I;
                        D = 0;
                        firstPoint = newpoints.get(numPoints - 1);
                    }
//                    double qx = points.get(i-1).x + ((I - D) / d) * (points.get(i).x - points.get(i-1).x);
//                    double qy = points.get(i-1).y + ((I - D) / d) * (points.get(i).y - points.get(i-1).y);
//                    point q = new point(qx, qy, points.get(i).id);
//                    newpoints.add(q); // append new point 'q'
//                    //points.add(i, q); // insert 'q' at position i in points s.t. 'q' will be the next i
//                    points = arrLSplice(i,(points.size()),q,points);
                    D = 0.0;
                }
                else D += d;
            }
        }
        Log.e("size",Double.toString(newpoints.size()));


        if (numPoints == n - 1) // sometimes we fall a rounding-error short of adding the last point, so add it if so
            newpoints.add(new point(points.get(points.size() - 1).x, points.get(points.size() - 1).y, points.get(points.size() - 1).id));
//            newpoints.set(numPoints++ , new point(points.get(points.size() - 1).x, points.get(points.size() - 1).y, points.get(points.size() - 1).id));
        return newpoints;
    }
    public static ArrayList<point> arrLSplice(int replaceIndex,int deleteNum, point point, ArrayList<point> points){
        ArrayList<point> newList = new ArrayList<point>();
        for(int k = 0; k < points.size(); k++){
            newList.add(points.get(k));
            if(k == replaceIndex-1)
                newList.add(point);

        }

        return newList;
    }
    public static double PathLength(ArrayList<point> points){
        double dist = 0.0;
        for (int i = 1; i < points.size(); i++) {
            //Log.e("HERE-ID",points.get(i).id);
//            Log.e("HERE-ID",points.get(i).id);
            if (points.get(i).id.equals(points.get(i-1).id))
                dist += Distance(points.get(i-1), points.get(i));
        }
        return dist;
    }
    public static double Distance(point p1,point p2){
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

}
