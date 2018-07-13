package org.weixin4j.message.normal;

import org.weixin4j.message.normal.NormalMessage;
import org.weixin4j.message.MsgType;

/**
 * 文本消息
 *
 * @author qsyang
 * @version 1.0
 */
public class TextInputMessage extends NormalMessage {

    //文本消息内容
    private String Content;

    public TextInputMessage(String Content) {
        this.Content = Content;
    }

    @Override
    public String getMsgType() {
        return MsgType.Text.toString();
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
