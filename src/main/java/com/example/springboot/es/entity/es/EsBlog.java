package com.example.springboot.es.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName EsBlog
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/9 16:58
 * @Version 1.0
 */

@Data
@Document(indexName = "blog",type = "_doc",
        useServerConfiguration = true,createIndex = false)
public class EsBlog {

    @Id
    private Integer id;
    //指定针对该字段的分词器，这里使用针对中文的分词器 ik_max_word
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String author;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Date,format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;
    @Field(type = FieldType.Date,format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;
    private String isPublic;
    private String state;
}
