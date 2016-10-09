package server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.portable.InputStream;



public class GetTorrent {

	/** 
     * �����ļ����浽���� 
     *  
     * @param path 
     *            �ļ�����λ�� 
     * @param url 
     *            �ļ���ַ 
     * @throws IOException 
     */  
	public void getFile(String url, String destFileName)
	        throws ClientProtocolException, IOException {
	    // ����һ��httpclient����
		@SuppressWarnings({ "resource", "deprecation" })
		HttpClient client = new DefaultHttpClient(); 
	    HttpGet httpget = new HttpGet(url);
	    HttpResponse response = client.execute(httpget);
	    byte[] result = EntityUtils.toByteArray(response.getEntity());
	    File file = new File(destFileName);
	    BufferedOutputStream bw = null;  
	    if (!file.getParentFile().exists()) 
	    { 
            file.getParentFile().mkdirs();
	    }
	    try {
	        //FileOutputStream fout = new FileOutputStream(file);
	        bw = new BufferedOutputStream(new FileOutputStream(destFileName));
	        bw.write(result); 
	        //byte[] tmp = new byte[1024];
	        /* while ((l = in.read(tmp)) != -1) {
	            fout.write(tmp, 0, l);
	            // ע�����������OutputStream.write(buff)�Ļ���ͼƬ��ʧ�棬��ҿ�������
	        }*/
	        bw.flush();
	    } finally {
	        // �رյͲ�����
	        bw.close();
	    }
	}
}
