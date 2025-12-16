package org.fastcampus.common.principal;

import lombok.Getter;
import org.fastcampus.auth.domain.UserRole;

@Getter
public class UserPricipal {
    private Long userId;
    private UserRole role;

    public UserPricipal(Long userId, String role) {
        this.userId = userId;
        this.role = UserRole.valueOf(role);
    }
}
