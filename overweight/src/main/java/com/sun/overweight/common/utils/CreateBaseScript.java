package com.sun.overweight.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CreateBaseScript {

    public static void createOracleBaseScript(String owner) {
        //   select *from user_tab_cols f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA' order by f.COLUMN_ID;
        //  select *from all_indexes f where f.table_name='LCR_BASE_INDEX_FORMULA';
        //  select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA';
        //   select *from all_col_comments f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
        // select *from all_tables where owner='HSLCR';
        //select *from all_tab_comments where owner='HSLCR' and table_type='TABLE';
        List<Map<String, Object>> tableList = DBUtil.executeQuery("select *from all_tables where owner='" + owner + "' ");
        for (Map<String, Object> en : tableList) {
            String tableName = String.valueOf(en.get("TABLE_NAME"));
            String tableSpaceName = String.valueOf(en.get("TABLESPACE_NAME"));
            StringBuilder sql = new StringBuilder(" declare v_rowcount number(10); \n  begin \n ");
            sql.append("select count(1) into v_rowcount from user_tables where table_name = upper('");
            sql.append(tableName.toLowerCase()).append("');\n");
            sql.append(" if v_rowcount = 0 then ");
            sql.append(" execute immediate ' \n ");
            sql.append("create  table  ").append(tableName.toLowerCase());
            sql.append("\n (");

            List<Map<String, Object>> colList = DBUtil.executeQuery(" select *from user_tab_cols f where f.TABLE_NAME='" + tableName + "' order by f.COLUMN_ID");
            //表字段生成
            int index = 0;
            for (Map<String, Object> col : colList) {

                String colName = String.valueOf(col.get("COLUMN_NAME"));
                String dataType = String.valueOf(col.get("DATA_TYPE"));
                int dataLen = Integer.parseInt(String.valueOf(col.get("DATA_LENGTH")));
                int dataPre = ObjectUtil.isNull(col.get("DATA_PRECISION")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_PRECISION")));
                int dataScale = ObjectUtil.isNull(col.get("DATA_SCALE")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_SCALE")));
                String nullFlag = String.valueOf(col.get("NULLABLE"));
                String nullValue = "";
                if ("N".equals(nullFlag)) {
                    nullValue = "NOT NULL";
                }
                if ("VARCHAR2".equals(dataType) || "CHAR".equals(dataType)) {
                    sql.append(colName).append("   ").append(dataType).append("(").append(dataLen).append(") default '' '' ").append(nullValue);
                }
                if ("NUMBER".equals(dataType)) {
                    sql.append(colName).append("   ").append(dataType);

                    if (dataScale == 0) {
                        sql.append("(").append(dataPre).append(") default 0 ").append(nullValue);
                    } else {
                        sql.append("(").append(dataPre).append(',').append(dataScale).append(") default 0.0 ").append(nullValue);
                    }

                }
                if ("CLOB".equals(dataType)) {
                    sql.append(colName).append("   ").append(dataType).append(" ").append(nullValue);

                }
                if ("DATE".equals(dataType)) {
                    sql.append(colName).append("   ").append(dataType).append(" ").append(nullValue);
                }

                ///最后一条
                if (colList.size() - 1 > index) {
                    sql.append(",");
                }
                sql.append("\n ");

                index++;
            }
            //   sql.append(" )'; \n");
            //  sql.append(") TABLESPACE ").append(tableSpaceName).append("';");

            //索引生成
            // 索引列表
            List<Map<String, Object>> idxList = DBUtil.executeQuery("select index_name,'P' as index_type from user_constraints c where c.table_name='" + tableName + "' and index_owner='" + owner + "'" +
                    "union all " +
                    "select  index_name,f.UNIQUENESS as index_type  from all_indexes f where f.table_name='" + tableName + "' " +
                    "and f.OWNER='" + owner + "' and  not exists(select * from user_constraints u where u.constraint_name=f.INDEX_NAME and f.OWNER='" + owner + "') "
            );

            boolean primaryFlag = false;
            for (Map<String, Object> idxMap : idxList) {
                String idxName = String.valueOf(idxMap.get("INDEX_NAME"));
                String idxType = String.valueOf(idxMap.get("INDEX_TYPE"));
                // String idxName = String.valueOf(idxMap.get("INDEX_NAME"));

                //select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
                List<Map<String, Object>> idxColList = DBUtil.executeQuery("select *from all_ind_columns f where f.TABLE_NAME='" + tableName + "' and index_name='" + idxName + "' and f.TABLE_OWNER='" + owner + "' and f.INDEX_OWNER='" + owner + "' order by column_position");
                String cols = StrUtil.join(",", idxColList.stream().map(e -> String.valueOf(e.get("COLUMN_NAME"))).collect(Collectors.toList()));
                //主键索引
                if ("P".equals(idxType)) {
                    primaryFlag = true;
                    sql.append(",").append("\n").append(" constraint ").append(idxName).append(" primary key (").append(cols).append(") \n ").append(")';");
                }
            }
            if(!primaryFlag){
                sql.append("\n )';");
            }

            for (Map<String, Object> idxMap : idxList) {
                String idxName = String.valueOf(idxMap.get("INDEX_NAME"));
                String idxType = String.valueOf(idxMap.get("INDEX_TYPE"));
                // String idxName = String.valueOf(idxMap.get("INDEX_NAME"));

                //select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
                List<Map<String, Object>> idxColList = DBUtil.executeQuery("select *from all_ind_columns f where f.TABLE_NAME='" + tableName + "' and index_name='" + idxName + "' and f.TABLE_OWNER='" + owner + "' and f.INDEX_OWNER='" + owner + "' order by column_position");
                String cols = StrUtil.join(",", idxColList.stream().map(e -> String.valueOf(e.get("COLUMN_NAME"))).collect(Collectors.toList()));
                //主键索引
                sql.append("\n");

                if ("P".equals(idxType)) {
                    continue;
                }
                if ("UNIQUE".equals(idxType)) {
                    sql.append("execute immediate 'create unique index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(") ';");
                } else {
                    sql.append("execute immediate 'create  index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(") ';");
                }

            }


            //表的注释
            List<Map<String, Object>> tabCommentList = DBUtil.executeQuery("select *from all_tab_comments where owner='" + owner + "' and table_type='TABLE' and table_Name='" + tableName + "'");

            for (Map<String, Object> commentMap : tabCommentList) {
                sql.append("\n");
                sql.append("execute immediate 'comment on table ").append(tableName).append("  is ''").append(commentMap.get("COMMENTS")).append("''';");
            }

            List<Map<String, Object>> colCommentList = DBUtil.executeQuery("select f.COMMENTS,f.COLUMN_NAME from all_col_comments f  inner join user_tab_cols c on  c.TABLE_NAME=f.TABLE_NAME  and c.COLUMN_NAME=f.COLUMN_NAME where f.TABLE_NAME='" + tableName + "' and f.OWNER='" + owner + "' order by  c.COLUMN_ID ");
            for (Map<String, Object> commentMap : colCommentList) {
                String comments = ObjectUtil.isNull(commentMap.get("COMMENTS")) ? null : String.valueOf(commentMap.get("COMMENTS"));
                if (StrUtil.isNotBlank(comments)) {
                    sql.append("\n");
                    sql.append(" execute immediate 'comment on column ").append(tableName).append('.').append(commentMap.get("COLUMN_NAME")).append("  is ''").append(commentMap.get("COMMENTS")).append("''';");
                }
            }


            sql.append(" \n commit; ");
            sql.append(" \n  end if;");
            sql.append("  \n end;");
            sql.append("\n/");

            String fileDir = "D:\\sqlfile";
            String fileName = tableName + ".sql.vm";
            //   System.out.println(sql);

            FileUtil.writeString(sql.toString(), new File(fileDir + "\\" + fileName), "UTF-8");
        }
    }

    //生成gauss语法的建表语句，一张表一个文件。
    public static void createPgSqlBaseScript(String owner) {
        //List<Map<String, Object>> tableList = DBUtil.executeQuery("select *from all_tables where owner='" + owner + "' ");
        List<Map<String, Object>> tableList = DBUtil.executeQuery("select * from all_tables where table_name in ('CRDM_BASE_CFG_PARAM','CRDM_BASE_DICT_INFO')");

        for (Map<String, Object> en : tableList) {
            //System.out.println("table_name: "+ en.get("TABLE_NAME"));
            String tableName = String.valueOf(en.get("TABLE_NAME"));
            //if (!tableName.startsWith("WF")) {
            //    continue;
            //}
            StringBuilder sql = new StringBuilder(
                    "CREATE OR REPLACE FUNCTION sp_db_pg_crs ( ) RETURNS void LANGUAGE plpgsql \n");
            sql.append("  AS $$ \n");
            sql.append("DECLARE \n");
            sql.append("  v_rowcount INTEGER; \n");
            sql.append("BEGIN \n\n ");
            sql.append(" --" + en.get("TABLE_NAME") + "建表语句  \n ");
            sql.append(" v_rowcount := NULL;  \n ");
            sql.append(" SELECT COUNT(*) \n ");
            sql.append("   INTO v_rowcount  \n ");
            sql.append("   FROM information_schema.tables\n ");
            sql.append("  WHERE TABLE_NAME = '").append(tableName.toLowerCase()).append("'; \n");

            sql.append("  IF v_rowcount = 0 THEN  \n");
            sql.append("    create table ").append(tableName).append(" ( \n");

            List<Map<String, Object>> colList = DBUtil.executeQuery(" select *from user_tab_cols f where f.TABLE_NAME='" + tableName + "' order by f.COLUMN_ID");
            //表字段生成
            int index = 0;
            for (Map<String, Object> col : colList) {

                String colName = String.valueOf(col.get("COLUMN_NAME"));
                String dataType = String.valueOf(col.get("DATA_TYPE"));
                int dataLen = Integer.parseInt(String.valueOf(col.get("DATA_LENGTH")));
                int dataPre = ObjectUtil.isNull(col.get("DATA_PRECISION")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_PRECISION")));
                int dataScale = ObjectUtil.isNull(col.get("DATA_SCALE")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_SCALE")));
                String nullFlag = String.valueOf(col.get("NULLABLE"));
                String nullValue = "";
                if ("N".equals(nullFlag)) {
                    nullValue = "NOT NULL";
                }
                if ("VARCHAR2".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("character varying ").append("(").append(dataLen).append(") default ' ' ").append(nullValue);
                }
                if ("CHAR".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("character  ").append("(").append(dataLen).append(") default ' ' ").append(nullValue);
                }
                if ("NUMBER".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("numeric");
                    sql.append("(").append(dataPre).append(',').append(dataScale).append(") default 0.0 ").append(nullValue);
                }
                if ("CLOB".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("text").append(" ").append(nullValue);

                }
                if ("DATE".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append(" timestamp without time zone ").append(" ").append(nullValue);
                }

                ///最后一条
                if (colList.size() - 1 > index) {
                    sql.append("      ").append(",");
                }
                sql.append("\n");

                index++;
            }
            sql.append("    );  ");

            //表的注释
            List<Map<String, Object>> tabCommentList = DBUtil.executeQuery("select *from all_tab_comments where owner='" + owner + "' and table_type='TABLE' and table_Name='" + tableName + "'");

            for (Map<String, Object> commentMap : tabCommentList) {
                sql.append("\n");
                sql.append("    comment on table ").append(tableName).append("  is '").append(commentMap.get("COMMENTS")).append("';");
            }

            List<Map<String, Object>> colCommentList = DBUtil.executeQuery("select f.COMMENTS,f.COLUMN_NAME from all_col_comments f  inner join user_tab_cols c on  c.TABLE_NAME=f.TABLE_NAME  and c.COLUMN_NAME=f.COLUMN_NAME where f.TABLE_NAME='" + tableName + "' and f.OWNER='" + owner + "' order by  c.COLUMN_ID ");
            for (Map<String, Object> commentMap : colCommentList) {
                String comments = ObjectUtil.isNull(commentMap.get("COMMENTS")) ? null : String.valueOf(commentMap.get("COMMENTS"));
                if (StrUtil.isNotBlank(comments)) {
                    sql.append("\n");
                    sql.append("    comment on column ").append(tableName).append('.').append(commentMap.get("COLUMN_NAME")).append("  is '").append(commentMap.get("COMMENTS")).append("';");
                }
            }
            //索引生成
            // 索引列表
            List<Map<String, Object>> idxList = DBUtil.executeQuery("select index_name,'P' as index_type from user_constraints c where c.table_name='" + tableName + "' and index_owner='" + owner + "'" +
                    "union all " +
                    "select  index_name,f.UNIQUENESS as index_type  from all_indexes f where f.table_name='" + tableName + "' " +
                    "and f.OWNER='" + owner + "' and  not exists(select * from user_constraints u where u.constraint_name=f.INDEX_NAME and f.OWNER='" + owner + "') "
            );

            for (Map<String, Object> idxMap : idxList) {
                String idxName = String.valueOf(idxMap.get("INDEX_NAME"));
                String idxType = String.valueOf(idxMap.get("INDEX_TYPE"));
                // String idxName = String.valueOf(idxMap.get("INDEX_NAME"));

                //select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
                List<Map<String, Object>> idxColList = DBUtil.executeQuery("select *from all_ind_columns f where f.TABLE_NAME='" + tableName + "' and index_name='" + idxName + "' and f.TABLE_OWNER='" + owner + "' and f.INDEX_OWNER='" + owner + "' order by column_position");
                String cols = StrUtil.join(",", idxColList.stream().map(e -> String.valueOf(e.get("COLUMN_NAME"))).collect(Collectors.toList()));
                //主键索引
                sql.append("\n");
                sql.append("    --\n");

                if ("P".equals(idxType)) {
                    sql.append("    ALTER TABLE ").append(tableName).append(" ADD constraint ").append(idxName).append(" primary key(").append(cols).append(") ;");
                } else if ("UNIQUE".equals(idxType)) {
                    sql.append("    create unique index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(") ;");
                } else {
                    sql.append("    create  index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(")  ;");
                }
            }
            sql.append("\n");
            sql.append("  END IF; \n");
            sql.append("\n\n");
            sql.append("  RETURN; \n");
            sql.append("END $$; \n");
            sql.append("SELECT sp_db_pg_crs();\n");
            sql.append("DROP FUNCTION IF EXISTS sp_db_pg_crs(); \n");

            String fileDir = "D:\\pgsqlfile";
            String fileName = tableName + ".sql.vm";
               //System.out.println(sql);

            FileUtil.writeString(sql.toString(), new File(fileDir + "\\" + fileName), "UTF-8");
        }

    }

    public static void createPgSqlBaseScript_user_views(String owner) {
        List<Map<String, Object>> tableList = DBUtil.executeQuery("select * from user_views");
        //List<Map<String, Object>> tableList = DBUtil.executeQuery("select * from all_tables where table_name in ('CRDM_BASE_CFG_PARAM','CRDM_BASE_DICT_INFO')");
        String fileDir = "D:\\pgsqlfile";
        //Create_Crk5table_Oracle.sql
        String fileName = "Create_user_views_table_Gauss"+ owner + ".sql.vm";

        StringBuilder sql = new StringBuilder("");
        for (Map<String, Object> en : tableList) {
            //System.out.println("table_name: "+ en.get("TABLE_NAME"));
            String tableName = String.valueOf(en.get("VIEW_NAME"));
            //if (!tableName.startsWith("WF")) {
            //    continue;
            //}


            sql.append("  --" + tableName + "  \n ");
            sql.append(" v_rowcount := NULL;  \n ");
            sql.append(" SELECT COUNT(*) \n ");
            sql.append("   INTO v_rowcount  \n ");
            sql.append("   FROM information_schema.views\n ");
            sql.append("  WHERE TABLE_NAME = '").append(tableName.toLowerCase()).append("'; \n");

            sql.append("  IF v_rowcount = 0 THEN  \n");
            sql.append("    create VIEW ").append(tableName).append(" AS \n");


            sql.append(String.valueOf(en.get("TEXT"))).append("; \n");
            sql.append("  END IF;").append(" \n\n");


        }
        sql.append("\n");


        //System.out.println(sql);

        FileUtil.writeString(sql.toString(), new File(fileDir + "\\" + fileName), "UTF-8");
    }

    //生成gauss语法的建表语句，所有脚本在一个文件下。
    public static void createPgSqlBaseScript_onefile(String owner) {
        List<Map<String, Object>> tableList = DBUtil.executeQuery("select *from all_tables where owner='" + owner + "' ");
        //List<Map<String, Object>> tableList = DBUtil.executeQuery("select * from all_tables where table_name in ('CRDM_BASE_CFG_PARAM','CRDM_BASE_DICT_INFO')");
        String fileDir = "D:\\pgsqlfile";
        //Create_Crk5table_Oracle.sql
        String fileName = "Create_"+ owner + "table_Gauss" + ".sql.vm";

        StringBuilder sql = new StringBuilder(
                "CREATE OR REPLACE FUNCTION sp_db_pg_crs ( ) RETURNS void LANGUAGE plpgsql \n");
        sql.append("  AS $$ \n");
        sql.append("DECLARE \n");
        sql.append("  v_rowcount INTEGER; \n");
        sql.append("BEGIN \n\n ");
        for (Map<String, Object> en : tableList) {
            //System.out.println("table_name: "+ en.get("TABLE_NAME"));
            String tableName = String.valueOf(en.get("TABLE_NAME"));
            //if (!tableName.startsWith("WF")) {
            //    continue;
            //}


            sql.append(" --" + en.get("TABLE_NAME") + "建表语句  \n ");
            sql.append(" v_rowcount := NULL;  \n ");
            sql.append(" SELECT COUNT(*) \n ");
            sql.append("   INTO v_rowcount  \n ");
            sql.append("   FROM information_schema.tables\n ");
            sql.append("  WHERE TABLE_NAME = '").append(tableName.toLowerCase()).append("'; \n");

            sql.append("  IF v_rowcount = 0 THEN  \n");
            sql.append("    create table ").append(tableName).append(" ( \n");

            List<Map<String, Object>> colList = DBUtil.executeQuery(" select *from user_tab_cols f where f.TABLE_NAME='" + tableName + "' order by f.COLUMN_ID");
            //表字段生成
            int index = 0;
            for (Map<String, Object> col : colList) {

                String colName = String.valueOf(col.get("COLUMN_NAME"));
                String dataType = String.valueOf(col.get("DATA_TYPE"));
                int dataLen = Integer.parseInt(String.valueOf(col.get("DATA_LENGTH")));
                int dataPre = ObjectUtil.isNull(col.get("DATA_PRECISION")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_PRECISION")));
                int dataScale = ObjectUtil.isNull(col.get("DATA_SCALE")) ? 0 : Integer.parseInt(String.valueOf(col.get("DATA_SCALE")));
                String nullFlag = String.valueOf(col.get("NULLABLE"));
                String nullValue = "";
                if ("N".equals(nullFlag)) {
                    nullValue = "NOT NULL";
                }
                if ("TIMESTAMP(6)".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append(" timestamp without time zone ").append(" ").append(nullValue);
                }
                if ("VARCHAR2".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("character varying ").append("(").append(dataLen).append(") default ' ' ").append(nullValue);
                }
                if ("CHAR".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("character  ").append("(").append(dataLen).append(") default ' ' ").append(nullValue);
                }
                if ("NUMBER".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("numeric");
                    sql.append("(").append(dataPre).append(',').append(dataScale).append(") default 0.0 ").append(nullValue);
                }
                if ("CLOB".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append("text").append(" ").append(nullValue);

                }
                if ("DATE".equals(dataType)) {
                    sql.append("      ").append(colName).append("   ").append(" timestamp without time zone ").append(" ").append(nullValue);
                }

                ///最后一条
                if (colList.size() - 1 > index) {
                    sql.append("      ").append(",");
                }
                sql.append("\n");

                index++;
            }
            sql.append("    );  ");

            //表的注释
            List<Map<String, Object>> tabCommentList = DBUtil.executeQuery("select *from all_tab_comments where owner='" + owner + "' and table_type='TABLE' and table_Name='" + tableName + "'");

            for (Map<String, Object> commentMap : tabCommentList) {
                sql.append("\n");
                sql.append("    comment on table ").append(tableName).append("  is '").append(commentMap.get("COMMENTS")).append("';");
            }

            List<Map<String, Object>> colCommentList = DBUtil.executeQuery("select f.COMMENTS,f.COLUMN_NAME from all_col_comments f  inner join user_tab_cols c on  c.TABLE_NAME=f.TABLE_NAME  and c.COLUMN_NAME=f.COLUMN_NAME where f.TABLE_NAME='" + tableName + "' and f.OWNER='" + owner + "' order by  c.COLUMN_ID ");
            for (Map<String, Object> commentMap : colCommentList) {
                String comments = ObjectUtil.isNull(commentMap.get("COMMENTS")) ? null : String.valueOf(commentMap.get("COMMENTS"));
                if (StrUtil.isNotBlank(comments)) {
                    sql.append("\n");
                    sql.append("    comment on column ").append(tableName).append('.').append(commentMap.get("COLUMN_NAME")).append("  is '").append(commentMap.get("COMMENTS")).append("';");
                }
            }
            //索引生成
            // 索引列表
            List<Map<String, Object>> idxList = DBUtil.executeQuery("select index_name,'P' as index_type from user_constraints c where c.table_name='" + tableName + "' and index_owner='" + owner + "'" +
                    "union all " +
                    "select  index_name,f.UNIQUENESS as index_type  from all_indexes f where f.table_name='" + tableName + "' " +
                    "and f.OWNER='" + owner + "' and  not exists(select * from user_constraints u where u.constraint_name=f.INDEX_NAME and f.OWNER='" + owner + "') "
            );

            for (Map<String, Object> idxMap : idxList) {
                String idxName = String.valueOf(idxMap.get("INDEX_NAME"));
                String idxType = String.valueOf(idxMap.get("INDEX_TYPE"));
                // String idxName = String.valueOf(idxMap.get("INDEX_NAME"));

                //select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
                //select *from all_ind_columns f where f.TABLE_NAME='LCR_BASE_INDEX_FORMULA'
                List<Map<String, Object>> idxColList = DBUtil.executeQuery("select *from all_ind_columns f where f.TABLE_NAME='" + tableName + "' and index_name='" + idxName + "' and f.TABLE_OWNER='" + owner + "' and f.INDEX_OWNER='" + owner + "' order by column_position");
                String cols = StrUtil.join(",", idxColList.stream().map(e -> String.valueOf(e.get("COLUMN_NAME"))).collect(Collectors.toList()));
                //主键索引
                sql.append("\n");
                sql.append("    --\n");

                if ("P".equals(idxType)) {
                    sql.append("    ALTER TABLE ").append(tableName).append(" ADD constraint ").append(idxName).append(" primary key(").append(cols).append(") ;");
                } else if ("UNIQUE".equals(idxType)) {
                    sql.append("    create unique index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(") ;");
                } else {
                    sql.append("    create  index  ").append(idxName).append(" on ").append(tableName).append("(").append(cols).append(")  ;");
                }

            }
            sql.append("\n");
            sql.append("  END IF; \n");
            sql.append("\n");

        }
        sql.append("\n");
        sql.append("  RETURN; \n");
        sql.append("END $$; \n");
        sql.append("SELECT sp_db_pg_crs();\n");
        sql.append("DROP FUNCTION IF EXISTS sp_db_pg_crs(); \n");


        //System.out.println(sql);

        FileUtil.writeString(sql.toString(), new File(fileDir + "\\" + fileName), "UTF-8");
    }
    public static void main(String[] args) throws ClassNotFoundException {
        //createOracleBaseScript();

        DBUtil.init("jdbc:oracle:thin:@10.20.25.160:1521:crs", "crfrmgt", "crfrmgt");
        createPgSqlBaseScript_onefile("CRFRMGT");
        createPgSqlBaseScript_user_views("CRFRMGT");
        //createOracleBaseScript("GAUSS");
    }


}
