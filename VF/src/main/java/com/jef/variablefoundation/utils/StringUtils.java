package com.jef.variablefoundation.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by mr.lin on 2018/12/4
 * 字符串工具
 */
public class StringUtils {


    //随机字符串
    public static String randomString(int length) {
        StringBuffer stringBuffer = new StringBuffer(length);
        String constant = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int p = random.nextInt(constant.length());
            stringBuffer.append(constant.charAt(p));
        }
        return stringBuffer.toString();
    }

    //签名
    public static String sign(String... strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            if (!TextUtils.isEmpty(string)) {
                stringBuffer.append(string).append('&');
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return toMD5(stringBuffer.toString());
    }

    public static String toMD5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return DigitUtils.byteToHex(messageDigest.digest(string.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isPhoneNumber(String phone) {
        return true;
    }

    public static boolean isPassword(String password) {
        return password.length() > 5 && password.length() < 21;
    }

    public static boolean isPasswordLenValid(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 20) {
            return false;
        }
        return true;
    }

    //可单击字符串
    public static CharSequence getClickable(String str, int startP, int endP, Click click) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ClickableString(click), startP, endP, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    static class ClickableString extends ClickableSpan {

        Click click;

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor("#23d5f0"));
            ds.setUnderlineText(false);
        }

        public ClickableString(Click click) {
            this.click = click;
        }

        @Override
        public void onClick(View widget) {
            click.onClick();
        }
    }

    public interface Click {
        void onClick();
    }

}
