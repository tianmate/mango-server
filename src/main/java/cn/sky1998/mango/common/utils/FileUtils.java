package cn.sky1998.mango.common.utils;


import cn.sky1998.mango.common.exception.CustomException;
import cn.sky1998.mango.common.utils.uuid.UUID;
import cn.sky1998.mango.framework.config.MangoConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件工具类
 * @author tcy@1753163342@qq.com
 * @Date 17-12-2021
 */

@Component
public class FileUtils {

    @Autowired
    private MangoConfig mangoConfig;

    /**
     * 文件上传到腾讯云oss
     */
    public void upload(MultipartFile file) {
        File toFile = null;
        String originalFilename = file.getOriginalFilename();
        InputStream ins = null;
        try {
            ins = file.getInputStream();
        } catch (IOException e) {
            throw new CustomException("上传文件异常");
        }
        toFile = new File(file.getOriginalFilename());
        inputStreamToFile(ins, toFile);
        try {
            ins.close();
        } catch (IOException e) {
            throw new CustomException("上传文件关闭流异常");
        }

        uploadImg(toFile, originalFilename);
    }

    /**
     * 文件上传到服务器
     *
     * @param file
     */
    public String uplodLocal( MultipartFile file) {
        String fileName = file.getOriginalFilename();//获取文件名称
        File targetFile = new File(mangoConfig.getProfile());
        if (!targetFile.exists()) {
            // 判断文件夹是否未空，空则创建
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, fileName);
        try {
            //指定本地存入路径
            file.transferTo(saveFile);
            String path1 = mangoConfig.getProfile() + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
            return "failed";
        }
        return "sucess";
    }


    /**
     * 上传图片到腾讯云OSS
     */
    public void uploadImg(File file,String fileName){
        //  1 初始化用户身份信息（secretId, secretKey）。
        String secretId = mangoConfig.getWxappOssSecreatId();
        String secretKey = mangoConfig.getWxappOssSecreatkey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(mangoConfig.getWxappOssRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定文件将要存放的存储桶
        String bucketName = mangoConfig.getWxappOssBucketName();
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    //获取流文件
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传图片到腾讯云OSS
     */
    public String uploadTencentOss(InputStream file,String fileName) throws IOException {

        //  1 初始化用户身份信息（secretId, secretKey）。
        String secretId = mangoConfig.getWxappOssSecreatId();
        String secretKey = mangoConfig.getWxappOssSecreatkey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(mangoConfig.getWxappOssRegion());
        String baseUrl=mangoConfig.getBaseUrl();
        ClientConfig clientConfig = new ClientConfig(region);

        //将图片的具体信息传入ObjectMetadate类
        ObjectMetadata meta=new ObjectMetadata();
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定文件将要存放的存储桶
        String bucketName = mangoConfig.getWxappOssBucketName();
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,file,meta );

        cosClient.putObject(putObjectRequest);

        cosClient.shutdown();

        return baseUrl+fileName;
    }

    /**
     * 从网络Url上传图片
     * @param urlStr 文件URL地址
     * @throws IOException
     */
    public  String uploadTencentOssFromUrl(String urlStr,String articleUrl){
        //把地址转换成URL对象
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //创建http链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("referer", articleUrl);

        //得到输入流
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //截取链接中的文件名
        String fileName= UUID.fastUUID().toString();
        //请求OSS方法
        String resUrl = null;
        try {
            resUrl = uploadTencentOss(inputStream,fileName+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resUrl;
    }

}