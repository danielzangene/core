package ir.netrira.core.application.filter.auth.util.captcha;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.StraightLineNoiseProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.application.filter.auth.dto.response.CaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;

@Component
public class CaptchaUtils {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);

    private static CaptchaModelRepository captchaModelRepository;

    @Autowired
    private CaptchaUtils(CaptchaModelRepository captchaModelRepository) {
        CaptchaUtils.captchaModelRepository = captchaModelRepository;
    }

    private static String getCaptchaId(String captchaAnswer) {
        CaptchaModel captchaModel = captchaModelRepository.save(new CaptchaModel().setAnswer(captchaAnswer));
        return captchaModel.getId();
    }

    public static CaptchaResponse generateNumericalCaptchaResponse(int width, int height) {
        Captcha captcha = CaptchaUtils.generateNumericalCaptcha(width, height);
        String captchaImage = CaptchaUtils.convertToBase64(captcha);
        String captchaId = getCaptchaId(captcha.getAnswer());
        return new CaptchaResponse(captchaImage, captchaId);
    }

    private static Captcha generateNumericalCaptcha(int width, int height) {
        return new Captcha.Builder(width, height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new NumbersAnswerProducer(5), new DefaultWordRenderer())
                .addNoise(new StraightLineNoiseProducer(Color.GRAY, 4))
                .build();
    }

    private static String convertToBase64(Captcha captcha) {
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

    public static void validateCaptcha(String answer, String captchaId) {
        Optional<CaptchaModel> captchaModelOptional = captchaModelRepository.findById(captchaId);
        if (captchaModelOptional.isEmpty()) {
            throw new BusinessException(ResponseConstant.CAPTCHA_NOT_FOUND, ResponseConstantMessage.CAPTCHA_NOT_FOUND);
        }
        if (!captchaModelOptional.get().getAnswer().equals(answer)) {
            throw new BusinessException(ResponseConstant.INVALID_CAPTCHA, ResponseConstantMessage.INVALID_CAPTCHA);

        }
    }
}
