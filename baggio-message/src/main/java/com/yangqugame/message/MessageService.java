package com.yangqugame.message;

import jazmin.server.protobuf.Context;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public interface MessageService {

    void invoke(Context context);

    void handler(Context context, Object o);
}
