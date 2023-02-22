package com.apache.fastandroid.demo.designmode.chain2;

/**
 * Created by Jerry on 2023/2/22.
 */

// 示例 Handler
public class LoggingHandler implements Handler {
   public Response handle(Request request) {
      System.out.println("Making request to " + request.getUrl());
      return null;
   }
}