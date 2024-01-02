package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestAds {
    static {
        System.loadLibrary("hello-jni");
    }

    public static native String GETUSERINFO();
    public static final String GETUSERINFO = GETUSERINFO();
    public static native String URLDELETEACC();
    public static final String URLDELETEACC = URLDELETEACC();
    public static native String SAVEUSERINFO();
    public static final String SAVEUSERINFO = SAVEUSERINFO();
    public static native String URLLOGINWITHGOOGLE();
    public static final String URLLOGINWITHGOOGLE = URLLOGINWITHGOOGLE();
    public static native String URLLOGOUT();
    public static final String URLLOGOUT = URLLOGOUT();
    public static native String rskey();
    public static final String rskey = rskey();

    public static String getGetuserinfo;
    public static String userdeleteAcc;
    public static String saveuserinfo;
    public static String urlwithlogin;
    public static String urllogout;
    static {
        try {
            getGetuserinfo = decryptMsg(parseHexStr2Byte(GETUSERINFO), rskey);
            userdeleteAcc = decryptMsg(parseHexStr2Byte(URLDELETEACC), rskey);
            saveuserinfo = decryptMsg(parseHexStr2Byte(SAVEUSERINFO), rskey);
            urlwithlogin = decryptMsg(parseHexStr2Byte(URLLOGINWITHGOOGLE), rskey);
            urllogout = decryptMsg(parseHexStr2Byte(URLLOGOUT), rskey);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterSpecException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static native String rikey();
    public static final String rikey = rikey();

    public static byte[] parseHexStr2Byte(String hexStr) {
        Log.i("decryptMsg",rikey());
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),
                    16);
            int low = Integer.parseInt(
                    hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String decryptMsg(byte[] cipherText, String  secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException
    {

        try {
            IvParameterSpec iv = new IvParameterSpec(rikey().getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(cipherText);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}


