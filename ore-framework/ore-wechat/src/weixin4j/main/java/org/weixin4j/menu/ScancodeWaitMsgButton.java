package org.weixin4j.menu;

/**
 * 扫码带提示
 *
 * @author qsyang
 * @version 1.0
 */
public class ScancodeWaitMsgButton extends SingleButton {

    /**
     * 类型必须.菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;

    public ScancodeWaitMsgButton() {
    }

    public ScancodeWaitMsgButton(String key) {
        this.key = key;
    }

    public ScancodeWaitMsgButton(String name, String key) {
        this.setName(name);
        this.key = key;
    }

    public String getType() {
        return ButtonType.Scancode_Waitmsg.toString();
    }

    /**
     * 获取 菜单KEY值
     *
     * <p>
     * 类型必须.菜单KEY值，用于消息接口推送，不超过128字节</p>
     *
     * @return 菜单KEY值
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置 菜单KEY值
     *
     * <p>
     * 类型必须.菜单KEY值，用于消息接口推送，不超过128字节</p>
     *
     * @param key 菜单KEY值
     */
    public void setKey(String key) {
        this.key = key;
    }
}
