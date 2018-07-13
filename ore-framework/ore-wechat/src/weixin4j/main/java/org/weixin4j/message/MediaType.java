package org.weixin4j.message;

/**
 *
 * 素材类型
 *
 * @author qsyang
 */
public enum MediaType {

    /**
     * 图片
     */
    Image("image"),
    /**
     * 语音
     */
    Voice("voice"),
    /**
     * 视频
     */
    Video("video"),
    /**
     * 缩略图
     */
    Thumb("thumb");

    private String value = "";

    MediaType(String value) {
        this.value = value;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return value;
    }
}
