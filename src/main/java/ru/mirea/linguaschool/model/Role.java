package ru.mirea.linguaschool.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT,
    TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
