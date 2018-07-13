package org.oreframework.boot.autoconfigure.datasource.customize.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huangzz 2017年3月18日
 */
@ConfigurationProperties
public class MapperProperties {
	private org.oreframework.boot.autoconfigure.datasource.mapper.MapperProperties mapper;

	public org.oreframework.boot.autoconfigure.datasource.mapper.MapperProperties getMapper() {
		return mapper;
	}

	public void setMapper(org.oreframework.boot.autoconfigure.datasource.mapper.MapperProperties mapper) {
		this.mapper = mapper;
	}

}
