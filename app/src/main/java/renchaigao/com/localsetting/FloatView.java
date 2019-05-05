package renchaigao.com.localsetting;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class FloatView {
    private Context mContext;
    private TextView move_speed;
    private ImageView ImageView_right;
    private ImageView ImageView_down;
    private ImageView ImageView_left;
    private ImageView ImageView_up;
    private ImageView miaozhun;
    private ImageView add, delete, after_one, next_one;
    private Button Button_view, ziyuan, monster, clear;
    private Button auto, auto_ziyuan, auto_zhuaguai, leitai;
    private ConstraintLayout ConstraintLayout_view;
    private ConstraintLayout ConstraintLayout_showView;
    private ConstraintLayout viewAll;
    private ConstraintLayout autoPart;
    //    private AppCompatSpinner spinner;
    //    private ConstraintLayout.LayoutParams mWindowParams;
    private WindowManager.LayoutParams mWindowParams;
    private WindowManager mWindowManager;
    private double now_longitude = 113.903526;
    private double now_latitude = 22.552019;
    private double moveDevLongitude = 0.0001;
    private double moveDevLatitude = 0.0001;

    private int moveSpeed = 1, moveClass = 0, class0number = 0, class1number = 0, class2number = 0;
    String[] moveSpeedString = {"X03", "X1", "X5", "X10", "X30"};
    int[] moveSpeedValue = {0, 1, 5, 10, 30};
    private int moveSpeedSelect = 0;
    private Boolean showAuto = false;
    private Boolean viewShow = false;
    private Boolean miaozhunShow = false;


    FloatView(Service context) {
        this.mContext = context;
        InitFloatView();
        SetClick();
    }

    private void InitFloatView() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (inflater == null)
            return;
        ConstraintLayout_view = (ConstraintLayout) inflater.inflate(R.layout.float_button, null);
        move_speed = (TextView) ConstraintLayout_view.findViewById(R.id.move_speed);
        ImageView_right = (ImageView) ConstraintLayout_view.findViewById(R.id.ImageView_right);
        ImageView_down = (ImageView) ConstraintLayout_view.findViewById(R.id.ImageView_down);
        ImageView_left = (ImageView) ConstraintLayout_view.findViewById(R.id.ImageView_left);
        ImageView_up = (ImageView) ConstraintLayout_view.findViewById(R.id.ImageView_up);
        miaozhun = (ImageView) ConstraintLayout_view.findViewById(R.id.miaozhun);
        add = (ImageView) ConstraintLayout_view.findViewById(R.id.add);
        delete = (ImageView) ConstraintLayout_view.findViewById(R.id.delete);
        after_one = (ImageView) ConstraintLayout_view.findViewById(R.id.after_one);
        next_one = (ImageView) ConstraintLayout_view.findViewById(R.id.next_one);
        Button_view = (Button) ConstraintLayout_view.findViewById(R.id.Button_view);
        ziyuan = (Button) ConstraintLayout_view.findViewById(R.id.ziyuan);
        monster = (Button) ConstraintLayout_view.findViewById(R.id.monster);
        clear = (Button) ConstraintLayout_view.findViewById(R.id.clear);
        auto = (Button) ConstraintLayout_view.findViewById(R.id.auto);
        auto_ziyuan = (Button) ConstraintLayout_view.findViewById(R.id.auto_ziyuan);
        auto_zhuaguai = (Button) ConstraintLayout_view.findViewById(R.id.auto_zhuaguai);
        leitai = (Button) ConstraintLayout_view.findViewById(R.id.leitai);
//        spinner = (AppCompatSpinner) ConstraintLayout_view.findViewById(R.id.spinner);
        ConstraintLayout_showView = (ConstraintLayout) ConstraintLayout_view.findViewById(R.id.ConstraintLayout_showView);
        viewAll = (ConstraintLayout) ConstraintLayout_view.findViewById(R.id.viewAll);
        autoPart = (ConstraintLayout) ConstraintLayout_view.findViewById(R.id.auto_part);
        viewAll.setVisibility(View.GONE);
        autoPart.setVisibility(View.GONE);
        miaozhun.setVisibility(View.GONE);
        moveSpeed = moveSpeedValue[moveSpeedSelect];
        move_speed.setText(moveSpeedString[moveSpeedSelect]);
//        mWindowParams = new ConstraintLayout.LayoutParams();
        mWindowParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        String ss = DataUtil.GetAllPoints(mContext, 0);
        String ss1 = DataUtil.GetAllPoints(mContext, 1);
//        SharedPreferences.Editor editor = mContext.getSharedPreferences("allPoint0", MODE_PRIVATE).edit();
//        editor.putString("allLongitude0" , "113.89248600000005w113.89153600000002w113.89211599999999w113.89212599999999w113.89144600000002w113.89242599999999w113.89241599999997w113.89244599999996w113.89343599999995w113.89353599999995w113.89427599999998w113.89509600000001w113.89519600000001w113.89550600000004w113.89649600000003w113.89755600000002w113.89828600000001w113.89848600000002w113.89948600000002w113.89964600000002w113.89899600000005w113.90071600000012w113.90107600000012w113.90127600000012w113.90134600000013w113.9014260000001w113.90212600000011w113.90226600000013w113.90333600000017w113.90359600000015w113.90423600000017w113.90357600000019w113.90466600000018w113.90395600000015w113.90439600000016w113.90385600000013w113.90358600000013w113.90298600000014w113.90272600000014w113.90322600000016w113.90322600000016w113.90376600000019w113.90386600000019w113.90457600000022w113.90468600000024w113.90351600000022w113.90331600000022w113.90271600000021w113.90281600000021w113.90251600000022w113.90271600000023w113.90248600000022w113.90181600000022w113.90194600000022w113.90114600000022w113.90024600000022w113.8992460000002w113.89921600000021w113.8990160000002w113.8982860000002w113.8972860000002w113.8971560000002w113.8973560000002w113.89752600000021w113.89638600000019w113.89624600000018w113.89497600000017w113.89460600000015w113.89420600000015w113.89396600000013w113.89409600000013w113.89349600000013");
//        editor.putString("allLatitude0" , "22.550409w22.549609w22.549309w22.548899w22.548458999999998w22.548669w22.548869w22.549439w22.549868999999997w22.549768999999998w22.550738999999997w22.551138999999996w22.551698999999992w22.55189899999999w22.55289899999999w22.55326899999999w22.55366899999999w22.55326899999999w22.553378999999993w22.554488999999993w22.554788999999992w22.554698999999996w22.554528999999995w22.554528999999995w22.555798999999993w22.556578999999996w22.556148999999998w22.556448999999997w22.556418999999998w22.556089w22.555959w22.556669000000003w22.557029w22.557629w22.558488999999994w22.558518999999993w22.558718999999993w22.55904899999999w22.55954899999999w22.55998899999999w22.55998899999999w22.55988899999999w22.55998899999999w22.56085899999999w22.56095899999999w22.561858999999988w22.56115899999999w22.56125899999999w22.56051899999999w22.56031899999999w22.55972899999999w22.55952899999999w22.55842899999999w22.55832899999999w22.55762899999999w22.557428999999992w22.556928999999993w22.556028999999995w22.556158999999994w22.555758999999995w22.554958999999997w22.554828999999998w22.554739w22.554009000000004w22.554039000000003w22.553139000000005w22.552709000000007w22.552509000000008w22.55217900000001w22.551899000000006w22.551729000000005w22.551089000000005");
//        editor.apply();
        String ssa = DataUtil.GetAllPoints(mContext, 0);
        String ssa1 = DataUtil.GetAllPoints(mContext, 1);
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        mWindowParams.format = PixelFormat.RGBA_8888;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowParams.gravity = Gravity.END | Gravity.BOTTOM;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


//        DataUtil.SaveNowPoint(mContext, now_longitude, now_latitude);
        String nowPoint = DataUtil.GetNowPoint(mContext);
        if (nowPoint != null) {
            String[] point = nowPoint.split(",");
            now_longitude = Double.valueOf(point[0]);
            now_latitude = Double.valueOf(point[1]);
        } else {
            DataUtil.SaveNowPoint(mContext, now_longitude, now_latitude);
        }

    }


    private void getMovePoint() {
        String point = null;
        switch (moveClass) {
            case 0:
                point = DataUtil.GetOnePoint(mContext, moveClass, class0number);
                break;
            case 1:
                point = DataUtil.GetOnePoint(mContext, moveClass, class1number);
                break;
            case 2:
                point = DataUtil.GetOnePoint(mContext, moveClass, class2number);
                break;
        }
        if (point != null) {
            now_longitude = Double.valueOf(point.split(",")[0]);
            now_latitude = Double.valueOf(point.split(",")[1]);
        }
    }

    private void startService() {
        DataUtil.SaveNowPoint(mContext, now_longitude, now_latitude);
        Intent intent = new Intent(mContext, BackService.class);
        intent.putExtra("longitude", now_longitude);
        intent.putExtra("latitude", now_latitude);
        mContext.startService(intent);
    }

    private void SetClick() {
        Button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewShow = !viewShow;
                if (viewShow) {
                    viewAll.setVisibility(View.VISIBLE);
                    auto.setText("自动");
                } else {
                    viewAll.setVisibility(View.GONE);
                    auto.setText("手动");
                }
            }
        });
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAuto = !showAuto;
                if (showAuto) {
                    autoPart.setVisibility(View.VISIBLE);
                } else {
                    autoPart.setVisibility(View.GONE);
                }
            }
        });
        leitai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveClass = 2;
                Toast.makeText(mContext, "切换类型：擂台", Toast.LENGTH_SHORT).show();
            }
        });
        auto_zhuaguai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miaozhunShow = !miaozhunShow;
                if (miaozhunShow) {
                    miaozhun.setVisibility(View.VISIBLE);
                } else {
                    miaozhun.setVisibility(View.GONE);
                }
            }
        });
        auto_ziyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (class0number < DataUtil.GetPointSum(mContext, 0) - 1) {
                    class0number++;
                } else {
                    class0number = 0;
                }
                Toast.makeText(mContext, "移动类型：" + 0 + " 位置：" + class0number, Toast.LENGTH_SHORT).show();
                String point;
                point = DataUtil.GetOnePoint(mContext, 0, class0number);
                if (point != null) {
                    now_longitude = Double.valueOf(point.split(",")[0]);
                    now_latitude = Double.valueOf(point.split(",")[1]);
                }
                startService();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataUtil.ClearPointAll(mContext, moveClass)) {
                    Toast.makeText(mContext, "删除成功，类型：" + moveClass, Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveSpeed == 0) {
                    now_longitude += moveDevLatitude * 0.3;
                } else {
                    now_longitude += moveDevLatitude * moveSpeed;
                }
                startService();
            }
        });
        ImageView_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveSpeed == 0) {
                    now_latitude -= moveDevLatitude * 0.3;
                } else {
                    now_latitude -= moveDevLatitude * moveSpeed;
                }
                startService();
            }
        });
        ImageView_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveSpeed == 0) {
                    now_longitude -= moveDevLongitude * 0.3;
                } else {
                    now_longitude -= moveDevLongitude * moveSpeed;
                }
                startService();
            }
        });
        ImageView_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveSpeed == 0) {
                    now_latitude += moveDevLongitude * 0.3;
                } else {
                    now_latitude += moveDevLongitude * moveSpeed;
                }
                startService();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtil.AddNewPoint(mContext, now_longitude, now_latitude, moveClass);
                Toast.makeText(mContext, "添加成功，类型：" + moveClass, Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtil.DeletePoint(mContext, now_longitude, now_latitude, moveClass);
                Toast.makeText(mContext, "删除成功，类型：" + moveClass, Toast.LENGTH_SHORT).show();
            }
        });
        ziyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveClass = 0;
                Toast.makeText(mContext, "切换类型：资源", Toast.LENGTH_SHORT).show();
            }
        });
        monster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveClass = 1;
                Toast.makeText(mContext, "切换类型：妖怪", Toast.LENGTH_SHORT).show();
            }
        });
        move_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveSpeedSelect >= 4) {
                    moveSpeedSelect = 0;
                } else {
                    moveSpeedSelect++;
                }
                moveSpeed = moveSpeedValue[moveSpeedSelect];
                move_speed.setText(moveSpeedString[moveSpeedSelect]);

                Toast.makeText(mContext, "调速：" + moveSpeedString[moveSpeedSelect], Toast.LENGTH_SHORT).show();
            }
        });
        next_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (moveClass) {
                    case 0:
                        if (class0number > 0) {
                            class0number--;
                        } else {
                            class0number = DataUtil.GetPointSum(mContext, moveClass) - 1;
                        }

                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class0number, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if (class1number > 0) {
                            class1number--;
                        } else {
                            class1number = DataUtil.GetPointSum(mContext, moveClass) - 1;
                        }
                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class1number, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if (class2number > 0) {
                            class2number--;
                        } else {
                            class2number = DataUtil.GetPointSum(mContext, moveClass) - 1;
                        }
                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class2number, Toast.LENGTH_SHORT).show();
                        break;
                }
                getMovePoint();
                startService();
            }
        });
        after_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (moveClass) {
                    case 0:
                        if (class0number < DataUtil.GetPointSum(mContext, moveClass) - 1) {
                            class0number++;
                        } else {
                            class0number = 0;
                        }
                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class0number, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if (class1number < DataUtil.GetPointSum(mContext, moveClass) - 1) {

                            class1number++;
                        } else {
                            class1number = 0;
                        }
                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class1number, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if (class2number < DataUtil.GetPointSum(mContext, moveClass) - 1) {

                            class2number++;
                        } else {
                            class2number = 0;
                        }
                        Toast.makeText(mContext, "移动类型：" + moveClass + " 位置：" + class2number, Toast.LENGTH_SHORT).show();
                        break;
                }
                getMovePoint();
                startService();
            }
        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                moveClass = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                moveClass = 0;
//            }
//        });

    }

    public void showFloatWindow() {
        if (ConstraintLayout_view.getParent() == null) {
            mWindowManager.addView(ConstraintLayout_view, mWindowParams);
        }
    }

    public void hideFloatWindow() {
        if (ConstraintLayout_view.getParent() != null)
            mWindowManager.removeView(ConstraintLayout_view);
    }


}
