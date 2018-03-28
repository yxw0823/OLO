package com.fh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {

/**

 * 字符串工具集合

 * @author guojf

 */
@SuppressWarnings("unchecked")

	
private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);


private static final String PASSWORD_CRYPT_KEY = "__jDlog_";

private final static String DES = "DES";

//===============================================================
private static String strChineseFirstPY = "";

public static final StringUtils instance = new StringUtils();

/**
 * 给str加上 %符号  
 * @param str
 * @param int  1是前  2是后  3是前后
 * @return
 */
public static String encodeLike(String str,int type){
	switch (type) {
	case 1:
		return "%"+str;
	case 2 :
		return str+"%";
	default:
		return "%"+str+"%";
	}
	
}

/**
 * 将一字符串转换成拼音首字母
 *
 * @since 1.1
 * @param strText
 *            字符串
 * @return 字符串对应的拼音首字母
 */
public static String getFirstPY(String strText) {
	if (strText == null || strText.trim().length() == 0)
		return "";
	String ret = "";
	for (int i = 0; i < strText.length(); i++) {
		char ch = strText.charAt(i);
		if ('\u4E00' <= ch && '\u9FA5' >= ch)
			ret = ret + strChineseFirstPY.charAt(ch - 19968);
		else
			ret = ret + ch;
	}

	return ret;
}

/**
 * 去空格
 * @param str
 * @return
 */
public static String replaceBlank(String str) {  
    String dest = "";  
    if (str!=null) {  
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
        Matcher m = p.matcher(str);  
        dest = m.replaceAll("");  
    }  
    return dest;  
}

/**
 * 替换字符串
 *
 * @since 1.1
 * @param strSc
 *            需要进行替换的字符串
 * @param oldStr
 *            源字符串
 * @param newStr
 *            替换后的字符串
 * @return 替换后对应的字符串
 */
public static String replace(String strSc, String oldStr, String newStr) {
	String ret = strSc;
	if (ret != null && oldStr != null && newStr != null) {
		ret = strSc.replaceAll(oldStr, newStr);
	}
	return ret;
}

/**
 * 替换字符串，修复java.lang.String类的replaceAll方法时第一参数是字符串常量正则时(如："address".
 * replaceAll("dd","$");)的抛出异常：java.lang.StringIndexOutOfBoundsException:
 * String index out of range: 1的问题。
 *
 * @since 1.2
 * @param strSc
 *            需要进行替换的字符串
 * @param oldStr
 *            源字符串
 * @param newStr
 *            替换后的字符串
 * @return 替换后对应的字符串
 */
public static String replaceAll(String strSc, String oldStr, String newStr) {
	int i = -1;
	while ((i = strSc.indexOf(oldStr)) != -1) {
		strSc = new StringBuffer(strSc.substring(0, i)).append(newStr).append(strSc.substring(i + oldStr.length()))
				.toString();
	}
	return strSc;
}

/**
 * 将字符串转换成HTML格式的字符串
 *
 * @since 1.1
 * @param str
 *            需要进行转换的字符串
 * @return 转换后的字符串
 */
public static String toHtml(String str) {
	String html = str;
	if (str == null || str.length() == 0) {
		return "";
	} else {
		html = replace(html, "&", "&");
		html = replace(html, "<", "<");
		html = replace(html, ">", ">");
		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "<br>\n");
		// html = replace(html, "\"", """);
		html = replace(html, " ", " ");
		return html;
	}
}

/**
 * 将HTML格式的字符串转换成常规显示的字符串
 *
 * @since 1.1
 * @param str
 *            需要进行转换的字符串
 * @return 转换后的字符串
 */
public static String toText(String str) {
	String text = str;
	if (str == null || str.length() == 0) {
		return "";
	} else {
		text = replace(text, "&", "&");
		text = replace(text, "<", "<");
		text = replace(text, ">", ">");
		text = replace(text, "<br>\n", "\n");
		text = replace(text, "<br>", "\n");
		//  text = replace(text, """, "\"");
		text = replace(text, " ", " ");
		return text;
	}
}

/**
 * 将一字符串数组以某特定的字符串作为分隔来变成字符串
 *
 * @since 1.0
 * @param strs
 *            字符串数组
 * @param token
 *            分隔字符串
 * @return 以token为分隔的字符串
 */
public static String join(String[] strs, String token) {
	if (strs == null)
		return null;
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < strs.length; i++) {
		if (i != 0)
			sb.append(token);
		sb.append(strs[i]);
	}
	return sb.toString();
}

/**
 * 验证字符串合法性
 *
 * @since 1.0
 * @param str
 *            需要验证的字符串
 * @param test
 *            非法字符串（如："~!#$%^&*()',;:?"）
 * @return true:非法;false:合法
 */
public static boolean check(String str, String test) {
	if (str == null || str.equals(""))
		return true;
	boolean flag = false;
	for (int i = 0; i < test.length(); i++) {
		if (str.indexOf(test.charAt(i)) != -1) {
			flag = true;
			break;
		}
	}
	return flag;
}

/**
 * 将数值型字符串转换成Integer型
 *
 * @since 1.0
 * @param str
 *            需要转换的字符型字符串
 * @param ret
 *            转换失败时返回的值
 * @return 成功则返回转换后的Integer型值；失败则返回ret
 */
public static Integer String2Integer(String str, Integer ret) {
	try {
		return Integer.parseInt(str);
	} catch (NumberFormatException e) {
		return ret;
	}
}

/**
 * 将数值型转换成字符串
 *
 * @since 1.0
 * @param it
 *            需要转换的Integer型值
 * @param ret
 *            转换失败的返回值
 * @return 成功则返回转换后的字符串；失败则返回ret
 */
public static String Integer2String(Integer it, String ret) {
	try {
		return Integer.toString(it);
	} catch (NumberFormatException e) {
		return ret;
	}
}

/**
 * 将阿拉伯数字的钱数转换成中文方式
 *
 * @since 1.1
 * @param num
 *            需要转换的钱的阿拉伯数字形式
 * @return 转换后的中文形式
 */
public static String num2Chinese(double num) {
	String result = "";
	String str = Double.toString(num);
	if (str.contains(".")) {
		String begin = str.substring(0, str.indexOf("."));
		String end = str.substring(str.indexOf(".") + 1, str.length());
		byte[] b = begin.getBytes();
		int j = b.length;
		for (int i = 0, k = j; i < j; i++, k--) {
			result += getConvert(begin.charAt(i));
			if (!"零".equals(result.charAt(result.length() - 1) + "")) {
				result += getWei(k);
			}
			System.out.println(result);

		}
		for (int i = 0; i < result.length(); i++) {
			result = result.replaceAll("零零", "零");
		}
		if ("零".equals(result.charAt(result.length() - 1) + "")) {
			result = result.substring(0, result.length() - 1);
		}
		result += "元";
		byte[] bb = end.getBytes();
		int jj = bb.length;
		for (int i = 0, k = jj; i < jj; i++, k--) {
			result += getConvert(end.charAt(i));
			if (bb.length == 1) {
				result += "角";
			} else if (bb.length == 2) {
				result += getFloat(k);
			}
		}
	} else {
		byte[] b = str.getBytes();
		int j = b.length;
		for (int i = 0, k = j; i < j; i++, k--) {
			result += getConvert(str.charAt(i));
			result += getWei(k);
		}
	}
	return result;
}

private static String getConvert(char num) {
	if (num == '0') {
		return "零";
	} else if (num == '1') {
		return "一";
	} else if (num == '2') {
		return "二";
	} else if (num == '3') {
		return "三";
	} else if (num == '4') {
		return "四";
	} else if (num == '5') {
		return "五";
	} else if (num == '6') {
		return "六";
	} else if (num == '7') {
		return "七";
	} else if (num == '8') {
		return "八";
	} else if (num == '9') {
		return "九";
	} else {
		return "";
	}
}

private static String getFloat(int num) {
	if (num == 2) {
		return "角";
	} else if (num == 1) {
		return "分";
	} else {
		return "";
	}
}

private static String getWei(int num) {
	if (num == 1) {
		return "";
	} else if (num == 2) {
		return "十";
	} else if (num == 3) {
		return "百";
	} else if (num == 4) {
		return "千";
	} else if (num == 5) {
		return "万";
	} else if (num == 6) {
		return "十";
	} else if (num == 7) {
		return "百";
	} else if (num == 8) {
		return "千";
	} else if (num == 9) {
		return "亿";
	} else if (num == 10) {
		return "十";
	} else if (num == 11) {
		return "百";
	} else if (num == 12) {
		return "千";
	} else if (num == 13) {
		return "兆";
	} else {
		return "";
	}
}

/**
 * 将字符串的首字母改为大写
 *
 * @since 1.2
 * @param str
 *            需要改写的字符串
 * @return 改写后的字符串
 */
public static String firstToUpper(String str) {
	return str.substring(0, 1).toUpperCase() + str.substring(1);
}

/**
* 判断字符串是否为Null或空字符串
*
* @param String
*            要判断的字符串
* @return String true-是空字符串,false-不是空字符串
*/
public static boolean nil(String s) {
	if (s == null || s.equals("")) {
		return true;
	}
	return false;
}

/**
* 字符串数组转换为字符串,使用逗号分隔
*/
public static String split(String[] s, String spliter) {
	if (StringUtils.nil(s))
		return "";
	if (s.length == 1)
		return s[0];
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < s.length; i++) {
		sb.append(s[i]).append(spliter);
	}
	sb.deleteCharAt(sb.lastIndexOf(spliter));
	return sb.toString();
}

/**
* 如果第一个字符串不为空则返回该字符串,否则返回第二个
*/
public static String nil(String s, String _default) {
	if (StringUtils.nil(s))
		return _default;
	else
		return s;
}

/**
* 判断字符串数组是否为空
*/
public static boolean nil(String[] s) {
	return (s == null || s.length == 0);
}

/**
* 如果数组为空,则返回空数组
*/
public static String[] notNil(String[] s) {
	if (s == null || s.length == 0) {
		return new String[0];
	}
	return s;
}
/**
* 字符串数组中是否包含指定的字符串。
* @param strings 字符串数组
* @param string 字符串
* @param caseSensitive 是否大小写敏感
* @return 包含时返回true，否则返回false
*/

public static boolean contains(String[] strings, String string, boolean caseSensitive) {
	for (int i = 0; i < strings.length; i++) {
		if (caseSensitive == true) {
			if (strings[i].equals(string)) {
				return true;
			}
		} else {
			if (strings[i].equalsIgnoreCase(string)) {
				return true;
			}
		}
	}
	return false;
}

/**
* 字符串数组中是否包含指定的字符串。大小写敏感。
* @param strings 字符串数组
* @param string 字符串
* @return 包含时返回true，否则返回false
*/

public static boolean contains(String[] strings, String string) {
	return contains(strings, string, true);
}

/**
 * 功能描述：去掉字符串中重复的子字符串
 * 
 * @param str
 *            原字符串，如果有子字符串则用空格隔开以表示子字符串
 * @return String 返回去掉重复子字符串后的字符串
 */
@SuppressWarnings("unused")
private static String removeSameString(String str) {
	Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
	String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
	StringBuffer sb = new StringBuffer();

	for (int i = 0; i < strArray.length; i++) {
		if (!mLinkedSet.contains(strArray[i])) {
			mLinkedSet.add(strArray[i]);
			sb.append(strArray[i] + " ");
		}
	}
	logger.debug(mLinkedSet.toString());
	System.out.println(mLinkedSet);
	return sb.toString();
}


/**native character 2 unicode ascill code*/
public static String native2ascii(String text) {
	if (text == null)
		return null;

	char[] myBuffer = text.toCharArray();
	StringBuffer sb = new StringBuffer();

	for (int i = 0; i < myBuffer.length; i++) {
		char c = myBuffer[i];
		Character.UnicodeBlock ub = UnicodeBlock.of(c);

		if (ub == UnicodeBlock.BASIC_LATIN) {
			//英文及数字等
			sb.append(c);
		} else {
			//汉字
			String hexS = Integer.toHexString(c & 0xffff);
			sb.append("\\u");

			if (hexS.length() < 4) {
				switch (hexS.length()) {
				case 1:
					sb.append("000");
					break;
				case 2:
					sb.append("00");
					break;
				case 3:
					sb.append('0');
				}
			}

			sb.append(hexS.toLowerCase());
		}
	}

	return sb.toString();
}

/**
 * 字符串替换函数
 *
 * @param str 原始字符串
 * @param substr 要替换的字符
 * @param restr 替换后的字符
 * @return 替换完成的字符串
 */
public static String replaceString(String str, String substr, String restr) {
	if (str == null)
		return null;

	if (substr == null || substr.length() == 0)
		return str;

	String[] tmp = splitString(str, substr);
	String returnstr = null;
	if (tmp.length != 0) {
		returnstr = tmp[0];
		for (int i = 0; i < tmp.length - 1; i++)
			returnstr = dealNull(returnstr) + restr + tmp[i + 1];
	}
	return dealNull(returnstr);
}

/**
 * 把null变成""<br>
 *
 */
public static String dealNull(String str) {
	String returnstr = null;
	if (str == null)
		returnstr = "";
	else
		returnstr = str;
	return returnstr;
}
/**
 * 分割字串，一般情况下客户端要考虑把返回字符数组中每个字符串进行trim()<br>
 * 本方法本身不会添加空格，不过也不会把前后空格删除。
 *
 * @param toSplit 原始字符串
 * @param delimiter 分割字符串
 * @return 字符串数组
 */
public static String[] splitString(String toSplit, String delimiter) {
	if (toSplit == null)
		return new String[0];

	int arynum = 0, intIdx = 0, intIdex = 0, div_length = delimiter.length();
	if (toSplit.compareTo("") != 0) {
		if (toSplit.indexOf(delimiter) != -1) {
			intIdx = toSplit.indexOf(delimiter);
			for (int intCount = 1;; intCount++) {
				if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
					intIdx = toSplit.indexOf(delimiter, intIdx + div_length);
					arynum = intCount;
				} else {
					arynum += 2;
					break;
				}
			}
		} else
			arynum = 1;
	} else
		arynum = 0;

	intIdx = 0;
	intIdex = 0;
	String[] returnStr = new String[arynum];

	if (toSplit.compareTo("") != 0) {
		if (toSplit.indexOf(delimiter) != -1) {
			intIdx = (int) toSplit.indexOf(delimiter);
			returnStr[0] = (String) toSplit.substring(0, intIdx);
			for (int intCount = 1;; intCount++) {
				if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
					intIdex = (int) toSplit.indexOf(delimiter, intIdx + div_length);
					returnStr[intCount] = (String) toSplit.substring(intIdx + div_length, intIdex);
					intIdx = (int) toSplit.indexOf(delimiter, intIdx + div_length);
				} else {
					returnStr[intCount] = (String) toSplit.substring(intIdx + div_length, toSplit.length());
					break;
				}
			}
		} else {
			returnStr[0] = (String) toSplit.substring(0, toSplit.length());
			return returnStr;
		}
	} else {
		return returnStr;
	}
	return returnStr;
}

/**
 * 字符串替换函数
 *
 * @param str 原始字符串
 * @param substr 要替换的字符
 * @param restr 替换后的字符
 * @return 替换完成的字符串
 */
public static String replaceStringIgnoreCase(String str, String substr, String restr) {
	if (str == null)
		return null;

	if (substr == null || substr.length() == 0)
		return str;

	String[] tmp = splitStringIgnoreCase(str, substr);
	String returnstr = null;
	if (tmp.length != 0) {
		returnstr = tmp[0];
		for (int i = 0; i < tmp.length - 1; i++)
			returnstr = dealNull(returnstr) + restr + tmp[i + 1];
	}
	return dealNull(returnstr);
}

/**
 * 分割字串，一般情况下客户端要考虑把返回字符数组中每个字符串进行trim()<br>
 * 本方法本身不会添加空格，不过也不会把前后空格删除。
 * 
 * @param toSplit 原始字符串
 * @param delimiter 分割字符串
 * @return 字符串数组
 */
public static String[] splitStringIgnoreCase(String toSplit, String delimiter) {
	if (toSplit == null)
		return new String[0];

	//我们基于按照大小写字符长度进行。
	String orginalToSplit = toSplit;

	toSplit = toSplit.toLowerCase();
	delimiter = delimiter.toLowerCase();

	int arynum = 0, intIdx = 0, intIdex = 0, div_length = delimiter.length();
	if (toSplit.compareTo("") != 0) {
		if (toSplit.indexOf(delimiter) != -1) {
			intIdx = toSplit.indexOf(delimiter);
			for (int intCount = 1;; intCount++) {
				if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
					intIdx = toSplit.indexOf(delimiter, intIdx + div_length);
					arynum = intCount;
				} else {
					arynum += 2;
					break;
				}
			}
		} else
			arynum = 1;
	} else
		arynum = 0;

	intIdx = 0;
	intIdex = 0;
	String[] returnStr = new String[arynum];

	if (toSplit.compareTo("") != 0) {
		if (toSplit.indexOf(delimiter) != -1) {
			intIdx = (int) toSplit.indexOf(delimiter);
			returnStr[0] = (String) orginalToSplit.substring(0, intIdx);
			for (int intCount = 1;; intCount++) {
				if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
					intIdex = (int) toSplit.indexOf(delimiter, intIdx + div_length);
					returnStr[intCount] = (String) orginalToSplit.substring(intIdx + div_length, intIdex);
					intIdx = (int) toSplit.indexOf(delimiter, intIdx + div_length);
				} else {
					returnStr[intCount] = (String) orginalToSplit.substring(intIdx + div_length, toSplit.length());
					break;
				}
			}
		} else {
			returnStr[0] = (String) orginalToSplit.substring(0, toSplit.length());
			return returnStr;
		}
	} else {
		return returnStr;
	}
	return returnStr;
}

/**
 * 删除字符串中的多余空格。并且把字符串的前后空格删掉。
 * <pre>
 * 例如把"     "变成" "，把制表符'\t'变成" ";
 * </pre>
 */
public static String squeezeWhiteSpace(String str) {
	if (str == null)
		return null;
	char[] cs = str.toCharArray();

	StringBuffer sb2 = new StringBuffer(cs.length);

	boolean alreadyMeetSpace = true;

	for (int i = 0; i < cs.length; i++) {
		char c = cs[i];

		//whitespace, tab, Chinese whitespace, no-breaking whitespace(ASCII code 160)
		if (c == ' ' || c == '\t' || c == '　' || c == ' ') {
			if (alreadyMeetSpace) {
				continue;
			} else {
				sb2.append(' ');
				alreadyMeetSpace = true;
			}
		} else {
			sb2.append(c);

			alreadyMeetSpace = false;
		}
	}

	//check the last char is not space
	if (alreadyMeetSpace) {
		//perform right-trim()
		if (sb2.length() > 0) {
			sb2.setLength(sb2.length() - 1);
		}
	}

	return sb2.toString();
}

public static int toInt(String s, int defaultValue) {
	if (s == null)
		return defaultValue;

	try {
		return new Integer(s).intValue();
	} catch (Exception e) {
		return defaultValue;
	}
}

public static float toFloat(String s, float defaultValue) {
	if (s == null)
		return defaultValue;

	try {
		return new Float(s).floatValue();
	} catch (Exception e) {
		return defaultValue;
	}
}

/**
 * 删除数组中相同的元素。<br>
 * 例如数组中元素为a b b c c c,合并重复元素后为a b c
 *
 * @param s 原始数组
 * @return 不含重复元素的数组。
 */
public static String[] mergeDuplicateArray(String[] s) {
	List list = Arrays.asList(s);
	Set set = new HashSet(list);
	return (String[]) set.toArray(new String[set.size()]);
}

/**
 * 检测字符串是否为null，或者trim()以后的长度是否为0。
 */
public static boolean isEmpty(String s) {
	if (s == null || "null".equals(s))
		return true;
	if (s.trim().length() == 0)
		return true;
	return false;
}

public static boolean isEmpty(Object obj) {
	if("String".equals(getType(obj))){
		return isEmpty(obj.toString());
	}
	if (obj == null) {
		return true;
	}
	return false;

}
/**
 * 对象类型判断
 * getType(这里用一句话描述这个方法的作用)  
 * (这里描述这个方法适用条件 – 可选)  
 * @param t
 * @return   
 *String  
 * @exception   
 * @since  1.0.0
 */
public static <T> String getType(T t){ 
if(t instanceof String){ 
return "String"; 
}else if(t instanceof Integer){ 
return "int"; 
}else{ 
return " do not know"; 
}
}
//===============================================================

/**

 * 加密

 * @param src 数据源

 * @param key 密钥，长度必须是8的倍数

 * @return  返回加密后的数据

 * @throws Exception

 */

public static byte[] encrypt(byte[] src, byte[] key)throws Exception {

        //DES算法要求有一个可信任的随机数源

        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建DESKeySpec对象

        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec转换成

        // 一个SecretKey对象

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作

        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象

        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        // 现在，获取数据并加密

        // 正式执行加密操作

        return cipher.doFinal(src);

     }



     /**

     * 解密

     * @param src 数据源

     * @param key 密钥，长度必须是8的倍数

     * @return   返回解密后的原始数据

     * @throws Exception

     */

     public static byte[] decrypt(byte[] src, byte[] key)throws Exception {

        // DES算法要求有一个可信任的随机数源

        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建一个DESKeySpec对象

        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成

        // 一个SecretKey对象

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作

        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象

        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        // 现在，获取数据并解密

        // 正式执行解密操作

        return cipher.doFinal(src);

     }

  /**

   * 密码解密

   * @param data

   * @return

   * @throws Exception

   */

  public final static String decrypt(String data){

     try {

      return new String(decrypt(hex2byte(data.getBytes()),

         PASSWORD_CRYPT_KEY.getBytes()));

    }catch(Exception e) {

    }

    return null;

  }

  /**

   * 密码加密

   * @param password

   * @return

   * @throws Exception

   */

  public final static String encrypt(String password){

    try {

      return byte2hex(encrypt(password.getBytes(),PASSWORD_CRYPT_KEY.getBytes()));
    }catch(Exception e) {

    }

    return null;

  }
  
  public static Map readLineFile(String filePath, String fileName, String encoding) {
	  Map retMap  = new FastHashMap();
      try {
          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath + fileName),encoding));
          String line = "";
          int i = 0;
          while ((line = br.readLine()) != null) {
              retMap.put(i++, line);
          }
          br.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
	return retMap;
  }
  
  public static String[] convertStrToArray(String str){  
      String[] strArray = null;  
      strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
      return strArray;
  }  
/**

 * 二行制转字符串

 * @param b

 * @return

 */

  public static String byte2hex(byte[] b) {

        String hs = "";

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1)

                hs = hs + "0" + stmp;

            else

                hs = hs + stmp;

        }

        return hs.toUpperCase();

   }

  

  public static byte[] hex2byte(byte[] b) {

    if((b.length%2)!=0)

       throw new IllegalArgumentException("长度不是偶数");

        byte[] b2 = new byte[b.length/2];

        for (int n = 0; n < b.length; n+=2) {

          String item = new String(b,n,2);

          b2[n/2] = (byte)Integer.parseInt(item,16);

        }

    return b2;}
  /*** 
   * 把一个字符串分离开，并按照key/value形式保存到Map中 
   * @param map 字符串中的字符存放在map中 
   * @param data 要分离的字符串 
   * @param encoding 字符编码 
   * @throws UnsupportedEncodingException 
   */  
  public static void parseParameters(Map map, String data, String encoding)  
          throws UnsupportedEncodingException {  

      if ((data != null) && (data.length() > 0)) {//将字符串转换为字节数组  
          byte[] bytes ;
          bytes=data.getBytes();  
          parseParameters(map, bytes, encoding);  
      }  

  }  

  /**** 
   * 将字节数组中的字符分离到map中，该方法支持字符分离的标记:'%', 
   * '?','&' 
   *  
   * @param map 存放分离的字符 
   * @param data  分离的字节数组 
   * @param encoding 按什么编码方法 
   * @throws UnsupportedEncodingException 
   */  
  public static void parseParameters(Map map, byte[] data, String encoding)  
          throws UnsupportedEncodingException {  

      if (data != null && data.length > 0) {  
          int ix = 0;  
          int ox = 0;  
          String key = null;  
          String value = null;  
          while (ix < data.length) {//对分离的字符数组循环  
              byte c = data[ix++];  
              switch ((char) c) {  
              case '%':  
              case '?':  
              case '&'://当字节数组中的元素，遇到'%','?','&'字符，就创建value字符串  
                  value = new String(data, 0, ox, encoding);  
                  if (key != null) {  
                      putMapEntry(map, key, value);  
                      key = null;  
                  }  
                  ox = 0;  
                  break;  
              case '='://当字节数组中的元素，遇到'='字符，那么创建key字符串  
                  key = new String(data, 0, ox, encoding);  
                  ox = 0;  
                  break;  
              case '+'://当字节数组中的元素，遇到'+'，那么就是以' '代替  
                  data[ox++] = (byte) ' ';  
                  break;  
              default:  
                  data[ox++] = c;  
              }  
          }  
          //最后一个截取的字符串，要进行处理  
          if (key != null) {  
              value = new String(data, 0, ox, encoding);  
              putMapEntry(map, key, value);  
          }  
      }  
  }  
    
  /**** 
   * 把分离的小字符串存放在map中 
   *  
   * @param map 
   * @param name 
   * @param value 
   */  
  private static void putMapEntry(Map map, String name, String value) {  
      map.put(name, value);  
  }  
  //========================================================================
  
  /**
   * 转换编码 ISO-8859-1到GB2312
   * @param text
   * @return
   */
  public String ISO2GB(String text) {
    String result = "";
    try {
      result = new String(text.getBytes("ISO-8859-1"), "GB2312");
    }
    catch (UnsupportedEncodingException ex) {
      result = ex.toString();
    }
    return result;
  }
  /**
   * 转换编码 GB2312到ISO-8859-1
   * @param text
   * @return
   */
  public String GB2ISO(String text) {
    String result = "";
    try {
      result = new String(text.getBytes("GB2312"), "ISO-8859-1");
    }
    catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }
    return result;
  }
  /**
   * Utf8URL编码
   * @param s
   * @return
   */
  public String Utf8URLencode(String text) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < text.length(); i++) {
     char c = text.charAt(i);
      if (c >= 0 && c <= 255) {
        result.append(c);
      }else {
        byte[] b = new byte[0];
        try {
          b = Character.toString(c).getBytes("UTF-8");
        }catch (Exception ex) {
        }
        for (int j = 0; j < b.length; j++) {
          int k = b[j];
          if (k < 0) k += 256;
          result.append("%" + Integer.toHexString(k).toUpperCase());
        }
      }
    }
    return result.toString();
  }
  /**
   * Utf8URL解码
   * @param text
   * @return
   */
  public String Utf8URLdecode(String text) {
    String result = "";
    int p = 0;
    if (text!=null && text.length()>0){
      text = text.toLowerCase();
      p = text.indexOf("%e");
      if (p == -1) return text;
      while (p != -1) {
        result += text.substring(0, p);
        text = text.substring(p, text.length());
        if (text == "" || text.length() < 9) return result;
        result += CodeToWord(text.substring(0, 9));
        text = text.substring(9, text.length());
        p = text.indexOf("%e");
      }
    }
    return result + text;
  }
  /**
   * utf8URL编码转字符
   * @param text
   * @return
   */
  private String CodeToWord(String text) {
    String result;
    if (Utf8codeCheck(text)) {
      byte[] code = new byte[3];
      code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
      code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
      code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
      try {
        result = new String(code, "UTF-8");
      }catch (UnsupportedEncodingException ex) {
        result = null;
      }
    }
    else {
      result = text;
    }
   return result;
  }
 /**
   * 编码是否有效
   * @param text
   * @return
   */
private boolean Utf8codeCheck(String text){
    String sign = "";
    if (text.startsWith("%e"))
      for (int i = 0, p = 0; p != -1; i++) {
           p = text.indexOf("%", p);
        if (p != -1)
          p++;
        sign += p;
      }
    return sign.equals("147-1");
  }
  /**
   * 是否Utf8Url编码
   * @param text
   * @return
   */
  public boolean isUtf8Url(String text) {
    text = text.toLowerCase();
    int p = text.indexOf("%");
    if (p != -1 && text.length() - p > 9) {
      text = text.substring(p, p + 9);
    }
    return Utf8codeCheck(text);
  }
  
   
  //========================================================================
  //转换为%E4%BD%A0形式  
  public static String toUtf8String(String s) {  
      StringBuffer sb = new StringBuffer();  
      for (int i = 0; i < s.length(); i++) {  
          char c = s.charAt(i);  
          if (c >= 0 && c <= 255) {  
              sb.append(c);  
          } else {  
              byte[] b;  
              try {  
                  b = String.valueOf(c).getBytes("utf-8");  
              } catch (Exception ex) {  
                  System.out.println(ex);  
                  b = new byte[0];  
              }  
              for (int j = 0; j < b.length; j++) {  
                  int k = b[j];  
                  if (k < 0)  
                      k += 256;  
                  sb.append("%" + Integer.toHexString(k).toUpperCase());  
              }  
          }  
      }  
      return sb.toString();  
  }  

  //将%E4%BD%A0转换为汉字  
  public static String unescape(String s) {  
      StringBuffer sbuf = new StringBuffer();  
      int l = s.length();  
      int ch = -1;  
      int b, sumb = 0;  
      for (int i = 0, more = -1; i < l; i++) {  
          /* Get next byte b from URL segment s */  
          switch (ch = s.charAt(i)) {  
          case '%':  
              ch = s.charAt(++i);  
              int hb = (Character.isDigit((char) ch) ? ch - '0'  
                      : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;  
              ch = s.charAt(++i);  
              int lb = (Character.isDigit((char) ch) ? ch - '0'  
                      : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;  
              b = (hb << 4) | lb;  
              break;  
          case '+':  
              b = ' ';  
              break;  
          default:  
              b = ch;  
          }  
          /* Decode byte b as UTF-8, sumb collects incomplete chars */  
          if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)     
              sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb     
              if (--more == 0)  
                  sbuf.append((char) sumb); // Add char to sbuf     
          } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)     
              sbuf.append((char) b); // Store in sbuf     
          } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)     
              sumb = b & 0x1f;  
              more = 1; // Expect 1 more byte     
          } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)     
              sumb = b & 0x0f;  
              more = 2; // Expect 2 more bytes     
          } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)     
              sumb = b & 0x07;  
              more = 3; // Expect 3 more bytes     
          } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)     
              sumb = b & 0x03;  
              more = 4; // Expect 4 more bytes     
          } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)     
              sumb = b & 0x01;  
              more = 5; // Expect 5 more bytes     
          }  
          /* We don't test if the UTF-8 encoding is well-formed */  
      }  
      return sbuf.toString();  
  }  
  
  //
  @SuppressWarnings("deprecation")
public static void main(String[] args) {
	String str = encrypt("郭井夫");
	Integer  num = 1;
	num =  -num;
	System.out.println(num);
	System.out.println(str);
	System.out.println(decrypt(str));
	

	StringUtils charTools = new StringUtils();
    String url;
    url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
    if(charTools.isUtf8Url(url)){
      System.out.println(charTools.Utf8URLdecode(url));
    }else{
      System.out.println(URLDecoder.decode(url));
    }
    url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
    if(charTools.isUtf8Url(url)){
      System.out.println(charTools.Utf8URLdecode(url));
    }else{
      System.out.println(URLDecoder.decode(url));
    }
    System.out.println(StringUtils.toUtf8String("你"));  
    System.out.println(StringUtils.unescape("%E4%BD%A0%20%E5%A5%BD"));  
    System.out.println(StringUtils.unescape("%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2"));  
}
  
  
  /** 
   * 修改字符串中的unicode码 
   * @param s 源str 
   * @return  修改后的str 
   */  
  public static String decode2( String s )   
  {  
      StringBuilder sb = new StringBuilder( s.length() );  
      char[] chars = s.toCharArray();  
      for( int i = 0; i < chars.length; i++ )  
      {  
          char c = chars[i];  
          if( c == '\\' && chars[i + 1] == 'u')  
          {  
              char cc = 0;  
              for( int j = 0; j < 4; j++ )  
              {  
                  char ch = Character.toLowerCase( chars[i + 2 + j] );  
                  if( '0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f' )  
                  {  
                      cc |= ( Character.digit( ch, 16 ) << ( 3 - j ) * 4 );  
                  }else  
                  {  
                      cc = 0;  
                      break;  
                  }  
              }  
              if ( cc > 0 )  
              {  
                  i += 5;  
                  sb.append( cc );  
                  continue;  
              }  
          }  
          sb.append( c );  
      }  
      return sb.toString();  
  }  
    
  /** 
   * 获取修复后的字符串 
   * @param str 
   * @return 
   */  
  public static String getFixStr( String str )  
  {  
      String ret = str;  
      Pattern p = Pattern.compile( "(\\\\u.{4})" );  
      Matcher m = p.matcher( ret );  
      while( m.find() )  
      {  
          String xxx = m.group( 0 );  
          ret = str.replaceAll( "\\" + xxx, decode2( xxx ) );  
      }  
      return ret;  
  }  
}