package com.jaxon.util.security;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSAUtil
{
    public static String KEY_ALGORITHM = "RSA";

    /**
    * 公钥加密
    * @param data
    * @param
    * @return
    * @throws Exception
    */
    public static byte[] encryptByPublicKey(byte[] data, String publicKeyMod, String publicKeyExp) throws Exception
    {
        // 构造KeySpec对象  
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(publicKeyMod, 16), new BigInteger(publicKeyExp, 16));

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(keySpec);

        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data
     * @param
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKeyMod, String privateKeyExp) throws Exception
    {
        // 构造KeySpec对象  
        RSAPrivateKeySpec spec = new RSAPrivateKeySpec(new BigInteger(privateKeyMod, 16), new BigInteger(privateKeyExp, 16));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(spec);

        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * byte[] 转 16进制字符串
     * 
     * @param arr
     * @return
     */
    public static String byteArray2HexString(byte[] arr)
    {
        StringBuilder sbd = new StringBuilder();
        for (byte b : arr)
        {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2)
            {
                tmp = "0" + tmp;
            }
            sbd.append(tmp);
        }
        return sbd.toString();
    }

    public static void main(String args[])
    {

    }
}
