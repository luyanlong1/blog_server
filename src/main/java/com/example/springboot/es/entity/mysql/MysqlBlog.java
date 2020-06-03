package com.example.springboot.es.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName MysqlBlog
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/9 15:54
 * @Version 1.0
 *
 * CREATE TABLE `t_blog`(
 * `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
 * `title` VARCHAR(60) DEFAULT null COMMENT '标题',
 * `author` VARCHAR(60) DEFAULT null COMMENT '作者',
 * `content` MEDIUMTEXT COMMENT '内容',
 * `create_time` datetime DEFAULT null COMMENT '创建时间',
 * `update_time` datetime DEFAULT null COMMENT '更新时间',
 * PRIMARY KEY (`id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
 *
 */


@Data
@Table(name = "t_blog")
@Entity
public class MysqlBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    private Date createTime;
    private Date updateTime;
    private String isPublic;
    private String state;

}
