package com.yangqugame;

import com.yangqugame.service.EchoServiceImpl;
import com.yangqugame.service.MathServiceImpl;
import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.server.rpc.RpcServer;

/**
 * Created by ging on 04/07/2017.
 * baggio
 */

public class BaggioRpcApplication extends Application {

    /**
     * 注册RPC Server 服务.
     * @throws Exception 异常
     */
    @Override
    public void init() throws Exception {
        super.init();
        RpcServer rpcServer = Jazmin.getServer(RpcServer.class);
        rpcServer.registerService(createWired(EchoServiceImpl.class));
        rpcServer.registerService(createWired(MathServiceImpl.class));
    }

}
