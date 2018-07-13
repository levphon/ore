package org.oreframework.boot.autoconfigure.datasource.customize.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huangzz 2017年3月18日
 */
@ConfigurationProperties
public class MybatisProperties {

	private org.oreframework.boot.autoconfigure.datasource.mybatis.MybatisProperties mybatis;

	public org.oreframework.boot.autoconfigure.datasource.mybatis.MybatisProperties getMybatis() {
		return mybatis;
	}

	public void setMybatis(org.oreframework.boot.autoconfigure.datasource.mybatis.MybatisProperties mybatis) {
		this.mybatis = mybatis;
	}

}
