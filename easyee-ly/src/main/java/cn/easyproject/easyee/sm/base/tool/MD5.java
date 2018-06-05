package cn.easyproject.easyee.sm.base.tool;

import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * MD5加密工具类
 * @author easyproject.cn
 *
 */
public class MD5 {
	
	/**
	 * 使用指定SALT加密原始字符串
	 * @param saw 原始字符串
	 * @param salt 加密盐
	 * @return 加密后的字符串
	 */
	public static String getMd5(String saw,String salt) {
		return new Md5Hash(saw, salt).toHex();
	}
	/**
	 * 使用指定SALT加密原始字符串
	 * @param saw 原始字符串
	 * @param salt 加密盐
	 * @return 加密后的字符串
	 */
	public static String getMd5(char[] saw,String salt) {
		return new Md5Hash(saw, salt).toHex();
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMd5("123456", "admin".toLowerCase()));
		System.out.println(MD5.getMd5("111111", "jay".toLowerCase()));
		System.out.println(MD5.getMd5("111111", "hr".toLowerCase()));
		System.out.println(MD5.getMd5("111111", "manager".toLowerCase()));
		System.out.println(MD5.getMd5("demo", "demo".toLowerCase()));
	}
	
	public static String code(String instr) {
        String s = null;
        byte[] source = instr.getBytes();
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();

            char[] str = new char[32];

            int k = 0;
            for (int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];

                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    
    public static String codeBytes(byte[] sourcex)
    {
      String s = null;
      byte[] source = sourcex;
      char[] hexDigits = { 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
        'e', 'f' };
      try {
        MessageDigest md = 
          MessageDigest.getInstance("MD5");
        md.update(source);
        byte[] tmp = md.digest();

        char[] str = new char[32];

        int k = 0;
        for (int i = 0; i < 16; ++i)
        {
          byte byte0 = tmp[i];
          str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];

          str[(k++)] = hexDigits[(byte0 & 0xF)];
        }
        s = new String(str);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return s;
    }
	
	
	//
//	private static MessageDigestPasswordEncoder mdpe = new MessageDigestPasswordEncoder(
//			"MD5");
//	
//	private static final String SALT = "salt"; //加密盐
//
//	/**
//	 *  使用默认SALT加密原始字符串
//	 * @param saw 原始字符串
//	 * @return 加密后的字符串
//	 */
//	public static String getMd5(String saw) {
//		return mdpe.encodePassword(saw, SALT);
//	}

//
}
