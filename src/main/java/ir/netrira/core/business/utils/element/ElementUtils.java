package ir.netrira.core.business.utils.element;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.cache.CacheModel;
import ir.netrira.core.application.cache.CacheUtils;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.business.utils.element.dto.ElementListDto;
import ir.netrira.core.models.application.utils.Element;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class ElementUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    private final static String elementClassName = Element.class.getSimpleName();
    private final static String elementRootClassName = Element.class.getSimpleName() + "Root";

    private static ElementRepo elementRepo;

    @Autowired
    private ElementUtils(ElementRepo elementRepo) {
        ElementUtils.elementRepo = elementRepo;
    }

    @SneakyThrows
    public static Element getElement(String code) {
        Optional<CacheModel> cacheModel = CacheUtils.get(code, elementClassName);
        if (cacheModel.isPresent()) {
            String value = cacheModel.get().getValue();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value, Element.class);
        } else {
            return getElementFromDB(code);
        }
    }

    public static ElementListDto getElementByRoot(String code) {
        Optional<CacheModel> cacheModel = CacheUtils.get(code, elementRootClassName);
        if (cacheModel.isPresent()) {
            String value = cacheModel.get().getValue();
            try {
                return new ObjectMapper().readValue(value, ElementListDto.class);
            } catch (IOException e) {
                throw  new BusinessException(ResponseConstant.ELEMENT_CODE_NOT_EXIST, ResponseConstantMessage.ELEMENT_CODE_NOT_EXIST);
            }
        } else {
            return getElementRootFromDB(code);
        }
    }

    private static Element getElementFromDB(String code) {
        return elementRepo.findByCode(code).orElseThrow(
                () -> new BusinessException(ResponseConstant.ELEMENT_CODE_NOT_EXIST, ResponseConstantMessage.ELEMENT_CODE_NOT_EXIST)
        );
    }

    private static ElementListDto getElementRootFromDB(String code) {
        List<Element> byRootCode = elementRepo.findByRootCode(code);
        return new ElementListDto().setRootCode(code).setElements(byRootCode);
    }

    public static void cacheElementsAndRootList() {
        cacheElements();
        cacheElementRoot();
    }

    private static void cacheElements() {
        Iterable<Element> all = elementRepo.findAll();
        CacheUtils.clear(elementClassName);
        for (Element element : all) {
            CacheUtils.save(element.getCode(), elementClassName, element);
        }
    }

    private static void cacheElementRoot() {
        List<String> allRootCodes = elementRepo.findAllRootCodes();
        CacheUtils.clear(elementRootClassName);
        for (String rootCode : allRootCodes) {
            List<Element> elements = elementRepo.findByRootCode(rootCode);
            CacheUtils.save(
                    rootCode,
                    elementRootClassName,
                    new ElementListDto().setRootCode(rootCode).setElements(elements)
            );
        }
    }

}
