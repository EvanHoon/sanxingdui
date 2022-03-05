/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/2/2 8:48
 **/
package com.evan.service.site;

import com.evan.dto.StatisticsDto;
import com.evan.model.CommentDomain;
import com.evan.model.ContentDomain;

import java.util.List;

/**
 * 网站相关Service接口
 */
public interface SiteService {

    /**
     * 获取评论列表
     * @param limit
     * @return
     */
    List<CommentDomain> getComments(int limit);

    /**
     * 获取文章列表
     * @param limit
     * @return
     */
    List<ContentDomain> getNewArticles(int limit);

    /**
     * 获取后台统计数
     * @return
     */
    StatisticsDto getStatistics();
}
