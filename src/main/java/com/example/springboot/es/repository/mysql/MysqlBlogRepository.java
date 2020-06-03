package com.example.springboot.es.repository.mysql;

import com.example.springboot.es.entity.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ClassName MysqlBlogRepository
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/9 16:14
 * @Version 1.0
 */
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog,Integer> {

    @Query("select e from MysqlBlog e order by e.createTime desc")
    List<MysqlBlog> queryAll();

    @Query("select e from MysqlBlog e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%')")
    List<MysqlBlog> queryBlogs(@Param("keyword") String kerword);


    @Modifying
    @Transactional
    @Query("update MysqlBlog a set " +
            "a.title = CASE WHEN :#{#param.title} IS NULL THEN a.title ELSE :#{#param.title} END ," +
            "a.author = CASE WHEN :#{#param.author} IS NULL THEN a.author ELSE :#{#param.author} END ," +
            "a.content = CASE WHEN :#{#param.content} IS NULL THEN a.content ELSE :#{#param.content} END ," +
            "a.isPublic =  CASE WHEN :#{#param.isPublic} IS NULL THEN a.isPublic ELSE :#{#param.isPublic} END, " +
            "a.state =  CASE WHEN :#{#param.state} IS NULL THEN a.state ELSE :#{#param.state} END, " +
            "a.createTime =  CASE WHEN :#{#param.createTime} IS NULL THEN a.createTime ELSE :#{#param.createTime} END, " +
            "a.updateTime =  CASE WHEN :#{#param.updateTime} IS NULL THEN a.updateTime ELSE :#{#param.updateTime} END " +
            "where a.id = :#{#param.id}")
    int update(@Param("param") MysqlBlog param);


    /**
     * springdata-jpa的@query中写的sql叫JPQL    JPQL是不支持limit函数的
     * 这里只能使用原生sql
     * @param limit1
     * @param limit2
     * @return
     */
    @Query(nativeQuery=true,value = "select * from t_blog  where state = :#{#state} limit :#{#limit1},:#{#limit2} ")
    List<MysqlBlog> queryByPage(@Param("limit1") int limit1,@Param("limit2") int limit2,@Param("state") String state);


    /**
     * 查询总数
     * @param state
     * @return
     */
    int countByState(String state);

}
