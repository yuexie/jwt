package com.xieyue.jwt.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName :   Visitor
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-28 23:16
 */
@Data
@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @Column(name = "visitor_id")
    private Integer visitorId;

    @Column(name = "visitor_name")
    private String visitorName;
}
