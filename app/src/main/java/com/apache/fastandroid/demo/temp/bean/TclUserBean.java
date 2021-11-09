package com.apache.fastandroid.demo.temp.bean;

import java.io.Serializable;

/**
 * Created by Jerry on 2021/11/3.
 */
public class TclUserBean implements Serializable {
   public String accountId;
   public String phone;

   public TclUserBean() {
   }

   public TclUserBean(String accountId, String phone) {
      this.accountId = accountId;
      this.phone = phone;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("{");
      sb.append("\"accountId\":\"")
              .append(accountId).append('\"');
      sb.append(",\"phone\":\"")
              .append(phone).append('\"');
      sb.append('}');
      return sb.toString();
   }
}

