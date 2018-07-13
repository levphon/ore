package org.weixin4j.spi;

import org.weixin4j.message.OutputMessage;
import org.weixin4j.message.event.ClickEventMessage;
import org.weixin4j.message.event.EventMessage;
import org.weixin4j.message.event.LocationEventMessage;
import org.weixin4j.message.event.LocationSelectEventMessage;
import org.weixin4j.message.event.PicPhotoOrAlbumEventMessage;
import org.weixin4j.message.event.PicSysPhotoEventMessage;
import org.weixin4j.message.event.PicWeixinEventMessage;
import org.weixin4j.message.event.QrsceneScanEventMessage;
import org.weixin4j.message.event.QrsceneSubscribeEventMessage;
import org.weixin4j.message.event.ScanCodePushEventMessage;
import org.weixin4j.message.event.ScanCodeWaitMsgEventMessage;
import org.weixin4j.message.event.SubscribeEventMessage;
import org.weixin4j.message.event.UnSubscribeEventMessage;
import org.weixin4j.message.event.ViewEventMessage;
import org.weixin4j.message.output.TextOutputMessage;

/**
 * DefaultEventMessageHandler业务
 *
 * @author qsyang
 * @version 1.0
 */
public class DefaultEventMessageHandler implements IEventMessageHandler {

    private OutputMessage allType(EventMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }

    @Override
    public OutputMessage subscribe(SubscribeEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage unSubscribe(UnSubscribeEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage qrsceneSubscribe(QrsceneSubscribeEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage qrsceneScan(QrsceneScanEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage location(LocationEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage click(ClickEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage view(ViewEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage scanCodePush(ScanCodePushEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage scanCodeWaitMsg(ScanCodeWaitMsgEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage picSysPhoto(PicSysPhotoEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage picPhotoOrAlbum(PicPhotoOrAlbumEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage picWeixin(PicWeixinEventMessage msg) {
        return allType(msg);
    }

    @Override
    public OutputMessage locationSelect(LocationSelectEventMessage msg) {
        return allType(msg);
    }

}
