package com.example.springboot.es.repository.es;

import com.example.springboot.es.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName EsBlogRepository
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/10 15:26
 * @Version 1.0
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {

}
