package com.mapper;

import com.model.SysUser;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/2/25 17:20
 **/
public interface SysUserMapper {

    SysUser selectById(Long id);
}
