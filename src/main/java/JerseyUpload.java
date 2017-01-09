
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by fengchong on 2016/12/13.
 */
public class JerseyUpload {
    public static void main(String[] args) throws IOException {

        //1:获得上传文件路径
        String filePath = "/Users/fengchong/works/Intellij/fy/FYCenter/web/imgs/bill.jpeg";
        //2:获得文件扩展名
        String fileExt = FilenameUtils.getExtension(filePath);
        //3:生成文件名
        String format = df.format(new Date());
        StringBuilder sb_file = new StringBuilder(format);
        Random r = new Random();
        for (int i = 0; i < 3; i++)
            sb_file.append(r.nextInt(10));
        //4:图片保存地址
        String url = "http://114.215.101.20:8080/img-server/uploads/" + sb_file.toString() + "." + fileExt;

        Client client = new Client();
        //5:设置请求路径
        WebResource resource = client.resource(url);

        //6:发送图片到tomcat服务器
        String rep = "";
        try {
            byte[] buf = FileUtils.readFileToByteArray(new File(filePath));
            //PUT方式向指定目录上传附加文件
            rep = resource.put(String.class, buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rep);
    }


    /**
     * 以下为一个快捷的图片上传函数，可以集成到其他项目中用来快速上传图片到图片服务器
     */
    public final static String IMG_SERVER_URL = "http://114.215.101.20:8080/img-server/uploads/";
    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 一个快捷图片上传函数
     * @param imgbuf    图片的字节数组形式
     * @param fn        图片名字
     * @return          返回上传后的的图片名字
     */
    public static String uploadImg2ImgServer(byte[] imgbuf,String fn){
        String fileExt = FilenameUtils.getExtension(fn);                                                                //文件扩展名
        //生成文件名
        String format = df.format(new Date());
        StringBuilder sb_file = new StringBuilder(format);
        Random r = new Random();
        for (int i = 0; i < 3; i++)
            sb_file.append(r.nextInt(10));
        String fileName = sb_file.toString()+"."+fileExt;
        //4:图片保存地址
        String url = IMG_SERVER_URL + fileName;

        Client client = new Client();
        //5:设置请求路径
        WebResource resource = client.resource(url);

        //6:发送图片到tomcat服务器
        String rep = "";
        try {
            //PUT方式向指定目录上传附加文件
            rep = resource.put(String.class, imgbuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
