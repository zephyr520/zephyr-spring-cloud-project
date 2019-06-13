package com.zephyr.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

	public static final String MOBILE_REGEX = "^1\\d{10}$";
    public static final String EMAIL_REGEX = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    public static final String TELEPHONE_REGEX = "^(\\d{3,4})?\\d{8}";
    /**
     * 用于获取固定电话中的区号
     */
    private final static String REGEX_ZIPCODE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";

	/**
     * 是否有效手机号码，可以更改参数以只检查特定运营商的号段
     * @param mobile 号码
     * @return 是否合法手机号码
     */
    public static boolean isValidMobile(String mobile){
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(mobile); 
        
        return matcher.matches();
    }

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email); 
        
        return matcher.matches();
	}

    /**
     * 验证固话
     * @param telephone
     * @return
     */
    public static boolean isValidTel(String telephone) {
        Pattern pattern = Pattern.compile(REGEX_ZIPCODE);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }

    /**
     * 获取固定号码号码中的区号
     * @param strNumber
     * @return
     */
    public static String getCityCode(String strNumber) {
        String cityCode = "0755";
        Pattern p = Pattern.compile(REGEX_ZIPCODE);
        Matcher m = p.matcher(strNumber);
        if (m.find()) {
            return m.group(1);
        } else {
            return cityCode;
        }
    }

    public static void main(String[] args) {
        System.out.println(ValidUtil.isValidTel("075536892541"));
    }
}
