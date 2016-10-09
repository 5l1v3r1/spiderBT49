package server;
import java.io.UnsupportedEncodingException;
public class ChangeCharset {
	/**
	  * ���ַ�����ת����UTF-8��
	  */
	/** 8 λ UCS ת����ʽ */
	 public static final String UTF_8 = "GBK";
	 public String toUTF_8(String str) throws UnsupportedEncodingException{
	  return this.changeCharset(str, UTF_8);
	 }
	 /**
	  * �ַ�������ת����ʵ�ַ���
	  * @param str  ��ת��������ַ���
	  * @param newCharset Ŀ�����
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 public String changeCharset(String str, String newCharset)
	   throws UnsupportedEncodingException {
	  if (str != null) {
	   //��Ĭ���ַ���������ַ�����
	   byte[] bs = str.getBytes();
	   //���µ��ַ����������ַ���
	   return new String(bs, newCharset);
	  }
	  return null;
	 }

}
