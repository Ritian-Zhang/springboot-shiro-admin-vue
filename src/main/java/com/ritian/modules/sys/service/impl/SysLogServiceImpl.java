package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.util.PageUtil;
import com.ritian.common.util.Query;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysLog;
import com.ritian.modules.sys.mapper.SysLogMapper;
import com.ritian.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2019/01/11
 **/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Override
    public PageUtil listByPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<SysLog> page = this.page(new Query<SysLog>(params).getPage(),
                new QueryWrapper<SysLog>().like(StringUtils.isNotBlank(key), "username", key));
        return new PageUtil(page);
    }
}
