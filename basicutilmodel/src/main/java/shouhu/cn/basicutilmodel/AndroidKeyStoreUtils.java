package shouhu.cn.basicutilmodel;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;


/**
 * Created by ZhangXinchao on 2018/4/8.
 */

public class AndroidKeyStoreUtils {

    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final String AndroidKeyStore = "AndroidKeyStore";
    private static final String FIXED_IV = "WT_INTIAL_IV";//must 12 bytes
    private static final String KEY_ALIAS = "Wallet";

    static AndroidKeyStoreUtils encryUtilsInstance;
    private KeyStore keyStore;

    public static AndroidKeyStoreUtils getInstance() {
        synchronized (AndroidKeyStoreUtils.class) {
            if (null == encryUtilsInstance) {
                encryUtilsInstance = new AndroidKeyStoreUtils();
            }
        }
        return encryUtilsInstance;
    }

    public AndroidKeyStoreUtils() {
        initKeyStore();
    }


    private void initKeyStore() {
        try {
            keyStore = KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
                keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                        setBlockModes(KeyProperties.BLOCK_MODE_GCM).
                        setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).
                        setRandomizedEncryptionRequired(false).
                        setKeySize(256).build());
                keyGenerator.generateKey();
            }
        } catch (Exception e) {

        }
    }

    private Key getSecretKey() throws Exception {

        return keyStore.getKey(KEY_ALIAS, null);
    }

    public String encryptData(String oringinalStr) {
        String encryptedBase64Encoded = null;
        try {
            Cipher c = Cipher.getInstance(AES_MODE);
            c.init(Cipher.ENCRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, FIXED_IV.getBytes()));
            byte[] encodedBytes = c.doFinal(oringinalStr.getBytes());
            encryptedBase64Encoded = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
        } catch (Exception e) {

        }
        return encryptedBase64Encoded;
    }

    public String decryptData(String oringinalStr) {
        byte[] decodedBytes = new byte[0];
        try {
            Cipher c = Cipher.getInstance(AES_MODE);
            c.init(Cipher.DECRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, FIXED_IV.getBytes()));
            decodedBytes = c.doFinal(Base64.decode(oringinalStr.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {

        }
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }
}
