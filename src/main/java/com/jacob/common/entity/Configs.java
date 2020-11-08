package com.jacob.common.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Configs implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    private String comment;
    @TableField(value = "`group`")
    private String group;


}
