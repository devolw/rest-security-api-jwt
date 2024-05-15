package ru.popoff.spring.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.popoff.spring.FirstSecurityApp.models.Person;
import ru.popoff.spring.FirstSecurityApp.repositories.PeopleRepository;
import ru.popoff.spring.FirstSecurityApp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // Метод поиска человека по имени-пользователя
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        // Если человек не найден
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        // Если человек найден
        return new PersonDetails(person.get());
    }
}
