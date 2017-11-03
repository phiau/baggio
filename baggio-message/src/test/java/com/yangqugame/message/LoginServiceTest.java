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
                .setAccessToken("Gi0xpw5HiGoa9sk7umjDGZoAOiK0")
                .build();
        ProtobufMessage msg = client.invokeSync(101100, req.toByteArray());

        Login.ReqCreateRoleMessage createRoleMessage = Login.ReqCreateRoleMessage.newBuilder().setRoleType(201).build();
        ProtobufMessage resCreate = client.invokeSync(101102, createRoleMessage.toByteArray());
        System.out.println(resCreate.getId());

//        MatcherAssert.assertThat(msg.getId(), CoreMatchers.equalTo(101101));
    }

}
