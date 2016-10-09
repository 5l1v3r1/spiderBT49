package server;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  

public class GetSysTime {
	static Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
	static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置显示格式
	static String nowtime = null;
	public static String getTime()
	{
		return nowtime = df.format(dt);
	}
	public static int getSort(String sort)
	{
		/**
		 * yunpan remux hdhj hdjilu 3d
		 **/
		if(sort.equals(new String("1080p")))
		{
			return 1200;
		}else if(sort.equals(new String("720p")))
		{
			return 1201;
		}else if(sort.equals(new String("yunpan")))
		{
			return 1202;
		}else if(sort.equals(new String("remux")))
		{
			return 1203;
		}else if(sort.equals(new String("hdhj")))
		{
			return 1204;
		}
		else if(sort.equals(new String("hdjilu")))
		{
			return 1205;
		}
		else if(sort.equals(new String("3d")))
		{
			return 1206;
		}
		/**
		**电视剧剧下载专区
		*/
		else if(sort.equals(new String("atv")))
		{
			return 3200;
		}else if(sort.equals(new String("ustv")))
		{
			return 3201;
		}
		/**
		**小容量电影
		*/
		else if(sort.equals(new String("bd")))
		{
			return 1300;
		}else if(sort.equals(new String("ipad")))
		{
			return 1301;
		}else if(sort.equals(new String("thund")))
		{
			return 1302;
		}else if(sort.equals(new String("asia")))
		{
			return 1303;
		}else if(sort.equals(new String("euro")))
		{
			return 1304;
		}
		/**
		**综艺
		*/
		else if(sort.equals(new String("zysk")))
		{
			return 100;
		}else if(sort.equals(new String("zyjp")))
		{
			return 101;
		}else if(sort.equals(new String("zycn")))
		{
			return 102;
		}else if(sort.equals(new String("zyeuus")))
		{
			return 103;
		}else if(sort.equals(new String("zyymt")))
		{
			return 104;
		}else if(sort.equals(new String("zycarton")))
		{
			return 105;
		}
		return 0;
	}
	public static int getType(String type,String sort)
	{
		switch (sort)
		{
		case "zysk":
		case "zyjp":
		case "zycn":
		case "zyeuus":
		case "zyymt":
		case "zycarton":
			/**
			* 综艺节目
			**/
			if(type.contains("1080"))
			{
				return 1;
			}else if(type.contains("720"))
			{
				return 2;
			}else if(type.contains("480"))
			{
				return 3;
			}else if(type.contains("漫画"))
			{
				return 4;
			}else if(type.contains("MV"))
			{
				return 5;
			}else if(type.contains("体育"))
			{
				return 6;
			}else if(type.contains("综艺"))
			{
				return 7; 
			}else if(type.contains("MTV"))
			{
				return 8; 
			}else if(type.contains("演唱会"))
			{
				return 9; 
			}else if(type.contains("音乐"))
			{
				return 10; 
			}
			else
			{
				return 11;
			}
		case "atv":
		case "ustv":
			/**
			*完结电视剧
			**/
			if(type.contains("大陆"))
			{
				return 3501;
			}else if(type.contains("港台"))
			{
				return 3502;
			}else if(type.contains("欧美"))
			{
				return 3503;
			}else if(type.contains("日剧"))
			{
				return 3504;
			}else if(type.contains("韩剧"))
			{
				return 3505;
			}else if(type.contains("分集"))
			{
				return 3507;
			}else if(type.contains("连载"))
			{
				return 3508; 
			}else if(type.contains("完结"))
			{
				return 3509; 
			}else
			{
				return 3506;
			}
		default:
			/**
			*高清电影 小容量电影
			**/
			if(type.contains("冒险"))
			{
				return 1501;
			}else if(type.contains("动作"))
			{
				return 1502;
			}else if(type.contains("犯罪"))
			{
				return 1503;
			}else if(type.contains("动画"))
			{
				return 1504;
			}else if(type.contains("家庭"))
			{
				return 1505;
			}else if(type.contains("爱情"))
			{
				return 1506;
			}else if(type.contains("奇幻"))
			{
				return 1507;
			}else if(type.contains("恐怖"))
			{
				return 1508;
			}
			else if(type.contains("剧情"))
			{
				return 1509;
			}
			else if(type.contains("科幻"))
			{
				return 1510;
			}
			else if(type.contains("悬疑"))
			{
				return 1511;
			}
			else if(type.contains("战争"))
			{
				return 1512;
			}
			else if(type.contains("悬念"))
			{
				return 1513;
			}
			else if(type.contains("喜剧"))
			{
				return 1514;
			}
			else if(type.contains("惊悚"))
			{
				return 1515;
			}
			else if(type.contains("传记"))
			{
				return 1516;
			}
			else if(type.contains("合集"))
			{
				return 1517;
			}
			else if(type.contains("综艺"))
			{
				return 1518;
			}
			else if(type.contains("电视剧"))
			{
				return 1519;
			}
			else if(type.contains("左右格式"))
			{
				return 1521;
			}
			else if(type.contains("上下格式"))
			{
				return 1522;
			}
			else if(type.contains("红蓝格式"))
			{
				return 1523;
			}
			else if(type.contains("交错格式"))
			{
				return 1524;
			}
			else if(type.contains("红绿格式"))
			{
				return 1525;
			}
			else if(type.contains("绿红格式"))
			{
				return 1526;
			}
			else if(type.contains("3D"))
			{
				return 1527;
			}
			else if(type.contains("未知原盘"))
			{
				return 1528;
			}
			else if(type.contains("蓝光"))
			{
				return 1529;
			}
			else if(type.contains("REMUX"))
			{
				return 1530;
			}
			else if(type.contains("1080"))
			{
				return 1531;
			}
			else if(type.contains("720"))
			{
				return 1532;
			}else
			{
				return 1520;
			}
			
		
		}
		
	}

}
