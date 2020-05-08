package com.xieyue.jwt.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

/**
 * 解密
 *
 * */
public class DecryptUtil {
	//	private static Logger logger = Logger.getLogger(DecryptUtil.class);
	public static final String ALGORITHM = "RSA";

	public String decrypt(String userNo, String txCode, String message, String key) {
		String mw = decryptAes(message, key);
		if (mw == null) {
			return null;
		}
		String sign = mw.substring(0, 32);
//		logger.info("MD5 val==" + sign);
		// MD5签名验证
		EncrypUtil util = new EncrypUtil();
		String _message = mw.substring(32);
		String msg = userNo + txCode + _message;
//		logger.info("Msg val==" + msg);
		String _sign = util.encryptMD5(msg);
		if (_sign == null) {
			return null;
		}
//		logger.info("MD5 check val==" + _sign);
		if (_sign.equals(sign)) {
			return _message;
		} else {
			return null;
		}
	}

	public String decryptAes(String message, String key) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(key.getBytes());
			keygen.init(128, random);
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey skey = new SecretKeySpec(raw, "AES");
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, skey);
			// 8.将加密并编码后的内容解码成字节数组
			byte[] byte_content = new BASE64Decoder().decodeBuffer(message);
			// 解密
			byte[] byte_decode = cipher.doFinal(byte_content);
			String aesDecode = new String(byte_decode, "utf-8");
//			logger.info("AES Dec==" + aesDecode);
			return aesDecode;
		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
