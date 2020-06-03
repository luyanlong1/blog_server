package com.example.springboot.es.controller;

import com.example.springboot.es.entity.es.EsBlog;
import com.example.springboot.es.entity.mysql.MysqlBlog;
import com.example.springboot.es.repository.es.EsBlogRepository;
import com.example.springboot.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import net.sf.json.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName DataContorller
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/10 16:36
 * @Version 1.0
 */

@RestController
@RequestMapping("/show")
public class DataController {

    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;


    /**
     * 获取全部
     * @return
     */
    @GetMapping("/blogs")
    public Object getBlogs(){
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.findAll();
        return mysqlBlogs;
    }


    /**
     * 检索
     * @param param
     * @return
     */
    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        HashMap<String,Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        if(type.equalsIgnoreCase("mysql")){
            //mysql
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list",mysqlBlogs);
        }else if (type.equalsIgnoreCase("es")){
            //es
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content",param.getKeyword()));
            Page<EsBlog> search = (Page<EsBlog>)esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list",content);
        }else {
            return "参数错误！";
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration",totalTimeMillis);
        return map;
    }


    /**
     * 查询某一篇详情
     * @param id
     * @return
     */
    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable("id") Integer id){
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        MysqlBlog blog = byId.get();
        return blog;
    }

    /**
     * 保存某一篇
     * @param jsonString
     * @return
     */
    @PostMapping("/save")
    public Object save(@RequestBody String jsonString){
        JSONObject resultJson = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String title =jsonObject.getString("title");
        String author =jsonObject.getString("author");
        String content =jsonObject.getString("content");
        String isPublic =jsonObject.getString("isPublic");
        String state =jsonObject.getString("state");
        MysqlBlog mysqlBlog = new MysqlBlog();
        mysqlBlog.setTitle(title);
        mysqlBlog.setAuthor(author);
        mysqlBlog.setContent(content);
        mysqlBlog.setIsPublic(isPublic);
        mysqlBlog.setState(state);
        mysqlBlog.setCreateTime(new Date());
        mysqlBlog.setUpdateTime(new Date());
        mysqlBlogRepository.save(mysqlBlog);
        resultJson.put("code","200");
        return resultJson;
    }


    /**
     * 更新
     * @param jsonString
     * @return
     */
    @PostMapping("/update")
    public Object update(@RequestBody String jsonString){
        JSONObject resultJson = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String id = jsonObject.getString("id");
        String title =jsonObject.containsKey("title") ? jsonObject.getString("title") : null;
        String author =jsonObject.containsKey("author") ? jsonObject.getString("author") : null;
        String content =jsonObject.containsKey("content") ? jsonObject.getString("content") : null;
        String isPublic =jsonObject.containsKey("isPublic") ? jsonObject.getString("isPublic") : null;
        String state =jsonObject.containsKey("state") ? jsonObject.getString("state") : null;
        MysqlBlog mysqlBlog = new MysqlBlog();
        mysqlBlog.setId(Integer.valueOf(id));
        mysqlBlog.setTitle(title);
        mysqlBlog.setAuthor(author);
        mysqlBlog.setContent(content);
        mysqlBlog.setIsPublic(isPublic);
        mysqlBlog.setState(state);
        mysqlBlog.setUpdateTime(new Date());
        mysqlBlogRepository.update(mysqlBlog);
        resultJson.put("code","200");
        return resultJson;
    }

    /**
     * 删除某条
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public Object del(@PathVariable("id") Integer id){
        JSONObject resultJson = new JSONObject();
        mysqlBlogRepository.deleteById(id);
        resultJson.put("code","200");
        return resultJson;
    }


    /**
     * 分页查询  条件查询
     * @param param
     * @return
     */
    @PostMapping("/query")
    public Object query(@RequestBody Param param){
        HashMap<String,Object> map = new HashMap<>();
        String pageNo = param.getPageNo();
        String pageSize = param.getPageSize();
        String state = param.getState();
        int limit1 = (Integer.valueOf(pageNo)-1)*Integer.valueOf(pageSize);
        int limit2 = Integer.valueOf(pageSize);
        List<MysqlBlog> blogs = mysqlBlogRepository.queryByPage(limit1,limit2,state);
        map.put("list",blogs);
        int count = mysqlBlogRepository.countByState(state);
        map.put("count",count);
        return map;
    }


    /**
     * 查询条件。。。
     */
    @Data
    public static class Param{
        //mysql  es
        private String type;
        private String keyword;

        //分页查询，以及 已发布  草稿箱区分
        private String pageNo;
        private String pageSize;
        private String state;
    }
}
