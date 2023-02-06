package ir.netrira.core.filter.dto.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest extends AuthRequest {
    @NotBlank
    private String captcha;
    @NotBlank
    private String captchaId;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }
}
