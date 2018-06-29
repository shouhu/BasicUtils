package shouhu.cn.basicutilmodel;

/**
 * Created by jinglun.guo on 2017/2/6.
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

//Toast统一管理类
public class ToastUtils {

    private ToastUtils() {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;


    public static void showToast(Context context, CharSequence content, int time) {
        Toast toast = Toast.makeText(context, content, time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, int content, int time) {
        Toast toast = Toast.makeText(context, content, time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) showToast(context, message, Toast.LENGTH_LONG);
    }


}
