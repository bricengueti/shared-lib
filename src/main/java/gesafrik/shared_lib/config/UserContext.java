package gesafrik.shared_lib.config;

import java.util.List;
import java.util.Optional;

public class UserContext {

    // Informations de base
    private String userId;
    private String name;
    private String email;

    // Société
    private String societyId;
    private String bdName;

    // Rôles et permissions
    private List<String> roles;
    private List<String> permissions;

    // Constructeurs
    public UserContext() {}

    public UserContext(String userId, String name, String email,
                       String societyId, String bdName,
                       List<String> roles, List<String> permissions) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.societyId = societyId;
        this.bdName = bdName;
        this.roles = roles;
        this.permissions = permissions;
    }

    // Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSocietyId() { return societyId; }
    public void setSocietyId(String societyId) { this.societyId = societyId; }

    public String getBdName() { return bdName; }
    public void setBdName(String bdName) { this.bdName = bdName; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }

    // Utilitaires
    public Optional<String> getOptionalBdName() {
        return Optional.ofNullable(bdName);
    }

    public Optional<String> getOptionalSocietyId() {
        return Optional.ofNullable(societyId);
    }
}

