package ir.netrira.core.business.utils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.models.application.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    private static ConfigModelRepository configModelRepository;

    @Autowired
    private ConfigUtils(ConfigModelRepository captchaModelRepository) {
        ConfigUtils.configModelRepository = captchaModelRepository;
    }

    public static Config saveConfig(String code, long rootId, String value) {
        Config configModel = new Config()
                .setCode(code)
                .setValue(value)
                .setRoot(configModelRepository.findById(rootId).orElseThrow(
                        UnsupportedOperationException::new
                ));
        return configModelRepository.save(configModel);
    }

    public static Config saveConfig(String code, long rootId, Object value) {
        Config configModel = new Config()
                .setCode(code)
                .setValue(getJson(value))
                .setRoot(Objects.nonNull(rootId) ? configModelRepository.findById(rootId).orElseThrow(
                        UnsupportedOperationException::new
                ) : null);
        return configModelRepository.save(configModel);
    }

    public static Config saveConfig(String code, Config root, Object value) {
        Config configModel = new Config()
                .setCode(code)
                .setValue(Objects.nonNull(value) ? getJson(value) : null)
                .setRoot(root);
        return configModelRepository.save(configModel);
    }

    private static String getJson(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String str = mapper.writeValueAsString(o);
            return str;
        } catch (Exception e) {
            throw new BusinessException(ResponseConstant.CAN_NOT_CONVERT_TO_JSON, ResponseConstantMessage.CAN_NOT_CONVERT_TO_JSON);
        }
    }

    public static Config getConfig(Long id) {
        return configModelRepository.findById(id).orElseThrow(
                () -> new BusinessException(ResponseConstant.CONFIG_NOT_FOUND, ResponseConstantMessage.CONFIG_NOT_FOUND)
        );
    }

    public static Config getConfig(String code) {
        return configModelRepository.findByCode(code).orElseThrow(
                () -> new BusinessException(ResponseConstant.CONFIG_NOT_FOUND, ResponseConstantMessage.CONFIG_NOT_FOUND)
        );
    }

    public static List<Config> getConfigs(Long rootId) {
        return configModelRepository.findAllByRoot_Id(rootId);
    }

    public static List<Config> getAllConfig() {
        return StreamSupport.stream(configModelRepository.findAll().spliterator(), Boolean.TRUE).collect(Collectors.toList());
    }

    public static List<Config> getAllMainNodes() {
        return StreamSupport.stream(configModelRepository.findAll().spliterator(), Boolean.TRUE)
                .filter(config -> Objects.isNull(config.getRoot()))
                .collect(Collectors.toList());
    }

}
