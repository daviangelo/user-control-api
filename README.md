# REST API for an user control application

#### Features:

- JWT Authentication;
- List Users;
- Get an User by ID;
- Delete an User;
- Register a new User;
- Update an existent User.

#### Resources and dependencies:

- JWT
- Spring Boot
- Spring Security
- Swagger
- DTO pattern
- JPA Repository

## API
####   authentication-controller : 
- Generate a JWT Token:  `  POST /auth`

- JWT Token refresh: `  POST /auth/refresh`

####   user-controller : 
- List Users: `   GET /api/users`

- Delete an User: `   DELETE /api/users/delete/{id}`

- Register a new User: `   POST /api/users/register`

- Update an User data: `   PUT /api/users/update/{id}`

- Get User by ID: `   GET /api/users/{id}`

## First authentication

The application uses a h2 embedded database, the data initialized for authentication is:

email: `mail@example.com`

password: `123456`
