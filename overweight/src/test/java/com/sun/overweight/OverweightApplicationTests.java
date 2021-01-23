package com.sun.overweight;
import javax.xml.namespace.QName;

import com.sun.overweight.utils.DateUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests {

    @Test
    public static void main(String[] args) throws Exception {
        try {
            //获取当前时间戳
            String oatime = DateUtil.formatDefaultTimeStr2(new Date());
            System.out.println(oatime);
            String originUrl = "http://10.20.25.228:7005/cas/login?service=http%3A%2F%2F10.20.25.228%3A7001%2Ftcmp%2Fbpm%2Fclient%2Fmw%2Fopen?taskId=20040278";
            String shu = "|";
            String uid = "admin";
            String otype = "fsk";
            String code= stringToMD5(otype+shu+uid+shu+oatime+shu+otype).toUpperCase();
            String newUrl = originUrl+"&uid="+uid+"&code="+code+"&oatime="+oatime+"&otype="+otype;
            System.out.println(newUrl);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }
    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * webservice请求
     *
     * @param menthname 方法名
     * @param xmldata   请求xml报文
     * @param url       webservice地址
     * @return
     * @throws Exception
     */
    public static String transferXmlDataByUrl(String menthname, String xmldata, String url) throws Exception {
//        JaxWsDynamicClientFactory dcflient = JaxWsDynamicClientFactory.newInstance();
        String result = null;
//        Client client = dcflient.createClient("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl");
//        Client client = new Client(new URL(url));
//        Object[] results = client.invoke(menthname, new Object[]{xmldata});
//        result = results[0].toString();
        System.out.println("result=" + result);
        return result;
    }



}
