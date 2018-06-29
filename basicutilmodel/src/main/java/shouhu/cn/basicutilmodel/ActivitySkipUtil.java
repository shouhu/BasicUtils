package shouhu.cn.basicutilmodel;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.swiftpass.cn.standardwallet.R;


/**
 * Created by ZhangXinchao on 2017/12/22.
 */

public class ActivitySkipUtil {
    public enum ANIM_TYPE {
        NONE, RIGHT_IN, LEFT_IN, LEFT_OUT, RIGHT_OUT,
    }

    public ActivitySkipUtil() {
        throw new UnsupportedOperationException("ActivitySkipUtil不能实例化");
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param activity       发起跳转的Activity实例
     * @param TargetActivity 目标Activity实例
     * @Time 2016年4月25日
     * @Author lizy18
     */
    public static void startAnotherActivity(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 直接启动一个activity <功能详细描述>
     *
     * @param cla
     * @see [类、类#方法、类#成员]
     */
    public static void startActivityForResult(Activity activity, Class<? extends Activity> cla, int requestCode, ANIM_TYPE animType) {
        Intent intent = new Intent(activity, cla);
        activity.startActivityForResult(intent, requestCode);
        int startAnimId = getAnimationId(animType);
        if (startAnimId != 0) {
            activity.overridePendingTransition(startAnimId, R.anim.none);
        }
    }

    public static void startActivityForResult(Activity activity, Class<? extends Activity> cla, int requestCode) {
        startActivityForResult(activity, cla, requestCode, ANIM_TYPE.NONE);
    }


    public static void startAnotherActivity(Activity activity, Class<? extends Activity> cls, ANIM_TYPE animType) {
        startAnotherActivity(activity, cls);
        int startAnimId = getAnimationId(animType);
        if (startAnimId != 0) {
            activity.overridePendingTransition(startAnimId, R.anim.none);
        }
    }

    private static int getAnimationId(ANIM_TYPE animType) {
        int startAnimId = 0;
        switch (animType) {
            case LEFT_IN:
                startAnimId = startFromLeftInAnim();
                break;
            case RIGHT_IN:
                startAnimId = startFromRightInAnim();
                break;
            case LEFT_OUT:
                startAnimId = finishFromLeftOutAnim();
                break;
            case RIGHT_OUT:
                startAnimId = finishFromRightOutAnim();
                break;
            default:
                startAnimId = 0;
                break;
        }
        return startAnimId;
    }

    public static void finishActivityWithAnim(Activity activity, ANIM_TYPE animType) {
        int finishAnimId = getAnimationId(animType);
        if (finishAnimId != 0) {
            activity.overridePendingTransition(R.anim.none, finishAnimId);
        }
    }

    /**
     * 结束此activity时动画 从屏幕的右边移出
     *
     * @see [类、类#方法、类#成员]
     */
    private static int finishFromRightOutAnim() {
        return R.anim.from_right_out;
    }

    /**
     * 结束此activity时动画 从屏幕的左边移出
     *
     * @see [类、类#方法、类#成员]
     */
    private static int finishFromLeftOutAnim() {
        return R.anim.from_left_out;
    }


    /**
     * @throws
     * @Title: startFromRightInAnim
     * @Description: 从屏幕的右边进入
     */
    public static int startFromRightInAnim() {
        return R.anim.from_right_in;
    }

    /**
     * @throws
     * @Title: startFromLeftInAnim
     * @Description: 从屏幕的左边进入
     */
    public static int startFromLeftInAnim() {
        return R.anim.from_left_in;
    }


    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param activity
     * @param cls
     * @param hashMap
     * @Time 2016年4月25日
     * @Author lizy18
     */
    public static void startAnotherActivity(Activity activity, Class<? extends Activity> cls, HashMap<String, ? extends Object> hashMap, ANIM_TYPE animType) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked") Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }

            if (value instanceof Serializable) {
                intent.putExtra(key, (Serializable) value);
            }
        }
        activity.startActivity(intent);
        int startAnimId = getAnimationId(animType);
        if (startAnimId != 0) {
            activity.overridePendingTransition(startAnimId, R.anim.none);
        }
    }

    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param activity
     * @param cls
     * @param hashMap
     * @Time 2016年4月25日
     * @Author lizy18
     */
    public static void startAnotherActivity(Activity activity, Class<? extends Activity> cls, HashMap<String, ? extends Object> hashMap) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked") Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }

            if (value instanceof Serializable) {
                intent.putExtra(key, (Serializable) value);
            }
        }
        activity.startActivity(intent);
    }



    public static void startAnotherActivityForResult(Activity activity, Class<? extends Activity> cls, HashMap<String, ? extends Object> hashMap, ANIM_TYPE animType,int requestCode) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked") Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }

            if (value instanceof Serializable) {
                intent.putExtra(key, (Serializable) value);
            }
        }
        activity.startActivityForResult(intent,requestCode);
        int startAnimId = getAnimationId(animType);
        if (startAnimId != 0) {
            activity.overridePendingTransition(startAnimId, R.anim.none);
        }
    }
}
