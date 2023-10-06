package com.lsim;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lsim {
  private String username;
  private String secret;
  private String sender;
  private String phone;
  private String text;

  private String url = null;

  public static final String BASE_URL = "http://apps.lsim.az/quicksms/v1/send";

  public String generateLink() {

    String key = Generator.generateKey(secret, username, text, phone, sender);

    URI uri;
    try {
      uri = new URIBuilder(BASE_URL, Charset.forName("UTF-8"))
          .addParameter("login", username)
          .addParameter("msisdn", phone)
          .addParameter("text", text)
          .addParameter("sender", sender)
          .addParameter("key", key)
          .build();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
    this.url = uri.toString();
    return this.url;
  }

  public Map<String, String> readParams() {
    if (url == null)
      return new HashMap<String, String>();

    List<NameValuePair> params;
    HashMap<String, String> result = new HashMap<String, String>();

    try {
      params = URLEncodedUtils.parse(new URI(url), Charset.forName("UTF-8"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return new HashMap<String, String>();
    }
    for (NameValuePair param : params) {
      result.put(param.getName(), param.getValue());
    }
    return result;
  }
}
