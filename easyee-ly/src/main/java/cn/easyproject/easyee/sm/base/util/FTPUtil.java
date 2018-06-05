package cn.easyproject.easyee.sm.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * description: FTP工具类 <br/>
 * date: 2017年10月16日 上午10:31:08 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 */
public class FTPUtil {

    /* 使用编码方式 */
    public static String USE_CONTROL_ENCODING = "gbk";

    public static FTPClient connect(String host, int port, String username, String password) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            // 连接FTP服务器
            ftpClient.connect(InetAddress.getByName(host), port);
            // 登录FTP服务器
            ftpClient.login(username, password);
            // 设置连接模式，主动&被动，默认主动
            // ftpClient.enterLocalActiveMode();
            ftpClient.enterLocalPassiveMode();
            // 设置文件类型，二进制&文本
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    public static void disconnect(FTPClient ftpClient) throws IOException {
        if (null != ftpClient && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public static boolean uploadBatch(FTPClient ftpClient, List<String> locals) throws IOException {
        boolean bool = true;
        for (String local : locals) {
            File file = new File(local);
            if (file.exists() && file.isFile()) {
                bool = upload(ftpClient, file.getName(), local);
                if (!bool) {
                    break;
                }
            }
        }
        return bool;
    }
    
    public static boolean upload(FTPClient ftpClient, String remote, InputStream is) throws IOException {
        boolean bool = false;
        try {
            bool = ftpClient.storeFile(transcode(remote), is);
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    public static boolean upload(FTPClient ftpClient, String remote, String local) throws IOException {
        boolean bool = false;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(local);
            bool = ftpClient.storeFile(transcode(remote), fis);
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {

            }
        }
        return bool;
    }

    public static boolean download(FTPClient ftpClient, String remote, OutputStream os) throws IOException {
        boolean bool = false;
        try {
            bool = ftpClient.retrieveFile(transcode(remote), os);
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    public static boolean download(FTPClient ftpClient, String remote, String local) throws IOException {
        boolean bool = false;
        FileOutputStream fos = null;
        remote = transcode(remote);
        try {
            if (existFile(ftpClient, remote)) {
                File file = new File(local);
                // 如果上级目录不存在则创建
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                // 如果目标文件存在则删除
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                fos = new FileOutputStream(file);
                bool = ftpClient.retrieveFile(remote, fos);
            } else {
                System.err.println(">>> 文件下载失败：文件不存在，地址：" + remote);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(">>> 文件下载失败：" + e.getMessage() + "，地址：" + remote);
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e) {

            }
        }
        return bool;
    }

    /**
     * 检查文件&文件夹在服务器上是否存在 true：存在 false：不存在
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean existFolder(FTPClient ftpClient, String path) throws IOException {
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(transcode(path));
            return ftpFiles.length > 1 ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean existFile(FTPClient ftpClient, String path) throws IOException {
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(transcode(path));
            return ftpFiles.length == 1 ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 转移到FTP服务器工作目录
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean changeWorkspace(FTPClient ftpClient, String path) throws IOException {
        String path_ = (null == path ? "/" : path);
        ftpClient.makeDirectory(transcode(path));
        return ftpClient.changeWorkingDirectory(transcode(path_));
    }
    public static String getCurrentWorkspace(FTPClient ftpClient) throws IOException {
        return new String(ftpClient.printWorkingDirectory().getBytes(FTP.DEFAULT_CONTROL_ENCODING), USE_CONTROL_ENCODING);
    }

    /**
     * 在服务器上创建目录
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean createFolder(FTPClient ftpClient, String path) throws IOException {
        boolean bool = true;
        path = path.replace("\\", "/");
        StringTokenizer st = new StringTokenizer(path, "/");
        String _path = "";
        while (st.hasMoreElements()) {
            _path += "/" + transcode(st.nextToken());
            if (!existFolder(ftpClient, _path)) {
                bool = ftpClient.makeDirectory(_path);
            }
        }
        return bool;
    }

    /**
     * 在服务器上删除目录
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean removeFolder(FTPClient ftpClient, String path) throws IOException {
        path = transcode(path);
        boolean bool = true;
        if (existFolder(ftpClient, path)) {
            FTPFile[] ftpFiles = ftpClient.listFiles(path);
            for (FTPFile ftpFile : ftpFiles) {
                String _path = path + "/" + ftpFile.getName();
                if (ftpFile.isDirectory() && !ftpFile.getName().matches("^[.]+$")) { // 排除.和..文件夹
                    // 文件夹，递归删除
                    bool = removeFolder(ftpClient, _path);
                    if (!bool) {
                        break;
                    }
                } else if (ftpFile.isFile()) {
                    // 文件，直接删除
                    bool = ftpClient.deleteFile(_path);
                    if (!bool) {
                        break;
                    }
                } else if (ftpFile.isSymbolicLink()) {
                    // 链接
                } else if (ftpFile.isUnknown()) {
                    // 未知
                } else {
                    // 其他
                }
            }
            bool = ftpClient.removeDirectory(path);
        }
        return bool;
    }

    public static boolean removeFile(FTPClient ftpClient, String path) throws IOException {
        path = transcode(path);
        boolean bool = true;
        if (existFile(ftpClient, path)) {
            bool = ftpClient.deleteFile(path);
        }
        return bool;
    }
    
    /**
     * 中文转码
     */
    private static String transcode(String s) throws IOException {
        if (null != s) {
            Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                return new String(s.getBytes(USE_CONTROL_ENCODING), FTP.DEFAULT_CONTROL_ENCODING);
            } else {
                return s;
            }
        } else {
            throw new NullPointerException();
        }
    }
    
    /**
     * 获取默认配置的ftp客户端
     */
    public static FTPClient getDefaultFTPClient() {
        String host = PropertiesUtil.getProperty("ftp.host");
        int port = Integer.valueOf(PropertiesUtil.getProperty("ftp.port"));
        String username = PropertiesUtil.getProperty("ftp.username");
        String password = PropertiesUtil.getProperty("ftp.password");
        FTPClient ftpClient = null;
        try {
            ftpClient = FTPUtil.connect(host, port, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

}
