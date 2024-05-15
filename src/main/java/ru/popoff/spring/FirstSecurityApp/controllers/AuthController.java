package ru.popoff.spring.FirstSecurityApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.popoff.spring.FirstSecurityApp.dto.AuthentificationDTO;
import ru.popoff.spring.FirstSecurityApp.dto.PersonDTO;
import ru.popoff.spring.FirstSecurityApp.models.Person;
import ru.popoff.spring.FirstSecurityApp.security.JWTUtil;
import ru.popoff.spring.FirstSecurityApp.services.RegistrationService;
import ru.popoff.spring.FirstSecurityApp.util.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                      BindingResult bindingResult) {

        // Конвертируем `PersonDTO` в `Person`
        Person person = convertToPerson(personDTO);
        // Валидация `Person`
        personValidator.validate(person, bindingResult);

        // Если есть ошибка, выбрасываем
        if (bindingResult.hasErrors()) {
            return Map.of("message", "error");
        }

        // Регистрируем пользователя
        registrationService.register(person);

        // Отправляем JWT-токен
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthentificationDTO authentificationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authentificationDTO.getUsername(),
                authentificationDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "incorrect credentials");
        }

        String token = jwtUtil.generateToken(authentificationDTO.getUsername());
        return Map.of("jwt_token", token);
    }

    // Метод конвертирует `PersonDTO` в `Person`
    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }
}