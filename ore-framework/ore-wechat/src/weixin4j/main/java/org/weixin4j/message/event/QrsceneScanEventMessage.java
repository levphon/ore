package org.weixin4j.message.event;

import org.weixin4j.message.EventType;

/**
 * 扫描带参数二维码事件
 * 
 * 用户已关注
 *
 * @author qsyang
 * @version 1.0
 */
public class QrsceneScanEventMessage extends EventMessage {

    //事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    private String EventKey;
    //二维码的ticket，可用来换取二维码图片
    private String Ticket;

    @Override
    public String getEvent() {
        return EventType.Scan.toString();
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
