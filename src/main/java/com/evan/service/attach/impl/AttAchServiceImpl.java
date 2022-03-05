/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/2/3 16:24
 **/
package com.evan.service.attach.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.evan.constant.ErrorConstant;
import com.evan.dao.AttAchDao;
import com.evan.dto.AttAchDto;
import com.evan.exception.BusinessException;
import com.evan.model.AttAchDomain;
import com.evan.service.attach.AttAchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件接口实现
 */
@Service
public class AttAchServiceImpl implements AttAchService {

    @Autowired
    private AttAchDao attAchDao;

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void addAttAch(AttAchDomain attAchDomain) {
        if (null == attAchDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchDao.addAttAch(attAchDomain);
    }

    @Override
    @Cacheable(value = "attCaches", key = "'atts' + #p0")
    public PageInfo<AttAchDto> getAtts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttAchDto> atts = attAchDao.getAtts();
        PageInfo<AttAchDto> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "attCaches", key = "'attAchByid' + #p0")
    public AttAchDto getAttAchById(Integer id) {
        if (null == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return attAchDao.getAttAchById(id);
    }

    @Override
    @CacheEvict(value = {"attCaches", "attCache"}, allEntries = true, beforeInvocation = true)
    public void deleteAttAch(Integer id) {
        if (null == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchDao.deleteAttAch(id);
    }
}
