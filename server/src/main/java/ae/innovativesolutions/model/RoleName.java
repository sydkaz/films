package ae.innovativesolutions.model;

/**
 * Created by Syed
 */
public enum  RoleName {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String roleLiteral;

    RoleName(String roleLiteral) {
        this.roleLiteral=roleLiteral;
    }

    @Override
    public String toString() {
        return this.roleLiteral;
    }
}
