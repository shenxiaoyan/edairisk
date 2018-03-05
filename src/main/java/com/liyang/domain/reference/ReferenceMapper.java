package com.liyang.domain.reference;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ReferenceMapper implements RowMapper<Reference> {
    @Override
    public Reference mapRow(ResultSet resultSet, int i) throws SQLException {
        Reference reference=new Reference();
        reference.setTableName(resultSet.getString("table_name"));
        reference.setColumnName(resultSet.getString("column_name"));
        reference.setReferencedTableName(resultSet.getString("referenced_table_name"));
        reference.setReferencedColumnName(resultSet.getString("referenced_column_name"));
        return reference;
    }
}
