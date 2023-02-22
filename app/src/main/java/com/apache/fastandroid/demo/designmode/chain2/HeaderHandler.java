package com.apache.fastandroid.demo.designmode.chain2;

/**
 * Created by Jerry on 2023/2/22.
 */
// 示例 Handler
public class HeaderHandler implements Handler {
   public Response handle(Request request) {
      Response response = new Response(200, "Hello World");
      return new Response(response.getStatusCode(), "Response for " + request.getUrl());
   }
}
