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
     * 下载文件保存到本地 
     *  
     * @param path 
     *            文件保存位置 
     * @param url 
     *            文件地址 
     * @throws IOException 
     */  
	public void getFile(String url, String destFileName)
	        throws ClientProtocolException, IOException {
	    // 生成一个httpclient对象
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
	            // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
	        }*/
	        bw.flush();
	    } finally {
	        // 关闭低层流。
	        bw.close();
	    }
	}
}
