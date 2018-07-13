/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.http;

import com.alibaba.fastjson.JSONObject;
import org.weixin4j.WeixinException;
import java.util.Date;

/**
 * 网页授权对象
 *
 * @author weixin4j<weixin4j@ansitech.com>
 * @version 1.0
 */
public final class OAuth2Token {

    private String access_token;
    private int expires_in = 7200;
    private String refresh_token;
    private long exprexpiredTime;
    private String openid;
    private String scope;

    public OAuth2Token() {
    }

    public OAuth2Token(String accessToken, int exprexpiredTime) {
        this.access_token = accessToken;
        setExpires_in(exprexpiredTime);
    }

    /**
     * 通过输出对象，从输出对象转换为JSON对象，后获取JSON数据包
     *
     * <p>
     * 要求输出内容为一个标准的JSON数据包，正常情况下， 微信会返回下述JSON数据包给公众号：
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}</p>
     *
     * @param response
     * @throws WeixinException
     */
    public OAuth2Token(Response response) throws WeixinException {
        this(response.asJSONObject());
    }

    /**
     * 通过微信公众平台返回JSON对象创建凭证对象
     *
     * <p>
     * 正常情况下，微信会返回下述JSON数据包给公众号：
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}</p>
     *
     * @param jsonObj JSON数据包
     * @throws WeixinException
     */
    public OAuth2Token(JSONObject jsonObj) throws WeixinException {
        this.access_token = jsonObj.getString("access_token");
        //根据当前时间的毫秒数+获取的秒数计算过期时间
        int expiresIn = jsonObj.getIntValue("expires_in");
        setExpires_in(expiresIn);
    }

    /**
     * 获取用户凭证
     *
     * @return 用户凭证
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * 设置用户凭证
     *
     * @param access_token 用户凭证
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * 判断用户凭证是否过期
     *
     * @return 过期返回 true,否则返回false
     */
    public boolean isExprexpired() {
        Date now = new Date();
        long nowLong = now.getTime();
        return nowLong > exprexpiredTime;
    }

    /**
     * 将数据转换为JSON数据包
     *
     * @return JSON数据包
     */
    @Override
    public String toString() {
        return "{\"access_token\"=\"" + this.access_token + "\",\"expires_in\"=" + this.getExpires_in() + "}";
    }

    /**
     * @return the expires_in
     */
    public int getExpires_in() {
        return expires_in;
    }

    /**
     * @param expires_in the expires_in to set
     */
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
        //获取当前时间
        Date now = new Date();
        //获取当前时间毫秒数
        long nowLong = now.getTime();
        //设置下次过期时间 = 当前时间 + (凭证有效时间(秒) * 1000)
        this.exprexpiredTime = nowLong + (expires_in * 1000);
    }

    /**
     * @return the refresh_token
     */
    public String getRefresh_token() {
        return refresh_token;
    }

    /**
     * @param refresh_token the refresh_token to set
     */
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    /**
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid the openid to set
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
