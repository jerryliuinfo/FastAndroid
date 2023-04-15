package com.apache.fastandroid.demo.designmode.chain2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2023/2/22.
 */
public class Interceptor {
    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public Response execute(Request request) {
        for (Handler handler : handlers) {
            Response response = handler.handle(request);
            if (response != null) {
                return response;
            }
        }
        return null;
    }
}
