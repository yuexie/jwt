package com.xieyue.jwt.dao.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName :   VisitorRecordView
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-08-03 23:05
 */
@Data
@Entity
@Table(name = "visitRecordView")
public class VisitorRecordView {

    @Column(name="visitor_id")
    private String visitorId;

    @Column(name="visitor_name")
    private String visitorName;

    @Column(name="visitToName")
    private String visitToName;

    @Column(name="visitToDate")
    private String visitToDate;

    @Column(name="addTime")
    private String addTime;

}
