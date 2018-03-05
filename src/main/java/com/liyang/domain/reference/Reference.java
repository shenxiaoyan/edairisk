package com.liyang.domain.reference;

import java.io.Serializable;

public class Reference implements Serializable {
    //table_name,column_name,referenced_table_name,referenced_column_name

    private static final long serialVersionUID = 124123L;

    private String tableName;
    private String columnName;
    private String referencedTableName;
    private String referencedColumnName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    public void setReferencedColumnName(String referencedColumnName) {
        this.referencedColumnName = referencedColumnName;
    }
}
