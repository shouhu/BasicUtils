package shouhu.cn.basicutilmodel;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.swiftpass.cn.standardwallet.R;
import cn.swiftpass.cn.standardwallet.application.ProjectApp;
import cn.swiftpass.cn.standardwallet.dialog.CustomMsgDialog;
import cn.swiftpass.cn.standardwallet.application.WalletConstants;
import cn.swiftpass.cn.standardwallet.login.view.LoginActivity;


/**
 * Created by admin on 2017/12/18.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}(Android相关的基础工具类)
 * @date 2017/12/18.14:51.
 */

public class AndroidUtils {
    private static final String LOG_TAG = AndroidUtils.class.getSimpleName();
    private static final String TAG = "AndroidUtils";

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context
     * @param dpValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //同上反向转换
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1) hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void showKeyboard(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取随机色
     *
     * @return
     */
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }


    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int randomColor(int alpha) {
        Random rnd = new Random();
        alpha = Math.min(Math.max(1, alpha), 255);
        return Color.argb(alpha, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static String getAndroidID(Context mContext) {
        String m_szAndroidID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    public static String getseriald() {

        return Build.SERIAL;
    }

    public static String getUuid() {
        return java.util.UUID.randomUUID().toString();
    }


    /**
     * 获取颜色值.
     *
     * @param context 上下文
     * @param resId   资源id
     * @return 颜色值
     */
    public static int getColor(Context context, int resId) {
        if (context != null) {
            return context.getResources().getColor(resId);
        } else {
            throw new RuntimeException("Context must not be null.");
        }
    }

    /**
     * 设置指纹
     *
     * @param context
     */
    public static void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    /**
     * 获取当前app包信息对象.
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    private static PackageInfo getCurrentAppPackageInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info;
        } catch (PackageManager.NameNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前app的版本号.
     *
     * @param context
     * @return
     */
    public static int getCurrentAppVersionCode(Context context) {
        PackageInfo info = getCurrentAppPackageInfo(context);
        int versionCode = info.versionCode;
        return versionCode;
    }

    /**
     * 获取当前app的版本名字.
     *
     * @param context
     * @return
     */
    public static String getCurrentAppVersionName(Context context) {
        PackageInfo info = getCurrentAppPackageInfo(context);
        String version = info.versionName;
        return version;
    }

    public static void launchAppDetail(String appPkg, String marketPkg, Context mContext) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机的imei.
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获取手机显示数据.
     *
     * @param activity 活动对象
     * @return 手机手机显示数据
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric;
        } else {
            throw new RuntimeException("Activity must not be null.");
        }
    }

    /**
     * 获取系统版本.2.2对应是8.
     *
     * @return
     */
    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本号.
     *
     * @return
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getMobileModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getMobileBrand() {
        return Build.BRAND;
    }


    /**
     * 外部存储是否可写(也可读)，true代表可写，false代表不可写.
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public static boolean needRequestApplyPermissien() {

        return Build.VERSION.SDK_INT >= 23;
    }

    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 获取SD卡外部剩于空间
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        return freeBlocks * blockSize;
    }

    /**
     * 获取内部剩余空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS 文件大小
     * @return
     */
    public static String FormetFileSize(long fileS) {
        if (fileS == 0) return "0.00B";

        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 判断当前网络是否处于2G/3G情况下.
     *
     * @param context 上下文
     * @return 布尔值，true代表可用，false代表不可用
     */
    public static boolean isMobileNetworkValid(Context context) {
        return isPhoneNetworkValid(context, ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 判断网络是否可用.
     *
     * @param context 上下文
     * @return true代表网络可用，false代表网络不可用.
     */
    public static boolean isNetworkValid(Context context) {
        boolean result = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            result = false;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info == null) {
                result = false;
            } else {
                if (info.isAvailable()) {
                    result = true;
                }
            }
        }
        return result;
    }

    public static boolean isValidLocationNetwork(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return providerEnabled;
    }

    public static boolean isValidLocation(Context context) {
        return isValidLocationNetwork(context) || isValidLocationGps(context);
    }

    public static boolean isValidLocationGps(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return providerEnabled;
    }

    public static boolean isPhoneNetworkValid(Context context, int type) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(type).getState();
        return mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING;
    }

    /**
     * 用来判断服务是否后台运行.
     *
     * @param mContext  上下文
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean IsRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                IsRunning = true;
                break;
            }
        }
        return IsRunning;
    }


    public static void switchLanguage(String language, Context mContext) {
        //设置应用语言类型
        Resources resources = mContext.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (!TextUtils.isEmpty(language) && (language.equals(WalletConstants.LANG_CODE_ZH_TW) || language.equals(WalletConstants.LANG_CODE_ZH_HK_NEW))) // 繁体
        {
            config.locale = Locale.TRADITIONAL_CHINESE;
        } else if (!TextUtils.isEmpty(language) && language.equals(WalletConstants.LANG_CODE_EN_US)) {
            config.locale = Locale.ENGLISH;
        } else if (!TextUtils.isEmpty(language) && language.equals(WalletConstants.LANG_CODE_ZH_CN)) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else { // 跟随系统
            config.locale = Locale.getDefault(); //默认英文
        }
        LogUtils.i(TAG, "Locale.getDefault()-->" + Locale.getDefault());

        if (!isEmptyOrNull(language)) {
            SpUtils.getInstance(mContext).setAppLanguage(language);
        } else {
            Locale locale = mContext.getResources().getConfiguration().locale;
            String lan = locale.getLanguage() + "-" + locale.getCountry();
            LogUtils.i(TAG, "lan-->" + lan);//  zh-HK  en-HK   zh-CN   en-
            if (lan.equalsIgnoreCase(WalletConstants.LANG_CODE_ZH_CN_NEW)) {
                config.locale = Locale.SIMPLIFIED_CHINESE;
            } else if (lan.equalsIgnoreCase(WalletConstants.LANG_CODE_ZH_HK_NEW) || lan.equalsIgnoreCase(WalletConstants.LANG_CODE_ZH_MO_NEW) || lan.equalsIgnoreCase(WalletConstants.LANG_CODE_ZH_TW_NEW)) {
                LogUtils.i(TAG, "LANG_CODE_ZH_HK-->");
                config.locale = Locale.TRADITIONAL_CHINESE;
            } else {
                config.locale = Locale.ENGLISH;
            }
            //设置系统语言为默认语言
            SpUtils.getInstance(mContext).setAppLanguage(lan);
        }
        resources.updateConfiguration(config, dm);
    }

    private static boolean isEmptyOrNull(String str) {
        return "".equals(str) || null == str || "null".equals(str);
    }


    /**
     * 判断任务是否空闲.
     *
     * @param asyncTask 异步任务
     * @return true代表是空闲的，false代表不是空闲的.
     */
    public static boolean isTaskIdle(AsyncTask asyncTask) {
        return asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED;
    }

    /**
     * 判断当前网络是否处于wifi情况下.
     *
     * @param context 上下文
     * @return 布尔值，true代表可用，false代表不可用
     */
    public static boolean isWifiNetworkValid(Context context) {
        return isPhoneNetworkValid(context, ConnectivityManager.TYPE_WIFI);
    }


    /**
     * 安全执行runnable的UI事件.
     *
     * @param activity
     * @param runnable
     */
    public static void runOnUiThreadSafety(Activity activity, final Runnable runnable) {
        if (activity == null) {
            return;
        }
        if (runnable == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 调用系统短信程序，发送短信.
     *
     * @param context
     * @param tel     短信接收号码
     * @param smsBody 短信内容
     */
    public static void sendMsg(Context context, String tel, String smsBody) {
        if (tel == null) {
            tel = "";
        }
        String uriString = "smsto:" + tel;// 联系人地址
        Uri smsToUri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);// 短信内容
        context.startActivity(intent);
    }

    public static void setBackgroundResourceNotChangePadding(View view, int resId) {
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();
        view.setBackgroundResource(resId);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 土司显示错误信息.
     *
     * @param applicationContext 上下文
     * @param exception          异常对象，里面有相关的错误信息.
     */
    public static void showErrMsg(Context applicationContext, Exception exception) {
        if (applicationContext != null && exception != null) {
            Toast.makeText(applicationContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示（时间是短暂的）土司消息
     *
     * @param context 上下文
     * @param msg     消息内容
     */
    public static void showMsg(Context context, String msg) {
        if (context != null && msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从View变成bitmap.
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    /**
     * 获取view的drawable.
     *
     * @param context
     * @param view
     * @param width
     * @param height
     * @return
     */
    public static Drawable getDrawableExactly(Context context, View view, int width, int height) {
        int specWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int specHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        view.measure(specWidth, specHeight);
        int viewMeasuredWidth = view.getMeasuredWidth();
        int viewMeasuredHeight = view.getMeasuredHeight();
        view.layout(0, 0, viewMeasuredWidth, viewMeasuredHeight);
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        Bitmap b = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(context.getResources(), viewBmp);
    }

    /**
     * 隐藏软键盘
     */
    public static void hiddelKey(Activity act) {
        try {
            ((InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {

        }
    }

    /**
     * 获取渠道编号
     *
     * @param context
     * @return
     */
   /* public static String getUmengChannelCode(Context context) {
        String code = getMetaData(context, "UMENG_CHANNEL");
        if(TextUtils.equals("A",code.substring(0,1))){
            String subcode = code.substring(1,code.length());
            if (subcode != null) {
                return subcode;
            }
        }else{
            if (code != null) {
                return code;
            }
        }

        return "-1";
    }*/
    private static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {


        }
        return null;
    }

    /**
     * 将字符串转码
     *
     * @param paramString
     * @return
     */
    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
//			YLog.i("","urlEncoded="+str);
            return str;
        } catch (Exception localException) {
        }
        return "";
    }

    /**
     * 将url字符串解码
     *
     * @param paramString
     * @return
     */
    public static String fromURLDecoder(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }
        return "";
    }

    public static void openLocalPdf(Context context, String path) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        i.setDataAndType(uri, "application/pdf");
        context.startActivity(i);
    }


    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) Result += line;
            return Result;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 长整形转换成“yyyy-MM-dd HH:mm:ss”格式
     *
     * @param time
     * @return
     * @throws
     * @Title: long2yMdHms
     * @Description: TODO
     */
    public static String long2yMdHms(long time) {
        return long2str(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String long2str(long time, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(long2calendar(time).getTime());
    }

    public static Calendar long2calendar(long time) {
        Date date = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }


    //根据当前输入的内容每四位添加一个空格，返回值为拼接之后的字符串
    public static String addSpaceByInputContent(String content, int Totalcount, String spaceString) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        //去空格
        content = content.replaceAll(" +", ""); //去掉所有空格
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        //用户名限制为8位的电话Totalcount=8
        if (content.length() > Totalcount) {
            content = content.substring(0, Totalcount);
        }
        StringBuilder newString = new StringBuilder();
        for (int i = 1; i <= content.length(); i++) {
            //当为第4位时，并且不是最后一个第4位时
            //拼接字符的同时，拼接一个空格spaceString
            //如果在最后一个第四位也拼接，会产生空格无法删除的问题
            //因为一删除，马上触发输入框改变监听，又重新生成了空格
            if (i % 4 == 0 && i != content.length()) {
                newString.append(content.charAt(i - 1) + spaceString);
            } else {
                //如果不是4位的倍数，则直接拼接字符即可
                newString.append(content.charAt(i - 1));
            }
        }
        return newString.toString();
    }


    /**
     * 验证六位支付密码是否连续  123456
     *
     * @param pwdStr
     * @return
     */
    private static boolean checkPwdLegal(String pwdStr) {
        if (pwdStr.length() != 6) return false;
        int index = 0;
        int lastValue = Integer.valueOf(pwdStr.charAt(index) + "").intValue() + 1;
        int nextValue = Integer.valueOf(pwdStr.charAt(index + 1) + "").intValue();
        while (lastValue == nextValue && index <= pwdStr.length() - 3) {
            index += 1;
            lastValue = Integer.valueOf(pwdStr.charAt(index) + "").intValue() + 1;
            nextValue = Integer.valueOf(pwdStr.charAt(index + 1) + "").intValue();
        }
        if (lastValue == nextValue) {
            return index != pwdStr.length() - 2;
        } else {
            return true;
        }
    }

    /**
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {

                return isCanUse;
            }
        }
        return isCanUse;
    }


    public static void startAppSetting(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        mContext.startActivity(intent);
    }


    public static boolean checkPwdIsLegal(String pwdStr) {
        return !checkPwdIsSameLegal(pwdStr) && !checkPwdIsBackLegal(pwdStr) && checkPwdLegal(pwdStr);
    }

    /**
     * 密码复杂度 倒叙 654321
     *
     * @param pwdStr
     * @return 123321
     */
    private static boolean checkPwdIsBackLegal(String pwdStr) {
        if (pwdStr.length() != 6) return false;
        int index = pwdStr.length() - 1;
        int lastValue = Integer.valueOf(pwdStr.charAt(index) + "").intValue() + 1;
        int nextValue = Integer.valueOf(pwdStr.charAt(index - 1) + "").intValue();
        while (lastValue == nextValue && index > 1) {
            index -= 1;
            lastValue = Integer.valueOf(pwdStr.charAt(index) + "").intValue() + 1;
            nextValue = Integer.valueOf(pwdStr.charAt(index - 1) + "").intValue();
        }
        if (lastValue != nextValue) return false;
        return index != pwdStr.length() - 1;
    }


    private static boolean checkPwdIsSameLegal(String pwdStr) {
        if (pwdStr.length() != 6) return false;
        for (int i = 0; i < pwdStr.length() - 1; i++) {
            int lastValue = Integer.valueOf(pwdStr.charAt(i) + "").intValue();
            int nextValue = Integer.valueOf(pwdStr.charAt(i + 1) + "").intValue();
            if (lastValue != nextValue) {
                return false;
            }
        }
        return true;
    }


    public static void showErrorMsgDialog(Context mContext, String msg) {
        if (mContext == null) return;
        CustomMsgDialog.Builder builder = new CustomMsgDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(mContext.getString(R.string.dialog_right_btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Activity mActivity = (Activity) mContext;
        if (mActivity != null && !mActivity.isFinishing()) {
            CustomMsgDialog mDealDialog = builder.create();
            mDealDialog.setCancelable(false);
            mDealDialog.show();
        }
    }

    public static void showErrorMsgDialogAndLogin(final Activity mContext, String msg) {
        if (mContext == null) return;
        CustomMsgDialog.Builder builder = new CustomMsgDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(mContext.getString(R.string.dialog_right_btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clearMemoryCache(mContext);
                if (mContext instanceof LoginActivity){

                }else {
                    ProjectApp.removeAllTaskStackExcludeSplish();
                    ActivitySkipUtil.startAnotherActivity(mContext, LoginActivity.class);
                }
            }
        });
        if (mContext != null && !mContext.isFinishing()) {
            CustomMsgDialog mDealDialog = builder.create();
            mDealDialog.setCancelable(false);
            mDealDialog.show();
        }
    }

    public static void showErrorMsgDialogAndResart(final Activity mContext, String msg) {
        if (mContext == null) return;
        CustomMsgDialog.Builder builder = new CustomMsgDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(mContext.getString(R.string.dialog_right_btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clearMemoryCache(mContext);
            }
        });
        if (mContext != null && !mContext.isFinishing()) {
            CustomMsgDialog mDealDialog = builder.create();
            mDealDialog.setCancelable(false);
            mDealDialog.show();
        }
    }


    /**
     * 登出操作
     *
     * @param context
     */
    public static void clearMemoryCache(Activity context) {
        //退出
        if (context == null) return;
        SPFuncUtils.clear();
    }


    public static float getScreenDensity(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        return density;
    }


    //是否有下方虚拟栏
    public static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }


    public static String formatSpecialCardMastNumberStr(String cardNumber) {
        if (cardNumber.length() == 16) {
            return "* * * * " + cardNumber.subSequence(12, 16);
        }
        return cardNumber;
    }

    public static String formatCardMastLongNumberStr(String cardNumber) {
        if (cardNumber.length() == 16) {
            return "****" + "     " + "****" + "     " + "****" + "     " + cardNumber.subSequence(12, 16);
        }
        return cardNumber;
    }

    public static String formatCardMastShortNumberStr(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() > 4) {
            return cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
        }
        return "";
    }

    public static String formatPhoneNumberStr(String cardNumber) {
        if (cardNumber.length() > 4) {
            return "****" + cardNumber.subSequence(4, cardNumber.length());
        }
        return cardNumber;
    }

    public static String formatCenterPhoneNumberStr(String cardNumber) {
        if (cardNumber.length() > 4) {
            int endIndex = cardNumber.indexOf("-") + 1;
            int otherLength = cardNumber.length() - endIndex;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < otherLength; i++) {
                sb.append("*");
            }
            return cardNumber.subSequence(0, endIndex) + sb.toString() + cardNumber.subSequence(cardNumber.length() - 4, cardNumber.length());
        }
        return cardNumber;
    }

    public static String formatCardExpireStr(String cardExpire) {
        if (cardExpire.length() == 4) {
            return cardExpire.substring(0, 2) + "/" + cardExpire.substring(2, 4);
        }
        return cardExpire;
    }


    public static boolean isRooted() {
        File sufile1 = new File("/system/xbin/su");
        File sufile2 = new File("/system/bin/su");
        return !(!sufile1.exists() && !sufile2.exists());
    }

    /**
     * 特殊字符过滤
     *
     * @param content
     * @return
     */
    public static boolean compileExChar(String content) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pp1 = Pattern.compile(regEx);
        Matcher m = pp1.matcher(content);
        return m.find();
    }


    public static String formatTimeString(String timeStr) {
        //1230235959
        if (timeStr.length() == 10) {
            String year = timeStr.substring(0, 2);
            String month = timeStr.substring(2, 4);
            String hour = timeStr.substring(4, 6);
            String minute = timeStr.substring(6, 8);
            String second = timeStr.substring(8, 10);
            return hour + ":" + minute + ":" + second + " " + month + "/" + year;
        }
        return timeStr;
    }


    public static String getFromAssets(String fileName, Context mContext) {
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateStr(String expire) {

        if (expire == null) return "";
        if (expire.length() < 6) {
            return expire;
        }
        return expire.substring(0, 4) + "/" + expire.substring(4, 6) + "/" + expire.substring(6, 8);
    }


}
