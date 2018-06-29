package shouhu.cn.basicutilmodel;

import android.util.Log;

import cn.swiftpass.cn.standardwallet.application.ProjectApp;


/**
 * Log
 * @author gc
 * @since 1.1
 */
public class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final boolean isDebug = true;

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, "wallet---" + msg + "---");
        }
    }

    public static void d(Object object, String msg) {
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void d(Object object, Object msg){
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, "wallet---" + msg + "---");
        }
    }

    public static void i(Object object, String msg) {
        if (isDebug) {
            Log.i(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void i(Object object, Object msg){
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, "wallet---" + msg + "---");
        }
    }

    public static void w(Object object, String msg) {
        if (isDebug) {
            Log.w(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void w(Object object, Object msg){
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, "wallet---" + msg + "---");
        }
    }

    public static void e(Object object, String msg) {
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), "wallet---" + msg + "---");
        }
    }



}
