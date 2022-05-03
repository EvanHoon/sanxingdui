/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/25 16:50
 **/
package com.evan.dao;

import com.evan.dto.cond.ContentCond;
import com.evan.model.ContentDomain;
import com.evan.model.RelationShipDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章相关Dao接口
 */
@Mapper
public interface ContentDao {

    /**
     * 添加文章
     * @param contentDomain
     */
    void addArticle(ContentDomain contentDomain);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    ContentDomain getArticleById(Integer cid);

    /**
     * 更新文章
     * @param contentDomain
     */
    void updateArticleById(ContentDomain contentDomain);

    /**
     * 根据条件获取文章列表
     * @param contentCond
     * @return
     */
    List<ContentDomain> getArticleByCond(ContentCond contentCond);

    /**
     * 删除文章
     * @param cid
     */
    void deleteArticleById(Integer cid);

    /**
     * 获取展文总数
     * @return
     */
    Long getPostArticleCount();

    /**
     * 获取简文总数
     * @return
     */
    Long getPhotoArticleCount();
    /**
     * 通过分类名获取文章
     * @param category
     * @return
     */
    List<ContentDomain> getArticleByCategory(@Param("category") String category);

    /**
     * 通过标签获取文章
     * @param cid
     * @return
     */
    List<ContentDomain> getArticleByTags(List<RelationShipDomain> cid);
}
