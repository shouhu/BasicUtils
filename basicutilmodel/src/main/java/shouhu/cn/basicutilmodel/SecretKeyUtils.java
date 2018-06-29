package shouhu.cn.basicutilmodel;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyAgreement;

import cn.swiftpass.cn.standardwallet.utils.Log.LogTool;

/**
 * Created by ramon on 2018/6/13.
 * 协商生成密钥类
 * 通过ECDH算法
 */

public class SecretKeyUtils {
    private static final String TAG = SecretKeyUtils.class.getSimpleName();
    //服务器公钥
    private static final String S_PUBLIC_KEY = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEs4sbfS8Qmk0FrmIYet3/9mKOEryx/Ld/cjS+TvykGB6CVy8UdglEoNJ+ju8ICC2AkD+HjFvaQlz7jbqcCLv/xw==";
    //客户端公钥
    private static final String C_PUBLIC_KEY = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE927LUgCGG94Zc0ruzbuMdu06DxSaq04Zam9Kbm+aX3fSMPVOS2cp7i+LSK9Sgdf/RsycaIEWm61hGhBkhsr/sQ==";
    //客户端私钥
    private static final String C_PRIVATE_KEY = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgdTCVvejVODomFgnmviWyn0Z5HSeVsBkF4hvZDtknmM2gCgYIKoZIzj0DAQehRANCAAT3bstSAIYb3hlzSu7Nu4x27ToPFJqrThlqb0pub5pfd9Iw9U5LZynuL4tIr1KB1/9GzJxogRabrWEaEGSGyv+x";
    //算法
    private static final String GEN_ALG = "ECDH";
    //字符集
    private static final String CHAR_SET = "utf-8";

    private static final int KEY_SIZE = 256;

    public static final String ENCODE_PUB_KEY =  "TEMP_PUBLIC_KEY";
    public static final String ENCODE_KEY =  "ENCODE_KEY";

    /**
     *生成临时加密密钥key
     *TEMP_PUBLIC_KEY - 返回临时公钥
     * ENCODE_KEY- 返回加密密钥
     */
    public static Map getEncodeKey(){
        try{
            KeyPair pair = generateKeyPair();
            if(null != pair){
                String pubStr = base64Encode(pair.getPublic().getEncoded());
                String privStr = base64Encode(pair.getPrivate().getEncoded());
                String key = ecdh(readPrivateKey(privStr), readPublicKey(S_PUBLIC_KEY));
                Map<String,String> keyMap = new HashMap<>();
                keyMap.put(ENCODE_PUB_KEY,pubStr);
                keyMap.put(ENCODE_KEY,key);
                return keyMap;
            }
        }catch (Exception e){
            LogTool.e(TAG,LogTool.getStackTraceString(e));
        }
        return null;
    }
    /**
     *生成临时解密密钥key
     *otherPubKey - 服务器临时公钥
     */
    public static String getDecodeKey(String otherPubKey){
        try{
            String key = ecdh(readPrivateKey(C_PRIVATE_KEY), readPublicKey(otherPubKey));
            return key;
        }catch (Exception e){
            LogTool.e(TAG,LogTool.getStackTraceString(e));
        }
        return null;
    }

    //生成密钥对
    private synchronized static KeyPair generateKeyPair() {
        getKPG().initialize(KEY_SIZE);
        return getKPG().generateKeyPair();
    }

    //生成协商密钥key
    private static String ecdh(PrivateKey myPrivKey, PublicKey otherPubKey) throws Exception {
        //ECPublicKey ecPubKey = (ECPublicKey) otherPubKey;
        KeyAgreement keyAgreement = KeyAgreement.getInstance(GEN_ALG, new BouncyCastleProvider());
        keyAgreement.init(myPrivKey);
        keyAgreement.doPhase(otherPubKey, true);
        byte[] keybyte = keyAgreement.generateSecret();
        return base64Encode(keybyte);
    }
    //生成公钥
    private static synchronized PublicKey readPublicKey(String keyStr) throws Exception {
        X509EncodedKeySpec x509ks = new X509EncodedKeySpec(
                org.bouncycastle.util.encoders.Base64.decode(keyStr));
        return getKF().generatePublic(x509ks);
    }
    //生成私钥
    private static synchronized PrivateKey readPrivateKey(String keyStr) throws Exception {
        PKCS8EncodedKeySpec p8ks = new PKCS8EncodedKeySpec(
                org.bouncycastle.util.encoders.Base64.decode(keyStr));

        return getKF().generatePrivate(p8ks);
    }

    private synchronized KeyPair readKeyPair(String pubKeyStr, String privKeyStr)
            throws Exception {
        return new KeyPair(readPublicKey(pubKeyStr), readPrivateKey(privKeyStr));
    }


    public static String base64Encode(byte[] b) {
        try {
            return new String(Base64.encode(b), CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            LogTool.e(TAG,LogTool.getStackTraceString(e));
            return null;
        }
    }

    //获取KeyFactory
    private static KeyFactory getKF(){
        KeyFactory kf = null;
        try {
            Provider pr = new BouncyCastleProvider();
            kf = KeyFactory.getInstance(GEN_ALG, pr);
        } catch (Exception e) {
            //NoSuchAlgorithmException
            LogTool.e(TAG,LogTool.getStackTraceString(e));
        }
        return kf;
    }

    //获取KeyPairGenerator
    private static KeyPairGenerator getKPG(){
        KeyPairGenerator kpg = null;
        try {
            Provider pr = new BouncyCastleProvider();
            kpg = KeyPairGenerator.getInstance(GEN_ALG,pr);
        } catch (Exception e) {
            //NoSuchAlgorithmException
            LogTool.e(TAG,LogTool.getStackTraceString(e));
        }
        return kpg;
    }
}
