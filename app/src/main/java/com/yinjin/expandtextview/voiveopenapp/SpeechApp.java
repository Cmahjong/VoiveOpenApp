package com.yinjin.expandtextview.voiveopenapp;

import android.app.Application;
import android.content.pm.PackageInfo;

import com.iflytek.cloud.SpeechUtility;
import com.yinjin.expandtextview.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class SpeechApp extends Application {
   public static ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据

    @Override
    public void onCreate() {
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SpeechUtility.createUtility(SpeechApp.this, "appid=" + getString(R.string.app_id));
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo =new AppInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            appList.add(tmpInfo);
        }
        super.onCreate();
    }

}
