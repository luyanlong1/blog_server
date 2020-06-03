package com.example.springboot.es.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UserInfoEntity
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/12 15:44
 * @Version 1.0
 *
    CREATE TABLE `user_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `account` varchar(100) NOT NULL,
    `pass_word` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `mail` varchar(100) DEFAULT NULL,
    `phone` varchar(100) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `state` varchar(10) DEFAULT NULL COMMENT '状态 1正常 0禁用',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
 *
 */

@Data
@Entity
@Table(name = "user_info")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account;
    private String passWord;
    private String name;
    private String mail;
    private String phone;
    private Date createTime;
    private String state;
}
