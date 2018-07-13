package org.weixin4j.message.normal;

import org.weixin4j.message.normal.NormalMessage;
import org.weixin4j.message.MsgType;

/**
 * 图片消息
 *
 * @author qsyang
 * @version 1.0
 */
public class ImageInputMessage extends NormalMessage {

    //图片链接
    private String PicUrl;
    //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MediaId;

    @Override
    public String getMsgType() {
        return MsgType.Image.toString();
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

}
