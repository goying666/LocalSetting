package renchaigao.com.localsetting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private String TAG = "MainActivity";

    private String mMockProviderName = LocationManager.GPS_PROVIDER;
    private Thread thread;
    private LocationManager locationManager;
    private Boolean is_run = true;

    private double now_longitude = 113.105969;
    private double now_latitude = 22.977886;

    private double new_longitude = 113.105969;
    private double new_latitude = 22.977886;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        权限检查并修改
        getPersimmions();
        //悬浮窗权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                //启动Activity让用户授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
        if (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0) {
            // 开启了地理位置模拟
            Log.i(TAG, "onCreate: ALLOW_MOCK_LOCATION ");
        }
        LitePal.initialize(this);
        Intent intent = new Intent(MainActivity.this,BackService.class);
        startService(intent);

//        setUse();

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

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    //
//
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    //
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            //悬浮窗
            if (checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

//    public void addX(View view) {
//        now_longitude += 0.01;
//    }

//
//
//    //经纬度字符串
////    private String latLngInfo = "113.105969,22.977886";
//    private String latLngInfo = "113.880554,22.560872";
////    private String latLngInfo = "111.893257,22.559802";
//
//    private LocationManager locationManager;
//    private HandlerThread handlerThread;
//    private Handler handler;
//
//    private boolean isStop = true;
//
//    //uuid random
//    public static String getUUID() {
//        return UUID.randomUUID().toString();
//    }
//
//
//    private void setUse() {
//        try {
//            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);//获得LocationManager引用
//            String providerStr = LocationManager.GPS_PROVIDER;
//            LocationProvider provider = locationManager.getProvider(providerStr);
//            if (provider != null) {
//                locationManager.addTestProvider(
//                        provider.getName()
//                        , provider.requiresNetwork()
//                        , provider.requiresSatellite()
//                        , provider.requiresCell()
//                        , provider.hasMonetaryCost()
//                        , provider.supportsAltitude()
//                        , provider.supportsSpeed()
//                        , provider.supportsBearing()
//                        , provider.getPowerRequirement()
//                        , provider.getAccuracy());
//            } else {
//                locationManager.addTestProvider(
//                        providerStr
//                        , true, true, false, false, true, true, true
//                        , Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
//            }
//            locationManager.setTestProviderEnabled(providerStr, true);
//            locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
//            // 模拟位置可用
//            locationManager.setTestProviderEnabled(providerStr, false);
//            locationManager.removeTestProvider(providerStr);
//        } catch (SecurityException e) {
//        }
//
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        rmNetworkProvider();
//        setNewNetworkProvider();
//
//        //thread
//        handlerThread = new HandlerThread(getUUID(), -2);
//        handlerThread.start();
//
//        handler = new Handler(handlerThread.getLooper()) {
//            public void handleMessage(Message msg) {
//                try {
//                    Thread.sleep(333);
//                    if (!isStop) {
//                        setNetworkLocation();
//                        sendEmptyMessage(0);
//                        //broadcast to MainActivity
////                        Intent intent = new Intent();
////                        intent.putExtra("statusCode", RunCode);
////                        intent.setAction("com.example.service.MockGpsService");
////                        sendBroadcast(intent);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "setNetworkLocation error");
//                    Thread.currentThread().interrupt();
//                }
//            }
//        };
//        handler.sendEmptyMessage(0);
//        isStop = false;
//    }
//
//    //remove network provider
//    private void rmNetworkProvider() {
//        try {
//            String providerStr = LocationManager.NETWORK_PROVIDER;
//            if (locationManager.isProviderEnabled(providerStr)) {
////                locationManager.setTestProviderEnabled(providerStr,true);
//                locationManager.removeTestProvider(providerStr);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //set new network provider
//    private void setNewNetworkProvider() {
//        String providerStr = LocationManager.NETWORK_PROVIDER;
//        try {
//            locationManager.addTestProvider(providerStr, false, false,
//                    false, false, false, false,
//                    false, 1, Criteria.ACCURACY_FINE);
////            locationManager.setTestProviderStatus("network", LocationProvider.AVAILABLE, null,
////                    System.currentTimeMillis());
//        } catch (SecurityException e) {
//            Log.d(TAG, "setNewNetworkProvider error");
//        }
//        if (!locationManager.isProviderEnabled(providerStr)) {
//            Log.d(TAG, "now  setTestProviderEnabled[network]");
//            locationManager.setTestProviderEnabled(providerStr, true);
//        }
//    }
//
//    //set network location
//    private void setNetworkLocation() {
//        String latLngStr[] = latLngInfo.split(",");
//        String providerStr = LocationManager.NETWORK_PROVIDER;
//        try {
//            locationManager.setTestProviderLocation(providerStr, generateLocation(Double.valueOf(latLngStr[0]), Double.valueOf(latLngStr[1])));
//        } catch (Exception e) {
//            Log.d(TAG, "setNetworkLocation error");
//            e.printStackTrace();
//        }
//    }
//
//    //generate a location
//    public Location generateLocation(double x, double y) {
//        Location loc = new Location("gps");
//        loc.setAccuracy(2.0F);
//        loc.setAltitude(55.0D);
//        loc.setBearing(1.0F);
//        Bundle bundle = new Bundle();
//        bundle.putInt("satellites", 7);
//        loc.setExtras(bundle);
//
//        loc.setLatitude(x);
//        loc.setLongitude(y);
//        loc.setTime(System.currentTimeMillis());
//        if (Build.VERSION.SDK_INT >= 17) {
//            loc.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
//        }
//        return loc;
//    }
}
