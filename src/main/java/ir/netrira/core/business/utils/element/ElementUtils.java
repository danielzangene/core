package ir.netrira.core.business.utils.element;

import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.models.application.utils.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElementUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    private static ElementRepo elementRepo;

    @Autowired
    private ElementUtils(ElementRepo elementRepo) {
        ElementUtils.elementRepo = elementRepo;
    }

    public static Element getElement(String code) {
        return elementRepo.findByCode(code).orElseThrow(
                () -> new BusinessException(ResponseConstant.ELEMENT_CODE_NOT_EXIST, ResponseConstantMessage.ELEMENT_CODE_NOT_EXIST)
        );
    }

}
