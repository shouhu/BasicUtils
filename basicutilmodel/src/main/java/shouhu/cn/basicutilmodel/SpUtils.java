package shouhu.cn.basicutilmodel;

import android.content.Context;

/**
 * Created by admin on 2017/12/19.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}(sharePerference的实现类)
 * @date 2017/12/19.17:47.
 */

public class SpUtils extends SpBaseUtil{
    private static SpUtils instance=null;
    private SpUtils(Context context){
        super(context);
    }
    public static SpUtils getInstance(Context context){
        Context applicationContext = context.getApplicationContext();
        if (null == instance || instance.mContext != applicationContext) {
            instance = new SpUtils(context);
        }
        return instance;
    }

    @Override
    protected String getSpName() {
        return "sp_config";
    }

    /**
     *
     * @Title: UserMapBase64
     * @Description: 存储用户的登录次数
     * @return
     * @throws
     */
    public void setUserLoginCountInfo(String  UserMapBase64){
        putString("User_login_count", UserMapBase64);
    }

    /**
     *
     * @Title:
     * @Description: 获取用户的登录次数
     * @return
     * @throws
     */
    public  String getUserLoginCountInfo(){

        return getString("User_login_count",null);
    }

    public String  getAppLanguage(){
        return getString("App_setting_language",null);
    }

    public void setAppLanguage(String language){
        putString("App_setting_language", language);
    }


    /**
     *
     * @Title: setIsFirstLaunch
     * @Description: TODO
     * @param isFirstLaunch
     * @throws
     */
    public void setIsFirstLaunch(boolean isFirstLaunch) {
        String version= AndroidUtils.getCurrentAppVersionName(mContext);
        putBoolean("is_first_launch_"+version, isFirstLaunch);
    }
    /**
     *
     * @Title: getIsFirstLaunch
     * @Description: 是否是当前版本的第一次打开
     * @return
     * @throws
     */
    public boolean getIsFirstLaunch() {
        String version= AndroidUtils.getCurrentAppVersionName(mContext);
        return getBoolean("is_first_launch_" + version, true);
    }



    /**
     * 设置升级弹出框每周显示一次
     */
    public boolean isNeedShowUpdateDialog(){
        long time= getLong("update_dialog_time", -1);
        long leftTime=System.currentTimeMillis()-time;
        if(leftTime>(7*24*3600)||leftTime<0){
            putLong("update_dialog_time",System.currentTimeMillis());
            return true;
        }
        return false;
    }

}
