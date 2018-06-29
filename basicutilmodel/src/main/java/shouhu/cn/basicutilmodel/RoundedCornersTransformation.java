package shouhu.cn.basicutilmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by ZhangXinchao on 2018/1/17.
 */

public class RoundedCornersTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int mRadius;

    public RoundedCornersTransformation(Context context, int mRadius) {
        this(Glide.get(context).getBitmapPool(), mRadius);
    }

    public RoundedCornersTransformation(BitmapPool mBitmapPool, int mRadius) {
        this.mBitmapPool = mBitmapPool;
        this.mRadius = mRadius;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, paint);
        return BitmapResource.obtain(result, mBitmapPool);
    }

    @Override
    public String getId() {
        return "RoundedTransformation(radius=" + mRadius + ")";
    }
}
