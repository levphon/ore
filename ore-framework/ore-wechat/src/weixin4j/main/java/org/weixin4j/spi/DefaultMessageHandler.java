package org.weixin4j.spi;

import org.weixin4j.message.InputMessage;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import javax.servlet.ServletInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.weixin4j.Configuration;
import org.weixin4j.WeixinException;
import org.weixin4j.message.EventType;
import org.weixin4j.message.MsgType;
import org.weixin4j.message.OutputMessage;
import org.weixin4j.util.XStreamFactory;

/**
 *
 *
 * @author qsyang
 * @version 1.0
 */
public class DefaultMessageHandler implements IMessageHandler {

    @Override
    public String invoke(ServletInputStream inputStream) throws WeixinException {
        try {
            //将输入流转换为字符串
            String xmlMsg = XStreamFactory.inputStream2String(inputStream);
            if (Configuration.isDebug()) {
                System.out.println("获取POST的消息:");
                System.out.println(xmlMsg);
                System.out.println("------------------------");
            }
            return this.invoke(xmlMsg);
        } catch (IOException ex) {
            throw new WeixinException("输入流转换错误：", ex);
        }
    }

    @Override
    public String invoke(String inputXml) throws WeixinException {
        //输出消息对象
        OutputMessage outputMsg = null;
        try {
            JAXBContext context = JAXBContext.newInstance(InputMessage.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputMessage inputMsg = (InputMessage) unmarshaller.unmarshal(new StringReader(inputXml));
            if (Configuration.isDebug()) {
                System.out.println("将指定节点下的xml节点数据转换为对象成功!");
            }
            // 取得消息类型
            String msgType = inputMsg.getMsgType();
            if (Configuration.isDebug()) {
                System.out.println("POST的消息类型:[" + msgType + "]");
            }
            //获取普通消息处理工具类
            INormalMessageHandler normalMsgHandler = HandlerFactory.getNormalMessageHandler();
            if (msgType.equals(MsgType.Text.toString())) {
                //处理文本消息
                outputMsg = normalMsgHandler.textTypeMsg(inputMsg.toTextInputMessage());
            } else if (msgType.equals(MsgType.Image.toString())) {
                //处理图片消息
                outputMsg = normalMsgHandler.imageTypeMsg(inputMsg.toImageInputMessage());
            } else if (msgType.equals(MsgType.Voice.toString())) {
                //处理语音消息
                outputMsg = normalMsgHandler.voiceTypeMsg(inputMsg.toVoiceInputMessage());
            } else if (msgType.equals(MsgType.Video.toString())) {
                //处理视频消息
                outputMsg = normalMsgHandler.videoTypeMsg(inputMsg.toVideoInputMessage());
            } else if (msgType.equals(MsgType.ShortVideo.toString())) {
                //处理小视频消息
                outputMsg = normalMsgHandler.shortvideoTypeMsg(inputMsg.toShortVideoInputMessage());
            } else if (msgType.equals(MsgType.Location.toString())) {
                //处理地理位置消息
                outputMsg = normalMsgHandler.locationTypeMsg(inputMsg.toLocationInputMessage());
            } else if (msgType.equals(MsgType.Link.toString())) {
                //处理链接消息
                outputMsg = normalMsgHandler.linkTypeMsg(inputMsg.toLinkInputMessage());
            } else if (msgType.equals(MsgType.Event.toString())) {
                //获取事件类型
                String event = inputMsg.getEvent();
                //获取消息处理工具类
                IEventMessageHandler eventMsgHandler = HandlerFactory.getEventMessageHandler();
                //自定义菜单事件
                if (event.equals(EventType.Click.toString())) {
                    //点击菜单拉取消息时的事件推送
                    outputMsg = eventMsgHandler.click(inputMsg.toClickEventMessage());
                } else if (event.equals(EventType.View.toString())) {
                    //点击菜单跳转链接时的事件推送
                    outputMsg = eventMsgHandler.view(inputMsg.toViewEventMessage());
                } else if (event.equals(EventType.Subscribe.toString())) {
                    //关注事件
                    outputMsg = eventMsgHandler.subscribe(inputMsg.toSubscribeEventMessage());
                } else if (event.equals(EventType.Unsubscribe.toString())) {
                    //取消关注事件
                    outputMsg = eventMsgHandler.unSubscribe(inputMsg.toUnSubscribeEventMessage());
                } else if (event.equals(EventType.Scan.toString())) {
                    //扫描带参数二维码事件
                    //获取事件KEY值，判断是否关注
                    String eventKey = inputMsg.getEventKey();
                    if (eventKey.startsWith("qrscene_")) {
                        //用户未关注时，进行关注后的事件推送
                        outputMsg = eventMsgHandler.qrsceneSubscribe(inputMsg.toQrsceneSubscribeEventMessage());
                    } else {
                        //用户已关注时的事件推送
                        outputMsg = eventMsgHandler.qrsceneScan(inputMsg.toQrsceneScanEventMessage());
                    }
                } else if (event.equals(EventType.Location.toString())) {
                    //上报地理位置事件
                    outputMsg = eventMsgHandler.location(inputMsg.toLocationEventMessage());
                } else if (event.equals(EventType.Scancode_Push.toString())) {
                    //扫码推事件的事件推送
                    outputMsg = eventMsgHandler.scanCodePush(inputMsg.toScanCodePushEventMessage());
                } else if (event.equals(EventType.Scancode_Waitmsg.toString())) {
                    //扫码推事件且弹出“消息接收中”提示框的事件推送
                    outputMsg = eventMsgHandler.scanCodeWaitMsg(inputMsg.toScanCodeWaitMsgEventMessage());
                } else if (event.equals(EventType.Pic_Sysphoto.toString())) {
                    //弹出系统拍照发图的事件推送
                    outputMsg = eventMsgHandler.picSysPhoto(inputMsg.toPicSysPhotoEventMessage());
                } else if (event.equals(EventType.Pic_Photo_OR_Album.toString())) {
                    //弹出拍照或者相册发图的事件推送
                    outputMsg = eventMsgHandler.picPhotoOrAlbum(inputMsg.toPicPhotoOrAlbumEventMessage());
                } else if (event.equals(EventType.Pic_Weixin.toString())) {
                    //弹出微信相册发图器的事件推送
                    outputMsg = eventMsgHandler.picWeixin(inputMsg.toPicWeixinEventMessage());
                } else if (event.equals(EventType.Location_Select.toString())) {
                    //弹出地理位置选择器的事件推送
                    outputMsg = eventMsgHandler.locationSelect(inputMsg.toLocationSelectEventMessage());
                }
            }
            if (outputMsg != null) {
                //设置收件人消息
                setOutputMsgInfo(outputMsg, inputMsg);
            }
        } catch (IOException ex) {
            throw new WeixinException("输入流转换错误：", ex);
        } catch (NoSuchMethodException ex) {
            throw new WeixinException("没有找打对应方法：", ex);
        } catch (SecurityException ex) {
            throw new WeixinException("安全错误：", ex);
        } catch (Exception ex) {
            throw new WeixinException("系统错误：", ex);
        }
        if (outputMsg != null) {
            try {
                // 把发送发送对象转换为xml输出
                String xml = outputMsg.toXML();
                if (Configuration.isDebug()) {
                    System.out.println("POST输出消息:");
                    System.out.println(xml);
                    System.out.println("------------------------");
                }
                return xml;
            } catch (Exception ex) {
                throw new WeixinException("转换回复消息为xml时错误：", ex);
            }
        }
        return "";
    }

    //设置详细信息
    private static void setOutputMsgInfo(OutputMessage oms, InputMessage msg) throws Exception {
        // 设置发送信息
        oms.setCreateTime(new Date().getTime());
        oms.setToUserName(msg.getFromUserName());
        oms.setFromUserName(msg.getToUserName());
    }
}
