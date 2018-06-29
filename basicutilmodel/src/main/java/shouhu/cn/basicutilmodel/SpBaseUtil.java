package shouhu.cn.basicutilmodel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/12/19.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}(sharePerference的基类)
 * @date 2017/12/19.17:45.
 */

public abstract class SpBaseUtil {
    protected Context mContext;
    private SharedPreferences sp;
    protected abstract String getSpName();
    public SharedPreferences getSp() {
        if (sp == null)
            sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        return sp;
    }

    public SharedPreferences.Editor getEdit() {
        return getSp().edit();
    }
    public SpBaseUtil(Context context){
        mContext=context;
    }

    public boolean getBoolean(String key,boolean defaultValue){
        return getSp().getBoolean(key, defaultValue);
    }

    public void putBoolean(String key,boolean value){
        getEdit().putBoolean(key,value).commit();
    }

    public String getString(String key,String defaultValue){
        return getSp().getString(key, defaultValue);
    }

    public void putString(String key,String value){
        getEdit().putString(key, value).commit();
    }

    public int getInt(String key,int defaultValue){
        return getSp().getInt(key, defaultValue);
    }

    public void putInt(String key,int value){
        getEdit().putInt(key,value).commit();
    }

    public void putFloat(String key,float value){
        getEdit().putFloat(key, value).commit();
    }

    public float getFloat(String key,float defaultValue){
        return getSp().getFloat(key, defaultValue);
    }

    public void putLong(String key,long value){
        getEdit().putLong(key, value).commit();
    }

    public long getLong(String key,long defaultValue){
        return getSp().getLong(key, defaultValue);
    }

}
