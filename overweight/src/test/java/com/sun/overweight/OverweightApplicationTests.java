package com.sun.overweight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests {

    @Test
    public static void main(String[] args) throws InterruptedException {
        Object ob = new Object();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName());
        if(ob == null){
            System.out.println("null");
        }
    }

}
