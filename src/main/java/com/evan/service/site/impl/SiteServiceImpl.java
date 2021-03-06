/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/2/2 8:48
 **/
package com.evan.service.site.impl;

import com.github.pagehelper.PageHelper;
import com.evan.constant.Types;
import com.evan.dao.AttAchDao;
import com.evan.dao.CommentDao;
import com.evan.dao.ContentDao;
import com.evan.dao.MetaDao;
import com.evan.dto.StatisticsDto;
import com.evan.dto.cond.CommentCond;
import com.evan.dto.cond.ContentCond;
import com.evan.model.CommentDomain;
import com.evan.model.ContentDomain;
import com.evan.service.site.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private AttAchDao attAchDao;



    @Override
    @Cacheable(value = "siteCache", key = "'comments_' + #p0")
    public List<CommentDomain> getComments(int limit) {
        LOGGER.debug("Enter recentComments method: limit={}", limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(1,limit);
        List<CommentDomain> rs = commentDao.getCommentsByCond(new CommentCond());
        LOGGER.debug("Exit recentComments method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'newArticles_' + #p0")
    public List<ContentDomain> getNewArticles(int limit) {
        LOGGER.debug("Enter recentArticles method:limit={}",limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(1,limit);
        List<ContentDomain> rs = contentDao.getArticleByCond(new ContentCond());
        LOGGER.debug("Exit recentArticles method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'statistics_'")
    public StatisticsDto getStatistics() {
        LOGGER.debug("Enter recentStatistics method");

        // ????????????
        Long articles = contentDao.getPostArticleCount();

        // ???????????????
        Long photos = contentDao.getPhotoArticleCount();

        // ????????????
        Long comments = commentDao.getCommentCount();

        // ?????????
        Long links = metaDao.getMetasCountByType(Types.LINK.getType());


        StatisticsDto rs = new StatisticsDto();
        rs.setArticles(articles);
        rs.setComments(comments);
        rs.setLinks(links);
        rs.setPhotos(photos);
        LOGGER.debug("Exit recentStatistics method");
        return rs;
    }
}
