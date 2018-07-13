package org.weixin4j.message.event;

import org.weixin4j.message.EventType;
import org.weixin4j.message.SendLocationInfo;

/**
 * 自定义菜单事件
 *
 * 弹出地理位置选择器的事件推送
 *
 * @author qsyang
 * @version 1.0
 */
public class LocationSelectEventMessage extends EventMessage {

    //事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;
    //发送的位置信息
    private SendLocationInfo SendLocationInfo;

    @Override
    public String getEvent() {
        return EventType.Pic_Sysphoto.toString();
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String EventKey) {
        this.EventKey = EventKey;
    }

    public SendLocationInfo getSendLocationInfo() {
        return SendLocationInfo;
    }

    public void setSendLocationInfo(SendLocationInfo SendLocationInfo) {
        this.SendLocationInfo = SendLocationInfo;
    }

}
