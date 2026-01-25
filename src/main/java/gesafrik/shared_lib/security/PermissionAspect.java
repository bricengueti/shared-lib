package gesafrik.shared_lib.security;

import gesafrik.shared_lib.config.UserGlobal;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

    private static final String ROLE_ADMIN = "ADMIN";

    @Before("@annotation(gesafrik.shared_lib.security.RequirePermission) || " +
            "@within(gesafrik.shared_lib.security.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        UserGlobal user = UserGlobal.fromRequest(request);

        // Si l'utilisateur a le rôle ADMIN, on autorise tout
        if (user.roles().contains(ROLE_ADMIN)) {
            return;
        }

        RequirePermission annotation = getAnnotation(joinPoint);
        if (annotation == null) {
            return;
        }

        String[] requiredPermissions = annotation.value();
        if (requiredPermissions.length == 0) {
            return;
        }

        Set<String> userPermissions = user.permissions();
        boolean hasPermission;

        if (annotation.requireAll()) {
            // L'utilisateur doit avoir TOUTES les permissions
            hasPermission = userPermissions.containsAll(Arrays.asList(requiredPermissions));
        } else {
            // L'utilisateur doit avoir AU MOINS UNE permission
            hasPermission = Arrays.stream(requiredPermissions)
                    .anyMatch(userPermissions::contains);
        }

        if (!hasPermission) {
            throw new PermissionDeniedException(
                    "Accès refusé. Permissions requises: " + Arrays.toString(requiredPermissions)
            );
        }
    }

    private RequirePermission getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Chercher d'abord sur la méthode
        RequirePermission annotation = method.getAnnotation(RequirePermission.class);

        // Si pas trouvé, chercher sur la classe
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(RequirePermission.class);
        }

        return annotation;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new IllegalStateException("Aucune requête HTTP active dans le contexte");
        }

        return attributes.getRequest();
    }
}