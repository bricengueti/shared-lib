package gesafrik.shared_lib.config;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class UserContextTest {

    @Test
    void shouldStoreAndRetrieveBasicFields() {
        UserContext user = new UserContext();
        user.setUserId("U123");
        user.setName("Brice");
        user.setEmail("brice@example.com");

        assertEquals("U123", user.getUserId());
        assertEquals("Brice", user.getName());
        assertEquals("brice@example.com", user.getEmail());
    }

    @Test
    void shouldHandleOptionalSocietyFields() {
        UserContext user = new UserContext();
        user.setSocietyId("S456");
        user.setBdName(null);

        assertEquals(Optional.of("S456"), user.getOptionalSocietyId());
        assertEquals(Optional.empty(), user.getOptionalBdName());
    }

    @Test
    void shouldStoreRolesAndPermissions() {
        List<String> roles = List.of("ADMIN", "USER");
        List<String> perms = List.of("READ", "WRITE");

        UserContext user = new UserContext();
        user.setRoles(roles);
        user.setPermissions(perms);

        assertEquals(roles, user.getRoles());
        assertEquals(perms, user.getPermissions());
    }
}
