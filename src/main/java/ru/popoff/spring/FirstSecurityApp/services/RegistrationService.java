package ru.popoff.spring.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popoff.spring.FirstSecurityApp.models.Person;
import ru.popoff.spring.FirstSecurityApp.repositories.PeopleRepository;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        // Сохраняем зашифрованный пароль
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        // Назначаем роль пользователю
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
