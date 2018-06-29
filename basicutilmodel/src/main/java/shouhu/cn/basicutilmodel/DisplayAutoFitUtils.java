package shouhu.cn.basicutilmodel;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Created by ZhangXinchao on 2018/6/21.
 * https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 * android中的dp在渲染前会将dp转为px，计算公式：
    px = density * dp;
    density = dpi / 160;
    px = dp * (dpi / 160);
    而dpi是根据屏幕真实的分辨率和尺寸来计算的，每个设备都可能不一样的
    DisplayMetrics#density 就是上述的density
    DisplayMetrics#densityDpi 就是上述的dpi
    DisplayMetrics#scaledDensity 字体的缩放因子，正常情况下和density相等，但是调节系统字体大小后会改变这个值
 */

public class DisplayAutoFitUtils {
    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;
    private static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
