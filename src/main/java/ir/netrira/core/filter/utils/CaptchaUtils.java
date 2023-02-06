package ir.netrira.core.filter.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import ir.netrira.core.filter.dto.response.CaptchaResponse;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Component
public class CaptchaUtils {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);

    private static final long CAPTCHA_EXPIRY_TIME = 60000;

    private static Map captchaCodeMap = new PassiveExpiringMap<String, String>(CAPTCHA_EXPIRY_TIME);

    public static SecureRandom random = new SecureRandom();

    public static String getCaptchaId(String captchaAnswer) {
        String id = new BigInteger(64, random).toString(32);
        captchaCodeMap.put(id, captchaAnswer);
        return id;
    }

    public static CaptchaResponse generateNumericalCaptchaResponse(int width, int height) {
        Captcha captcha = CaptchaUtils.generateNumericalCaptcha(100, 50);
        String captchaImage = CaptchaUtils.convertToBase64(captcha);
        String captchaId = CaptchaUtils.getCaptchaId(captcha.getAnswer());
        return new CaptchaResponse(captchaImage, captchaId);
    }

    public static Captcha generateNumericalCaptcha(int width, int height) {
        return new Captcha.Builder(width, height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new NumbersAnswerProducer(), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    public static String convertToBase64(Captcha captcha) {
        String image = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "jpg", bos);
            byte[] byteArray = Base64.getEncoder().encode(bos.toByteArray());
            image = new String(byteArray);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return image;
    }

    public static boolean isValidCaptcha(String answer, String captchaId) {
        return Objects.isNull(answer) || !answer.equals(captchaCodeMap.get(captchaId));
    }
}
