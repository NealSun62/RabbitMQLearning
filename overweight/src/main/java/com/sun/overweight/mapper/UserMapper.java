package com.sun.overweight.mapper;

import com.sun.overweight.ramp.common.model.CrdmBaseComptCfgDetailTypeVo;
import com.sun.overweight.ramp.common.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date
 */

@Repository("userMapper")
public interface UserMapper {
    /**
     * 查询用户信息
     * @param name
     * @return
     */
    Users selectUser(@Param("name") String name);

    /**
     * a
     * @return
     */
    List<CrdmBaseComptCfgDetailTypeVo> getInfo();
}
