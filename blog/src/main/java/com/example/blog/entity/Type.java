package com.example.blog.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类的ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类的名字
     */
    private String name;

    public Type() {
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
