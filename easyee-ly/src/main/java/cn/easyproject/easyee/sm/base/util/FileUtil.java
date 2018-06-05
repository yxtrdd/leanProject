package cn.easyproject.easyee.sm.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
	public static void main(String[] args) throws Exception {
		// 上传文件测试
//		String str = uploadFile("http://127.0.0.1:8080/image/image.do", new String[] { "/Users//H__D/Desktop//1.png", "//Users/H__D/Desktop/2.png" });
//		System.out.println(str);
		// 下载文件测试
		String filePath = "http://192.168.28.232:8080/wcpms/files/a.mp3";
        downloadFile(filePath, "D:/yinpin1");
	}
	
	/**
	 * 将多个文件压缩在一起
	 * @param filePaths 被压缩文件列表
	 * @param zipPath	压缩文件路径
	 * @param zipFileName	压缩文件名
	 */
	public static void zip(List<String> filePaths, String zipPath, String zipFileName) {
		// 如果没有需要压缩的文件
		if ((null==filePaths || filePaths.size()==0) || (null==zipFileName || "".equals(zipFileName))) {
			return;
		}
		ZipOutputStream zos = null;
		byte[] buff = new byte[8192];
		int len;
		try {
			// 判断压缩文件保存的路径是否存在，如果不存在，则创建目录
			File zipDir = new File(zipPath);
			if (!zipDir.exists() || !zipDir.isDirectory()) {
				zipDir.mkdirs();
			}
			// 创建压缩文件保存的文件对象
			String zipFilePath = zipDir.getPath() + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
				// SecurityManager securityManager = new SecurityManager();
				// securityManager.checkDelete(zipFilePath);
				// 删除已存在的目标文件
				zipFile.delete();
			}
			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (!file.isFile())
					continue;
				// 压缩文件中的单个文件路径
				BufferedInputStream bis = null;
				try {
					zos.putNextEntry(new ZipEntry(file.getName()));
					bis = new BufferedInputStream(new FileInputStream(file),8192);
					while ((len = bis.read(buff)) > 0) {
						zos.write(buff, 0, len);
					}
					zos.flush();
				} catch (Exception e) { // 异常跳过
					continue;
				} finally {
					try {
						if (null != bis)
							bis.close();
					} catch (Exception e) {}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != zos) {
					zos.closeEntry();
					zos.close();
				}
			} catch (Exception e) {}
		}
	}

	/**
     * 解压文件
     * 
     * @param zipFilePath zip文件全路径
     * @param unzipPath 解压后的文件保存的路径
     */
	public static void unzip(String zipFilePath, String unzipPath) {
		if (zipFilePath == null || "".equals(zipFilePath)) {
			System.err.println("parameter is null.");
			return;
		}
		File target = new File(zipFilePath);
		if (!target.exists()) {
			return;
		}
		// 如果解压路径不合法，默认是zip父目录下
		if(zipFilePath == null || "".equals(zipFilePath)){
			zipFilePath = target.getParentFile().getPath();
		}
		// 创建解压缩文件保存的路径
		File unzipDir = new File(unzipPath);
		if (!unzipDir.exists() || !unzipDir.isDirectory()) {
			unzipDir.mkdirs();
		}
		ZipFile zipFile = null;
		ZipInputStream zis = null;
		try {
			zipFile = new ZipFile(target);
			zis = new ZipInputStream(new FileInputStream(target));
			Enumeration<? extends ZipEntry> emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(unzipDir.getPath() + File.separator  + entry.getName()).mkdirs();
					continue;
				}
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					bis = new BufferedInputStream(zipFile.getInputStream(entry));
					File file = new File(unzipDir.getPath() + File.separator + entry.getName());
					// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
					// 而这个文件所在的目录还没有出现过，所以要建出目录来。
					File parent = file.getParentFile();
					if (parent != null && !parent.exists()) {
						parent.mkdirs();
					}
					bos = new BufferedOutputStream(new FileOutputStream(file), 8192);
					int count;
					byte buff[] = new byte[8192];
					while ((count = bis.read(buff)) != -1) {
						bos.write(buff, 0, count);
					}
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if(null != bos)
							bos.close();
						if(null != bis)
							bis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != zipFile)
					zipFile.close();
				if (null != zis) {
					zis.closeEntry();
					zis.close();
				}
			} catch (IOException e) {
				
			}
		}
	}

	/**
	 * 复制单个文件
	 * @param oldFilePath 原文件路径 如：c:/fqf.txt
	 * @param newFilePath 复制后路径 如：f:/fqf.txt
	 * @return
	 */
	public static void copyFile(String oldFilePath, String newFilePath) {
		if (oldFilePath == null || "".equals(oldFilePath) || newFilePath == null || "".equals(newFilePath)) {
			System.err.println("parameter is invalid.");
			return;
		}
		// 定义缓冲流
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File oldFile = new File(oldFilePath);
			File newFile = new File(newFilePath);
			// 如果源文件不是文件
			if (!oldFile.isFile()) {
				System.err.println("file is not exists.");
				return;
			}
			// 如果源文件路径是否就是目标文件路径
			if(oldFile.getCanonicalPath().equals(newFile.getCanonicalPath())){
				System.err.println("target is self.");
				return;
			}
			// 如果目标文件存在则删除
			if (newFile.exists()) {
				newFile.delete();
			}
			// 如果上级目录不存在则创建
			if(!newFile.getParentFile().exists()){
				newFile.getParentFile().mkdirs();
			}
			newFile.createNewFile();
			int len = 0;
			byte[] buf = new byte[8192];
			bis = new BufferedInputStream(new FileInputStream(oldFile), buf.length);
			bos = new BufferedOutputStream(new FileOutputStream(newFile), buf.length);
			while ((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bos) bos.close();
				if (null != bis) bis.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
	 * 删除文件&文件夹
	 * @param filePath 文件(夹)全路径
	 * @return
	 */
	public static void delete(String filePath) {
		if (null == filePath || "".equals(filePath)) {
			return;
		}
		File target = new File(filePath);
		// 如果文件不存在
		if (!target.exists()) {
			return;
		}
		// 如果是文件夹，递归
		if (target.isDirectory()) {
			File[] files = target.listFiles();
			for (File file : files) {
				delete(file.getPath());
			}
			// 删除自身
			target.delete();
		} else { // 如果是文件
			target.delete();
		}
	}

	/**
	 * 创建文件
	 * @param filePath 文件路径
	 * @param delete  文件存在时是否删除
	 * @return
	 */
    public static File createFile(String filePath, boolean delete) {
        if (null == filePath || "".equals(filePath)) {
            return null;
        }
        File target = null;
        try {

            target = new File(filePath);
            // 如果上级目录不存在则创建
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            // 如果目标文件存在则删除
            if (target.exists()) {
                if (delete) {
                    target.delete();
                    target.createNewFile();
                }
            } else {
                target.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }
	
	/**
     * 下载文件
     * @param urlPath 下载路径
     * @param downloadDir 下载存放目录
     * @return 返回下载文件名
	 * @throws Exception 
     */
	public static String downloadFile(String urlPath, String downloadDir) throws Exception {
		// 定义缓冲流
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 创建解压缩文件保存的路径
			File fileDir = new File(downloadDir);
			if (!fileDir.exists() || !fileDir.isDirectory()) {
				fileDir.mkdirs();
			}
			String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
			String filePath = fileDir.getPath() + File.separatorChar + fileName;
			if (new File(filePath).exists()) {
				String suffix = urlPath.substring(urlPath.lastIndexOf(".") + 1);
				fileName = new Date().getTime() + "." + suffix;
				filePath = fileDir.getPath() + File.separatorChar + fileName;
			}
			// 统一资源
			URL url = new URL(urlPath);
			// http的连接类
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			// 设定请求的方法，默认是GET
//			httpUrlConn.setRequestMethod("POST");
			// 设置字符编码
			httpUrlConn.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpUrlConn.connect();
			// 开始下载，写入本地
			int len = 0;
			byte[] buff = new byte[8192];
			bis = new BufferedInputStream(httpUrlConn.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)), buff.length);
			while ((len = bis.read(buff)) != -1) {
				bos.write(buff, 0, len);
			}
			bos.flush();
			httpUrlConn.disconnect();
			return fileName;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (null != bos) bos.close();
				if (null != bis) bis.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
	 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
	 * 但不够简便；
	 * 
	 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
	 * 4.以输入流的形式获取返回内容 5.关闭输入流
	 * 
	 * 多文件上传的方法
	 * 
	 * @param actionUrl：上传的路径
	 * @param uploadFilePaths：需要上传的文件路径，数组
	 * @return
	 * @throws Exception 
	 */
	public static String uploadFile(String actionUrl, String[] uploadFilePaths) throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		DataOutputStream ds = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			// 统一资源
			URL url = new URL(actionUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置是否向httpUrlConnection输出
			httpURLConnection.setDoOutput(true);
			// Post 请求不能使用缓存
			httpURLConnection.setUseCaches(false);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码连接参数
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 设置请求内容类型
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			// 设置DataOutputStream
			ds = new DataOutputStream(httpURLConnection.getOutputStream());
			for (int i = 0; i < uploadFilePaths.length; i++) {
				String uploadFile = uploadFilePaths[i];
				String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + i + "\";filename=\"" + filename
						+ "\"" + end);
				ds.writeBytes(end);
				FileInputStream fStream = new FileInputStream(uploadFile);
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = -1;
				while ((length = fStream.read(buffer)) != -1) {
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				/* close streams */
				fStream.close();
			}
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			ds.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}

			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				tempLine = null;
				resultBuffer = new StringBuffer();
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return resultBuffer.toString();
	}
	
	public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }  

}
