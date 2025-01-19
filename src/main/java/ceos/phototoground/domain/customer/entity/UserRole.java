package ceos.phototoground.domain.customer.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    CUSTOMER("ROLE_CUSTOMER"),
    PHOTOGRAPHER("ROLE_PHOTOGRAPHER");

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