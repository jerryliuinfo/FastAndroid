package com.apache.fastandroid.demo.designmode.chain2;

/**
 * Created by Jerry on 2023/2/22.
 */
class ChainDemo {
   public static void main(String[] args) {
      Interceptor interceptor = new Interceptor();
      interceptor.addHandler(new LoggingHandler());
      interceptor.addHandler(new HeaderHandler());

      Request request = new Request("https://www.example.com");
      Response response = interceptor.execute(request);
      System.out.println(response);
   }
}
