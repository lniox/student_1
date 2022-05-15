package security;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public final class AES {
    private static final String PSD = "OhiJBVd9Y4O58eoQwgHvCA=="; //建议使用create-Key来生成
    public static String encode(String s) {
        final var byteData = encrypt(s, stringToKey(PSD));
        return Base64.getEncoder().encodeToString(byteData);
    }

    public static String decode(String s) {
        final var byteData = Base64.getDecoder().decode(s);
        return decrypt(byteData, stringToKey(PSD));
    }
    /*private static Key createKey() {
        try {
            //构造密钥生成器，指定为AES算法,不区分大小写
            var keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); //生成一个128位的随机源,根据传入的字节数组

            final var secretKey = keyGenerator.generateKey(); //产生原始对称密钥
            final byte[] keyBytes = secretKey.getEncoded(); //获得原始对称密钥的字节数组

            return new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }*/
    private static byte[] encrypt(String context, Key key) {
        try {
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(context.getBytes());
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String decrypt(byte[] result, Key key) {
        try {
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(result);
    }
    public static Key stringToKey(String stringInBase64) {
        final var byteCode = Base64.getDecoder().decode(stringInBase64);
        return new SecretKeySpec(byteCode, 0, byteCode.length, "AES");
    }
   // public static String keyToString(Key key) {return Base64.getEncoder().encodeToString(key.getEncoded());}
}
