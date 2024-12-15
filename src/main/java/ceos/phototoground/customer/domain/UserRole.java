package ceos.phototoground.customer.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static UserRole fromAuthority(String authority) {
        for (UserRole role : UserRole.values()) {
            if (role.authority.equalsIgnoreCase(authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid authority: " + authority);
    }

}