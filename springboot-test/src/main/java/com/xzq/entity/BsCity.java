package com.xzq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzq
 * @since 2021-11-11
 */
@TableName("bs_city")
public class BsCity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BsCity{" +
            "id=" + id +
            ", name=" + name +
            ", createTime=" + createTime +
        "}";
    }
}
