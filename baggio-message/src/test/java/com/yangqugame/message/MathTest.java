package com.yangqugame.message;

import com.alibaba.fastjson.JSON;
import jazmin.server.msg.client.MessageClient;
import jazmin.server.msg.codec.ResponseMessage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Created by Administrator on 2017/8/24 0024.
 */
public class MathTest {

    @Test
    public void add() {
        MathMessage.MathItem item = new MathMessage.MathItem();
        item.num1 = 1;
        item.num2 = 2;

        MessageClient client = new MessageClient();
        client.connect("127.0.0.1", 3001);
//        ResponseMessage responseMessage = client.invokeSync("MathMessage.add", new String[]{JSON.toJSONString(item)});
        ResponseMessage responseMessage = client.invokeSync("MathMessage.add", new String[]{"1", "2"});
//        MathMessage.MathItem result = JSON.parseObject(responseMessage.responseObject.toString(), MathMessage.MathItem.class);
        MatcherAssert.assertThat(responseMessage.responseObject, CoreMatchers.equalTo(3));
    }
}
