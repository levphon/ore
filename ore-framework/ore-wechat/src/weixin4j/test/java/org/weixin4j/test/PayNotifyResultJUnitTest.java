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
import org.weixin4j.pay.PayNotifyResult;

/**
 *
 * @author Yakson
 */
public class PayNotifyResultJUnitTest {

    public PayNotifyResultJUnitTest() {
    }

    @Test
    public void payNotifyResult() throws JAXBException {
        String xmlStr = "<xml>\n"
                + "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n"
                + "   <attach><![CDATA[支付测试]]></attach>\n"
                + "   <bank_type><![CDATA[CFT]]></bank_type>\n"
                + "   <fee_type><![CDATA[CNY]]></fee_type>\n"
                + "   <is_subscribe><![CDATA[Y]]></is_subscribe>\n"
                + "   <mch_id><![CDATA[10000100]]></mch_id>\n"
                + "   <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n"
                + "   <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n"
                + "   <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n"
                + "   <result_code><![CDATA[SUCCESS]]></result_code>\n"
                + "   <return_code><![CDATA[SUCCESS]]></return_code>\n"
                + "   <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n"
                + "   <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n"
                + "   <time_end><![CDATA[20140903131540]]></time_end>\n"
                + "   <total_fee>1</total_fee>\n"
                + "   <trade_type><![CDATA[JSAPI]]></trade_type>\n"
                + "   <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n"
                + "</xml>";
        JAXBContext context = JAXBContext.newInstance(PayNotifyResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PayNotifyResult result = (PayNotifyResult) unmarshaller.unmarshal(new StringReader(xmlStr));
        assertEquals(result.getAppid(), "wx2421b1c4370ec43b");
        assertEquals(result.getAttach(), "支付测试");
        assertEquals(result.getBank_type(), "CFT");
        assertEquals(result.getFee_type(), "CNY");
        assertEquals(result.getIs_subscribe(), "Y");
        assertEquals(result.getMch_id(), "10000100");
        assertEquals(result.getNonce_str(), "5d2b6c2a8db53831f7eda20af46e531c");
        assertEquals(result.getOpenid(), "oUpF8uMEb4qRXf22hE3X68TekukE");
        assertEquals(result.getOut_trade_no(), "1409811653");
        assertEquals(result.getResult_code(), "SUCCESS");
        assertEquals(result.getReturn_code(), "SUCCESS");
        assertEquals(result.getSign(), "B552ED6B279343CB493C5DD0D78AB241");
        assertEquals(result.getTime_end(), "20140903131540");
        assertEquals(result.getTotal_fee(), "1");
        assertEquals(result.getTrade_type(), "JSAPI");
        assertEquals(result.getTransaction_id(), "1004400740201409030005092168");
    }
}
