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
package org.weixin4j.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.weixin4j.misc.BASE64Decoder;
import org.weixin4j.misc.BASE64Encoder;

/**
 * 链接工具类
 *
 * @author weixin4j<weixin4j@ansitech.com>
 * @version 1.0
 */
public class UrlUtil {

    private final static BASE64Encoder base64En = new BASE64Encoder();

    public static String encode(String str) {
        try {
            String encode = base64En.encode(str.getBytes());
            return URLEncoder.encode(encode, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return str;
    }

    private static String decode(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        try {
            String decode = URLDecoder.decode(str, "UTF-8");
            return new String(new BASE64Decoder().decodeBuffer(decode));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return str;
    }

    /**
     * 解析加密的url
     *
     * @param param 加密的url字符串
     * @return
     */
    public static Map<String, String> parse(String param) {
        Map<String, String> paramMap = new HashMap<String, String>();
        //解密
        String str = decode(param);
        //判断解密是否错误。
        if (str != null && !str.equals("")) {
            //url参数一般以&符合分隔多个，以=号分隔参数和值
            String[] urls = str.split("&");
            for (String url : urls) {
                //原因，参数至少有一个 = 号和一个key 所以至少要有3个值,并且肯定要存在=号
                if (!url.equals("") && url.length() > 3 && url.indexOf("=") > 0) {
                    //分隔参数
                    String key = url.substring(0, url.indexOf("="));
                    String value = url.substring(url.indexOf("=") + 1);
                    //如果参数出现多个以第一个为准
                    if (!paramMap.containsKey(key)) {
                        paramMap.put(key, value);
                    }
                }
            }
        }
        return paramMap;
    }

    public static String encode(Map<String, String> map) {
        StringBuilder param = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (index > 0) {
                param.append("&");
            }
            param.append(key);
            param.append("=");
            param.append(value);
            index++;
        }
        return encode(param.toString());
    }
}
