package com.apache.fastandroid.demo.designmode.chain2;

/**
 * Created by Jerry on 2023/2/22.
 */
// 示例 Response 类
public class Response {
   private int statusCode;
   private String content;

   public Response(int statusCode, String content) {
      this.statusCode = statusCode;
      this.content = content;
   }

   public int getStatusCode() {
      return statusCode;
   }

   public String getContent() {
      return content;
   }
}
