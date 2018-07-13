package org.weixin4j.message.event;

import org.weixin4j.message.EventType;
import org.weixin4j.message.ScanCodeInfo;

/**
 * 自定义菜单事件
 *
 * 扫码推事件且弹出“消息接收中”提示框的事件推送
 *
 * @author qsyang
 * @version 1.0
 */
public class ScanCodeWaitMsgEventMessage extends EventMessage {

    //事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;
    //扫描信息
    private ScanCodeInfo ScanCodeInfo;

    @Override
    public String getEvent() {
        return EventType.Scancode_Waitmsg.toString();
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String EventKey) {
        this.EventKey = EventKey;
    }

    public ScanCodeInfo getScanCodeInfo() {
        return ScanCodeInfo;
    }

    public void setScanCodeInfo(ScanCodeInfo ScanCodeInfo) {
        this.ScanCodeInfo = ScanCodeInfo;
    }

}
