package com.jacob.common.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ddns更新日志
 * </p>
 *
 * @author jobob
 * @since 2020-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DdnsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    public DdnsLog(){

    }
    public DdnsLog(String ip, String hostname, String status){
        this.ip = ip;
        this.hostname = hostname;
        this.status = status;
        this.updateTime = LocalDateTime.now();
    }

    private String ip;

    private String hostname;

    /**
     * 返回状态
     */
    private String status;

    private LocalDateTime updateTime;


}
