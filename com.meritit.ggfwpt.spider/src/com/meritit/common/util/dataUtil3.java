package com.meritit.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

/**
 * hml类
 * 
 * @author viki
 *
 */
public class dataUtil3 {
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			} else {
				return false;
			}
		} catch (Exception e) {
		} finally {
			file = null;

		}
		return false;
	}

	public static void main(String args[]) {
		String urls = "http://www.sysinet.gov.cn/web/tjnianjian/2016sy/Mulu.htm";
		Document doc = null;

		try {
			doc = Jsoup.parse(new URL(urls).openStream(), "GBK", urls);
		} catch (MalformedURLException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		// System.out.println(doc);
		Elements links = doc.getAllElements();
		List<String> cNames = new ArrayList<String>();

		Elements ids = links.select("span[lang]");
		for (Element element : ids) {

			if (element.text().length() > 1) {
				String x = element.text();
				String tit = x.substring(x.indexOf(" ") + 1);
				if (tit.equals("饮业") || tit.contains("2016")) {
					continue;
				}
				if (!cNames.contains(tit)) {
					cNames.add(tit);

				}
			}

		}
		// String[]
		// ux={"01%E7%BB%BC%E5%90%88/1%E7%BB%BC%E5%90%88","02%E6%A0%B8%E7%AE%97/2%E6%A0%B8%E7%AE%97","03%E5%86%9C%E4%B8%9A/3%E5%86%9C%E4%B8%9A","04%E5%B7%A5%E4%B8%9A/4%E5%B7%A5%E4%B8%9A","05%E6%8A%95%E8%B5%84/5%E6%8A%95%E8%B5%84","06%E8%B4%B8%E6%98%93/6%E8%B4%B8%E6%98%93","07%E5%A4%96%E7%BB%8F/7%E5%A4%96%E7%BB%8F","08%E4%BA%A4%E9%80%9A%E8%BF%90%E8%BE%93%E5%8F%8A%E9%82%AE%E7%94%B5/8%E4%BA%A4%E9%80%9A%E9%82%AE%E7%94%B5%E8%BF%90%E8%BE%931","09%E8%B4%A2%E9%87%91/9%E8%B4%A2%E9%87%91","10%E7%89%A9%E4%BB%B7/10%E7%89%A9%E4%BB%B7","11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB/11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB","12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84/12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84","13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8/13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8","14%E4%BA%BA%E5%8F%A3/14%E4%BA%BA%E5%8F%A3","15%E7%A4%BE%E4%BC%9A/15%E7%A4%BE%E4%BC%9A","16%E5%8C%BA%E5%8E%BF/16%E5%8C%BA%E5%8E%BF"};
		String[] ux2 = { "01%E7%BB%BC%E5%90%88/01%E7%BB%BC%E5%90%88", "02%E6%A0%B8%E7%AE%97/02%E6%A0%B8%E7%AE%97",
				"03%E5%86%9C%E4%B8%9A/03%E5%86%9C%E4%B8%9A", "04%E5%B7%A5%E4%B8%9A/04%E5%B7%A5%E4%B8%9A",
				"05%E6%8A%95%E8%B5%84/05%E6%8A%95%E8%B5%84", "06%E8%B4%B8%E6%98%93/06%E8%B4%B8%E6%98%93",
				"07%E5%A4%96%E7%BB%8F/07%E5%A4%96%E7%BB%8F",
				"08%E4%BA%A4%E9%80%9A%E8%BF%90%E8%BE%93%E5%8F%8A%E9%82%AE%E7%94%B5/08%E4%BA%A4%E9%80%9A%E8%BF%90%E8%BE%93%E5%8F%8A%E9%82%AE%E7%94%B5",
				"09%E8%B4%A2%E9%87%91/09%E8%B4%A2%E9%87%91", "10%E7%89%A9%E4%BB%B7/10%E7%89%A9%E4%BB%B7",
				"11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB/11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB",
				"12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84/12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84",
				"13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8/13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8",
				"14%E4%BA%BA%E5%8F%A3/14%E4%BA%BA%E5%8F%A3", "15%E7%A4%BE%E4%BC%9A/15%E7%A4%BE%E4%BC%9A",
				"16%E5%8C%BA%E5%8E%BF/16%E5%8C%BA%E5%8E%BF" };
		String[] ux = { "01%E7%BB%BC%E5%90%88/01%E7%BB%BC%E5%90%88", "02%E6%A0%B8%E7%AE%97/02%E6%A0%B8%E7%AE%97",
				"03%E5%86%9C%E4%B8%9A/03%E5%86%9C%E4%B8%9A", "04%E5%B7%A5%E4%B8%9A/04%E5%B7%A5%E4%B8%9A",
				"05%E6%8A%95%E8%B5%84/05%E6%8A%95%E8%B5%84", "06%E8%B4%B8%E6%98%93/06%E8%B4%B8%E6%98%93",
				"07%E5%A4%96%E7%BB%8F/07%E5%A4%96%E7%BB%8F",
				"08%E4%BA%A4%E9%80%9A%E8%BF%90%E8%BE%93%E5%8F%8A%E9%82%AE%E7%94%B5/08%E4%BA%A4%E9%80%9A%E8%BF%90%E8%BE%93%E5%8F%8A%E9%82%AE%E7%94%B5",
				"09%E8%B4%A2%E9%87%91/09%E8%B4%A2%E9%87%91", "10%E7%89%A9%E4%BB%B7/10%E7%89%A9%E4%BB%B7",
				"11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB/11%E4%BA%BA%E6%B0%91%E7%94%9F%E6%B4%BB",
				"12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84/12%E5%8D%95%E4%BD%8D%E4%BB%8E%E4%B8%9A%E4%BA%BA%E5%91%98%E5%8F%8A%E5%B7%A5%E8%B5%84",
				"13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8/13%E5%9F%8E%E5%B8%82%E5%85%AC%E7%94%A8",
				"14%E4%BA%BA%E5%8F%A3/14%E4%BA%BA%E5%8F%A3", "15%E7%A4%BE%E4%BC%9A/15%E7%A4%BE%E4%BC%9A",
				"16%E5%8C%BA%E5%8E%BF/16%E5%8C%BA%E5%8E%BF" };

		List<String> linklist = new ArrayList<String>();
		Elements elements = links.select("a[href]");
		int i = 0;
		for (Element element : elements) {

			String urll = element.attr("href");
			String para = "http://www.sysinet.gov.cn/web/tjnianjian/2012sy/" + ux2[i];
			String para2 = "http://www.sysinet.gov.cn/web/tjnianjian/2012sy/" + ux[i];
			String cName = cNames.get(i++);
			String url = para + ".htm";

			// 解析每一页
			Document doc2 = null;

			System.out.println(url);
			System.out.println(cName);

			try {
				doc2 = Jsoup.parse(new URL(url).openStream(), "gbk", url);
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			Elements links2 = doc2.getAllElements();
			Elements elements2 = links2.select("A[HREF]");
			String name1 = null;
			int num = 1;
			int num2 = 1;
			String real = null;
			for (Element element2 : elements2) {
				System.out.println(element2.attr("HREF"));
				if (element2.attr("HREF").contains("08交通")) {
					real = para2 + element2.attr("HREF").substring(9);
				} else if (element2.attr("HREF").contains("11人民生活") || element2.attr("HREF").contains("13城市公用")) {
					real = para2 + element2.attr("HREF").substring(6);
				} else if (element2.attr("HREF").contains("12从业")) {
					real = para2 + element2.attr("HREF").substring(9);
				}

				else {
					real = para2 + element2.attr("HREF").substring(4);
				}
				System.out.println(real);
				String name = element2.text();
				if (!name.contains("-")) {
					name = name1 + "续表" + num2;
					num2++;
				} else {
					if (num < 10) {
						name = name.substring(name.indexOf("-") + 2);
					} else {
						name = name.substring(name.indexOf("-") + 3);
					}
					name1 = name;
					num2 = 1;
					System.out.println(num);
					num++;
				}

				System.out.println(real + "+" + name);
				try {

					URL page = new URL(real);
					URLConnection con = page.openConnection();

					// 创建目录
					String dirName = "C:\\Users\\merit\\Desktop\\沈阳市\\统计年鉴\\2012\\" + cName;

					if (mkDirectory(dirName)) {
						System.out.println(dirName + "建立完毕");
					}

					// 创建文件
					File file = new File(dirName + "\\" + name + ".xls");
					FileOutputStream out = new FileOutputStream(file, false);
					InputStream ins = con.getInputStream();
					byte[] b = new byte[1024];
					int j = 0;
					while ((j = ins.read(b)) != -1) {
						out.write(b, 0, j);
					}
					ins.close();

					out.close();
				} catch (IOException e1) {
					System.out.println("404:" + real);
					e1.printStackTrace();
					// continue;
				}

			}

		}

		//
		//
		// int num=0;
		// String title=null;
		// int t=0;
		// for (Element e : links) {
		// if(num>0){
		// break;
		// }
		// //Elements elements = e.select("li").select("a[href]");
		//
		// Elements elements = e.select("a[href]");
		// for (Element element : elements) {
		//
		// System.out.println(element.attr("href"));

		// String x=element.attr("href");
		// // title=cNames.get(t);
		// if(x.contains("-1.htm")){
		//
		// String str=cNames.get(t++);
		// title=str;
		// System.out.println("title"+title);
		// }
		//// http://www.stats-hlheb.gov.cn/hrb/zhonghe/nj2013/data2013/1/1-3.htm
		// if(x.contains("data2012/")&&x.contains(".htm")){
		// //http://www.stats-hlheb.gov.cn/nj2017/
		// String href = "http://www.stats-hlheb.gov.cn/hrb/zhonghe/nj2012/"+x;
		//
		// //文件名
		// String str = element.text();
		// String name = str.substring(str.indexOf(" ") + 1);
		// if(name.contains("主要统计指标解释")){
		// continue;
		// }
		// //文件夹名
		// String cName = title;
		////
		//// //处理续表覆盖
		//// if(name.contains("续表")){
		//// name=str;
		//// System.out.println(name);
		//// }
		//
		//
		//
		// try {
		//
		// URL url = new URL(href);
		// URLConnection con= url.openConnection();
		//
		//
		// // 创建目录
		// String dirName =
		// "C:\\Users\\merit\\Desktop\\哈尔滨\\统计年鉴\\2012\\"+cName;
		//
		// if (mkDirectory(dirName)) {
		// System.out.println(dirName + "建立完毕");
		// }
		//
		// // 创建文件
		// File file = new File(dirName+"\\"+name+ ".xls");
		// FileOutputStream out = new FileOutputStream(file,false);
		// InputStream ins = con.getInputStream();
		// byte[] b = new byte[1024];
		// int i = 0;
		// while ((i = ins.read(b)) != -1) {
		// out.write(b, 0, i);
		// }
		// ins.close();
		//
		// out.close();
		// } catch (IOException e1) {
		// System.out.println("404:"+href);
		// e1.printStackTrace();
		// //continue;
		// }
		//
		//
		//
		// }
		//
		// }
		// num++;
		// }
		//

	}
}
