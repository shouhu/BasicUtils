package shouhu.cn.basicutilmodel;

import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by admin on 2017/12/19.
 *
 * @Package cn.swiftpass.wallet.intl.utils
 * @Description: ${TODO}(View 类的工具类)
 * @date 2017/12/19.18:10.
 */

public class ViewUtil {
    /**
     * 让listView自适应
     *
     * @param listView
     * @param attHeight
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int attHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + attHeight;
        listView.setLayoutParams(params);
    }


    // 计算出该TextView中文字的长度(像素)
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    public static float getTextHeight(TextView textView, String text){
        TextPaint paint = textView.getPaint();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float height = bounds.bottom + bounds.height();
        return height;
    }

    public static int getTextViewWidth(TextView textView){
        return (int) (textView.getPaddingLeft()+textView.getPaddingRight()+getTextViewLength(textView, textView.getText().toString()));
    }

    public static int getTextViewHeight(TextView textView){
        return (int)(textView.getPaddingTop()+textView.getPaddingBottom()+getTextHeight(textView,textView.getText().toString()));
    }
}
