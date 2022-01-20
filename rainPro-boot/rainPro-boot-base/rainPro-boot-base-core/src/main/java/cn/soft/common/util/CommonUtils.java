package cn.soft.common.util;

import cn.soft.common.constant.CommonConstant;
import cn.soft.common.constant.DataBaseConstant;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
@Slf4j
public class CommonUtils {

    //中文正则
    private static Pattern ZHONGWEN_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 判断文件名是否带盘符，重新处理
     *
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (Math.max(winSep, unixSep));
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "")
                .replace("#", "").replace("“", "").replace("”", "");
        //替换上传文件名字中的空格
        fileName = fileName.replaceAll("\\s", "");
        return fileName;
    }

    // java 判断字符串里是否包含中文字符
    public static boolean ifContainChinese(String str) {
        if (str.getBytes().length != str.length()) {
            Matcher m = ZHONGWEN_PATTERN.matcher(str);
            return m.find();
        }
        return false;
    }

    /**
     * 当前系统数据库类型
     */
    private static String DB_TYPE = "";
    private static DbType dbTypeEnum = null;

    /**
     * 全局获取平台数据库类型（作废了）
     *
     * @return
     */
    @Deprecated
    public static String getDatabaseType() {
        if (ConvertUtils.isNotEmpty(DB_TYPE)) {
            return DB_TYPE;
        }
        DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        try {
            return getDatabaseTypeByDataSource(dataSource);
        } catch (SQLException e) {
            //e.printStackTrace();
            log.warn(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 全局获取平台数据库类型（对应mybaisPlus枚举）
     *
     * @return
     */
    public static DbType getDatabaseTypeEnum() {
        if (ConvertUtils.isNotEmpty(dbTypeEnum)) {
            return dbTypeEnum;
        }
        try {
            DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
            dbTypeEnum = JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL());
            return dbTypeEnum;
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据数据源key获取DataSourceProperty
     *
     * @param sourceKey
     * @return
     */
    public static DataSourceProperty getDataSourceProperty(String sourceKey) {
        DynamicDataSourceProperties prop = SpringContextUtils.getApplicationContext().getBean(DynamicDataSourceProperties.class);
        Map<String, DataSourceProperty> map = prop.getDatasource();
        return map.get(sourceKey);
    }

    /**
     * 根据sourceKey 获取数据源连接
     *
     * @param sourceKey
     * @return
     * @throws SQLException
     */
    public static Connection getDataSourceConnect(String sourceKey) throws SQLException {
        if (ConvertUtils.isEmpty(sourceKey)) {
            sourceKey = "master";
        }
        DynamicDataSourceProperties prop = SpringContextUtils.getApplicationContext().getBean(DynamicDataSourceProperties.class);
        Map<String, DataSourceProperty> map = prop.getDatasource();
        DataSourceProperty db = map.get(sourceKey);
        if (db == null) {
            return null;
        }
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(db.getDriverClassName());
        ds.setUrl(db.getUrl());
        ds.setUsername(db.getUsername());
        ds.setPassword(db.getPassword());
        return ds.getConnection();
    }

    /**
     * 获取数据库类型
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    private static String getDatabaseTypeByDataSource(DataSource dataSource) throws SQLException {
        if ("".equals(DB_TYPE)) {
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData md = connection.getMetaData();
                String dbType = md.getDatabaseProductName().toLowerCase();
                if (dbType.contains("mysql")) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_MYSQL;
                } else if (dbType.contains("oracle") || dbType.contains("dm")) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_ORACLE;
                } else if (dbType.contains("sqlserver") || dbType.contains("sql server")) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_SQLSERVER;
                } else if (dbType.contains("postgresql")) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_POSTGRESQL;
                } else if (dbType.contains("mariadb")) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_MARIADB;
                } else {
                    log.error("数据库类型:[" + dbType + "]不识别!");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return DB_TYPE;

    }
}
