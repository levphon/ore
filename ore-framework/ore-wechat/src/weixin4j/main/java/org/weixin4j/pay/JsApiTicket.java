package org.weixin4j.pay;

import java.util.Date;

/**
 * JsApiTicket业务
 *
 * @author qsyang
 * @version 1.0
 */
public final class JsApiTicket {

    private String ticket;
    private int expires_in;
    private long exprexpiredTime;
    private long create_time;

    public JsApiTicket(String ticket, int expiresIn) {
        this.ticket = ticket;
        setExpires_in(expiresIn);
    }

    public JsApiTicket(String ticket, int expiresIn, long createTime) {
        this.ticket = ticket;
        setExpires_in(expiresIn, createTime);
    }

    /**
     * 判断js api ticket是否过期
     *
     * @return 过期返回 true,否则返回false
     */
    public boolean isExprexpired() {
        Date now = new Date();
        long nowLong = now.getTime();
        return nowLong >= exprexpiredTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    /**
     * 设置 凭证有效时间，单位：秒
     *
     * <p>
     * 为了与微信服务器保存同步，误差设置为提前1分钟，即：将创建时间提早1分钟
     * </p>
     *
     * @param expires_in 凭证有效时间，单位：秒
     */
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
        //获取当前时间
        Date now = new Date();
        //获取当前时间毫秒数 - 1 分钟，即提前1分钟过期
        create_time = now.getTime() - 60000;
        //设置下次过期时间 = 当前时间 + (凭证有效时间(秒) * 1000)
        this.exprexpiredTime = create_time + (expires_in * 1000);
    }

    /**
     * 设置 凭证有效时间，单位：秒
     *
     * <p>
     * 为了与微信服务器保存同步，误差设置为提前1分钟，即：将创建时间提早1分钟
     * </p>
     *
     * @param expires_in 凭证有效时间，单位：秒
     * @param createTime 凭证创建时间
     */
    public void setExpires_in(int expires_in, long createTime) {
        this.expires_in = expires_in;
        //获取当前时间毫秒数
        create_time = createTime - 60000;
        //设置下次过期时间 = 当前时间 + (凭证有效时间(秒) * 1000)
        this.exprexpiredTime = create_time + (expires_in * 1000);
    }

    /**
     * 获取 此次凭证创建时间 单位：毫秒数
     *
     * @return 创建时间 毫秒数
     */
    public long getCreate_time() {
        return this.create_time + 60000;
    }

    /**
     * 将数据转换为JSON数据包
     *
     * @return JSON数据包
     */
    @Override
    public String toString() {
        //对外的时间 需要加上扣掉的 60秒
        return "{\"ticket\":\"" + this.getTicket() + "\",\"expires_in\":" + this.getExpires_in() + ",\"create_time\" : " + this.getCreate_time() + "}";
    }
}
