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
package org.weixin4j;

import com.alibaba.fastjson.JSONObject;
import org.weixin4j.http.OAuth;
import org.weixin4j.http.OAuth2Token;
import org.weixin4j.http.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.weixin4j.http.HttpsClient;

/**
 * 网页授权获取用户基本信息
 *
 * @author weixin4j<weixin4j@ansitech.com>
 * @version 1.0
 */
public class OAuth2 extends WeixinSupport implements java.io.Serializable {

    /**
     * 公众号对象
     */
    protected OAuth oauth = null;
    /**
     * 网页授权对象
     */
    protected OAuth2Token oauth2Token = null;
    /**
     * 网页授权code code说明 ：
     * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     */
    private String _code;
    /**
     * 默认授权请求URL
     */
    private String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 网页授权基础支持
     */
    public OAuth2() {
    }

    /**
     * 开放的网页授权基础支持
     *
     * @param authorize_url 第三方网页授权开发URL
     */
    public OAuth2(String authorize_url) {
        this.authorize_url = authorize_url;
    }

    /**
     * 根据公众号获取网页授权AccessToken
     *
     * System property -Dweixin4j.oauth.appid and -Dweixin4j.oauth.secret
     * override this attribute.
     *
     * @param accessToken
     * @param appId
     * @param secret
     * @param scope
     * @param refresh_token
     * @param openid
     * @param code
     * @param expiresIn
     * @throws WeixinException when Weixin service or network is unavailable, or
     * the user has not authorized
     * @since Weixin 1.0.0
     */
    public void init(String accessToken, String appId, String secret, String scope, String refresh_token, String openid, String code, int expiresIn) throws WeixinException {
        if (null == accessToken || accessToken.equals("")) {
            throw new IllegalStateException("access_token is null!");
        }
        oauth2Token = new OAuth2Token(accessToken, expiresIn);
        oauth2Token.setScope(scope);
        oauth2Token.setOpenid(openid);
        oauth2Token.setRefresh_token(refresh_token);
        oauth = new OAuth(appId, secret);
        this._code = code;
    }

    private void checkToken() throws WeixinException {
        //判断是否过期，如果已过期，则发送重新登录命令
        if (oauth2Token == null || _code == null) {
            throw new WeixinException("oauth2Token or code is null,you must call login first!");
        } else {
            //已过期
            if (oauth2Token.isExprexpired()) {
                //如果用户名和密码正确，则自动登录，否则返回异常
                if (oauth != null) {
                    //自动重新获取授权
                    login(oauth.getAppId(), oauth.getSecret(), this._code);
                } else {
                    throw new WeixinException("oauth is null and oauth2Token is exprexpired, please log in again!");
                }
            }
        }
    }

    public String getOAuth2CodeBaseUrl(String appId, String returnUrl) {
        return getOAuth2CodeUrl(appId, returnUrl, "snsapi_base");
    }

    public String getOAuth2CodeUserInfoUrl(String appId, String returnUrl) {
        return getOAuth2CodeUrl(appId, returnUrl, "snsapi_userinfo");
    }

    private String getOAuth2CodeUrl(String appId, String returnUrl, String scope) {
        return getOAuth2CodeUrl(appId, returnUrl, scope, "DEFAULT");
    }

    public String getOAuth2CodeUrl(String appId, String returnUrl, String scope, String state) {
        try {
            return authorize_url + "?appid=" + appId + "&redirect_uri=" + URLEncoder.encode(returnUrl, "UTF-8") + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }

    /**
     * 向微信平台发送获取access_token请求
     *
     * @param appId 第三方用户唯一凭证
     * @param secret 第三方用户唯一凭证密钥，既appsecret
     * @param code
     * @return 用户凭证
     * @throws WeixinException
     * @since Weixin4J 1.0.0
     */
    public OAuth2Token login(String appId, String secret, String code) throws WeixinException {
        return login(appId, secret, code, "authorization_code");
    }

    /**
     * 向微信平台发送获取access_token请求
     *
     * @param appId 第三方用户唯一凭证
     * @param secret 第三方用户唯一凭证密钥，既appsecret
     * @param code
     * @param grantType 获取access_token填写authorization_code
     * @return 用户凭证
     * @throws WeixinException
     * @since Weixin4J 1.0.0
     */
    public OAuth2Token login(String appId, String secret, String code, String grantType) throws WeixinException {
        if (appId == null || secret == null || appId.equals("") || secret.equals("") || code == null || code.equals("") || grantType == null || grantType.equals("")) {
            throw new WeixinException("invalid null, appid or secret or code is null.");
        }
        //设置code
        this._code = code;//发送登陆请求验证，由于接口有频率限制，所以，一次请求后，在过期时间内，不在进行第二次请求
        //所以先从当前HttpClient内验证OAuthToken是否已验证，并且未过期
        if (this.oauth != null && this.oauth2Token != null) {
            //判断是否过期
            if (!oauth2Token.isExprexpired()) {
                //先验证用户公众号信息是否一致，不一致则需要重新登录获取
                if (this.oauth.getAppId().equals(appId) && this.oauth.getSecret().equals(secret)) {
                    //如果没有过期，则直接返回对象
                    return oauth2Token;
                }
            }
        }
        //拼接参数
        String param = "?grant_type=" + grantType + "&appid=" + appId + "&secret=" + secret + "&code=" + code;
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/sns/oauth2/access_token" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj != null) {
            if (Configuration.isDebug()) {
                System.out.println("login返回json：" + jsonObj.toString());
            }
            Object errcode = jsonObj.get("errcode");
            if (errcode != null) {
                //返回异常信息
                throw new WeixinException(getCause(Integer.parseInt(errcode.toString())));
            }
            //判断是否登录成功，并判断过期时间
            Object obj = jsonObj.get("access_token");
            //登录成功，设置accessToken和过期时间
            if (obj != null) {
                //设置公众号信息
                oauth = new OAuth(appId, secret);
                //设置凭证
                this.oauth2Token = (OAuth2Token) JSONObject.toJavaObject(jsonObj, OAuth2Token.class);
            }
        }
        return oauth2Token;
    }

    /**
     * 刷新Token
     *
     * @return
     * @throws WeixinException when Weixin service or network is unavailable, or
     * the user has not authorized
     */
    public OAuth2Token refreshToken() throws WeixinException {
        //必须先调用检查登录方法
        checkToken();
        //拼接参数
        String param = "?appid=" + this.oauth.getAppId() + "&refresh_token=" + this.oauth2Token.getRefresh_token() + "&grant_type=refresh_token";
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/sns/oauth2/refresh_token" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj != null) {
            if (Configuration.isDebug()) {
                System.out.println("login返回json：" + jsonObj.toString());
            }
            Object errcode = jsonObj.get("errcode");
            if (errcode != null) {
                //返回异常信息
                throw new WeixinException(getCause(Integer.parseInt(errcode.toString())));
            }
            //判断是否登录成功，并判断过期时间
            Object obj = jsonObj.get("access_token");
            //登录成功，设置accessToken和过期时间
            if (obj != null) {
                //设置凭证
                this.oauth2Token = (OAuth2Token) JSONObject.toJavaObject(jsonObj, OAuth2Token.class);
            }
        }
        return oauth2Token;
    }

    /**
     * 获取用户对象
     *
     * <p>
     * 通过公众号，返回用户对象，进行用户相关操作</p>
     *
     * @return 用户对象
     * @throws WeixinException when Weixin service or network is unavailable, or
     * the user has not authorized
     */
    public OAuth2User getUserInfo() throws WeixinException {
        //默认简体中文
        return getUserInfo("zh_CN");
    }

    /**
     * 获取用户对象
     *
     * <p>
     * 通过公众号，返回用户对象，进行用户相关操作</p>
     *
     * @param lang 国家地区语言版本 zh_CN 简体，zh_TW 繁体，en 英语
     * @return 用户对象
     * @throws WeixinException when Weixin service or network is unavailable, or
     * the user has not authorized
     */
    public OAuth2User getUserInfo(String lang) throws WeixinException {
        //必须先调用检查登录方法
        checkToken();
        OAuth2User user = null;
        //拼接参数
        String param = "?access_token=" + this.oauth2Token.getAccess_token() + "&openid=" + this.oauth2Token.getOpenid() + "&lang=" + lang;
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/sns/userinfo" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj != null) {
            if (Configuration.isDebug()) {
                System.out.println("getUserInfo返回json：" + jsonObj.toString());
            }
            Object errcode = jsonObj.get("errcode");
            if (errcode != null) {
                //返回异常信息
                throw new WeixinException(getCause(Integer.parseInt(errcode.toString())));
            }
            //设置公众号信息
            user = (OAuth2User) JSONObject.toJavaObject(jsonObj, OAuth2User.class);
        }
        return user;
    }
}
