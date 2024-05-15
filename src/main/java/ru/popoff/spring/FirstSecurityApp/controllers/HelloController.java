package ru.popoff.spring.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.popoff.spring.FirstSecurityApp.security.PersonDetails;
import ru.popoff.spring.FirstSecurityApp.services.AdminService;

@Controller
public class HelloController {

    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        // Получить объект аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Получаем данные аутентифицированного пользователя
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Выводим данные пользователя
        System.out.println(personDetails.getPerson());

        return personDetails.getUsername();
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStaff();
        return "admin";
    }
}