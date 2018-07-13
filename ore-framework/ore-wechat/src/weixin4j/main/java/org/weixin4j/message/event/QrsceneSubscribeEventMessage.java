package org.weixin4j.message.event;

import org.weixin4j.message.EventType;

/**
 * 扫描带参数二维码事件
 *
 * 用户未关注
 *
 * @author qsyang
 * @version 1.0
 */
public class QrsceneSubscribeEventMessage extends EventMessage {

    //事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String EventKey;
    //二维码的ticket，可用来换取二维码图片
    private String Ticket;

    @Override
    public String getEvent() {
        return EventType.Subscribe.toString();
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String EventKey) {
        this.EventKey = EventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String Ticket) {
        this.Ticket = Ticket;
    }

}
