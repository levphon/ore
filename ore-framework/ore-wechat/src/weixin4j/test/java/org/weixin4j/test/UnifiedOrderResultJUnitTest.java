/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.weixin4j.test;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;
import static org.junit.Assert.*;
import org.weixin4j.pay.UnifiedOrderResult;

/**
 *
 * @author Yakson
 */
public class UnifiedOrderResultJUnitTest {

    public UnifiedOrderResultJUnitTest() {
    }

    @Test
    public void unifiedOrderResult() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "   <return_code><![CDATA[SUCCESS]]></return_code>\n"
                + "   <return_msg><![CDATA[OK]]></return_msg>\n"
                + "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n"
                + "   <mch_id><![CDATA[10000100]]></mch_id>\n"
                + "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\n"
                + "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\n"
                + "   <result_code><![CDATA[SUCCESS]]></result_code>\n"
                + "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\n"
                + "   <trade_type><![CDATA[JSAPI]]></trade_type>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(UnifiedOrderResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UnifiedOrderResult result = (UnifiedOrderResult) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(result.getReturn_code(), "SUCCESS");
        assertEquals(result.getReturn_msg(), "OK");
        assertEquals(result.getAppid(), "wx2421b1c4370ec43b");
        assertEquals(result.getMch_id(), "10000100");
        assertEquals(result.getNonce_str(), "IITRi8Iabbblz1Jc");
        assertEquals(result.getSign(), "7921E432F65EB8ED0CE9755F0E86D72F");
        assertEquals(result.getResult_code(), "SUCCESS");
        assertEquals(result.getPrepay_id(), "wx201411101639507cbf6ffd8b0779950874");
        assertEquals(result.getTrade_type(), "JSAPI");
    }
}
