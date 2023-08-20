package com.tesla.framework.common.util;

/**
 * Created by Jerry on 2023/7/11.
 */
public class BitUtil {

   public static boolean hasFlag(long flags, long value, boolean indexBase) {
      if (indexBase) {
         if (value >= 0 && value < 64) {
            value = 1 << value;
         } else {
            return false;
         }
      }
      return (flags & value) == value;
   }

   public static boolean hasFlag(int flags, int value, boolean indexBase) {
      if (indexBase) {
         if (value >= 0 && value < 32) {
            value = 1 << value;
         } else {
            return false;
         }
      }
      return (flags & value) == value;
   }

   public static int addFlag(int flags, int value, boolean indexBase) {
      if (indexBase) {
         if (value >= 0 && value < 32) {
            value = 1 << value;
         } else {
            throw new IllegalArgumentException("invalid index value: " + value);
         }
      }
      flags |= value;
      return flags;
   }

   public static int removeFlag(int flags, int value, boolean indexBase) {
      if (indexBase) {
         if (value >= 0 && value < 32) {
            value = 1 << value;
         } else {
            throw new IllegalArgumentException("invalid index value: " + value);
         }
      }
      flags &= ~value;
      return flags;
   }
   /**
    * 判断第n位是1还是0确定当前功能是否开启
    *
    * @param flags 云端返回的behavior
    * @param n     标志对应的位数， cong
    * @return true 当前标志位已开启, false 当前标志位关闭
    */
   public static boolean isOpenFlag(int flags, int n) {
      if (n < 1) {
         throw new IllegalArgumentException(" 位数一定要大于1");
      }
      return ((flags) & (1 << (n - 1))) > 0;
   }


   /**
    * 字节数组转int 小端模式
    */
   public static int byteArrayToIntLittleEndian(byte[] bytes) {
      int x = 0;
      for (int i = 0; i < 4; i++) {
         int b = (bytes[i] & 0xFF) << (i * 8);
         x |= b;
      }
      return x;
   }


   /**
    * 读取 指定 bit 位置的值
    * @param n
    * @param start 开始读取位置
    * @param end  结束读取位置
    * @return
    */
   public static int getBits(int n, int start, int end) {
      // 将需要读取的二进制位移动到最高位
      int mask = ((1 << (end - start + 1)) - 1) << start;
      int masked_n = n & mask;
      // 将结果向右移动start位
      return masked_n >>> start;
   }
}
