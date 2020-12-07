//package com.sun.overweight.common.utils;
//
//import com.znjt.automatic.ramp.common.constant.Constant;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by bf on 2018/11/19.
// */
//public class TokenUtil {
//
//    public static Long getUserId(HttpServletRequest request) throws Exception{
//        Long userId = 0L;
//        String token = request.getHeader(Constant.AUTHORIZATION);
//        if(token==null){
//            userId = 0L;
//            return userId;
//        }
//        userId = Long.parseLong(token.split(",")[0]);
//        if(userId.longValue()==0){
//            userId = 0L;
//        }
//        return userId;
//    }
//
//}
