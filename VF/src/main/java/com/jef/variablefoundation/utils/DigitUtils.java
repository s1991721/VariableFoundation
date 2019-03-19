package com.jef.variablefoundation.utils;

/**
 * Created by mr.lin on 2019/3/15
 * 数字工具
 */
public class DigitUtils {

    //字节转十六进制
    public static String byteToHex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer(bytes.length);
        for (byte b : bytes) {
            String temp = Integer.toHexString(0xFF & b);
            if (temp.length() == 1) {
                stringBuffer.append('0');
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    //十六进制转字节
    public static byte[] hex2byte(byte[] bytes) {
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("参数有误");
        }
        byte[] b2 = new byte[bytes.length / 2];
        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
