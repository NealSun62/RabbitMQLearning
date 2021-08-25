package com.sun.overweight.service;

import com.sun.overweight.config.Ds;
import com.sun.overweight.mapper.UserMapper;
import com.sun.overweight.ramp.common.model.CrdmBaseComptCfgDetailResponseVo;
import com.sun.overweight.ramp.common.model.CrdmBaseComptCfgDetailTypeVo;
import com.sun.overweight.ramp.common.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunwx33102
 * @description
 * @date 2021-07-05 10:53
 */

@Service("userService")
public class UserServiceImpl implements UserService{
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Override
    @Ds("ds1")
    public Users findDs1User(String name){
        Users users = userMapper.selectUser(name);
        return users;
    }

    @Override
    @Ds("ds2")
    public Users findDs2User(String name){
        Users users = userMapper.selectUser(name);
        return users;
    }

    @Override
    @Ds("ds1")
    public List<CrdmBaseComptCfgDetailTypeVo> getInfo(){
        List<CrdmBaseComptCfgDetailTypeVo> lisy = userMapper.getInfo();
        return lisy;
    }
}