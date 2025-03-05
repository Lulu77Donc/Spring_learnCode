package com.ljx.spring6.jdbc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(locations = "classpath:bean.xml")
public class JdbcTemplateTest {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
    JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

    //查询：返回对象
    @Test
    public void testSelectObject(){

        String sql = "select * from t_emp where id=?";
        /*Emp empResult = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    Emp emp = new Emp();
                    emp.setId(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setAge(rs.getInt("age"));
                    emp.setSex(rs.getString("sex"));
                    return emp;
                }, 1);*/

        Emp empResult = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Emp.class), 1);
        System.out.println(empResult);
    }

    //查询：返回list集合
    @Test
    public void testSelectForList(){
        String sql = "select * from t_emp";
        List<Emp> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class));
        System.out.println(query);
    }

    //查询：返回单个值
    @Test
    public void testSelectForValue(){
        String sql = "select count(*) from t_emp";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }


    //添加 修改 删除操作
    @Test
    public void testUpdate(){
        /*ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);*/
        System.out.println("JdbcTemplate: " + jdbcTemplate);
        if (jdbcTemplate == null) {
            System.out.println("JdbcTemplate is null!");
            return;
        }

        //1 添加操作
        //第一步 编写sql语句
        /*String sql = "insert into t_emp values (null,?,?,?)";
        //第二部 调用jdbcTemplate的方法，传入相关参数
        int rows = jdbcTemplate.update(sql, "james", 40, "男");
        System.out.println(rows);*/
        
        //2 修改操作
        /*String sql = "update t_emp set name = ? where id = ?";
        int rows = jdbcTemplate.update(sql, "brown", 2);
        System.out.println(rows);*/

        //3 删除操作
        String sql = "delete from t_emp where id=?";
        int rows = jdbcTemplate.update(sql, 3);
        System.out.println(rows);

    }

}
