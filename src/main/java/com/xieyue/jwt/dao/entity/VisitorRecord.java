package com.xieyue.jwt.dao.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName :   VisitorRecord
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-08-03 23:02
 */
@Data
@Entity
@Table(name = "visitor_record")
public class VisitorRecord {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "ID",insertable = false)
    private Integer recordId;

    @Column(name="visitor_id")
    private Integer visitorId;

    @Column(name="visitToName")
    private String visitToName;

    @Column(name="visitToDate")
    private Date visitToDate;

    @Column(name="addTime")
    private Date addTime;

}
