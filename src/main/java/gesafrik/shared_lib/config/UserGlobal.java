package gesafrik.shared_lib.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record UserGlobal(
        String userId,
        String societyId,
        String userName,
        String userEmail,
        String dbName,
        String employeId,
        String employeName,
        String active,
        String locked,
        Set<String> roles,
        Set<String> permissions
) {
    public static UserGlobal fromRequest(HttpServletRequest request) {
        // 🔹 Conversion en Set<String> pour éviter le mismatch
        Set<String> roles = Optional.ofNullable(request.getHeader("X-User-Roles"))
                .map(r -> Arrays.stream(r.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        Set<String> permissions = Optional.ofNullable(request.getHeader("X-User-Permissions"))
                .map(p -> Arrays.stream(p.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        return new UserGlobal(
                request.getHeader("X-User-Id"),
                request.getHeader("X-Society-Id"),
                request.getHeader("X-User-Name"),
                // 🔹 fallback : si "X-User-Email" est absent, on prend "sub" (subject du JWT)
                Optional.ofNullable(request.getHeader("X-User-Email"))
                        .orElse(request.getHeader("sub")),
                request.getHeader("X-Database-Name"),
                request.getHeader("X-Employe-Id"),
                request.getHeader("X-Employe-Name"),
                request.getHeader("X-User-Active"),
                request.getHeader("X-User-Locked"),
                roles,
                permissions
        );
    }
}
