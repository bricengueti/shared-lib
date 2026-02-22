package gesafrik.shared_lib.security;

import gesafrik.shared_lib.config.UserContextHolder;
import gesafrik.shared_lib.config.UserGlobal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

    private static final Logger logger = LoggerFactory.getLogger(PermissionAspect.class);
    private static final String ROLE_ADMIN = "ADMIN";

    @Before("@annotation(gesafrik.shared_lib.security.RequirePermission) || " +
            "@within(gesafrik.shared_lib.security.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        UserGlobal user = UserContextHolder.get();

        if (user == null) {
            logger.warn("Tentative d'accès sans authentification sur: {}", joinPoint.getSignature().toShortString());
            throw new PermissionDeniedException("Utilisateur non authentifié");
        }

        // ADMIN bypass
        if (user.roles() != null && user.roles().contains(ROLE_ADMIN)) {
            logger.debug("Accès ADMIN autorisé pour {} sur {}", user.userName(), joinPoint.getSignature().toShortString());
            return;
        }

        RequirePermission annotation = getAnnotation(joinPoint);
        if (annotation == null || annotation.value().length == 0) {
            return;
        }

        String[] requiredPermissions = annotation.value();
        Set<String> userPermissions = user.permissions();

        boolean hasPermission = annotation.requireAll()
                ? userPermissions.containsAll(Arrays.asList(requiredPermissions))
                : Arrays.stream(requiredPermissions).anyMatch(userPermissions::contains);

        if (!hasPermission) {
            logger.warn("Accès refusé pour {} sur {}. Requis: {}, Possède: {}",
                    user.userName(),
                    joinPoint.getSignature().toShortString(),
                    Arrays.toString(requiredPermissions),
                    userPermissions);
            throw new PermissionDeniedException(
                    String.format("Accès refusé. Permissions requises: %s",
                            Arrays.toString(requiredPermissions))
            );
        }

        logger.debug("Accès autorisé pour {} sur {}", user.userName(), joinPoint.getSignature().toShortString());
    }

    private RequirePermission getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RequirePermission annotation = method.getAnnotation(RequirePermission.class);

        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(RequirePermission.class);
        }

        return annotation;
    }
}