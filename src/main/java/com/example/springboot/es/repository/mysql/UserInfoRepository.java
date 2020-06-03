package com.example.springboot.es.repository.mysql;

import com.example.springboot.es.entity.mysql.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName UserInfoRepository
 * @Description TODO
 * @Author 15258
 * @Date 2020/5/12 15:52
 * @Version 1.0
 */
public interface UserInfoRepository extends JpaRepository<UserInfoEntity,Integer> {

    /**
     * 通过账号查询
     * @param account
     * @return
     */
    @Query("select e from UserInfoEntity e where e.account = :account and e.state = '1' ")
    List<UserInfoEntity> checkAccount(@Param("account") String account);
}
