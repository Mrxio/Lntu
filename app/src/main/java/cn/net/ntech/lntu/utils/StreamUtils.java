package cn.net.ntech.lntu.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NTECHER on 2016/2/28.
 */
public class StreamUtils {
    //将输入流读取成字符串
    public static String streamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //获取流里的数据
        int len = 0;
        byte[] buffer = new byte[1024];

        while((len = inputStream.read(buffer)) != -1){
            out.write(buffer,0,len);
        }

        String reasult = out.toString();
        inputStream.close();
        out.close();
        return reasult;
    }
}
