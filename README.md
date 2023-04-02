# Run application

- Open terminal in spring-cloud-security directory (near docker-compose.yml)
- `docker-compose up`
- After consul run
- Go to http://localhost:8500 (consul ui) and add in KV store properties for h2 database 
  (see **application.properties** from library/src/main/resources/application.properties).
  In KV store have to need these properties:
  1) spring.datasource.url=jdbc:h2:mem:library
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=root
     spring.datasource.password=root
     spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
     spring.jpa.hibernate.ddl-auto=update
- After setting up properties, go to directory /library and open terminal here
- `./mvnw spring-boot:run`
- After run library app go to directory /reader and open terminal here
- `./mvnw spring-boot:run`

# Postman / Library app
You can add users and books to db.
### CSRF protection

In Postman to do POST, PUT, DELETE etc., you have to add **X-XSRF-TOKEN** 
in headers with the **csrf token** value that comes from server. 
To do it automaticaly, visit https://www.baeldung.com/postman-send-csrf-token
and follow the instructions.

or you can disable **csrf** in SecurityConfig class.
~~~
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                ...
                .csrf().disable()
                .build();
    }
~~~
### Authentication
While sending requests in Postman to secured uri you have to add **Authorization** header.
- Go to Authorization tab
- Select type: **Basic Auth**

for admin:
~~~
username: admin
password: admin
~~~
for non-admin:
~~~
username: user
password: user
~~~

### REST API
Admin can:
1) Add new users:
    - **POST** localhost:8080/api/users
```
{
    "username": "type_username_here",
    "password": "type_password_here",
    "enabled": true
}
```
2) Add new books:
    - **POST** localhost:8080/api/books
```
{
    "title": "book title",
    "isbn": "book isbn",
    "text": "some long book text"
}
```
3) Assign the book to the user:
    - **POST** localhost:8080/api/users/{userId}/books?bookId={bookId}

Admin can:
1) View purchased books:
    - **GET** localhost:8080/api/users/{userId}/books
2) View details about the book:
    - **GET** localhost:8080/api/books/{bookId}

**Don't forget about authentication**

# Reader UI app
- Visit http://localhost:8080
- Add account from library: click to link and type your username and password from the library app.
- Now you can view your purchased books and view more detailed specific book.

