package com.hy.commons.result; 

import java.io.Serializable;
import java.util.Map;

/**
 * Description:代表一个command处理的结果
 * @author  LiChunming
 * @version V1.0 
 * @createDateTime：2011-3-19 上午11:41:25 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public interface Result extends Serializable{
	/** 在models表中代表默认的model的key。 */
	String DEFAULT_MODEL_KEY = "_defaultModel";

	/**
	 * 请求是否成功。
	 * 
	 * @return 如果成功，则返回<code>true</code>
	 */
	boolean isSuccess();

	/**
	 * 设置请求成功标志。
	 * 
	 * @param success
	 *            成功标志
	 */
	void setSuccess(boolean success);

	/**
	 * 取得结果代码。
	 * 
	 * @return 结果代码
	 */
	String getResultCode();

	/**
	 * 设置结果代码。
	 * 
	 * @param resultCode
	 *            结果代码
	 */
	void setResultCode(String resultCode);

	/**
	 * 取得默认model对象的key。
	 * 
	 * @return 默认model对象的key
	 */
	String getDefaultModelKey();

	/**
	 * 取得model对象。
	 * 
	 * <p>
	 * 此调用相当于<code>getModels().get(getDefaultModelKey())</code>。
	 * </p>
	 * 
	 * @return model对象
	 */
	Object getDefaultModel();

	/**
	 * 设置model对象。
	 * 
	 * <p>
	 * 此调用相当于<code>getModels().put(DEFAULT_MODEL_KEY, model)</code>。
	 * </p>
	 * 
	 * @param model
	 *            model对象
	 */
	void setDefaultModel(Object model);

	/**
	 * 设置model对象。
	 * 
	 * <p>
	 * 此调用相当于<code>getModels().put(key, model)</code>。
	 * </p>
	 * 
	 * @param key
	 *            字符串key
	 * @param model
	 *            model对象
	 */
	void setDefaultModel(String key, Object model);

	/**
	 * 取得所有model对象。
	 * 
	 * @return model对象表
	 */
	Map getModels();

	public void setErrorMessages(String key, String error);

	public Map<String, String> getErrorMessages();

	public void setError(Object erro);

	public Object getError();

}
 