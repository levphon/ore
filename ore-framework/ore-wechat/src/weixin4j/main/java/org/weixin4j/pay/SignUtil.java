package org.weixin4j.pay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.weixin4j.Configuration;
import org.weixin4j.util.MapUtil;

/**
 * 签名算法
 *
 * @author qsyang
 * @version 1.0
 */
public class SignUtil {

    /**
     * 生成jsapi授权签名
     *
     * 签名算法
     *
     * 签名生成规则如下：
     *
     * 参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）,
     * url（当前网页的URL，不包含#及其后面部分）。
     *
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
     * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
     *
     * 这里需要注意的是所有参数名均为小写字符。
     *
     * 对string1进行sha1签名，得到signature， 字段名和字段值都采用原始值，不进行URL 转义。
     *
     * @param jsapi_ticket 微信JS接口的临时票据
     * @param noncestr 随机字符串
     * @param timestamp 时间戳
     * @param url 调用的页面Url,包括?后的部分
     * @return 成功返回授权签名，否则返回空
     */
    public static String getSignature(String jsapi_ticket, String noncestr, String timestamp, String url) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsapi_ticket", jsapi_ticket);
        params.put("noncestr", noncestr);
        params.put("timestamp", timestamp);
        params.put("url", url);
        //1.1 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, String> sortParams = MapUtil.sortAsc(params);
        //1.2 使用URL键值对的格式拼接成字符串
        String string1 = MapUtil.mapJoin(sortParams, false);
        try {
            //2 对string1进行sha1签名
            return DigestUtils.sha1Hex(string1.getBytes("UTF-8"));
        } catch (IOException e) {
        }
        return "";
    }

    /**
     * 支付签名（paySign）生成方法
     *
     * 旧版
     *
     * @param M 待签名参数
     * @return 签名paySign
     */
    public static String getOldSign(Map<String, String> M) {
        //1.1 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, String> sortParams = MapUtil.sortAsc(M);
        //1.2 使用URL键值对的格式拼接成字符串
        String string1 = MapUtil.mapJoin(sortParams, false);
        try {
            //2 对string1进行sha1签名
            return DigestUtils.sha1Hex(string1.getBytes("UTF-8"));
        } catch (IOException e) {
        }
        return "";
    }

    /**
     * getBrandWCPayRequest签名
     *
     * @param M 待签名参数
     * @param paternerKey 商户密钥
     * @return 签名paySign
     */
    public static String getSign(Map<String, String> M, String paternerKey) {
        //1.1 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, String> sortParams = MapUtil.sortAsc(M);
        //1.2 使用URL键值对的格式
        String string1 = MapUtil.mapJoin(sortParams, false);
        if (Configuration.isDebug()) {
            System.out.println("#1.生成字符串：");
            System.out.println(string1);
        }
        //拼接签名字符串
        String stringSignTemp = string1 + "&key=" + paternerKey;
        if (Configuration.isDebug()) {
            System.out.println("#2.连接商户key：");
            System.out.println(stringSignTemp);
        }
        //2.对string1进行MD5签名
        String sign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        if (Configuration.isDebug()) {
            System.out.println("#3.md5编码并转成大写：");
            System.out.println("sign=" + sign);
        }
        return sign;
    }
}
