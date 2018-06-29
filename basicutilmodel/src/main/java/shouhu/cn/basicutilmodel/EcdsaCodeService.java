package shouhu.cn.basicutilmodel;

import org.bouncycastle.jce.ECNamedCurveTable;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.KeyAgreement;

/**
 * Created by ZhangXinchao on 2018/6/9.
 */

public class EcdsaCodeService {

/*
    private static final String TAG = "EcdsaCodeService";
    private final static String charset = "utf-8";

    private final static String KEY_INSTANCE = "EC";

    private final static String SIGN_INSTANCE = "SHA1withECDSA";

    private final static String PROVIDER = "BC";

    private final static String ECDH = "ECDH";

//    private CipherService cipherService;

    *//**
     * 签名
     *
     * @param pKey    服务端私钥
     * @param content 需要签名的内容
     * @return
     *//*
    private String sign(String priKey, String content) {
        try {
            byte[] keyBytes = Base64Utils.decode(priKey.getBytes(charset));
            byte[] contentBytes = Base64Utils.decode(content.getBytes(charset));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_INSTANCE);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGN_INSTANCE);
            signature.initSign(privateKey);
            signature.update(contentBytes);
            return Base64Utils.encodeToString(signature.sign());
        } catch (Exception e) {
            logger.error("报文加签出现出现异常", e);
            throw new ProcessException("", "报文加签出现出现异常");
        }
    }

    *//**
     * 验签
     *
     * @param pubKey  临时公钥
     * @param content 验签类容
     * @return
     *//*
    private boolean verify(String pubKey, String content, String signData) {
        try {
            //验签
            byte[] keyBytes = Base64Utils.decode(pubKey.getBytes(charset));
            byte[] contentBytes = Base64Utils.decode(content.getBytes(charset));
            byte[] signBytes = Base64Utils.decode(signData.getBytes(charset));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_INSTANCE);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGN_INSTANCE);
            signature.initVerify(publicKey);
            signature.update(contentBytes);
            return signature.verify(signBytes);
        } catch (Exception e) {
            LogUtils.i(TAG, "报文签名验证出现异常");
        }
        return false;
    }

    *//**
     * 加密
     *
     * @throws ProcessException
     *//*
*//*    public void encrypt(AppResponse response, String serverPriKey, String clientPubKey) throws ProcessException {
        try {
            String content = response.getContent();
            //设置签名内容
            response.setSignData(sign(serverPriKey, content));
            //生成临时公私钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_INSTANCE);
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            String tmpPubKey = Base64Utils.encodeToString(keyPair.getPublic().getEncoded());
            String tmpPriKey = Base64Utils.encodeToString(keyPair.getPrivate().getEncoded());
            //临时公钥传送给客户端
            response.setTempKey(tmpPubKey);
            //加密
            String ecdhKey = ecdhKey(tmpPriKey, clientPubKey);
            //aes加密处理
            content = cipherService.encrypt(content, ecdhKey);
            //回填加密加签过后的内容
            response.setContent(content);
        } catch (Exception e) {
            LogUtils.i(TAG,"报文加密出现异常");
            throw new ProcessException("", "报文加密出现异常");
        }
    }*//*

    *//**
     * 解密
     *
     * @throws ProcessException
     *//*
  *//*  public void decrypt(AppRequest request, String serverPriKey, String clietPubKey) throws ProcessException {
        try {
            String tmpPubKey = request.getTempKey();
            String content = request.getContent();
            String signData = request.getSignData();

            //验签
            if(!verify(clietPubKey, content, signData)){
                throw new ProcessException("", "签名验证失败");
            }
            //解密
            String ecdhKey = ecdhKey(serverPriKey, tmpPubKey);
            content = cipherService.decrypt(content, ecdhKey);
            request.setContent(content);
        } catch (Exception e) {
            logger.error("报文解密出现异常", e);
            throw new ProcessException("", "报文解密出现异常");
        }
    }*//*

    *//**
     * 秘钥磋商
     *
     * @param priKey
     * @param pubKey
     * @return
     *//*
    private String ecdhKey(String priKey, String pubKey) {
        return Base64Utils.encodeToString(ecdh(priKey, pubKey));
    }


    *//**
     * 秘钥磋商
     *
     * @param privateKey
     * @param publicKey
     * @return
     *//*
    private byte[] ecdh(String priKey, String pubKey) {
        try {
            //使用ECDH-BC前，BC方法添加进环境信息内
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            //初始化ecdh keyFactory
            KeyFactory keyFactory = KeyFactory.getInstance(ECDH, PROVIDER);
            //处理私钥
            byte[] priKeyBytes = Base64Utils.decode(priKey.getBytes(charset));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
            PrivateKey ecPriKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //处理公钥
            byte[] pubKeyBytes = Base64Utils.decode(pubKey.getBytes(charset));
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(pubKeyBytes);
            PublicKey ecPubKey = keyFactory.generatePublic(pubX509);
            //秘钥磋商生成新的秘钥byte数组
            KeyAgreement aKeyAgree = KeyAgreement.getInstance(ECDH, PROVIDER);
            aKeyAgree.init(ecPriKey);
            aKeyAgree.doPhase(ecPubKey, true);
            return aKeyAgree.generateSecret();
        } catch (Exception e) {
            logger.error("秘钥磋商出现异常", e);
            throw new ProcessException("", "秘钥磋商出现异常");
        }
    }

    public static void main(String[] args) {
        try {
            String src = "yanghm";
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();

            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] res = signature.sign();
            //byte----》string
            System.out.println("签名后的数据：" + ByteUtil.bytesToHexString(res));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(res);
            System.out.println("签名验证结果：" + bool);

            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
            //使用BC前，BC方法添加进环境信息内
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDH", "BC");
            g.initialize(ecSpec, new SecureRandom());

            KeyPair aKeyPair = g.generateKeyPair();
            KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
            aKeyAgree.init(aKeyPair.getPrivate());

            KeyPair bKeyPair = g.generateKeyPair();
            KeyAgreement bKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
            bKeyAgree.init(bKeyPair.getPrivate());

            aKeyAgree.doPhase(bKeyPair.getPublic(), true);
            bKeyAgree.doPhase(aKeyPair.getPublic(), true);

            byte[] aSecret = aKeyAgree.generateSecret();
            byte[] bSecret = bKeyAgree.generateSecret();

            System.out.println(Arrays.toString(aSecret));
            System.out.println(Arrays.toString(bSecret));
            System.out.println(MessageDigest.isEqual(aSecret, bSecret));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}



