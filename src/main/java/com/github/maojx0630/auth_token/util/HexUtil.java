package com.github.maojx0630.auth_token.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public final class HexUtil {
  private static final int[] DEC =
      new int[] {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15
      };
  private static final byte[] HEX =
      new byte[] {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
  private static final char[] hex = "0123456789abcdef".toCharArray();

  private HexUtil() {}

  private static int getDec(int index) {
    try {
      return DEC[index - 48];
    } catch (ArrayIndexOutOfBoundsException var2) {
      return -1;
    }
  }

  private static byte getHex(int index) {
    return HEX[index];
  }

  private static String toHexString(char c) {
    return String.valueOf(hex[(c & '\uf000') >> 12])
        + hex[(c & 3840) >> 8]
        + hex[(c & 240) >> 4]
        + hex[c & 15];
  }

  public static String toHexString(byte[] bytes) {
    if (null == bytes) {
      return null;
    } else {
      StringBuilder sb = new StringBuilder(bytes.length << 1);
      for (byte aByte : bytes) {
        sb.append(hex[(aByte & 240) >> 4]).append(hex[aByte & 15]);
      }
      return sb.toString();
    }
  }

  public static byte[] fromHexString(String input) {
    if (input == null) {
      return null;
    } else if ((input.length() & 1) == 1) {
      throw new IllegalArgumentException("hexUtils.fromHex.oddDigits");
    } else {
      char[] inputChars = input.toCharArray();
      byte[] result = new byte[input.length() >> 1];
      for (int i = 0; i < result.length; ++i) {
        int upperNibble = getDec(inputChars[2 * i]);
        int lowerNibble = getDec(inputChars[2 * i + 1]);
        if (upperNibble < 0 || lowerNibble < 0) {
          throw new IllegalArgumentException("hexUtils.fromHex.nonHex");
        }
        result[i] = (byte) ((upperNibble << 4) + lowerNibble);
      }
      return result;
    }
  }
}
