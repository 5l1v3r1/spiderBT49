package server;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  

public class GetSysTime {
	static Date dt=new Date();//�������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
	static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//������ʾ��ʽ
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
		**���Ӿ������ר��
		*/
		else if(sort.equals(new String("atv")))
		{
			return 3200;
		}else if(sort.equals(new String("ustv")))
		{
			return 3201;
		}
		/**
		**С������Ӱ
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
		**����
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
			* ���ս�Ŀ
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
			}else if(type.contains("����"))
			{
				return 4;
			}else if(type.contains("MV"))
			{
				return 5;
			}else if(type.contains("����"))
			{
				return 6;
			}else if(type.contains("����"))
			{
				return 7; 
			}else if(type.contains("MTV"))
			{
				return 8; 
			}else if(type.contains("�ݳ���"))
			{
				return 9; 
			}else if(type.contains("����"))
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
			*�����Ӿ�
			**/
			if(type.contains("��½"))
			{
				return 3501;
			}else if(type.contains("��̨"))
			{
				return 3502;
			}else if(type.contains("ŷ��"))
			{
				return 3503;
			}else if(type.contains("�վ�"))
			{
				return 3504;
			}else if(type.contains("����"))
			{
				return 3505;
			}else if(type.contains("�ּ�"))
			{
				return 3507;
			}else if(type.contains("����"))
			{
				return 3508; 
			}else if(type.contains("���"))
			{
				return 3509; 
			}else
			{
				return 3506;
			}
		default:
			/**
			*�����Ӱ С������Ӱ
			**/
			if(type.contains("ð��"))
			{
				return 1501;
			}else if(type.contains("����"))
			{
				return 1502;
			}else if(type.contains("����"))
			{
				return 1503;
			}else if(type.contains("����"))
			{
				return 1504;
			}else if(type.contains("��ͥ"))
			{
				return 1505;
			}else if(type.contains("����"))
			{
				return 1506;
			}else if(type.contains("���"))
			{
				return 1507;
			}else if(type.contains("�ֲ�"))
			{
				return 1508;
			}
			else if(type.contains("����"))
			{
				return 1509;
			}
			else if(type.contains("�ƻ�"))
			{
				return 1510;
			}
			else if(type.contains("����"))
			{
				return 1511;
			}
			else if(type.contains("ս��"))
			{
				return 1512;
			}
			else if(type.contains("����"))
			{
				return 1513;
			}
			else if(type.contains("ϲ��"))
			{
				return 1514;
			}
			else if(type.contains("���"))
			{
				return 1515;
			}
			else if(type.contains("����"))
			{
				return 1516;
			}
			else if(type.contains("�ϼ�"))
			{
				return 1517;
			}
			else if(type.contains("����"))
			{
				return 1518;
			}
			else if(type.contains("���Ӿ�"))
			{
				return 1519;
			}
			else if(type.contains("���Ҹ�ʽ"))
			{
				return 1521;
			}
			else if(type.contains("���¸�ʽ"))
			{
				return 1522;
			}
			else if(type.contains("������ʽ"))
			{
				return 1523;
			}
			else if(type.contains("�����ʽ"))
			{
				return 1524;
			}
			else if(type.contains("���̸�ʽ"))
			{
				return 1525;
			}
			else if(type.contains("�̺��ʽ"))
			{
				return 1526;
			}
			else if(type.contains("3D"))
			{
				return 1527;
			}
			else if(type.contains("δ֪ԭ��"))
			{
				return 1528;
			}
			else if(type.contains("����"))
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
