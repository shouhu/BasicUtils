package shouhu.cn.basicutilmodel;

import android.content.Context;
import android.os.Environment;

/**
 * Created by ramon on 2018/6/9.
 */

public class StorageUtils {
    public static String getRootPath(Context context){
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        String path = externalPath + "/" + context.getPackageName() + "/";
        return path;
    }

    public static String getDirectoryByDirType(Context context, StorageType fileType) {
        return getRootPath(context)+ fileType.getStoragePath();
    }

    public enum StorageType {
        TYPE_LOG(DirectoryName.LOG_DIRECTORY_NAME),
        TYPE_TEMP(DirectoryName.TEMP_DIRECTORY_NAME),
        TYPE_IMAGE(DirectoryName.IMAGE_DIRECTORY_NAME),
        ;
        private DirectoryName storageDirectoryName;

        public String getStoragePath() {
            return storageDirectoryName.getPath();
        }

        StorageType(DirectoryName dirName) {
            this.storageDirectoryName = dirName;
        }

        public enum DirectoryName {
            LOG_DIRECTORY_NAME("log/"),
            TEMP_DIRECTORY_NAME("temp/"),
            IMAGE_DIRECTORY_NAME("image/"),
            ;

            private String path;

            public String getPath() {
                return path;
            }

            private DirectoryName(String path) {
                this.path = path;
            }
        }
    }
}
