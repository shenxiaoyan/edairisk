package com.liyang.service;

import com.liyang.domain.reference.Reference;
import com.liyang.domain.reference.ReferenceMapper;
import com.liyang.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class DevService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private String table_schema;
    private List<Reference> references;
    private final static String[] notDeleteEntities = {"department"};

    @PostConstruct
    private void init() {
        table_schema = jdbcTemplate.queryForObject("select database()", String.class);
        references = jdbcTemplate.query("select `table_name`,`column_name`,referenced_table_name,referenced_column_name from INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE table_schema = ? ", new Object[]{table_schema}, new ReferenceMapper());
    }

    @Transactional
    public void deleteDataByForeignKey(String tableName, String strId, String password) {
        if (!CommonUtil.getPrincipal().getPassword().equals(MD5Util.encode(password))) {
            throw new FailReturnObject(1543, "密码错误!!", ReturnObject.Level.INFO);
        }
        Assert.notNull(tableName, "tableName不能为空");
        for (String notDeleteEntity : notDeleteEntities) {
            if (notDeleteEntity.equals(tableName)) {
                throw new FailReturnObject(1523, "不能从" + tableName + "开始删", ReturnObject.Level.INFO);
            }
        }

        checkTableName(tableName);


        Integer id = Integer.valueOf(strId);
        jdbcTemplate.execute("SET foreign_key_checks = 0");

        runDelete(tableName, id);
        jdbcTemplate.update("delete from " + tableName + " where id = " + id);

        jdbcTemplate.execute("SET foreign_key_checks = 1");
    }

//    private List<Reference> findAll() {
////        String table_schema = jdbcTemplate.queryForObject("select database()", String.class);
////        return jdbcTemplate.query("select `table_name`,`column_name`,referenced_table_name,referenced_column_name from INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE table_schema = ? ", new Object[]{table_schema}, new ReferenceMapper());
//    }

    private void runDelete(String tableName, Integer id) {
        references.forEach(reference -> {
            if (tableName.equals(reference.getReferencedTableName()) && "id".equals(reference.getReferencedColumnName())) {
                String currentTableName = reference.getTableName();
                String currentColumnName = reference.getColumnName();

                List<Map<String, Object>> deletes = jdbcTemplate.queryForList("select * from " + currentTableName + " WHERE " + currentColumnName + " = ?", new Object[]{id});

                System.out.println("----------------------" + currentTableName + "---" + currentColumnName + ":" + id + "---number of rows:" + deletes.size());
                securityCheck(deletes, currentTableName);

                jdbcTemplate.update("delete from " + currentTableName + " where " + currentColumnName + " = " + id);


                deletes.forEach(delete -> {
                    if (delete.containsKey("id")) {
                        runDelete(currentTableName, (Integer) delete.get("id"));
                    }
                });
            }
        });
    }

    private void securityCheck(List<Map<String, Object>> deletes, String currentTable) {
        if (deletes == null || deletes.isEmpty()) {
            return;
        }
        if ("user".equals(currentTable)) {
            deletes.forEach((Map<String, Object> user) -> {
                if (3 != (Integer) user.getOrDefault("role_id", 0)) {//3是前段用户
                    throw new FailReturnObject(1432, "不能删除非前端用户", ReturnObject.Level.INFO);
                }
            });
        } else if ("department".equals(currentTable)) {
            throw new FailReturnObject(1432, "不能删除department里数据", ReturnObject.Level.INFO);
        }

        checkTableName(currentTable);
    }

    private void checkTableName(String tableName) {
        if (Pattern.matches("^[\\S]*_(act|act_roles|state|state_acts|workflow|workflow_states)$", tableName)) {
            throw new FailReturnObject(1432, "不能删除配置表里数据", ReturnObject.Level.INFO);
        }
    }
}
