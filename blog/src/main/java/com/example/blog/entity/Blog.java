package com.example.blog.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Getter
@Setter
@ToString
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;





    /**
     * ID
     */
    private Integer id;

    /**
     * 博客题目
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 标记（转载，原创）
     */
    private String flag;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 是否赞赏
     */
    private Boolean appreciation;

    /**
     * 转载声明是否开启
     */
    private Boolean shareStatement;

    /**
     * 是否允许评论
     */
    private Boolean commentabled;

    /**
     * 是否发布（或保存草稿）
     */
    private Boolean published;

    /**
     * 是否推荐
     */
    private Boolean recommened;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型的id
     */
    private Integer typeId;

    /**
     * user的id
     */
    private Integer userId;

    @TableField(exist = false)
    private Type type;  // 添加这个字段用于存储关联的分类信息

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Boolean getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Boolean commentabled) {
        this.commentabled = commentabled;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRecommened() {
        return recommened;
    }

    public void setRecommened(Boolean recommened) {
        this.recommened = recommened;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommened=" + recommened +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", typeId='" + typeId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
