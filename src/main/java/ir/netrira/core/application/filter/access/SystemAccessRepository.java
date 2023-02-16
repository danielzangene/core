package ir.netrira.core.application.filter.access;

import ir.netrira.core.models.application.personnel.User;
import ir.netrira.core.models.application.systemaccess.SystemAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemAccessRepository extends JpaRepository<SystemAccess, Long> {

//    @Query("SELECT systemAccess FROM User user " +
//            "INNER JOIN user.groupAccess AS groupAccess " +
//            "INNER JOIN groupAccess.accessList AS systemAccess " +
//            "WHERE user = ?1 " +
//            "AND systemAccess = ?2 ")
//    List<SystemAccess> userHasAccess(@Param("user") User user, @Param("systemAccess") SystemAccess systemAccess);
//
//    @Query("SELECT systemAccess FROM User user " +
//            "INNER JOIN user.groupAccess AS groupAccess " +
//            "INNER JOIN groupAccess.accessList AS systemAccess " +
//            "WHERE user.username = ?1 " +
//            "AND systemAccess.method = ?2 " +
//            "AND systemAccess.requestUri = ?3 ")
//    List<SystemAccess> userHasAccess(@Param("username") String username,
//                                     @Param("method") String method,
//                                     @Param("uri") String uri);
//
    Optional<SystemAccess> findByMethodAndRequestUri(String systemMethod, String systemRequestUri);
}
