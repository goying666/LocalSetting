package renchaigao.com.localsetting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

public class BackService extends Service implements LocationListener {
    public BackService() {
    }

    private String mMockProviderName = LocationManager.GPS_PROVIDER;
    private Thread thread;
    private LocationManager locationManager;
    private Boolean is_run = true;

    private double now_longitude = 113.505969;
    private double now_latitude = 22.977886;

    private double new_longitude = 113.105969;
    private double new_latitude = 22.977886;
    private FloatView floatView;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        now_longitude = intent.getDoubleExtra("longitude", now_longitude);
        now_latitude = intent.getDoubleExtra("latitude", now_latitude);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init_location();
        String nowPoint = DataUtil.GetNowPoint(this);
        if (nowPoint != null) {
            String[] point = nowPoint.split(",");
            now_longitude = Double.valueOf(point[0]);
            now_latitude = Double.valueOf(point[1]);
        } else {
            DataUtil.SaveNowPoint(this, now_longitude, now_latitude);
        }
        // 开启线程，一直修改GPS坐标
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (is_run) {
                    try {
                        Thread.sleep(500);
                        setLocation(now_longitude, now_latitude);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        floatView = new FloatView(this);
        floatView.showFloatWindow();
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        floatView.hideFloatWindow();
    }

    /**
     * inilocation 初始化 位置模拟
     */
    private void init_location() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true, true, 0, 5);
        locationManager.setTestProviderEnabled(mMockProviderName, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(mMockProviderName, 0, 0, this);
    }

    @SuppressLint("NewApi")
    private void setLocation(double longitude, double latitude) {
        Location location = new Location(mMockProviderName);
        location.setTime(System.currentTimeMillis());
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(2.0f);
        location.setAccuracy(3.0f);
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        locationManager.setTestProviderLocation(mMockProviderName, location);
    }

    /**
     * Called when the location has changed.
     *
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
