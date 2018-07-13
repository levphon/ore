package org.oreframework.boot.autoconfigure.diamond.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huangzz 2017年3月18日
 */
@ConfigurationProperties("ore.diamond")
public class DiamondProperty {
	public String group;

	private String dataId;

	private String env;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}
