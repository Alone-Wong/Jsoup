package com.meritit.customize.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * 
 *  FastDFS分布式文件系统操作客户端 .
 */
public class FastDFSClient {

//	private static final String CONF_FILENAME = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "fdfs_client.conf";
//	private static final String CONF_FILENAME = "fdfs_client.conf";
	private static StorageClient1 storageClient1 = null;

	private static Logger logger = Logger.getLogger(FastDFSClient.class);

	static Properties prop = null;
	/**
	 * 只加载一次.
	 */
	static {
		try {
//			logger.info("=== CONF_FILENAME:" + CONF_FILENAME);
			prop = new Properties();
			prop.load(FastDFSClient.class.getClassLoader().getResourceAsStream("fdfs_client.conf"));
			String connect_timeout = prop.getProperty("connect_timeout");
			String network_timeout = prop.getProperty("network_timeout");
			String charset = prop.getProperty("charset");
			String tracker_http_port = prop.getProperty("http.tracker_http_port");
			String anti_steal_token = prop.getProperty("http.anti_steal_token");
			String secret_key = prop.getProperty("http.secret_key");
			String tracker_server = prop.getProperty("tracker_server");
			ClientGlobal.init(connect_timeout, network_timeout, charset, tracker_http_port, anti_steal_token, secret_key, tracker_server);
			TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if (trackerServer == null) {
				logger.error("getConnection return null");
			}
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if (storageServer == null) {
				logger.error("getStoreStorage return null");
			}
			if(storageClient1 == null){
				storageClient1 = new StorageClient1(trackerServer, storageServer);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 */
	public static String uploadFile(File file, String fileName) {
		FileInputStream fis = null;
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			fis = new FileInputStream(file);
			byte[] file_buff = null;
			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}

			String fileid = storageClient1.upload_file1(file_buff, getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}finally{
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param is
	 * 			输入流
	 * @param fileName
	 * 			文件名
	 * @return
	 * 			返回null为失败
	 */
	public static String uploadFile(InputStream is, String fileName) {
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buff = new byte[1024];
			while((len = is.read(buff)) != -1){
				baos.write(buff, 0, len);
			}

			String fileid = storageClient1.upload_file1(baos.toByteArray(), getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}finally{
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param url
	 * 			上传文件的url连接
	 * @return
	 */
	public static String uploadFile(URL url) {
		URLConnection conn = null;
		InputStream is = null;
		try {
			conn = FileUtils.getConn(url);
			is = conn.getInputStream();
			String path = url.getFile();
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			return  uploadFile(is, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

	/**
	 * 根据组名和远程文件名来删除一个文件
	 * 
	 * @param groupName
	 *            例如 "group1" 如果不指定该值，默认为group1
	 * @param fileName
	 *            例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static int deleteFile(String groupName, String fileName) {
		try {
			int result = storageClient1.delete_file(groupName == null ? "group1" : groupName, fileName);
			return result;
		} catch (Exception ex) {
			logger.error(ex);
			return 0;
		}
	}

	/**
	 * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
	 * 
	 * @param fileId
	 *            file_id源码中的解释file_id the file id(including group name and filename);例如 group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static int deleteFile(String fileId) {
		try {
			int result = storageClient1.delete_file1(fileId);
			return result;
		} catch (Exception ex) {
			logger.error(ex);
			return 0;
		}
	}

	/**
	 * 修改一个已经存在的文件
	 * 
	 * @param oldFileId
	 *            原来旧文件的fileId, file_id源码中的解释file_id the file id(including group name and filename);例如 group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @param file
	 *            新文件
	 * @param filePath
	 *            新文件路径
	 * @return 返回空则为失败
	 */
	public static String modifyFile(String oldFileId, File file, String filePath) {
		String fileid = null;
		try {
			// 先上传
			fileid = uploadFile(file, filePath);
			if (fileid == null) {
				return null;
			}
			// 再删除
			int delResult = deleteFile(oldFileId);
			if (delResult != 0) {
				return null;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
		return fileid;
	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 * @return 返回一个流
	 */
	public static InputStream downloadFile(String fileId) {
		try {
			byte[] bytes = storageClient1.download_file1(fileId);
			InputStream inputStream = new ByteArrayInputStream(bytes);
			return inputStream;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
	}

	/**
	 * 获取文件后缀名（不带点）.
	 * 
	 * @return 如："jpg" or "".
	 */
	private static String getFileExt(String fileName) {
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
		}
	}
	
	public static void main(String args[])throws Exception{
//		String fileid = FastDFSClient.uploadFile("http://ecp.sgcc.com.cn/structData/900000000000048168990000000000089141.zip");
//		System.out.println(fileid);
		InputStream is = FastDFSClient.downloadFile("group1/M00/00/4B/v6gBI1it5YiAU0bRAAAAAAAAAAA882.doc");
		OutputStream out = new FileOutputStream(new File("E://wenjian.doc"));
		int len = 0;
		byte[] buff = new byte[1024];
		while((len = is.read(buff)) != -1){
			out.write(buff, 0, len);
		}
		out.flush();
		out.close();
	}
}
