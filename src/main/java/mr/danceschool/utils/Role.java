package mr.danceschool.utils;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DIRECTOR, INSTRUCTOR, STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
