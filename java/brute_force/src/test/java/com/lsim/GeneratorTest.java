package com.lsim;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import static com.lsim.Generator.bruteForce;

public class GeneratorTest {
  @Test
  public void testCaseWithLength3() {
    String password = "aaa";
    int charCount = 3;
    Lsim lsim = Lsim.builder()
        .username("lsim")
        .secret(password)
        .sender("example")
        .phone("994000000000")
        .text("lorem ipsum dolor")
        .build();

    lsim.generateLink();

    Map<String, String> params = lsim.readParams();

    if (params.isEmpty()) {
      System.out.println("Params is empty: " + params);
      return;
    }

    String resulString = bruteForce(params, charCount);

    assertTrue("Password could not find", password.equals(resulString));
  }

  @Test
  public void testCaseWithLength4() {
    String password = "aaaa";
    int charCount = 4;
    Lsim lsim = Lsim.builder()
        .username("lsim")
        .secret(password)
        .sender("example")
        .phone("994000000000")
        .text("lorem ipsum dolor")
        .build();

    lsim.generateLink();

    Map<String, String> params = lsim.readParams();

    if (params.isEmpty()) {
      System.out.println("Params is empty: " + params);
      return;
    }

    String resulString = bruteForce(params, charCount);

    assertTrue("Password could not find", password.equals(resulString));
  }

  @Test
  public void testCaseWithLength5() {
    String password = "aaaaa";
    int charCount = 5;
    Lsim lsim = Lsim.builder()
        .username("lsim")
        .secret(password)
        .sender("example")
        .phone("994000000000")
        .text("lorem ipsum dolor")
        .build();

    lsim.generateLink();

    Map<String, String> params = lsim.readParams();

    if (params.isEmpty()) {
      System.out.println("Params is empty: " + params);
      return;
    }

    String resulString = bruteForce(params, charCount);

    assertTrue("Password could not find", password.equals(resulString));
  }
}
