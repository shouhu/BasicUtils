package shouhu.cn.basicutilmodel;

import android.content.Context;
import android.util.Log;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Created by ZhangXinchao on 2018/1/19.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 读取文件中的json数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readFromFile(Context context, String fileName) {
        try {
            FileInputStream inStream = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }
            inStream.close();
            if (sb.toString().isEmpty()) {
                return null;
            }
            return sb.toString();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 缓存json数据到文件
     *
     * @param context
     * @param content
     * @param fileName
     */
    public static void writeToFile(Context context, String content, String fileName) {
        if (content == null) return;
        try {

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {

        }
    }

    /**
     * 清除文件内容
     *
     * @param context
     * @param content
     * @param fileName
     */
    public static void clearFile(Context context, String content, String fileName) {
        if (content == null) {
            return;
        }
        try {

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {

        }
    }

    /**
     * 关闭流,普通不严重的可采用此函数关闭
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }
}
