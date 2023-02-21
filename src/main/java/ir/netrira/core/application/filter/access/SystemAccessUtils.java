package ir.netrira.core.application.filter.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.cache.CacheModel;
import ir.netrira.core.application.cache.CacheUtils;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.models.application.systemaccess.GroupSystemAccess;
import ir.netrira.core.models.application.systemaccess.SystemAccess;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SystemAccessUtils {

    private static final Logger logger = LoggerFactory.getLogger(SystemAccessUtils.class);

    private final static String systemAccessClassName = SystemAccess.class.getSimpleName();
    private final static String groupSystemAccessClassName = GroupSystemAccess.class.getSimpleName();

    private static SystemAccessRepository systemAccessRepository;
    private static GroupSystemAccessRepository groupSystemAccessRepository;

    @Autowired
    private SystemAccessUtils(SystemAccessRepository systemAccessRepository, GroupSystemAccessRepository groupSystemAccessRepository) {
        SystemAccessUtils.systemAccessRepository = systemAccessRepository;
        SystemAccessUtils.groupSystemAccessRepository = groupSystemAccessRepository;
    }

    public static Boolean isSystemAccessExist(String method, String uri) {
        Optional<CacheModel> cacheModel = CacheUtils.get(
                getSystemAccessKey(method, uri),
                systemAccessClassName
        );
        return cacheModel.isPresent();
    }

    public static Boolean groupUserHasAccess(String code, String method, String uri) {
        Optional<CacheModel> cacheModel = CacheUtils.get(
                getGroupSystemAccessKey(code, method, uri),
                groupSystemAccessClassName
        );
        return cacheModel.isPresent();
    }

    @SneakyThrows
    public static SystemAccess getSystemAccess(String method, String uri) {
        Optional<CacheModel> cacheModel = CacheUtils.get(
                getSystemAccessKey(method, uri),
                groupSystemAccessClassName
        );
        if (cacheModel.isPresent()) {
            String value = cacheModel.get().getValue();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value, SystemAccess.class);
        } else {
            return getSystemAccessFromDB(method, uri);
        }
    }

    private static SystemAccess getSystemAccessFromDB(String method, String uri) {
        return systemAccessRepository.findByMethodAndRequestUri(method, uri).orElseThrow(
                () -> new BusinessException(ResponseConstant.ELEMENT_CODE_NOT_EXIST, ResponseConstantMessage.ELEMENT_CODE_NOT_EXIST)
        );
    }

    public static void cacheSystemAccessAndGroups() {
        cacheSystemAccess();
        cacheGroupSystemAccess();
    }

    private static void cacheSystemAccessAndClearFirst() {
        CacheUtils.clear(systemAccessClassName);
        cacheSystemAccess();
    }

    private static void cacheSystemAccess() {
        Iterable<SystemAccess> all = systemAccessRepository.findAll();
        for (SystemAccess access : all) {
            CacheUtils.save(
                    getSystemAccessKey(access.getMethod(), access.getRequestUri()),
                    systemAccessClassName,
                    access
            );
        }
    }

    private static String getSystemAccessKey(String method, String uri) {
        return uri + "::" + method;
    }

    private static String getGroupSystemAccessKey(String groupCode, String method, String uri) {
        return groupCode + "/" + uri + "::" + method;
    }

    private static void cacheGroupSystemAccessAndClearFirst() {
        CacheUtils.clear(groupSystemAccessClassName);
        cacheGroupSystemAccess();
    }

    private static void cacheGroupSystemAccess() {
        Iterable<GroupSystemAccess> all = groupSystemAccessRepository.findAll();
        for (GroupSystemAccess groupSystemAccess : all) {
            if (Objects.nonNull(groupSystemAccess.getAccessList()))
                for (SystemAccess access : groupSystemAccess.getAccessList()) {
                    CacheUtils.save(
                            getGroupSystemAccessKey(
                                    groupSystemAccess.getCode(),
                                    access.getMethod(),
                                    access.getRequestUri()
                            ),
                            groupSystemAccessClassName,
                            access
                    );
                }
        }
    }

}
