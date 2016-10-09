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
 * DZ 登录与发贴 实例
 * 
 * @author Administrator aizili.com创建人
 * 
 */
@SuppressWarnings("deprecation")
public class SpiderBT49 extends Thread{
	static final String domainurl = "http://www.49lou.com/";	//原始地址
	static final String loginurl = "http://www.49lou.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";//登录地址
	static final String loginUsername = "username"; // 登录用户名
	static final String loginPassword = "password"; // 密码
	
	static final String username = "xxgchappy"; // 登录用户名(自己改)
	static final String password = "zchzchzch"; // 登录密码(自己改)

	/**
	 * 状态码对应 HttpServletResponse 的常量详细描述
	 * 
	 * @author Administrator
	 * @time 2012-4-8 12:24
	 */
	static class HttpStatus {
		static int SC_MOVED_TEMPORARILY = 301; // 页面已经永久移到另外一个新地址
		static int SC_MOVED_PERMANENTLY = 302; // 页面暂时移动到另外一个新的地址
		static int SC_SEE_OTHER = 303; // 客户端请求的地址必须通过另外的 URL 来访问
		static int SC_TEMPORARY_REDIRECT = 307; // 页面暂时移动到另外一个新的地址
	}

	/**
	 * 获取 formhash 值value
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
		// 输出页面内容
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

		// 找出这个 formhash 的内容，这是登录用的 formhash
		String login_formhash = sb.substring(pos + 23, pos + 23 + 8);
		//System.out.print(login_formhash);
		return login_formhash;
	}
	
	/**
	 * 登录
	 * @param httpclient	
	 * @param user	用户名
	 * @param pass	密码
	 * @param formhash	提交的表单formhash值
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean logicDz(DefaultHttpClient httpclient,String formhash) throws ClientProtocolException, IOException{
		/* 创建post连接 */
		HttpPost httpPost = new HttpPost(loginurl);
		
		/* 创建登录条件 */
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("formhash", formhash));
		
		/* 添加到httpPost提交的内容中 */
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		
		/*执行并打印登录后内容显示情况*/
		//printHttpGet(httpclient.execute(httpPost));
		
		HttpResponse response = httpclient.execute(httpPost);//不打印登录情况

		/*判断登录是否成功*/
		HttpEntity entity = response.getEntity();
		StringBuffer sb = null;
		// 输出页面内容
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
			System.out.println("#################################   登录成功   ############################");
			//cynadd 抓取指定页面的内容
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
		/*if(sb.indexOf("title=\"访问我的空间\"") != -1){
			int pos = sb.indexOf("title=\"访问我的空间\"");
			String username =sb.substring(pos+15, pos+50);
			username = username.substring(0, username.indexOf("<", 1));
			
			System.out.println("登录时的 用户名为："+username);
			System.out.println("#################################   登录成功   ############################");
			return true;
		}else{
			return false;
		}*/
		
		/*释放资源*/
//		httpPost.abort();
	}
	
	/**
	 * 定向到指定的url
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
	 * 发贴 成功后返回页面内容
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
		nvps.add(new BasicNameValuePair("message", message));			//内容
		nvps.add(new BasicNameValuePair("subject", subject));			//标题
		nvps.add(new BasicNameValuePair("formhash", login_formhash));	//提交form的hash值(防外提交form的)
		
		/*以下的可以不设置，看了一下论坛中，这两个都有值的*/
		nvps.add(new BasicNameValuePair("allownoticeauthor", "1"));
		nvps.add(new BasicNameValuePair("wysiwyg", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		
		response = httpclient.execute(httpPost);
		
		/*释放资源*/
		httpPost.abort();
		return response;
	}
	
	/**
	 * 回复
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
		
		/*提交的url，需要加上domainurl的地址*/
		url = getReMessageUrl(url,httpclient);
		System.out.println("回复提交表单地址  url="+url);
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("message", message));			//内容
		nvps.add(new BasicNameValuePair("subject", subject));			//标题
		nvps.add(new BasicNameValuePair("formhash", login_formhash));	//提交form的hash值(防外提交form的)
		
		/*以下的可以不设置，看了一下论坛中，这两个都有值的*/
		nvps.add(new BasicNameValuePair("allownoticeauthor", "1"));
		nvps.add(new BasicNameValuePair("wysiwyg", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
		response = httpclient.execute(httpPost);
		
		/* 释放资源 */
		httpPost.abort();
		return response;
	} 
	
	/**
	 * 获取回复的url
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
		// 输出页面内容
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
		// 找出这个 reMessageUrl 的内容
		String reMessageUrl = domainurl + sb.substring(pos+26, pos_end+8);
		
		//去除amp;
		reMessageUrl = reMessageUrl.replaceAll("amp;", "");
		
		//释放资源
		httpGet.abort();
		return reMessageUrl;
	}
	
	/**
	 * 获取重定向的url
	 * @param httpclient
	 * @param response
	 * @return 返回url地址
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
	 * 根据HttpResponse对象 打印页面内容
	 * @param response
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void printHttpGet(HttpResponse response) throws IllegalStateException, IOException{
		HttpEntity entity = response.getEntity();
		
		StringBuffer sb = null;
		// 输出页面内容
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
	 * 主函数 main()
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void main(String args[]) throws IOException, InterruptedException, SQLException {
		LoginDZ loginDZ = new LoginDZ();
		
		int jump=0;//1 跳转向1080p
		int index_start=0;
		int total_page=1;
		String sort = "ustv";// 高清电影区分类 1080p 720p yunpan remux hdhj hdjilu 3d  atv ustv 
		ArrayList<String> re_link = new ArrayList<String>();
		re_link = null;
		int score = 1;//电影分数
		String weburl="http://www.49lou.com/";
		int diff_db=0;//0 电影; 1 电视剧分区; 2 小容量电影分区 
		/**
		 * 控制转向分支
		 */
		@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in);
		System.out.println("输入要求的资源列表："); 
		System.out.println("高清电影区： 1:1080p; 2:720p; 3:yunpan; 4:remux; 5:高清合集; 6:纪录片; 7:3D电影");
		System.out.println("电视剧区：8:完结版电视剧下载; 9:美剧下载");
		System.out.println("小容量电影：10:BD; 11:IPAD; 12:迅雷mkv; 13:亚洲电影下载; 14:欧美电影下载;");
		System.out.println("综艺节目：15:韩国; 16:日本; 17:华语; 18:欧美; 19:演唱会/MV/体育;20：动漫卡团;");
		jump = input.nextInt();
		
		switch(jump)
		{
			case 1:sort="1080p";
				System.out.println("1080p抓取开始");
			break;
			case 2:sort="720p";
				System.out.println("720p抓取开始");
			break;
			case 3:sort="yunpan";
				System.out.println("云盘抓取开始");
			break;
			case 4:sort="remux";
				System.out.println("云盘抓取开始");
			break;
			case 5:sort="hdhj";
				System.out.println("云盘抓取开始");
			break;
			case 6:sort="hdjilu";
				System.out.println("云盘抓取开始");
			break;
			case 7:sort="3d";
				System.out.println("云盘抓取开始");
			break;
			case 8:sort="atv";
				System.out.println("电视剧抓取开始");
				   diff_db=1;
			break;
			case 9:sort="ustv";
				System.out.println("美剧抓取开始");
					diff_db=1;
			break;
			case 10:sort="bd";
				System.out.println("BD抓取开始");
					diff_db=2;
			break;
			case 11:sort="ipad";
				System.out.println("IPAD抓取开始");
					diff_db=2;
			break;
			case 12:sort="thund";
				System.out.println("thunder抓取开始");
					diff_db=2;
			break;
			case 13:sort="asia";
				System.out.println("亚洲电影抓取开始");
					diff_db=2;
			break;
			case 14:sort="euro";
				System.out.println("欧洲电影抓取开始");
					diff_db=2;
			break;
			case 15:sort="zysk";
				System.out.println("韩国综艺");
				diff_db=3;
			break;
			case 16:sort="zyjp";
				System.out.println("日本综艺");
				diff_db=3;
				break;
			case 17:sort="zycn";
				System.out.println("华语综艺");
				diff_db=3;
				break;
			case 18:sort="zyeuus";
				System.out.println("欧美综艺");
				diff_db=3;
				break;
			case 19:sort="zyymt";
				System.out.println("演唱会/mv/体育");
				diff_db=3;
				break;
			case 20:sort="zycarton";
				System.out.println("动漫");
				diff_db=3;
				break;
			default:
				System.out.println("没有这个选项");
				return;
		
		}
		System.out.println("输入首页抓取起始链接数目：");
		index_start = input.nextInt();
		System.out.println(">>抓取首页首页跳过连接数"+index_start);
		System.out.println("输入抓取总页数：");
		total_page = input.nextInt();
		System.out.println(">>抓取总页数："+total_page);
		System.out.println("输入起始分数：");
		score = input.nextInt();
		System.out.println(">>起始分数："+total_page);
//		connectionManager
		

		DefaultHttpClient httpclient = new DefaultHttpClient();// 得到httpclient实例

		/* 登录论坛 */
		System.out.println("#################################   开始登录   ############################");
		String login_formhash = loginDZ.getFormhash(domainurl,httpclient);//获取formhash
		if(!loginDZ.logicDz(httpclient,login_formhash)){
			System.out.println("#################################   登录失败   ############################");
			return;
		}
		else
		{
			String content = null;
			JsoupGet getone = new JsoupGet();
			switch(sort)
			{
			/**
			 * 针对不同资源区域转向
			 */
				case "1080p":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("1080p专区抓取：url="+weburl+"forum-57-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-57-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的第一页所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "720p":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("720p专区抓取：url="+weburl+"forum-58-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-58-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0,diff_db );//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "yunpan":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("云盘专区抓取：url="+weburl+"forum-94-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-94-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "remux":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("蓝光专区抓取：url="+weburl+"forum-82-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-82-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "hdhj":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("合集专区抓取：url="+weburl+"forum-87-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-87-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "hdjilu":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("纪录片专区抓取：url="+weburl+"forum-62-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-62-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "3d":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("3d专区抓取：url="+weburl+"forum-71-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-71-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * 电视剧抓取区域
				 */
				case "atv":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-86-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-86-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "ustv":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("美剧专区抓取：url="+weburl+"forum-41-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-41-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * 小容量电影下载
				 */
				case "bd":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-39-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-39-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "ipad":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-61-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-61-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "thund":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-63-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-63-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "asia":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-36-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-36-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "euro":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("完结电视剧专区抓取：url="+weburl+"forum-37-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-37-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				/**
				 * 综艺节目
				 */
				case "zysk":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("韩国综艺抓取：url="+weburl+"forum-44-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-44-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyjp":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("日本综艺节目抓取：url="+weburl+"forum-45-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-45-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zycn":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("华语综艺节目抓取：url="+weburl+"forum-46-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-46-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyeuus":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("欧美综艺节目专区抓取：url="+weburl+"forum-60-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-60-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zyymt":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("演唱会/mv/体育专区抓取：url="+weburl+"forum-42-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-42-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				case "zycarton":
					/**
					 * 转向指定的html分析页面
					 */
					for(int i=total_page;i>=1;i--){//获取剩余所有链表 单次循环抓取一页的链表
						System.out.println("卡通动漫抓取：url="+weburl+"forum-73-"+i+".html");
						content = loginDZ.seizPointWeb(httpclient,weburl+"forum-73-"+i+".html?mobile=no");//定向到指定的html分析页面
						
						if(i == 1)//获取第一个链接列表页面
						{
							//for(int j=3;j<re_link.toArray().length;j++)
							re_link = getone.getDivForum(content, sort,index_start,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=re_link.toArray().length-1;j>=0;j--)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								//content = loginDZ.seizPointWeb(httpclient,weburl+"thread-85971-1-1.html?mobile=yes");
								getone.getDivthread(content, sort, score, weburl, re_link.toArray()[j].toString(),diff_db);
							}
							
						}else//获取剩余所有链表
						{
							re_link = getone.getDivForum(content, sort,0 ,diff_db);//获取板块的所有电影导向链接
							if(re_link.size() == 0)
							{
								System.out.println("所搜链接均已在数据库中存在，不需要继续添加！");
								break;
							}
							for(int j=0;j<re_link.toArray().length;j++)
							{
								System.out.println(re_link.toArray()[j].toString());
								content = loginDZ.seizPointWeb(httpclient,re_link.toArray()[j].toString()+"?mobile=yes");//定向到指定的html分析页面
								getone.getDivthread(content, sort, score, weburl,re_link.toArray()[j].toString(),diff_db);
							}
						}
					}
				break;
				default:
					System.out.println("没有这个资源分类");
				
				
				
				
				
			
			}
			//content = loginDZ.seizPointWeb(httpclient,"http://www.bt49.com/thread-86046-1-1.html");//定向到指定的html分析页面
			//String content = loginDZ.seizPointWeb(httpclient,"http://www.bt49.com/thread-86246-1-1.html");//定向到指定的html分析页面
			//getone = new JsoupGet();
			//getone.getDivthread(content , sort,"http://www.bt49.com/thread-86046-1-1.html?mobile=no");
			//System.out.println(content);
		}
		
		
		/* 开发发贴 
		System.out.println("#################################   开发发贴   ############################");
		String url = null;		//发贴的url
		String message = null;	//发贴的内容
		String subject = null;	//发贴的标题*/
		
		
//		/* 这个就是发贴机了  自己去加吧,可以用线程多个一起发，不用等  其中fid不同而已，可从1开始到50*/
//		for(int i=0;i<1;i++){
//			/* 发贴参数 */
//			url = "http://www.aizili.com/forum.php?mod=post&action=newthread&fid=24&extra=&topicsubmit=yes";
//			message = "最近组建创业团队，搞IT行业，欢迎加入，请留下你的联系方式";
//			subject = "最近组建创业团队，搞IT行业，欢迎加入";
//			login_formhash = loginDZ.getFormhash(url,httpclient);
//			
//			/* 调用发贴方法 四个参数，其中最后一个为form提交的hash值 */
//			HttpResponse response = loginDZ.postMessage(httpclient, url, message, subject, login_formhash);
//			/* 测试看看内容 */
//			System.out.println("#################################   发贴完成后 内容如下   ############################");
//			loginDZ.printHttpGet(response);	//打印发贴后的页面看看
//			
//			System.out.println("#################################   重定向跳转页面   ############################");
//			/* 获取重定向标识码 */
//			int statuscode = response.getStatusLine().getStatusCode();
//			if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
//					|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
//					|| (statuscode == HttpStatus.SC_SEE_OTHER)
//					|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
//				System.out.println("#################################   开始发表回复   ############################");
//				/* 发表回复 */
//				url = loginDZ.redirectHttp(httpclient, response);
//				message = "最好介绍一下自己，我会选择性的加大家的，最好加上联系的QQ之类的";
//				subject = "最近组建创业团队，搞IT行业，欢迎加入";
//				login_formhash = loginDZ.getFormhash(url,httpclient);
//				
//				for(int j=0;j<1;j++){
//					//可以多次对一个帖子回复
//				}
//
//				sleep(10000);//等10秒再回复
//				
//				response = loginDZ.postReMessage(httpclient,url, message, subject, login_formhash);
//	
//				/*打印页面内容*/
//				loginDZ.printHttpGet(response);	
//			}
//		}	
		
		
		/*url = "http://www.aizili.com/thread-264893-1-1.html";
		message = "这个真不敢乱留联系方式，怕！好怕";
		subject = "最近组建创业团队，搞IT行业，欢迎加入";
		login_formhash = loginDZ.getFormhash(url,httpclient);
		for(int i=15;i<20;i++){
			System.out.println(message+" 现在是" + i);
			loginDZ.postReMessage(httpclient,url, message, subject, login_formhash);
			sleep(10000);
		}*/
		
		/* 关闭连接管理器 */
		httpclient.getConnectionManager().shutdown();
	}
}

