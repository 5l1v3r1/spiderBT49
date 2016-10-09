package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * DZ ��¼�뷢�� ʵ��
 * 
 * @author Administrator aizili.com������
 * 
 */
@SuppressWarnings("deprecation")
public class SpiderBT49 extends Thread{
	static final String domainurl = "http://www.49lou.com/";	//ԭʼ��ַ
	static final String loginurl = "http://www.49lou.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";//��¼��ַ
	static final String loginUsername = "username"; // ��¼�û���
	static final String loginPassword = "password"; // ����
	
	static final String username = "xxgchappy"; // ��¼�û���(�Լ���)
	static final String password = "zchzchzch"; // ��¼����(�Լ���)

	/**
	 * ״̬���Ӧ HttpServletResponse �ĳ�����ϸ����
	 * 
	 * @author Administrator
	 * @time 2012-4-8 12:24
	 */
	static class HttpStatus {
		static int SC_MOVED_TEMPORARILY = 301; // ҳ���Ѿ������Ƶ�����һ���µ�ַ
		static int SC_MOVED_PERMANENTLY = 302; // ҳ����ʱ�ƶ�������һ���µĵ�ַ
		static int SC_SEE_OTHER = 303; // �ͻ�������ĵ�ַ����ͨ������� URL ������
		static int SC_TEMPORARY_REDIRECT = 307; // ҳ����ʱ�ƶ�������һ���µĵ�ַ
	}

	/**
	 * ��ȡ formhash ֵvalue
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String getFormhash(String url,DefaultHttpClient httpclient) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpGet);

		
		HttpEntity entity = response.getEntity();
		StringBuffer sb = null;
		// ���ҳ������
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			InputStream is = entity.getContent();
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			is.close();
		}
		
		
		int pos = sb.indexOf("name=\"formhash\" value=");

		// �ҳ���� formhash �����ݣ����ǵ�¼�õ� formhash
		String login_formhash = sb.substring(pos + 23, pos + 23 + 8);
		//System.out.print(login_formhash);
		return login_formhash;
	}
	
	/**
	 * ��¼
	 * @param httpclient	
	 * @param user	�û���
	 * @param pass	����
	 * @param formhash	�ύ�ı�formhashֵ
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean logicDz(DefaultHttpClient httpclient,String formhash) throws ClientProtocolException, IOException{
		/* ����post���� */
		HttpPost httpPost = new HttpPost(loginurl);
		
		/* ������¼���� */
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("formhash", formhash));
		
		/* ��ӵ�httpPost�ύ�������� */
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		
		/*ִ�в���ӡ��¼��������ʾ���*/
		//printHttpGet(httpclient.execute(httpPost));
		
		HttpResponse response = httpclient.execute(httpPost);//����ӡ��¼���

		/*�жϵ�¼�Ƿ�ɹ�*/
		HttpEntity entity = response.getEntity();
		StringBuffer sb = null;
		// ���ҳ������
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			InputStream is = entity.getContent();
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\t\n");
			}
			is.close();
		}
		
		if(sb!=null)
		{
			//System.out.println(sb);
			System.out.println("#################################   ��¼�ɹ�   ############################");
			//cynadd ץȡָ��ҳ�������
			/*HttpGet httpGet = new HttpGet("http://www.bt49.com/thread-85971-1-1.html");
//			 Invalid use of SingleClientConnManager: connection still allocated.
			HttpResponse rresponse = httpclient.execute(httpGet);

			HttpEntity eentity = rresponse.getEntity();
			StringBuffer ssb = null;
			if (eentity != null) {
				String charset = EntityUtils.getContentCharSet(eentity);
				InputStream is = eentity.getContent();
				ssb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,
						charset));
				String line = null;
				while ((line = br.readLine()) != null) {
					ssb.append(line+"\t\n");
				}
				is.close();
			}
			System.out.println(ssb);*/
			
			
			return true;
		}else
		{
			return false;
		}
		/*if(sb.indexOf("title=\"�����ҵĿռ�\"") != -1){
			int pos = sb.indexOf("title=\"�����ҵĿռ�\"");
			String username =sb.substring(pos+15, pos+50);
			username = username.substring(0, username.indexOf("<", 1));
			
			System.out.println("��¼ʱ�� �û���Ϊ��"+username);
			System.out.println("#################################   ��¼�ɹ�   ############################");
			return true;
		}else{
			return false;
		}*/
		
		/*�ͷ���Դ*/
//		httpPost.abort();
	}
	
	/**
	 * ����ָ����url
	 * @param httpclient
	 * @param url
	 * @param message
	 * @param subject
	 * @param login_formhash
	 * @return HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String seizPointWeb(DefaultHttpClient httpclient , String url)throws ClientProtocolException, IOException
	{
		HttpGet httpGet = new HttpGet(url);
//		 Invalid use of SingleClientConnManager: connection still allocated.
		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();
		StringBuffer sb = null;
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			InputStream is = entity.getContent();
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\t\n");
			}
			is.close();
		}
		//System.out.println(sb);
		return sb.toString();
	}
	/**
	 * ���� �ɹ��󷵻�ҳ������
	 * @param httpclient
	 * @param url
	 * @param message
	 * @param subject
	 * @param login_formhash
	 * @return HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse postMessage(DefaultHttpClient httpclient,String url,String message,String subject,String login_formhash) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("message", message));			//����
		nvps.add(new BasicNameValuePair("subject", subject));			//����
		nvps.add(new BasicNameValuePair("formhash", login_formhash));	//�ύform��hashֵ(�����ύform��)
		
		/*���µĿ��Բ����ã�����һ����̳�У�����������ֵ��*/
		nvps.add(new BasicNameValuePair("allownoticeauthor", "1"));
		nvps.add(new BasicNameValuePair("wysiwyg", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		
		response = httpclient.execute(httpPost);
		
		/*�ͷ���Դ*/
		httpPost.abort();
		return response;
	}
	
	/**
	 * �ظ�
	 * @param httpclient
	 * @param httpost
	 * @param message
	 * @param subject
	 * @param login_formhash
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse postReMessage(DefaultHttpClient httpclient,String url,String message,String subject,String login_formhash) throws ClientProtocolException, IOException{
		HttpResponse response = null;
		
		/*�ύ��url����Ҫ����domainurl�ĵ�ַ*/
		url = getReMessageUrl(url,httpclient);
		System.out.println("�ظ��ύ����ַ  url="+url);
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("message", message));			//����
		nvps.add(new BasicNameValuePair("subject", subject));			//����
		nvps.add(new BasicNameValuePair("formhash", login_formhash));	//�ύform��hashֵ(�����ύform��)
		
		/*���µĿ��Բ����ã�����һ����̳�У�����������ֵ��*/
		nvps.add(new BasicNameValuePair("allownoticeauthor", "1"));
		nvps.add(new BasicNameValuePair("wysiwyg", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		response = httpclient.execute(httpPost);
		
		/* �ͷ���Դ */
		httpPost.abort();
		return response;
	} 
	
	/**
	 * ��ȡ�ظ���url
	 * @param url
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getReMessageUrl(String url,DefaultHttpClient httpclient) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
//		 Invalid use of SingleClientConnManager: connection still allocated.
		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();
		StringBuffer sb = null;
		// ���ҳ������
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			InputStream is = entity.getContent();
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\t\n");
			}
			is.close();
		}
		int pos = sb.indexOf("id=\"fastpostform\" action=");
		int pos_end = sb.indexOf("fastpost\"");
		
		System.out.println(sb.length()+"  pos="+pos+"  pos_end="+pos_end);
		// �ҳ���� reMessageUrl ������
		String reMessageUrl = domainurl + sb.substring(pos+26, pos_end+8);
		
		//ȥ��amp;
		reMessageUrl = reMessageUrl.replaceAll("amp;", "");
		
		//�ͷ���Դ
		httpGet.abort();
		return reMessageUrl;
	}
	
	/**
	 * ��ȡ�ض����url
	 * @param httpclient
	 * @param response
	 * @return ����url��ַ
	 */
	public String redirectHttp(DefaultHttpClient httpclient,HttpResponse response) {
		Header header = response.getFirstHeader("location");
		String urlRedirect = "";
		if(!header.getValue().contains(domainurl)){
			urlRedirect = domainurl+header.getValue();
		} else {
			urlRedirect = header.getValue();
		}

		return urlRedirect;
	}
	
	/**
	 * ����HttpResponse���� ��ӡҳ������
	 * @param response
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void printHttpGet(HttpResponse response) throws IllegalStateException, IOException{
		HttpEntity entity = response.getEntity();
		
		StringBuffer sb = null;
		// ���ҳ������
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			InputStream is = entity.getContent();
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\t\n");
			}
			is.close();
			System.out.println(sb.toString());
		}
		
	}

	
	/**
	 * ������ main()
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void main(String args[]) throws IOException, InterruptedException, SQLException {
		LoginDZ loginDZ = new LoginDZ();
		
		int jump=0;//1 ��ת��1080p
		int index_start=0;
		int total_page=1;
		String sort = "ustv";// �����Ӱ������ 1080p 720p yunpan remux hdhj hdjilu 3d  atv ustv 
		ArrayList<String> re_link = new ArrayList<String>();
		re_link = null;
		int score = 1;//��Ӱ����
		String weburl="http://www.49lou.com/";
		int diff_db=0;//0 ��Ӱ; 1 ���Ӿ����; 2 С������Ӱ���� 
		/**
		 * ����ת���֧
		 */
		@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in);
		System.out.println("����Ҫ�����Դ�б�"); 
		System.out.println("�����Ӱ���� 1:1080p; 2:720p; 3:yunpan; 4:remux; 5:����ϼ�; 6:��¼Ƭ; 7:3D��Ӱ");
		System.out.println("���Ӿ�����8:������Ӿ�����; 9:��������");
		System.out.println("С������Ӱ��10:BD; 11:IPAD; 12:Ѹ��mkv; 13:���޵�Ӱ����; 14:ŷ����Ӱ����;");
		System.out.println("���ս�Ŀ��15:����; 16:�ձ�; 17:����; 18:ŷ��; 19:�ݳ���/MV/����;20����������;");
		jump = input.nextInt();
		
		switch(jump)
		{
			case 1:sort="1080p";
				System.out.println("1080pץȡ��ʼ");
			break;
			case 2:sort="720p";
				System.out.println("720pץȡ��ʼ");
			break;
			case 3:sort="yunpan";
				System.out.println("����ץȡ��ʼ");
			break;
			case 4:sort="remux";
				System.out.println("����ץȡ��ʼ");
			break;
			case 5:sort="hdhj";
				System.out.println("����ץȡ��ʼ");
			break;
			case 6:sort="hdjilu";
				System.out.println("����ץȡ��ʼ");
			break;
			case 7:sort="3d";
				System.out.println("����ץȡ��ʼ");
			break;
			case 8:sort="atv";
				System.out.println("���Ӿ�ץȡ��ʼ");
				   diff_db=1;
			break;
			case 9:sort="ustv";
				System.out.println("����ץȡ��ʼ");
					diff_db=1;
			break;
			case 10:sort="bd";
				System.out.println("BDץȡ��ʼ");
					diff_db=2;
			break;
			case 11:sort="ipad";
				System.out.println("IPADץȡ��ʼ");
					diff_db=2;
			break;
			case 12:sort="thund";
				System.out.println("thunderץȡ��ʼ");
					diff_db=2;
			break;
			case 13:sort="asia";
				System.out.println("���޵�Ӱץȡ��ʼ");
					diff_db=2;
			break;
			case 14:sort="euro";
				System.out.println("ŷ�޵�Ӱץȡ��ʼ");
					diff_db=2;
			break;
			case 15:sort="zysk";
				System.out.println("��������");
				diff_db=3;
			break;
			case 16:sort="zyjp";
				System.out.println("�ձ�����");
				diff_db=3;
				break;
			case 17:sort="zycn";
				System.out.println("��������");
				diff_db=3;
				break;
			case 18:sort="zyeuus";
				System.out.println("ŷ������");
				diff_db=3;
				break;
			case 19:sort="zyymt";
				System.out.println("�ݳ���/mv/����");
				diff_db=3;
				break;
			case 20:sort="zycarton";
				System.out.println("����");
				diff_db=3;
				break;
			default:
				System.out.println("û�����ѡ��");
				return;
		
		}
		System.out.println("������ҳץȡ��ʼ������Ŀ��");
		index_start = input.nextInt();
		System.out.println(">>ץȡ��ҳ��ҳ����������"+index_start);
		System.out.println("����ץȡ��ҳ����");
		total_page = input.nextInt();
		System.out.println(">>ץȡ��ҳ����"+total_page);
		System.out.println("������ʼ������");
		score = input.nextInt();
		System.out.println(">>��ʼ������"+total_page);
//		connectionManager
		

		DefaultHttpClient httpclient = new DefaultHttpClient();// �õ�httpclientʵ��

		/* ��¼��̳ */
		System.out.println("#################################   ��ʼ��¼   ############################");
		String login_formhash = loginDZ.getFormhash(domainurl,httpclient);//��ȡformhash
		if(!loginDZ.logicDz(httpclient,login_formhash)){
			System.out.println("#################################   ��¼ʧ��   ############################");
			return;
		}
		else
		{
			String content = null;
			JsoupGet getone = new JsoupGet();
			switch(sort)
			{
			/**
			 * ��Բ�ͬ��Դ����ת��
			 */
				case "1080p":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("1080pר��ץȡ��url="+weburl+"forum-57-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-57-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ���ĵ�һҳ���е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "720p":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("720pר��ץȡ��url="+weburl+"forum-58-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-58-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0,diff_db );//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "yunpan":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("����ר��ץȡ��url="+weburl+"forum-94-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-94-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "remux":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("����ר��ץȡ��url="+weburl+"forum-82-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-82-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "hdhj":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�ϼ�ר��ץȡ��url="+weburl+"forum-87-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-87-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "hdjilu":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("��¼Ƭר��ץȡ��url="+weburl+"forum-62-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-62-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "3d":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("3dר��ץȡ��url="+weburl+"forum-71-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-71-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * ���Ӿ�ץȡ����
				 */
				case "atv":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-86-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-86-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "ustv":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("����ר��ץȡ��url="+weburl+"forum-41-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-41-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * С������Ӱ����
				 */
				case "bd":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-39-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-39-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "ipad":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-61-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-61-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "thund":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-63-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-63-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "asia":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-36-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-36-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "euro":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�����Ӿ�ר��ץȡ��url="+weburl+"forum-37-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-37-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * ���ս�Ŀ
				 */
				case "zysk":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("��������ץȡ��url="+weburl+"forum-44-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-44-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyjp":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�ձ����ս�Ŀץȡ��url="+weburl+"forum-45-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-45-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zycn":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�������ս�Ŀץȡ��url="+weburl+"forum-46-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-46-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyeuus":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("ŷ�����ս�Ŀר��ץȡ��url="+weburl+"forum-60-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-60-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyymt":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("�ݳ���/mv/����ר��ץȡ��url="+weburl+"forum-42-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-42-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zycarton":
					/**
					 * ת��ָ����html����ҳ��
					 */
					for(int i=total_page;i>=1;i--){//��ȡʣ���������� ����ѭ��ץȡһҳ������
						System.out.println("��ͨ����ץȡ��url="+weburl+"forum-73-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-73-"+i+".html?mobile=no");//����ָ����html����ҳ��
						
						if(i == 1)//��ȡ��һ�������б�ҳ��
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//��ȡʣ����������
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//��ȡ�������е�Ӱ��������
							if(re_link.size() == 0)
							{
								System.out.println("�������Ӿ��������ݿ��д��ڣ�����Ҫ������ӣ�");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//����ָ����html����ҳ��
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				default:
					System.out.println("û�������Դ����");
				
				
				
				
				
			
			}
			//content = loginDZ.seizPointWeb(httpclient,"http://www.bt49.com/thread-86046-1-1.html");//����ָ����html����ҳ��
			//String content = loginDZ.seizPointWeb(httpclient,"http://www.bt49.com/thread-86246-1-1.html");//����ָ����html����ҳ��
			//getone = new JsoupGet();
			//getone.getDivthread(content , sort,"http://www.bt49.com/thread-86046-1-1.html?mobile=no");
			//System.out.println(content);
		}
		
		
		/* �������� 
		System.out.println("#################################   ��������   ############################");
		String url = null;		//������url
		String message = null;	//����������
		String subject = null;	//�����ı���*/
		
		
//		/* ������Ƿ�������  �Լ�ȥ�Ӱ�,�������̶߳��һ�𷢣����õ�  ����fid��ͬ���ѣ��ɴ�1��ʼ��50*/
//		for(int i=0;i<1;i++){
//			/* �������� */
//			url = "http://www.aizili.com/forum.php?mod=post&action=newthread&fid=24&extra=&topicsubmit=yes";
//			message = "����齨��ҵ�Ŷӣ���IT��ҵ����ӭ���룬�����������ϵ��ʽ";
//			subject = "����齨��ҵ�Ŷӣ���IT��ҵ����ӭ����";
//			login_formhash = loginDZ.getFormhash(url,httpclient);
//			
//			/* ���÷������� �ĸ��������������һ��Ϊform�ύ��hashֵ */
//			HttpResponse response = loginDZ.postMessage(httpclient, url, message, subject, login_formhash);
//			/* ���Կ������� */
//			System.out.println("#################################   ������ɺ� ��������   ############################");
//			loginDZ.printHttpGet(response);	//��ӡ�������ҳ�濴��
//			
//			System.out.println("#################################   �ض�����תҳ��   ############################");
//			/* ��ȡ�ض����ʶ�� */
//			int statuscode = response.getStatusLine().getStatusCode();
//			if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
//					|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
//					|| (statuscode == HttpStatus.SC_SEE_OTHER)
//					|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
//				System.out.println("#################################   ��ʼ����ظ�   ############################");
//				/* ����ظ� */
//				url = loginDZ.redirectHttp(httpclient, response);
//				message = "��ý���һ���Լ����һ�ѡ���ԵļӴ�ҵģ���ü�����ϵ��QQ֮���";
//				subject = "����齨��ҵ�Ŷӣ���IT��ҵ����ӭ����";
//				login_formhash = loginDZ.getFormhash(url,httpclient);
//				
//				for(int j=0;j<1;j++){
//					//���Զ�ζ�һ�����ӻظ�
//				}
//
//				sleep(10000);//��10���ٻظ�
//				
//				response = loginDZ.postReMessage(httpclient,url, message, subject, login_formhash);
//	
//				/*��ӡҳ������*/
//				loginDZ.printHttpGet(response);	
//			}
//		}	
		
		
		/*url = "http://www.aizili.com/thread-264893-1-1.html";
		message = "����治��������ϵ��ʽ���£�����";
		subject = "����齨��ҵ�Ŷӣ���IT��ҵ����ӭ����";
		login_formhash = loginDZ.getFormhash(url,httpclient);
		for(int i=15;i<20;i++){
			System.out.println(message+" ������" + i);
			loginDZ.postReMessage(httpclient,url, message, subject, login_formhash);
			sleep(10000);
		}*/
		
		/* �ر����ӹ����� */
		httpclient.getConnectionManager().shutdown();
	}
}

