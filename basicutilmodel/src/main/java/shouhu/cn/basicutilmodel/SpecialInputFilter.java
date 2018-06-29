package shouhu.cn.basicutilmodel;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangXinchao on 2018/2/7.
 */

public class SpecialInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
//        String speChat = "[^[a-zA-Z]+\\s]";
        String speChat = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern pattern = Pattern.compile(speChat);
        Matcher matcher = pattern.matcher(source.toString());
        if (matcher.find()) return "";
        else return null;
    }
}
