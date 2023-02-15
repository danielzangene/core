package ir.netrira.core.application.utils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public static ConfigModel saveConfig(String code, String parentId, String value) {
        ConfigModel configModel = new ConfigModel()
                .setCode(code)
                .setValue(value)
                .setParentId(parentId);
        return configModelRepository.save(configModel);
    }

    @Deprecated
      public static ConfigModel saveConfig(String code, String parentId, Object value) {
        ConfigModel configModel = new ConfigModel()
                .setCode(code)
                .setValue(getJson(value))
                .setParentId(parentId);
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

    public static ConfigModel getConfig(String code) {
        Optional<ConfigModel> optionalConfig = configModelRepository.findById(code);
        if (optionalConfig.isPresent()) {
            return optionalConfig.get();
        } else {
            throw new BusinessException(ResponseConstant.CONFIG_NOT_FOUND, ResponseConstantMessage.CONFIG_NOT_FOUND);
        }
    }

    public static List<ConfigModel> getConfigs(String parentId) {
        return configModelRepository.findAllByParentId(parentId);
    }

    public static List<ConfigModel> getAllConfig() {
        return StreamSupport.stream(configModelRepository.findAll().spliterator(), Boolean.TRUE).collect(Collectors.toList());
    }

    public static List<ConfigModel> getAllMainNodes() {
        return StreamSupport.stream(configModelRepository.findAll().spliterator(), Boolean.TRUE)
                .filter(config -> Objects.isNull(config.getParentId()))
                .collect(Collectors.toList());
    }

}
