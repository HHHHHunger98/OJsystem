# Backend Part
> author: [Hhhhhunger](https://github.com/HHHHHunger98)

The backend part of OJ-System project is based on the spring-boot initial project template [springboot-init-master](https://github.com/LURENYUANSHI/springboot-init-master) by [程序员鱼皮](https://github.com/liyupi)

## Key Highlights:

- Comprehensive Tech Stack:
    1. Spring Boot 2.7.x
    2. Spring MVC
    3. MyBatis Plus
    4. Supports AOP, transactions, scheduled tasks, global exception handling, etc.

- Pre-Integrated Utility Libraries:
    Hutool, Lombok, Gson, Easy Excel, and more to boost development efficiency
- Rich Business Features:
    1. User System: Registration, login, and permission management
    2. Post Management: CRUD operations, likes, favorites
    3. Problem Management: CRUD operations, solution submission
    4. Code Judging Sandbox: Organized by business logic
- Quick Configuration & Setup:

    application.yml includes TODO markers for easy customization of database, Redis, ES, etc.
    Provides SQL scripts & Elasticsearch index mappings
    Integrated with Swagger + Knife4j for online API testing

## Quick Use

### MySQL Database

1）Modify the database configuration in `application.yml`：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ojsystem_db
    username: root
    password: 123456
```

2）run the `sql/create_table.sql` to create tables

3）build and run project，accessing the API documentation at `http://localhost:8101/api/doc.html` for online debugging and testing

![](doc/swagger.png)
