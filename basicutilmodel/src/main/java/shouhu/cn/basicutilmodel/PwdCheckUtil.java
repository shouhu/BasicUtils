package shouhu.cn.basicutilmodel;


/**
 * Created by ZhangXinchao on 2018/5/23.
 * 密码复杂度校验
 */

public class PwdCheckUtil {

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        boolean isLetterOrDigit = false;//定义一个boolean值，用来表示是否包含字母或数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetterOrDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 规则2：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
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
}
