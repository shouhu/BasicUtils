package shouhu.cn.basicutilmodel;

import android.os.CountDownTimer;


/**
 * Created by ZhangXinchao on 2018/3/9.
 */

public class AdvancedCountdownTimer {

    private static class AdvancedCountdownTimerHolder {
        private static AdvancedCountdownTimer instance = new AdvancedCountdownTimer();
    }

    public static AdvancedCountdownTimer getInstance() {
        return AdvancedCountdownTimer.AdvancedCountdownTimerHolder.instance;
    }

    private String lastMobilePhone = "";
    private int delayFinishTime;

    /**
     * 主要解决登录时输入一个手机号 发送验证码 这个时候推出 重新发送这个验证码 次倒计时为全局处理
     * @param onCountDownListener
     * @param phoneNumber
     * @param totlaSecond
     */
    public void startCountDown( final String phoneNumber,int totlaSecond,final OnCountDownListener onCountDownListener) {
//        if (phoneNumber.equals(lastMobilePhone)) {
//            //倒计时跟之前登录的倒计时一样，接着上次的时间进行
//            countDownEvent(phoneNumber,delayFinishTime,onCountDownListener);
//        }else {
            countDownEvent(phoneNumber,totlaSecond,onCountDownListener);
//        }
    }


    /**
     * 正常倒计时
     * @param totlaSecond
     * @param onCountDownListener
     */
    public void countDownEvent( final String phoneNumber,int totlaSecond,final OnCountDownListener onCountDownListener){
        this.lastMobilePhone = phoneNumber;
        new CountDownTimer(totlaSecond*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int delaySecond = (int) (millisUntilFinished / 1000);
                delayFinishTime = delaySecond;
                onCountDownListener.onTick(delaySecond);
            }

            public void onFinish() {
                onCountDownListener.onFinish();
            }
        }.start();
    }


    public interface OnCountDownListener {
        void onTick(int millisUntilFinished);

        void onFinish();
    }


}
