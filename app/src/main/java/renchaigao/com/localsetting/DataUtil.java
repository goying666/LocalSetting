package renchaigao.com.localsetting;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/7/27/027.
 */

public class DataUtil {

    public static Boolean AddNewPoint(Context context, Double longitude, Double latitude, int moveClass) {
        try {
            String allPoints = GetAllPoints(context, moveClass);
            String allLongitude, allLatitude;
            if (allPoints != null) {
                allLongitude = allPoints.split(",")[0];
                allLatitude = allPoints.split(",")[1];

                allLongitude += "w" + longitude.toString();
                allLatitude += "w" + latitude.toString();
            } else {
                allLongitude = longitude.toString();
                allLatitude = latitude.toString();
            }
            SharedPreferences.Editor editor = context.getSharedPreferences("allPoint" + String.valueOf(moveClass), MODE_PRIVATE).edit();
            editor.putString("allLongitude" + String.valueOf(moveClass), allLongitude);
            editor.putString("allLatitude" + String.valueOf(moveClass), allLatitude);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String GetAllPoints(Context context, int moveClass) {
        SharedPreferences pref = context.getSharedPreferences("allPoint" + String.valueOf(moveClass), MODE_PRIVATE);
        String allLongitude = pref.getString("allLongitude" + String.valueOf(moveClass), null);
        String allLatitude = pref.getString("allLatitude" + String.valueOf(moveClass), null);
        if (allLongitude != null && allLatitude != null) {
            return allLongitude + "," + allLatitude;
        } else {
            return null;
        }
    }


    public static String GetNowPoint(Context context) {
        SharedPreferences pref = context.getSharedPreferences("nowPoint", MODE_PRIVATE);
        String allLongitude = pref.getString("longitude", null);
        String allLatitude = pref.getString("latitude", null);
        if (allLongitude != null && allLatitude != null) {
            return allLongitude + "," + allLatitude;
        } else {
            return null;
        }
    }

    public static Boolean SaveNowPoint(Context context, Double longitude, Double latitude) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("nowPoint", MODE_PRIVATE).edit();
            editor.putString("longitude", longitude.toString());
            editor.putString("latitude", latitude.toString());
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String GetOnePoint(Context context, int moveClass, int num) {
        try {
            String allPoints = GetAllPoints(context, moveClass);
            StringBuilder allLongitude;
            StringBuilder allLatitude;
            if (allPoints != null) {
                allLongitude = new StringBuilder(allPoints.split(",")[0]);
                allLatitude = new StringBuilder(allPoints.split(",")[1]);
                ArrayList<String> lonArray, latArray;
                lonArray = new ArrayList<>(Arrays.asList(allLongitude.toString().split("w")));
                latArray = new ArrayList<>(Arrays.asList(allLatitude.toString().split("w")));
                return lonArray.get(num) + "," + latArray.get(num);
            } else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static int GetPointSum(Context context, int moveClass) {
        try {
            String allPoints = GetAllPoints(context, moveClass);
            StringBuilder allLongitude;
            if (allPoints != null) {
                allLongitude = new StringBuilder(allPoints.split(",")[0]);
                ArrayList<String> lonArray;
                lonArray = new ArrayList<>(Arrays.asList(allLongitude.toString().split("w")));
                return lonArray.size();
            } else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Boolean ClearPointAll(Context context, int moveClass) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("allPoint" + String.valueOf(moveClass), MODE_PRIVATE).edit();
            editor.putString("allLongitude" + String.valueOf(moveClass), null);
            editor.putString("allLatitude" + String.valueOf(moveClass), null);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean DeletePoint(Context context, Double longitude, Double latitude, int moveClass) {
        try {
            String allPoints = GetAllPoints(context, moveClass);
            StringBuilder allLongitude;
            StringBuilder allLatitude;
            if (allPoints != null) {
                allLongitude = new StringBuilder(allPoints.split(",")[0]);
                allLatitude = new StringBuilder(allPoints.split(",")[1]);
                ArrayList<String> lonArray, latArray;
                lonArray = new ArrayList<>(Arrays.asList(allLongitude.toString().split("w")));

                latArray = new ArrayList<>(Arrays.asList(allLatitude.toString().split("w")));
                int i = 0, target = 0;
                Boolean targetUse = false;
                for (String lon : lonArray) {
                    Double beforelon, beforelat, nowlon, nowlat;
                    beforelon = Double.valueOf(lon);
                    beforelat = Double.valueOf(latArray.get(i));
                    nowlon = Double.valueOf(longitude);
                    nowlat = Double.valueOf(latitude);
                    if (Math.abs(nowlon - beforelon) < 0.0001 && Math.abs(nowlat - beforelat) < 0.0001) {
                        target = i;
                        targetUse = true;
                    }
                    i++;
                }
                if (targetUse) {
                    lonArray.remove(target);
                    latArray.remove(target);
                }else {
                    return false;
                }
                allLongitude = null;
                for (String s : lonArray) {
                    if (allLongitude != null) {
                        allLongitude.append("w").append(s);
                    } else {
                        allLongitude = s == null ? null : new StringBuilder(s);
                    }
                }
                allLatitude = null;
                for (String s : latArray) {
                    if (allLatitude != null) {
                        allLatitude.append("w").append(s);
                    } else {
                        allLatitude = s == null ? null : new StringBuilder(s);
                    }
                }
            } else {
                return false;
            }
            SharedPreferences.Editor editor = context.getSharedPreferences("allPoint" + String.valueOf(moveClass), MODE_PRIVATE).edit();
            editor.putString("allLongitude" + String.valueOf(moveClass), allLongitude == null ? null : allLongitude.toString());
            editor.putString("allLatitude" + String.valueOf(moveClass), allLatitude == null ? null : allLatitude.toString());
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
