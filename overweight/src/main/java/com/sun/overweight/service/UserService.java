package com.sun.overweight.service;

import com.sun.overweight.ramp.common.model.CrdmBaseComptCfgDetailResponseVo;
import com.sun.overweight.ramp.common.model.CrdmBaseComptCfgDetailTypeVo;
import com.sun.overweight.ramp.common.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunwx33102
 * @description
 * @date 2021-06-20 10:50
 */
@Service
public interface UserService {
    /**
     * 查询库1的用户信息
     * @param name
     * @return
     */
    Users findDs1User(String name);

    /**
     * 查询库2的用户信息
     * @param name
     * @return
     */
    Users findDs2User(String name);

    /**
     * aa
     * @return
     */
    List<CrdmBaseComptCfgDetailTypeVo> getInfo();
}