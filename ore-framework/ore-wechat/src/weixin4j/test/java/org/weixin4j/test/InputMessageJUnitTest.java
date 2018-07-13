package org.weixin4j.test;

import java.io.StringReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;
import static org.junit.Assert.*;
import org.weixin4j.message.InputMessage;
import org.weixin4j.message.PicList;

/**
 *
 * @author Yakson
 */
public class InputMessageJUnitTest {

    public InputMessageJUnitTest() {
    }

    @Test
    public void text() throws JAXBException {
        String xmlStr = "<xml>"
                + " <ToUserName><![CDATA[toUser]]></ToUserName>"
                + " <FromUserName><![CDATA[fromUser]]></FromUserName>"
                + " <CreateTime>1348831860</CreateTime>"
                + " <MsgType><![CDATA[text]]></MsgType>"
                + " <Content><![CDATA[this is a test]]></Content>"
                + " <MsgId>1234567890123456</MsgId>"
                + " </xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1348831860L);
        assertEquals(inputMessage.getMsgType(), "text");
        assertEquals(inputMessage.getContent(), "this is a test");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void image() throws JAXBException {
        String xmlStr = "<xml>\n"
                + " <ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + " <FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + " <CreateTime>1348831860</CreateTime>\n"
                + " <MsgType><![CDATA[image]]></MsgType>\n"
                + " <PicUrl><![CDATA[this is a url]]></PicUrl>\n"
                + " <MediaId><![CDATA[media_id]]></MediaId>\n"
                + " <MsgId>1234567890123456</MsgId>\n"
                + " </xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1348831860L);
        assertEquals(inputMessage.getMsgType(), "image");
        assertEquals(inputMessage.getPicUrl(), "this is a url");
        assertEquals(inputMessage.getMediaId(), "media_id");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void voice() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>1357290913</CreateTime>\n"
                + "<MsgType><![CDATA[voice]]></MsgType>\n"
                + "<MediaId><![CDATA[media_id]]></MediaId>\n"
                + "<Format><![CDATA[Format]]></Format>\n"
                + "<MsgId>1234567890123456</MsgId>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1357290913L);
        assertEquals(inputMessage.getMsgType(), "voice");
        assertEquals(inputMessage.getMediaId(), "media_id");
        assertEquals(inputMessage.getFormat(), "Format");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void video() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>1357290913</CreateTime>\n"
                + "<MsgType><![CDATA[video]]></MsgType>\n"
                + "<MediaId><![CDATA[media_id]]></MediaId>\n"
                + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>\n"
                + "<MsgId>1234567890123456</MsgId>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1357290913L);
        assertEquals(inputMessage.getMsgType(), "video");
        assertEquals(inputMessage.getMediaId(), "media_id");
        assertEquals(inputMessage.getThumbMediaId(), "thumb_media_id");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void shortvideo() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>1357290913</CreateTime>\n"
                + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                + "<MediaId><![CDATA[media_id]]></MediaId>\n"
                + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>\n"
                + "<MsgId>1234567890123456</MsgId>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1357290913L);
        assertEquals(inputMessage.getMsgType(), "shortvideo");
        assertEquals(inputMessage.getMediaId(), "media_id");
        assertEquals(inputMessage.getThumbMediaId(), "thumb_media_id");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void location() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>1351776360</CreateTime>\n"
                + "<MsgType><![CDATA[location]]></MsgType>\n"
                + "<Location_X>23.134521</Location_X>\n"
                + "<Location_Y>113.358803</Location_Y>\n"
                + "<Scale>20</Scale>\n"
                + "<Label><![CDATA[位置信息]]></Label>\n"
                + "<MsgId>1234567890123456</MsgId>\n"
                + "</xml> ";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1351776360L);
        assertEquals(inputMessage.getMsgType(), "location");
        assertEquals(inputMessage.getLocation_X(), "23.134521");
        assertEquals(inputMessage.getLocation_Y(), "113.358803");
        assertEquals(inputMessage.getScale().longValue(), 20L);
        assertEquals(inputMessage.getLabel(), "位置信息");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void link() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>1351776360</CreateTime>\n"
                + "<MsgType><![CDATA[link]]></MsgType>\n"
                + "<Title><![CDATA[公众平台官网链接]]></Title>\n"
                + "<Description><![CDATA[公众平台官网链接]]></Description>\n"
                + "<Url><![CDATA[url]]></Url>\n"
                + "<MsgId>1234567890123456</MsgId>\n"
                + "</xml> ";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 1351776360L);
        assertEquals(inputMessage.getMsgType(), "link");
        assertEquals(inputMessage.getTitle(), "公众平台官网链接");
        assertEquals(inputMessage.getDescription(), "公众平台官网链接");
        assertEquals(inputMessage.getUrl(), "url");
        assertEquals(inputMessage.getMsgId().longValue(), 1234567890123456L);
    }

    @Test
    public void event_subscribe() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[FromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[subscribe]]></Event>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "FromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "subscribe");
    }

    @Test
    public void event_qrscene_subscribe() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[FromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[subscribe]]></Event>\n"
                + "<EventKey><![CDATA[qrscene_123123]]></EventKey>\n"
                + "<Ticket><![CDATA[TICKET]]></Ticket>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "FromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "subscribe");
        assertEquals(inputMessage.getEventKey(), "qrscene_123123");
        assertEquals(inputMessage.getTicket(), "TICKET");
    }

    @Test
    public void event_scan_subscribe() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[FromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[SCAN]]></Event>\n"
                + "<EventKey><![CDATA[SCENE_VALUE]]></EventKey>\n"
                + "<Ticket><![CDATA[TICKET]]></Ticket>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "FromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "SCAN".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "SCENE_VALUE");
        assertEquals(inputMessage.getTicket(), "TICKET");
    }

    @Test
    public void event_location() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[LOCATION]]></Event>\n"
                + "<Latitude>23.137466</Latitude>\n"
                + "<Longitude>113.352425</Longitude>\n"
                + "<Precision>119.385040</Precision>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "fromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "LOCATION".toLowerCase());
        assertEquals(inputMessage.getLatitude(), "23.137466");
        assertEquals(inputMessage.getLongitude(), "113.352425");
        assertEquals(inputMessage.getPrecision(), "119.385040");
    }

    @Test
    public void event_click() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[FromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[CLICK]]></Event>\n"
                + "<EventKey><![CDATA[EVENTKEY]]></EventKey>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "FromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "CLICK".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "EVENTKEY");
    }

    @Test
    public void event_view() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>\n"
                + "<FromUserName><![CDATA[FromUser]]></FromUserName>\n"
                + "<CreateTime>123456789</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[VIEW]]></Event>\n"
                + "<EventKey><![CDATA[www.qq.com]]></EventKey>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "toUser");
        assertEquals(inputMessage.getFromUserName(), "FromUser");
        assertEquals(inputMessage.getCreateTime().longValue(), 123456789L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "VIEW".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "www.qq.com");
    }

    @Test
    public void event_scancode_push() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408090502</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[scancode_push]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>\n"
                + "<ScanResult><![CDATA[1]]></ScanResult>\n"
                + "</ScanCodeInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408090502L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "scancode_push".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "6");
        assertEquals(inputMessage.getScanCodeInfo().getScanType(), "qrcode");
        assertEquals(inputMessage.getScanCodeInfo().getScanResult(), "1");
    }

    @Test
    public void event_scancode_waitmsg() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408090606</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[scancode_waitmsg]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>\n"
                + "<ScanResult><![CDATA[2]]></ScanResult>\n"
                + "</ScanCodeInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408090606L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "scancode_waitmsg".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "6");
        assertEquals(inputMessage.getScanCodeInfo().getScanType(), "qrcode");
        assertEquals(inputMessage.getScanCodeInfo().getScanResult(), "2");
    }

    @Test
    public void event_pic_sysphoto() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408090651</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[pic_sysphoto]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<SendPicsInfo><Count>1</Count>\n"
                + "<PicList><item><PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>\n"
                + "</item>\n"
                + "</PicList>\n"
                + "</SendPicsInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408090651L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "pic_sysphoto".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "6");
        assertEquals(inputMessage.getSendPicsInfo().getCount(), 1);
        List<PicList> list = inputMessage.getSendPicsInfo().getPicList();
        for (PicList pic : list) {
            assertEquals(pic.getPicMd5Sum(), "1b5f7c23b5bf75682a53e7b6d163e185");
        }
    }

    @Test
    public void event_pic_photo_or_album() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408090816</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[pic_photo_or_album]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<SendPicsInfo><Count>1</Count>\n"
                + "<PicList><item><PicMd5Sum><![CDATA[5a75aaca956d97be686719218f275c6b]]></PicMd5Sum>\n"
                + "</item>\n"
                + "</PicList>\n"
                + "</SendPicsInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408090816L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "pic_photo_or_album".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "6");
        assertEquals(inputMessage.getSendPicsInfo().getCount(), 1);
        List<PicList> list = inputMessage.getSendPicsInfo().getPicList();
        for (PicList pic : list) {
            assertEquals(pic.getPicMd5Sum(), "5a75aaca956d97be686719218f275c6b");
        }
    }

    @Test
    public void event_pic_weixin() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408090816</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[pic_weixin]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<SendPicsInfo><Count>1</Count>\n"
                + "<PicList><item><PicMd5Sum><![CDATA[5a75aaca956d97be686719218f275c6b]]></PicMd5Sum>\n"
                + "</item>\n"
                + "</PicList>\n"
                + "</SendPicsInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408090816L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "pic_weixin".toLowerCase());
        assertEquals(inputMessage.getEventKey(), "6");
        assertEquals(inputMessage.getSendPicsInfo().getCount(), 1);
        List<PicList> list = inputMessage.getSendPicsInfo().getPicList();
        for (PicList pic : list) {
            assertEquals(pic.getPicMd5Sum(), "5a75aaca956d97be686719218f275c6b");
        }
    }

    @Test
    public void event_location_select() throws JAXBException {
        String xmlStr = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>\n"
                + "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>\n"
                + "<CreateTime>1408091189</CreateTime>\n"
                + "<MsgType><![CDATA[event]]></MsgType>\n"
                + "<Event><![CDATA[location_select]]></Event>\n"
                + "<EventKey><![CDATA[6]]></EventKey>\n"
                + "<SendLocationInfo><Location_X><![CDATA[23]]></Location_X>\n"
                + "<Location_Y><![CDATA[113]]></Location_Y>\n"
                + "<Scale><![CDATA[15]]></Scale>\n"
                + "<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
                + "<Poiname><![CDATA[]]></Poiname>\n"
                + "</SendLocationInfo>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(InputMessage.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputMessage inputMessage = (InputMessage) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(inputMessage.getToUserName(), "gh_e136c6e50636");
        assertEquals(inputMessage.getFromUserName(), "oMgHVjngRipVsoxg6TuX3vz6glDg");
        assertEquals(inputMessage.getCreateTime().longValue(), 1408091189L);
        assertEquals(inputMessage.getMsgType(), "event");
        assertEquals(inputMessage.getEvent(), "location_select".toLowerCase());
        assertEquals(23, inputMessage.getSendLocationInfo().getLocation_X(), 0);
        assertEquals(113, inputMessage.getSendLocationInfo().getLocation_Y(), 0);
        assertEquals(inputMessage.getSendLocationInfo().getScale(), 15);
        assertEquals(inputMessage.getSendLocationInfo().getLabel(), " 广州市海珠区客村艺苑路 106号");
        assertEquals(inputMessage.getSendLocationInfo().getPoiname(), "");
    }
}
