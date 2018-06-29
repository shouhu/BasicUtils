package shouhu.cn.basicutilmodel;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

import cn.swiftpass.cn.standardwallet.application.ProjectApp;


/**
 * Created by ZhangXinchao on 2018/1/17.
 */

public class UnsafeOkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File storageDirectory = Environment.getExternalStorageDirectory();
        //TODO FIX 改成动态配置文件中获取
        String downloadDirectoryPath = storageDirectory + "/cache";
        int cacheSize = 2000;
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}