package com.ljx.dao;

import com.ljx.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface AccountDao {
    @Insert("insert into account (name) value #{name}")
    void save(Account account);

    @Delete("delete from account where id = #{id}")
    void delete(Integer id);

    @Update("update account set name=#{name} where id=#{id}")
    void update(Account account);

    @Select("select * from account")
    List<Account> findAll();

    @Select("select * from account where id=#{id}")
    Account findById(Integer id);




}
