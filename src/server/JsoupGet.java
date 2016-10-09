package server;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class JsoupGet {
	
	public boolean writeFile(String c)
	{
		try{
		     // String data = " This content will append to the end of the file";

		      File file =new File("worm.log");

		      //if file doesnt exists, then create it
		      if(!file.exists()){
		       file.createNewFile();
		      }

		      //true = append file
		      FileWriter fileWritter = new FileWriter(file.getName(),true);
	             BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	             bufferWritter.write(c);
	             bufferWritter.close();
		         System.out.println("Done");
		         return true;

	     }catch(IOException e){
	      e.printStackTrace();
	      return false;
	     }

		
	}


	/**
	 * 获取forum57的内容
	 * @throws SQLException 
	 */
	public ArrayList<String> getDivForum(String html ,String sort, int start, int diff_db) throws SQLException
	{
		String reduce_link = null;
		String linkhref = null;
		int flag=1;
		ResultSet rs = null;
		String sql = null;
		Connection conn=Db_op.getConn();
		try {
			if(!conn.isClosed()) 
			    System.out.println("test:repeat Succeeded connecting to the Database!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Db_op.close(conn, null, null);
		}
		Document doc = Jsoup.parse(html);
		//String chtml= doc.html();
		//System.out.print(chtml);
		//String [] get_link=new String[100];
		ArrayList<String> re_link = new ArrayList<String>(); 
		//re_link = null;
		Elements divcontent = doc.select("td.num > a"); //抓取链接信息
		if(divcontent.first() == null)
		{
			System.out.println("获取列表链接失败！");
		}else
		{
			for (Element link : divcontent)
			{
				linkhref = link.attr("href");
				
				if(linkhref != null && !linkhref.isEmpty())
				{
					if(flag<start)
					{
						++flag;
						continue;
					}
					/*
					 * 链接数据库
					 */
					reduce_link = linkhref.replaceAll("^http://www.[a-zA-Z0-9]+.com/thread-", "");
					reduce_link = reduce_link.replace(".html", "");
					
					//main_desc=" 　　　　　　威廉•菲德内尔 William Fichtner 史莱德 ....The Shredder";
					if(diff_db == 0)
					{
						sql= "select 1 from kuying_hdfilm where LINK ="+"'"+reduce_link+"'";
					}else if(diff_db == 1)
					{
						sql= "select 1 from kuying_hdju where LINK ="+"'"+reduce_link+"'";
					}else if(diff_db == 2)
					{
						sql= "select 1 from kuying_hdbd where LINK ="+"'"+reduce_link+"'";
					}else if(diff_db == 3)
					{
						sql= "select 1 from kuying_hdzy where LINK ="+"'"+reduce_link+"'";
					}
					
					rs = Db_op.excuteQuery(conn, sql);
					int rowCount = 0;       
					try 
					{ 
			            rs.last(); 
			            rowCount = rs.getRow();
			            System.out.println("检测是否数据库中已经有"+rowCount);
					} 
					catch (Exception e) 
					{           
					  // TODO: handle exception            
						e.printStackTrace();   
					}
					if(rowCount == 0)
					{
						re_link.add(linkhref);
					}
					//System.out.println(re_link[tid]);
				}	
			}
		}
		Db_op.close(conn, null, null);
		return re_link;
	}
	/**
	 * 获取具体单个film的内容
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean getDivthread(String html ,String sort, int score, String weburl,String url, int diff_db) throws ClientProtocolException, IOException
	{
		Document doc = Jsoup.parse(html);
		//System.out.print(doc.html());
		String type = null;
		String name = null;
		String rel_dir = "../../../../../Public";
		int flag_showdiv_a_null = 0;
		int flag_mes_charge = 0;
		long timetoken = System.currentTimeMillis();//获得时间戳
		/**
		 * 时间戳转换为时间格式
		 */
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    String datef = format.format(timetoken); 
	    System.out.println("time:"+datef);
	    
		Elements  divcontent = doc.select("div.bm_h a"); //抓取电影最顶部题目类型信息
		/**
		 * 抓取题目信息
		 */
		String title = divcontent.first().text();
		name = divcontent.text();
		if(name == null || name.isEmpty() )
		{
			System.out.println("抓取题目出错！！！将跳过该链接下载");
			return false;
		}else
		{
			Pattern pattern = Pattern.compile("\\[.+?\\]");
			Matcher mat = pattern.matcher(title);
			if(mat.find())
			{
				type=mat.group(); //film type
				//System.out.println(type); 
				//System.out.println(name); 
				name=name.replaceFirst("\\[.+?\\]","");//film name 
				
			}
			
		}
		System.out.println("电影类型" + type); 
		System.out.println("电影名称" + name); 
		
		/**
		 * 判断showhide 是否为空
		 */
		//doc.select("div.showhide").first().remove();
		String upload_dir = null;
		//String singleshowhide = null;
		//String showhide = null;
		Element showdiv_exit = doc.select("div.showhide").first();
		if(showdiv_exit == null)
		{
			score = 0;
			//singleshowhide  = "^-^";
			System.out.println("此内容不包含showhide～");
		}
		Elements showhidedivc = doc.select("div.showhide");

		for(Element showhidediv:showhidedivc)
		{
			
			if(showhidediv==null)
			{
				score = 0;
				//singleshowhide  = "^-^";
				//System.out.println("此内容不包含showhide～");
			}else
			{
				/*
				 * 删除其中的图片
				 */
				//divcontent = doc.select("div.showhide img"); // 删除img图片
				Elements imgs= showhidediv.getElementsByTag("img");
				for (Element alink : imgs) 
				{
					if(alink != null)
					{
						alink.remove();
						System.out.println("删除showhide图片完成！");
					}
				}
				/**
				 * 抓取showhide href
				 */
				Element ht = showhidediv.getElementsByTag("h4").first();
				if(ht != null)
				{
					ht.text("-------------下载地址-------------");//修改隐藏帖
				}
				//Elements divhref = doc.select("div.showhide a");
				Elements divhref= showhidediv.getElementsByTag("a");
				//String href = null;
				
				for (Element link : divhref) {
					if(link == null)
					{
						flag_showdiv_a_null = 1;
						score=0;
						continue;
					}
					  String href = link.attr("href");
					  
					  if(href.contains("forum.php") )
					  {
						  	/**
							 * 下载网站本地种子文件并上传
							 * 
							 */
							//String upload_dir = null;
							//int localdown = 0; 
							//long currentTime = System.currentTimeMillis();//获得时间戳
							href = weburl+href;
							String torrent = link.text();//获取种子名称
							torrent=torrent.replaceAll("bt49", "");
							torrent = "[www.kyhd.net]"+torrent; //域名可以修改
							upload_dir = "/upload/"+sort+"/"+torrent;
							//System.out.println(upload_dir);
							//System.out.println(href);
							System.out.println("正在抓取种子文件到本地目录"+href);
							new GetTorrent().getFile(href, "D:/"+upload_dir);//下载文件到指定的目录 !!!!!!change
							link.attr("href",rel_dir+upload_dir);
							link.text(torrent);
							System.out.println("抓取完成"+"__PUBLIC__"+upload_dir);
							//localdown = 1;//表明是本地下载
					  }else if(href.contains("http://"))
					  {
						  System.out.println("showhide文件为http类型链接");
					  }else
					  {
						  System.out.println("showhide中的链接不合法，删除链接");
						  link.remove();
					  }
					  link.prepend("<img src='"+rel_dir+"/img/icon/bt.gif' >");//在a前添加icon内容
					}
				/**
				 * 抓取showhide的主要内容
				 */
				
				divcontent =  showhidediv.getElementsByTag("em");
				for (Element alink : divcontent) 
				{
					
					if(alink != null)
					{
						
						alink.remove();
					}
				}
				System.out.println("删除showhide em info完成！");
				//divcontent = doc.select("div.showhide");
				//doc.select("div.showhide").first().remove();
				//singleshowhide  = showhidediv.outerHtml();
				//showhidediv.remove();
				//doc.select("div.showhide").first().remove();//移出showhide
				//System.out.println(showhide);
				System.out.println("showhide中html内容获取成功！");
			}
			//showhide = showhide+singleshowhide;
		}
		
		
		//String href = divhref.attr("href");
		/*if(href == null || href.isEmpty() )
		{
			return "下载链接抓取失败！";
		}else
		{
			href=href.replaceAll("&mobile=yes", "");
		}*/
		//System.out.println(href);
		/**
		 * 下载网站本地种子文件并上传
		 * 
		 */
		//String upload_dir = null;
		//int localdown = 0; 
		//long currentTime = System.currentTimeMillis();//获得时间戳
		/*if(href.contains("forum.php")) // 如果是本地下载
		{
			href = "http://www.bt49.com/"+href;
			String torrent = divhref.text();//获取种子名称
			torrent = "[www.kuyingweb.com]"+torrent; //域名可以修改
			upload_dir = "/upload/"+sort+"/"+torrent;
			//System.out.println(upload_dir);
			System.out.println(href);
			new GetTorrent().getFile(href, "D:/"+upload_dir);//下载文件到指定的目录 !!!!!!change
			divcontent = doc.select("div.showhide a");
			divcontent.attr("href",upload_dir);
			//localdown = 1;//表明是本地下载
		}*/
		/**
		 * 抓取电影具体信息
		 */
		//doc.select("div.showhide").first().remove();
		//divcontent = doc.select("div.mes");
		//divcontent.first().removeClass("mes");
		System.out.println("开始抓取影片介绍内容。。。");
		//long timetoken = 0;
		Element divcontid = doc.select("div.mes").first();
		if(divcontid!=null)
		{
			String tempstr = divcontid.select("div.postmessage").first().text();
			//System.out.println("postmessage"+tempstr.text());
			if(tempstr.contains("本内容需购买可见"))
			{
				score = 0;
				flag_mes_charge = 1;
			}
			divcontent = divcontid.select("img.vm");//美剧中出现
			for (Element alink : divcontent) 
			{
				if(alink!=null)
				{
					System.out.println("修改mes出现的种子下载图标");
					alink.attr("src",rel_dir+"/img/icon/bt.gif");
				}
			}
			divcontent = divcontid.select("p.xg1 ");//美剧中出现
			for (Element alink : divcontent) 
			{
				if(alink!=null)
				{
					System.out.println("修改mes出现的种子下载次数等信息");
					alink.text("上传时间："+datef);
				}
			}
			divcontent = divcontid.getElementsByTag("a");
			String arrow="->";
			String alinkHref = null;
			System.out.println("开始抓取影片介绍内容中包含的图片/种子");
			for (Element alink : divcontent) {
				  alinkHref = alink.attr("href");
				  //alinkHref = alinkHref.replaceAll("&mobile=yes", "");
				  if(alinkHref.contains("forum.php"))//抓取本地文件
				  {
					  System.out.println("检测到mes中有目标网站本站链接：");
					  if(alink.text().contains(".torrent")||alink.text().contains(".rar")||alink.text().contains(".srt")||alink.text().contains(".zip")||alink.text().contains(".txt"))//抓取本地种子下载
					  {
						    alinkHref = weburl+alinkHref;
							String torrent = alink.text();//获取种子名称
							torrent=torrent.replaceAll("bt49", "");
							torrent = "[www.kyhd.net]"+torrent; //域名可以修改
							upload_dir = "/upload/"+sort+"/"+torrent;
							//System.out.println(upload_dir);
							System.out.println("正在下载mes内容中的种子文件"+alinkHref);
							new GetTorrent().getFile(alinkHref, "D:/"+upload_dir);//下载文件到指定的目录 !!!!!!change
							alink.attr("href",rel_dir+upload_dir);
							alink.text(torrent);
							System.out.println("完成下载mes内容中的种子文件："+rel_dir+upload_dir);
					  } //抓取本地图片下载
					  else
					  {
						  
						 
						  alinkHref = weburl+alinkHref;
						  System.out.println("正在下载mes内容中的图片"+alinkHref);
						  long _timetoken = System.currentTimeMillis();//获得时间戳
						  upload_dir = "/imgshow/"+sort+"/"+_timetoken+".jpg";
						  new GetTorrent().getFile(alinkHref, "D:/"+upload_dir);//下载文件到指定的目录 !!!!!!__PUBLIC__/
						  arrow = arrow +"-s";
						  System.out.print(arrow);
						  alink.attr("href",rel_dir+upload_dir);
						  alink.html("<img src=\""+rel_dir+upload_dir+"\">");
						  System.out.println("完成下载mes内容中的图片："+rel_dir+upload_dir);
						  
					  }
					  
					  //alink.append("<img src=\""+upload_dir+"\">");
					 //System.out.println(alinkHref);
				  }else if(!alinkHref.contains("forum.php")&&(alinkHref.contains(".jpg")||alinkHref.contains(".png")||alinkHref.contains(".gif")))
				  { 
					  alink.html("<img src=\""+alinkHref+"\">");
					  System.out.println("检测到互联网图片，修改内容img链接："+alinkHref);
				  }
				}
			//System.out.println("检测内容mes的图片种子任务complete!");
			divcontent = divcontid.select("div.mes i");
			for (Element alink : divcontent) 
			{
				if(alink!=null)
				{
					alink.remove();
				}
			}
			
			String main_desc = divcontid.outerHtml();
			//main_desc = main_desc +showhide;
			if(main_desc !=null && !main_desc.isEmpty())
			{
				main_desc = main_desc.replaceAll("'", "");
				main_desc = main_desc.replaceAll("`", "");
				name = name.replaceAll("'", "");
				name = name.replaceAll("`", "");
				name = name.replaceAll(";", "；");
				System.out.println("mes中影片内容检测获取工作完成，准备连接数据库。。。");
				//System.out.println(main_desc);
				int sort_num = 0;
				int type_num = 0;
				sort_num = GetSysTime.getSort(sort);
				type_num = GetSysTime.getType(type,sort);
				//System.out.println(type_num);
				//timetoken = System.currentTimeMillis();
				if(flag_showdiv_a_null == 1)
				{
					String file_content= ">>showdiv没有下载链接：\n----ID:"+timetoken+";SORT："+sort_num+";TYPE="+type_num+";DB:"+diff_db+"\n-----论坛链接:"+url+"\n" ;
					if(writeFile(file_content))
					{
						System.out.println("showdiv没有下载链接情况写入日志成功！");
					}else
					{
						System.out.println("showdiv没有下载链接情况写入日志失败！");
					}
				}
				if(flag_mes_charge ==1)
				{
					String file_content= ">>mes没有下载链接：\n----ID:"+timetoken+";SORT："+sort_num+";TYPE="+type_num+";DB:"+diff_db+"\n-----论坛链接:"+url+"\n" ;
					if(writeFile(file_content))
					{
						System.out.println("mes没有下载链接情况写入日志成功！");
					}else
					{
						System.out.println("mes没有下载链接情况写入日志失败！");
					}
				}
				url = url.replaceAll("^http://www.[a-zA-Z0-9]+.com/thread-", "");
				url = url.replace(".html", "");
				System.out.println("link"+url);
				/**
				 * 链接数据库输出相关内容
				 * 
				 */
				Connection conn=Db_op.getConn();
				try {
					if(!conn.isClosed()) 
					    System.out.println("Succeeded connecting to the Database!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//main_desc="  　　　　　　威廉•菲德内尔 William Fichtner 史莱德 ....The Shredder";
				String sql = null;
				long ffid = timetoken + sort_num;
				if(diff_db == 0)
				{
					sql= "INSERT INTO kuying_hdfilm (FID, SORT,LINK,FNAME, TYPE, MES_SHOW_DIV, UPTIME, DOWN_NO, SCAN_NUM, SCORE ,ALDOWN,ORTOP) VALUES('"+ffid+"','" +sort_num+"','"+url+"','"+name+"','"+type_num+"','"+main_desc+"','"+timetoken+ "','0','0','"+score+"','#','0')";
				}else if(diff_db == 1)
				{
					sql= "INSERT INTO kuying_hdju (FID, SORT,LINK,FNAME, TYPE, MES_SHOW_DIV, UPTIME, DOWN_NO, SCAN_NUM, SCORE ,ALDOWN,ORTOP) VALUES('"+ffid+"','" +sort_num+"','"+url+"','"+name+"','"+type_num+"','"+main_desc+"','"+timetoken+ "','0','0','"+score+"','#','0')";
				}else if(diff_db == 2)
				{
					sql= "INSERT INTO kuying_hdbd (FID, SORT,LINK,FNAME, TYPE, MES_SHOW_DIV, UPTIME, DOWN_NO, SCAN_NUM, SCORE ,ALDOWN,ORTOP) VALUES('"+ffid+"','" +sort_num+"','"+url+"','"+name+"','"+type_num+"','"+main_desc+"','"+timetoken+ "','0','0','"+score+"','#','0')";
				}else if(diff_db == 3)
				{
					sql= "INSERT INTO kuying_hdzy (FID, SORT,LINK,FNAME, TYPE, MES_SHOW_DIV, UPTIME, DOWN_NO, SCAN_NUM, SCORE ,ALDOWN,ORTOP) VALUES('"+ffid+"','" +sort_num+"','"+url+"','"+name+"','"+type_num+"','"+main_desc+"','"+timetoken+ "','0','0','"+score+"','#','0')";
				}
				
				Db_op.excute(conn, sql);
				Db_op.close(conn, null, null);
			}else
			{
				System.out.println("mes中影片内容获取失败禁止连接数据库，请查看日志。。。");
			}
			
			//System.out.println(main_desc);
			
			
		}else
		{
			System.out.println("获取mes失败！mes网页中不存在，不会存入数据库，请检查原因！");
			String file_content= ">>mes获取失败：\n----DB:"+diff_db+"\n-----链接:"+url+"\n" ;
			if(writeFile(file_content))
			{
				System.out.println("获取mes失败！写入日志成功！");
			}else
			{
				System.out.println("获取mes失败！写入日志失败！");
			}
			return false;
		}
		
		

		return true;
	}
	

}
