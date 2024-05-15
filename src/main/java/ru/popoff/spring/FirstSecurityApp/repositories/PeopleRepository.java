package ru.popoff.spring.FirstSecurityApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.popoff.spring.FirstSecurityApp.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    // Ищем человека по имени-пользователя
    Optional<Person> findByUsername(String username);
}