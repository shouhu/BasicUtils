package shouhu.cn.basicutilmodel;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/12/19.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}( 输入信息验证)
 * @date 2017/12/19.18:59.
 */

public class VerifyInputContentUtils {
    /**
     * 手机号码验证
     *
     * @param phoneNum
     * @return
     */
    public static boolean mobileMumVerify(String phoneNum) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(14[5,7]))\\d{8}$");
        return p.matcher(phoneNum).matches();
    }

    /**
     * 邮箱验证
     *
     * @param mailAddress
     * @return
     */
    public static boolean mailAddressVerify(String mailAddress) {
        if (mailAddress.contains(" ")) {
            return false;
        } else {
            String emailExp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
            // String emailExp =
            // "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile(emailExp);
            return p.matcher(mailAddress).matches();
        }

    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            // String check =
            // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            String check = "\\w+@\\w+(\\.\\w+)+";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 校验密码是否正确
     *
     * @param pwd
     *            以字母开头，长度在6~20之间，只能包含字符、数字和下划线。
     * @return
     */
    public static boolean checkPwd(String pwd) {
        boolean flag = false;
        try {
            // String check = "^[a-zA-Z]\\w{5,19}$";
            // String check = "\\.{6,19}";
            // Pattern regex = Pattern.compile(check);
            // Matcher matcher = regex.matcher(pwd);
            // flag = matcher.matches();
            if (pwd.length() >= 6 && pwd.length() <= 20) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 输入类型仅限汉字和字母
     *
     * @param input
     * @return
     */
    public static boolean checkEn(String input) {
        String str = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2DA-Za-z]{2,10}$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 输入的是数字的判断
     *
     * @param str
     * @return
     */
    public static boolean checkNum(String str) {
        if(TextUtils.isEmpty(str)){
            return false;
        }
        int count = 0;
        Pattern pattern = Pattern.compile("[0-9]");
        str = str.trim();
        char c[] = str.toCharArray();
        int str_length = c.length;
        for (int i = 0; i < str_length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if (matcher.matches()) {
                count++;
            }
        }
        return count == str_length;
    }

    /**
     * 汉字、字母、数字验证
     *
     * @param str
     * @return
     */
    public static boolean check_zimu_shuzi(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\u4e00-\u9fa5]{3,20}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 字母、数字验证 最少为五位数
     *
     * @param str^
     * @return
     */
    public static boolean checkzimu_shuzi(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{5,}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 是否包含空格
     * @param input
     * @return true 没包含空格     false 有空格
     */
    public static boolean checkSpace(String input){
        return check(input,"^[^\\s]+$");
    }

    /**
     * 是否全是空格
     * @param input
     * @return true  全是空格
     */
    public static boolean checkIsOnlySpace(String input){
        return check(input,"^[\\s]+$");
    }

    public static String checkSpaceWithHint(String input){
        if(!checkSpace(input)){
            return "输入内容包含空格，请出新输入!";
        }
        return null;
    }

    public static boolean check(String input, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
