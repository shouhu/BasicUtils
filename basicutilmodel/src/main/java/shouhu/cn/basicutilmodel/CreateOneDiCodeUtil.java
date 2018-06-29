/*
 * 文 件 名:  CreateOneDiCodeUtil.java
 * 版    权:   Ltd. Copyright swiftpass,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  he_hui
 * 修改时间:  2015-6-26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package shouhu.cn.basicutilmodel;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * 生成一维码/条形码
 *
 * @author he_hui
 * @version [版本号, 2015-6-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CreateOneDiCodeUtil {
    private static final String CODE = "utf-8";

    public static Bitmap createCode(String str, Integer width, Integer height) {

        if (width == null || width < 200) {
            width = 200;
        }

        if (height == null || height < 50) {
            height = 50;
        }

        try {
            // 文字编码  
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);

            return BitMatrixToBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BitMatrix转换成Bitmap
     *
     * @param matrix
     * @return
     */
    private static Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }
        return createBitmap(width, height, pixels);
    }

    /**
     * 生成Bitmap
     *
     * @param width
     * @param height
     * @param pixels
     * @return
     */
    private static Bitmap createBitmap(int width, int height, int[] pixels) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
