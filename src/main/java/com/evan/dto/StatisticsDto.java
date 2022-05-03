/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/2/2 9:25
 **/
package com.evan.dto;

/**
 * 后台统计对象
 */
public class StatisticsDto {

    /**
     * 文章数
     */
    private Long articles;

    /**
     * 文件数
     */
    private Long photos;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 链接数
     */
    private Long links;

    public Long getArticles() {
        return articles;
    }

    public void setArticles(Long articles) {
        this.articles = articles;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLinks() {
        return links;
    }

    public void setLinks(Long links) {
        this.links = links;
    }

    public Long getPhotos() {
        return photos;
    }

    public void setPhotos(Long photos) {
        this.photos = photos;
    }
}
