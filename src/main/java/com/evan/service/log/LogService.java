/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/23 16:57
 **/
package com.evan.service.log;


import com.github.pagehelper.PageInfo;
import com.evan.model.LogDomain;

/**
 * 日志相关Service接口
 */
public interface LogService {

    /**
     * 添加日志
     * @param action    触发动作
     * @param data      产生数据
     * @param ip        产生IP
     * @param authorId  产生人
     */
    void addLog(String action, String data, String ip, Integer authorId);

    /**
     * 获取日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<LogDomain> getLogs(int pageNum, int pageSize);
}
