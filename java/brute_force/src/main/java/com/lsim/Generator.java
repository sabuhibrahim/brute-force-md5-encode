package com.lsim;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class Generator {
  public static final String chars = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
      "1234567890" + "!@#$%^&*()";

  public static String generateSignature(String body) {
    MessageDigest md5;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }

    md5.update(body.getBytes());

    return DatatypeConverter.printHexBinary(md5.digest()).toLowerCase();
  }

  public static String generateKey(
      String password, String login, String text, String phone, String sender) {
    return generateSignature(
        generateSignature(password)
            + login
            + text
            + phone
            + sender);
  }

  public static String bruteForce(Map<String, String> params, int length) {

    String pass = bruteForce(params, length, "");
    System.out.println(j);
    return pass;
  }

  private static int j = 0;

  private static String bruteForce(Map<String, String> params, int length, String current) {
    if (current.length() == length) {
      if (generateKey(
          current,
          params.get("login"),
          params.get("text"),
          params.get("msisdn"),
          params.get("sender")).equals(params.get("key"))) {
        return current;
      }
      return null;
    }

    for (int i = 0; i < chars.length(); i++) {
      char ch = chars.charAt(i);
      j++;
      String result = bruteForce(params, length, current + ch);
      if (result != null)
        return result;
    }

    return null;
  }
}