package org.weixin4j.message.event;

import org.weixin4j.message.EventType;
import org.weixin4j.message.SendPicsInfo;

/**
 * 自定义菜单事件
 *
 * 扫码推事件的事件推送
 *
 * @author qsyang
 * @version 1.0
 */
public class PicSysPhotoEventMessage extends EventMessage {

    //事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;
    //发送的图片信息
    private SendPicsInfo SendPicsInfo;

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

    public SendPicsInfo getSendPicsInfo() {
        return SendPicsInfo;
    }

    public void setSendPicsInfo(SendPicsInfo SendPicsInfo) {
        this.SendPicsInfo = SendPicsInfo;
    }

}
