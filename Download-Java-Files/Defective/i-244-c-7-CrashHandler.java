package com.hasee.minibuslocalhost.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangju on 2019/2/15
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mContext;//上下文
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;//系统默认处理类
    private static CrashHandler instance = null;//私有静态成员变量
    private Map<String, String> infos = new HashMap<>();//用来存储设备信息和异常信息
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//格式化日期

    //私有构造函数
    private CrashHandler() {

    }

    //获取CrashHandler实例
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param mContext
     */
    public void init(Context mContext) {
        this.mContext = mContext;
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();//获取系统默认的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);//设置CrashHandler为程序的默认处理器
        autoClear(7);
    }

    /**
     * 自动清除本地文件
     *
     * @param i
     */
    private void autoClear(final int i) {
        FileUtil.delete(getGlobalpath(), new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String s = FileUtil.getFileNameWithoutExtension(name);//获得不带扩展名的文件名称
                int day = i < 0 ? i : -1 * i;//
                String date = "crash-" + DateUtil.getOtherDay(day);
                return date.compareTo(s) >= 0;
            }
        });
    }

    /**
     * File.separator:与系统有关的默认名称分隔符
     * 返回路径名
     *
     * @return
     */
    private String getGlobalpath() {
        String path = Environment.getExternalStorageDirectory() +
                File.separator + "crash" + File.separator;
        return path;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && defaultExceptionHandler != null) {//用户没有处理则让系统默认来处理
            defaultExceptionHandler.uncaughtException(t, e);
        } else {
            //退出程序
            ActivityCollector.finshAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告
     *
     * @param e
     * @return 如果处理了改信息返回true，否则false
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        try {
            //收集设备信息
            collectDeviceInfo(mContext);
            //保存错误信息
            saveCrashInfoFile(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * 保存错误信息
     * @param e
     */
    private String saveCrashInfoFile(Throwable e) {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            sb.append("\r\n"+date+"\n");
            for (Map.Entry<String,String> entry: infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key+"="+value+"\n");
            }
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while(cause != null){
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);
            String fileName = writeFile(sb.toString());
            return fileName;
        } catch (Exception e1) {
            e1.printStackTrace();
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    /**
     * 输出文件
     * @param s
     * @return
     */
    private String writeFile(String s) {
        String time = dateFormat.format(new Date());
        String fileName = "crash-"+time+".log";
        if(FileUtil.hasSdcard()){//判断SD卡是否可用
            String path = getGlobalpath();
            File dir = new File(path);
            if(!dir.exists()){//判断文件或者目录是否存在
                dir.mkdirs();//创建此文件的目录
            }
            try {
                File file = new File(path+fileName);
                if(!file.exists()){//文件不存在
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file,true);
                fos.write(s.getBytes());
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 收集设备信息
     *
     * @param mContext
     */
    private void collectDeviceInfo(Context mContext) {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName + "";
                String versionCode = packageInfo.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(),field.toString());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
