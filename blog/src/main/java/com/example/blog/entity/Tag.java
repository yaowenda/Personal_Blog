package com.example.blog.entity;

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
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签的ID
     */
    private Integer id;

    /**
     * 标签的名字
     */
    private String name;

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
