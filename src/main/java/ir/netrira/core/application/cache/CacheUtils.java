package ir.netrira.core.application.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CacheUtils {

    private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static CacheModelRepository cacheModelRepository;

    @Autowired
    private CacheUtils(CacheModelRepository captchaModelRepository) {
        CacheUtils.cacheModelRepository = captchaModelRepository;
    }

    public static void save(String key, String type, Object obj) {
        CacheModel captchaModel = cacheModelRepository.save(
                new CacheModel()
                        .setId(type + "-" + key)
                        .setType(type)
                        .setValue(getJson(obj))
        );
        logger.info("PERSISTED: " + key);
    }

    public static Optional<CacheModel> get(String key, String type) {
        return cacheModelRepository.findById(type + "-" + key);
    }

    public static void clear(String type) {
        cacheModelRepository.deleteAll(
                cacheModelRepository.findAllByType(type)
        );
        logger.info("CLEARED: " + type);
    }

    private static String getJson(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            throw new BusinessException(ResponseConstant.CAN_NOT_CONVERT_TO_JSON, ResponseConstantMessage.CAN_NOT_CONVERT_TO_JSON);
        }
    }
}
