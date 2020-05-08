package com.xieyue.jwt.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * 加密
 *
 * @author wangshanfang
 *
 */
public class EncrypUtil {
//	private static Logger logger = Logger.getLogger(EncrypUtil.class);

	public String encrypt(String userNo, String txCode, String msg, String key) {
		String message = userNo + txCode + msg;
		// MD5签名
		String sign = encryptMD5(message);
		String message2 = sign + msg;
		// AES加密
		String result = encodeAes(message2, key);
//		logger.info("AES Result==" + result);
		return result;
	}

	public String encryptMD5(String msg) {
		String newstr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] b = md5.digest(msg.getBytes("utf-8"));
			int i = 0;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			newstr = buf.toString();
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
		}
//		logger.info("MD5 Val==" + newstr);
		return newstr;
	}

	private String encodeAes(String msg, String key) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(key.getBytes());
			keygen.init(128, random);
			//keygen.init(128, new SecureRandom(key.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey skey = new SecretKeySpec(raw, "AES");
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			// 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = msg.getBytes("utf-8");
			// 9.根据密码器的初始化方式--加密：将数据加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			// 10.将加密后的数据转换为字符串
			String aesEncode = new String(new BASE64Encoder().encode(byte_AES));
//			logger.info("AES Val=" + aesEncode);
			// 11.将字符串返回
			return aesEncode;
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
		}
		return null;
	}
}

