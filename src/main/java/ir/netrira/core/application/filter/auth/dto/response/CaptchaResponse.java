package ir.netrira.core.application.filter.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptchaResponse {

  @JsonProperty
  private String captcha;

  @JsonProperty
  private String id;

  public CaptchaResponse(String captcha, String id) {
    this.captcha = captcha;
    this.id = id;
  }
}
