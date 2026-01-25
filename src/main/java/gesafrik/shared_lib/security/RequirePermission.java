package gesafrik.shared_lib.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    /**
     * Liste des permissions requises
     */
    String[] value() default {};

    /**
     * Si true, l'utilisateur doit avoir TOUTES les permissions
     * Si false, l'utilisateur doit avoir AU MOINS UNE permission
     */
    boolean requireAll() default false;
}