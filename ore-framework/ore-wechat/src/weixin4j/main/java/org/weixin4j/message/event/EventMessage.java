package org.weixin4j.message.event;

/**
 * 事件消息
 *
 * @author qsyang
 * @version 1.0
 */
public abstract class EventMessage {

    //开发者微信号
    private String ToUserName;
    //发送方帐号（一个OpenID）
    private String FromUserName;
    //消息创建时间 （整型）
    private Long CreateTime;
    //消息类型，event
    private String MsgType = "event";

    /**
     * 获取 事件类型
     *
     * @return 事件类型
     */
    public abstract String getEvent();

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String ToUserName) {
        this.ToUserName = ToUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {
        this.FromUserName = FromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getMsgType() {
        return MsgType;
    }
}
