//package com.hepsiburada.etl.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Repository
//public class UserIdDao {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
////    public List<Employee> getEmployeesFromIdListNamed(List<Integer> ids) {
////        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
////        List<Employee> employees = namedJdbcTemplate.query(
////          "SELECT * FROM EMPLOYEE WHERE id IN (:ids)",
////          parameters,
////          (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
////
////        return employees;
////    }
////
////    public List<Employee> getEmployeesFromIdList(List<Integer> ids) {
////        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
////        List<Employee> employees = jdbcTemplate.query(
////          String.format("SELECT * FROM EMPLOYEE WHERE id IN (%s)", inSql),
////          ids.toArray(),
////          (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
////
////        return employees;
////    }
//
//    public void deleteFromUserIds() {
//        jdbcTemplate.update("DELETE FROM user_ids");
//    }
//}
