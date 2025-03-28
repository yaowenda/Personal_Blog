package com.example.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论的id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 评论者邮箱
     */
    private String email;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 所评论的博客的id
     */
    private Integer blogId;

    /**
     * 父评论的id
     */
    private Integer fatherCommentId;

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(Integer fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blogId='" + blogId + '\'' +
                ", fatherCommentId='" + fatherCommentId + '\'' +
                '}';
    }
}
