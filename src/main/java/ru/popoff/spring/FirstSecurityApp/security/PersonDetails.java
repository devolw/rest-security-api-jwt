package ru.popoff.spring.FirstSecurityApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.popoff.spring.FirstSecurityApp.models.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Класс-обертка над сущностью `Person`
public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    // Имя пользователя
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // Пароль пользователя
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    // Права пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Возвращаем роль для текущего пользователя : ROLE_ADMIN || ROLE_USER
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
        // Если необходимо возвращать права пользователя : SHOW_ACCOUNT || SEND_MONEY || ...
    }

    // Этот аккаунт включен
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Пароль не просрочен
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Этот аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Этот аккаунт не просрочен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Нужно, чтобы получать данные аунтефицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}