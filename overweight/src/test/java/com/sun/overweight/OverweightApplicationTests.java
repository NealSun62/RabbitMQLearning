package com.sun.overweight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.time.LocalDate;
import java.util.*;

import static com.sun.overweight.common.utils.DateUtil.*;

/**
 * @param
 * @author neal
 * @return
 * @date 2020/01/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests {
    @Test
    public static void main(String[] args) throws Exception {
        try {

//            int times = 100;
//            for (int i = 0; i < times; i++) {
//                System.out.println(uuid());
//            }
//            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                    .setNameFormat("queryInfoMngrPrsn-pool-%d").build();
//            ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 100L, TimeUnit.MILLISECONDS,
//                    new LinkedBlockingQueue<Runnable>(10), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("后台开始计算评级历史结果并落地");
//                    Date now = new Date();
//                    try {
//                        Thread.sleep(10000);
//                        System.out.println("休息结束");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    SimpleDateFormat sfymd = new SimpleDateFormat("yyyyMMdd");
//                    String nowDayYmd = sfymd.format(now);
//                    System.out.println("结束");
//                }
//            });
//            Var03Test var03Test1 = new Var03Test() ;
//            Thread thread1 = new Thread(var03Test1) ;
//            thread1.start();
//            Thread thread2 = new Thread(var03Test1) ;
//            thread2.start();
//            Thread thread3 = new Thread(var03Test1) ;
//            thread3.start();

//            List<Map<String,Object>> list = new ArrayList<>();
//            List<Map<String,Object>> needUpdateStatuList= new ArrayList<>();
//            Map<String,List<Map<String,Object>>> needUpdateStatuMap =new HashMap<>(needUpdateStatuList.size());
//            needUpdateStatuMap.put(String.valueOf("SCR_NUM"), list);
//            Map<String,Object> oldItem=(Map<String, Object>) needUpdateStatuMap.get("SCR_NUM");
//            System.out.println(oldItem);
           Set aSet = new HashSet();
           aSet.add(1);
           aSet.add("ad");
           Set bSet = new HashSet();
           bSet.add(2);
           bSet.add(1);
            aSet.addAll(bSet);
            System.out.println(aSet.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }
    /**
     * 返回昨天
     * @param today
     * @return
     */
    public static Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 30);
        return calendar.getTime();
    }
    static class Var03Test implements Runnable {
        private Integer count = 0;

        public void countAdd() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            synchronized(this) {
            count++;
            System.out.println("count=" + count);
//            }
        }

        @Override
        public void run() {
            countAdd();
        }
    }

    /**
     * 两个日期之间的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static long betweenDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return 0;
        }
        LocalDate begin = dateToLocalDate(beginDate);
        LocalDate end = dateToLocalDate(endDate);
        return end.toEpochDay() - begin.toEpochDay();
    }

    public static long betweenDays(Integer beginDate, Integer endDate) {
        Date begin = parseDateStrInt(beginDate);
        Date end = parseDateStrInt(endDate);
        return betweenDays(begin, end);
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

    <process xmlns='http://jbpm.org/4.4/jpdl' name='舆情点评' key='platform_BIZ_yqdp_20201014085449'>
  <start name='start' isProcessTemplate='false' g='50,100,48,48'>
    <transition name='to 人工 1' to='发起'/>
  </start>
  <end name='end' isProcessTemplate='false' g='500,102,48,48'/>
  <human wfCompanyId='BIZ' enableRemind='false' canNotice='false' canReassign='false' canCoordinate='false' form='cf572edb08e44ef1a4111a9b14ada998' formName='舆情点评' message-notice='默认' g='133,100,90,50' name='发起'>
    <form>[]</form>
    <coordinateParticipants subtask-must-submit='false'>[]</coordinateParticipants>
    <participants>[{&quot;name&quot;:&quot;舆情点评发起岗&quot;,&quot;code&quot;:&quot;CRS_OPINION_001&quot;,&quot;typecode&quot;:&quot;pos&quot;,&quot;typename&quot;:&quot;岗位&quot;,&quot;type&quot;:&quot;&quot;,&quot;_index&quot;:4,&quot;_rowKey&quot;:5}]</participants>
    <extaction/>
    <variable name='CURRENT_NODE_ID' type='String' init-expr='launch'/>
  <transition wfCompanyId='BIZ' name='transition 1' to='action 1'/>
  </human>
  <action wfCompanyId='BIZ' assignNext='0' irrevocable='0' comeBack='0' g='243,110,30,30' text='提交' name='action 1'>
    <form>[]</form>
    <transition name='to 人工 2' to='审核'/>
  </action>
  <human wfCompanyId='BIZ' enableRemind='false' canNotice='false' canReassign='false' canCoordinate='false' form='cf572edb08e44ef1a4111a9b14ada998' formName='舆情点评' message-notice='默认' g='295,100,90,50' name='审核'>
    <form>[]</form>
    <coordinateParticipants subtask-must-submit='false'>[]</coordinateParticipants>
    <participants>[{&quot;name&quot;:&quot;舆情点评审核岗&quot;,&quot;code&quot;:&quot;CRS_OPINION_002&quot;,&quot;typecode&quot;:&quot;pos&quot;,&quot;typename&quot;:&quot;岗位&quot;,&quot;type&quot;:&quot;&quot;,&quot;_index&quot;:2,&quot;_rowKey&quot;:3}]</participants>
    <extaction/>
    <variable name='CURRENT_NODE_ID' type='String' init-expr='audit'/>
  <transition wfCompanyId='BIZ' name='transition 2' to='action 2'/>
  <transition name='transition 6' to='action 3'/>
  </human>
  <action wfCompanyId='BIZ' assignNext='0' irrevocable='0' comeBack='0' g='405,110,30,30' text='同意' name='action 2'>
    <form>[]</form>
    <transition name='to end' to='end'/>
  </action>
  <action assignNext='0' irrevocable='0' comeBack='0' g='325,172,30,30' text='驳回' name='action 3'>
    <form>[]</form>
    <transition name='to 人工 1' g='178,185:96,-23' to='发起'/>
  </action>
</process>


}
