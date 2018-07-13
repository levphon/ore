package org.weixin4j.message.normal;

import org.weixin4j.message.normal.NormalMessage;
import org.weixin4j.message.MsgType;

/**
 * 视频消息
 *
 * @author qsyang
 * @version 1.0
 */
public class VideoInputMessage extends NormalMessage {

    //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MediaId;
    //视频消息 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
    private String ThumbMediaId;

    @Override
    public String getMsgType() {
        return MsgType.Video.toString();
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String ThumbMediaId) {
        this.ThumbMediaId = ThumbMediaId;
    }
}
