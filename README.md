# Проект Spring Rest API с использованием Spring Security и JWT

Проект Spring Rest API представляет собой веб-приложение, которое обеспечивает безопасность с помощью Spring Security и JSON Web Token (JWT).

## Авторизация и аутентификация


### Регистрация

**Endpoint:** `POST http://localhost:3000/auth/registration`

Создает новую учетную запись пользователя.

#### Параметры

- `username`: Строка. Имя пользователя для создания учетной записи.
- `yearOfBirth`: Число. Год рождения пользователя.
- `password`: Строка. Пароль для учетной записи.

<img width="1912" alt="registration" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/39c0f62e-f70a-474c-a4da-9b070a519c1a">


### Логин

**Endpoint:** `POST http://localhost:3000/auth/login`

Аутентифицирует пользователя и выдает JWT токен для доступа к защищенным ресурсам.

#### Параметры

- `username`: Строка. Имя пользователя для входа.
- `password`: Строка. Пароль пользователя.

<img width="1912" alt="login" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/d461f4a4-e1c3-45b9-ab87-6703a218f65e">





## Ресурсы

### Hello

**Endpoint:** `GET http://localhost:3000/hello`

Этот эндпоинт предназначен для проверки аутентификации пользователя.

<img width="1912" alt="hello" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/8162c3c2-b756-4578-9b0b-0813c1a23d70">


### Admin

**Endpoint:** `GET http://localhost:3000/admin`

Этот эндпоинт предназначен для проверки авторизации пользователя как администратора.

<img width="1912" alt="admin" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/8e361962-0014-4c64-ba9f-144314a7e064">


### Show User Info

**Endpoint:** `GET http://localhost:3000/showUserInfo`

Этот эндпоинт предназначен для проверки авторизации пользователя и отображения его информации.

<img width="1912" alt="showUserInfo()" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/bc9e6940-9e71-4e9d-914f-9e6b8622ae70">

<img width="951" alt="showUserInfo() - db" src="https://github.com/devolw/rest-security-app-jwt/assets/104515806/6b76c6a7-aba6-4bc2-990a-aebed7dd6c7c">

#### Заголовки

- `Authorization`: Строка. Токен авторизации пользователя. Формат: `Bearer your_jwt_token`.
