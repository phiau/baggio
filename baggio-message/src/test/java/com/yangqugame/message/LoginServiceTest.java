package com.yangqugame.message;

import jazmin.server.protobuf.ProtobufMessage;
import jazmin.server.protobuf.client.ProtobufMessageClient;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import x1.proto.pb.Login;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class LoginServiceTest {

    @Test
    public void login() {
        ProtobufMessageClient client = new ProtobufMessageClient();
        client.connect("127.0.0.1", 2001);
        Login.ReqLoginMessage req = Login
                .ReqLoginMessage
                .newBuilder()
                .setAccountName("Ging")
                .setPassword("passowrd")
                .setPlatform(1)
                .build();
        ProtobufMessage msg = client.invokeSync(10001, req.toByteArray());
        MatcherAssert.assertThat(msg.getId(), CoreMatchers.equalTo(10002));
    }

}
