package shouhu.cn.basicutilmodel;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by admin on 2017/12/19.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2017/12/19.20:29.
 */

public class GsonUtils {
    private static final String TAG = GsonUtils.class.getSimpleName();
    /**空json对象*/
    public static final String EMPTY_JSON_STR = "{}";
    /**空json数据*/
    public static final String EMPTY_JSON_ARRAY_STR = "[]";

    private static Gson GSON;

    private static synchronized Gson getGson() {
        if (GSON == null) {
            GSON = new Gson();
        }

        return GSON;
    }

    /**
     * 判断是不是空的json字符串。这里只做简单判断
     * @param jsonStr
     * @return
     */
    public static boolean isEmpty(String jsonStr) {
        return TextUtils.isEmpty(jsonStr)
                || TextUtils.equals(EMPTY_JSON_STR, jsonStr.trim())
                || TextUtils.equals(EMPTY_JSON_ARRAY_STR, jsonStr.trim());
    }

    /**
     * 将object转成json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return toJson(object, null, null);
    }

    /**
     * 将object转成json字符串
     * @param object
     * @param typeOfSrc
     * @return
     */
    public static String toJson(Object object, Type typeOfSrc) {
        return toJson(object, typeOfSrc, null);
    }

    public static String toJson(Object object,TypeToken<?> token){
        return toJson(object, token.getType(), null);
    }

    /**
     * 将object转成json字符串
     * @param object
     * @param typeOfSrc
     * @param builder
     * @return
     */
    public static String toJson(Object object, Type typeOfSrc, GsonBuilder builder) {
        if (object == null) return EMPTY_JSON_STR;

        Gson gson = builder != null ? builder.create() : getGson();
        try {
            return typeOfSrc == null ? gson.toJson(object) : gson.toJson(
                    object, typeOfSrc);
        } catch (Exception e) {
            Log.i(TAG, "To json error!", e);
        }

        return EMPTY_JSON_STR;
    }

    /**
     * 将JSON字符串转成指定的对象
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> cls) {
        if (isEmpty(jsonStr)) return null;

        try {
            return getGson().fromJson(jsonStr, cls);
        } catch (Exception e) {
            Log.i(TAG, "Json to Object error!", e);
        }

        return null;
    }

    /**
     * 将JSON字符串转成指定的对象
     * @param jsonStr
     * @param token
     * @return
     */
    public static <T> T fromJson(String jsonStr, TypeToken<T> token) {
        if (isEmpty(jsonStr) || token == null) return null;

        try {
            return getGson().fromJson(jsonStr, token.getType());
        } catch (Exception e) {
            Log.i(TAG, "Json to Object error!", e);
        }
        return null;
    }
}
