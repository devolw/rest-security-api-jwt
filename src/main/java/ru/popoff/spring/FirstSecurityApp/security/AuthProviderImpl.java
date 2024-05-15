//package ru.popoff.spring.FirstSecurityApp.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import ru.popoff.spring.FirstSecurityApp.services.PersonDetailsService;
//
//import java.util.Collections;
//
//
//// Провайдер аутентификации
//@Component
//public class AuthProviderImpl implements AuthenticationProvider {
//
//    private final PersonDetailsService personDetailsService;
//
//    @Autowired
//    public AuthProviderImpl(PersonDetailsService personDetailsService) {
//        this.personDetailsService = personDetailsService;
//    }
//
//    // Логика аутентификации
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // Получаем имя пользователя с формы
//        String username = authentication.getName();
//
//        // Ищем человека по имени-пользователя в БД
//        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
//
//        // Получаем пароль пользователя с формы
//        String password = authentication.getCredentials().toString();
//
//        // Сравниваем пароль полученный с формы с паролем пользователя
//        if (!password.equals(personDetails.getPassword()))
//            throw new BadCredentialsException("Incorrect password");
//
//        // Возвращаем пользователя, пароль, список прав
//        return new UsernamePasswordAuthenticationToken(personDetails, password,
//                Collections.emptyList());
//    }
//
//    // Нужен для Spring, чтобы понять для какого объекта работает Authentication
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}