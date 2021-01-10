package com.git.hui.boot.multi.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.git.hui.boot.multi.datasource.entity.MoneyPo;
import com.git.hui.boot.multi.datasource.mapper.MoneyMapper;
import com.git.hui.boot.multi.datasource.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YiHui
 * @since 2020-04-06
 */
@Service
@DS("story")
public class StoryMoneyServiceImpl extends ServiceImpl<MoneyMapper, MoneyPo> implements MoneyService {
}
