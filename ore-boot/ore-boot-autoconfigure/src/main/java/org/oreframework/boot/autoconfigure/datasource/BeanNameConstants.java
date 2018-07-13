package org.oreframework.boot.autoconfigure.datasource;

/**
 * @author huangzz
 * 2017年3月18日
 */
public class BeanNameConstants
{
    public final static String PRIMARY_DATASOURCE = "primaryDataSource";
    public final static String PRIMARY_SQLSESSIONFACTORY = "primarySqlSessionFactory";
    public final static String PRIMARY_SQLSESSIONTEMPLATE = "primarySqlSessionTemplate";
    
    public final static String SECONDARY_DATASOURCE = "secondaryDataSource";
    public final static String SECONDARY_SQLSESSIONFACTORY = "secondarySqlSessionFactory";
    public final static String SECONDARY_SQLSESSIONTEMPLATE = "secondarySqlSessionTemplate";
    
    public final static String PRIMARY_DRUIDPROPERTIES = "primaryDruidProperties";
    public final static String PRIMARY_MAPPERPROPERTIES = "primaryMapperProperties";
    public final static String PRIMARY_MYBATISPROPERTIES = "primaryMybatisProperties";
    public final static String SECONDARY_DRUIDPROPERTIES = "secondaryDruidProperties";
    public final static String SECONDARY_MAPPERPROPERTIES = "secondaryMapperProperties";
    public final static String SECONDARY_MYBATISPROPERTIES = "secondaryMybatisProperties";
}
