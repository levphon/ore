package org.weixin4j.message.event;

import org.weixin4j.message.EventType;

/**
 * 关注事件
 *
 * @author qsyang
 * @version 1.0
 */
public class SubscribeEventMessage extends EventMessage {

    @Override
    public String getEvent() {
        return EventType.Subscribe.toString();
    }

}
