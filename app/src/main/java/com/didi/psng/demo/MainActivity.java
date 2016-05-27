package com.didi.psng.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sdu.didi.openapi.DIOpenSDK;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //一下两行代码在入口activity调用尽早调用，第一个参数为appid，第二个为secret。
        //http://open.xiaojukeji.com/这里可以申请
        DIOpenSDK.registerApp(this, "appid", "secret");
    }

    //使用webapp
    public void DidiClick(View view) {
        HashMap<String, String> map = new HashMap<String, String>();
        DIOpenSDK.showDDPage(this, map);
    }

    //获取ticket
    public void DidiClick2(View view) {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("aa", "bb");
//        map.put("bb", "cc");
//        map.put("lat", "39.916195");
//        map.put("lng", "116.66355");
//        map.put("maptype", "wgs84");
        DIOpenSDK.asynGetTicket(this, DIOpenSDK.TicketType.LONGTIME, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("asynGetTicket", result.toString());
            }
        });
    }

    //打开发票页面需要先登录
    public void DidiClick3(View view) {
        Map map = new HashMap<String, String>();
        map.put("page", "menu");
        DIOpenSDK.openPage(this, "invoice", map, null);
    }

    //登录页面
    public void DidiClick4(View view) {
        Map map = new HashMap<String, String>();
        map.put("cardbind", "true");
        map.put("autoclose", "true");
        DIOpenSDK.openPage(this, "login", map, null);
    }

    //订单详情
    public void DidiClick5(View view) {
        Map map = new HashMap<String, String>();
        map.put("biz", "2");
        map.put("oid", "这里需要传实际获取到的oid");
        DIOpenSDK.openPage(this, "orderDetail", map, null);
    }

    //预估时间
    public void DidiClick6(View view) {
        //getEstimateTime
        Map map = new HashMap<String, String>();
        map.put("biz", "2");
        map.put("fromlat", "40.043571");
        map.put("fromlng", "116.290506");
        map.put("maptype", "soso");

        DIOpenSDK.asynCallDDApi(this, "getEstimateTime", map, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("asynCallDDApi", result.toString());
            }
        });

    }

    //预估车费
    public void DidiClick7(View view) {
        Map map = new HashMap<String, String>();
        map.put("biz", "2");
        map.put("fromlat", "40.043571");
        map.put("fromlng", "116.290506");
        map.put("tolat", "40.046571");
        map.put("tolng", "116.200506");
        map.put("maptype", "soso");

        DIOpenSDK.asynCallDDApi(this, "getEstimatePrice", map, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("asynCallDDApi", result.toString());
            }
        });

//        getOrderList

//        map.clear();
//        map.put("size", 10);
//        map.put("offset", 0);
//        map.put("biz", 2);
//        //获取订单列表
//        DIOpenSDK.asynCallDDApi(this, "getOrderList", map, new DIOpenSDK.DDCallBack() {
//            @Override
//            public void onFinish(Map<String, String> result) {
//                Log.e("asynCallDDApi", result.toString());
//            }
//        });

    }

    //打开订单列表界面
    public void DidiClick8(View view) {
        final HashMap<String, String> map = new HashMap<String, String>();
        DIOpenSDK.openPage(this, "orderList", map, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("callback", result.toString());
            }
        });
    }

    //获取当前进行中订单的司机信息
    public void DidiClick10(final View view) {
        Map map = new HashMap<String, String>();

        DIOpenSDK.asynCallDDApi(this, "getCurrentDriverInfo", map, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("asynCallDDApi", result.toString());
                //拨打司机电话
                DIOpenSDK.callPhone(view.getContext(), result.get("phone"));
            }
        });
    }

    //获取当前进行中的订单状态
    public void DidiClick9(View view) {
        Map map = new HashMap<String, String>();

        DIOpenSDK.asynCallDDApi(this, "getCurrentOrderStatus", map, new DIOpenSDK.DDCallBack() {
            @Override
            public void onFinish(Map<String, String> result) {
                Log.e("asynCallDDApi", result.toString());
            }
        });
    }
}
