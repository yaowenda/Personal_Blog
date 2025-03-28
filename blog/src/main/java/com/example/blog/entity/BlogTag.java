package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("blog_tag")
public class BlogTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 博客的id
     */
    private Integer blogId;

    /**
     * tag的id
     */
    private Integer tagId;

    public BlogTag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "BlogTag{" +
                "id='" + id + '\'' +
                ", blogId='" + blogId + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
