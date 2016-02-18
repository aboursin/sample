# Sample Spring Security

This sample is an ordinary portal based on [Spring Security].

![Alt text](https://cloud.githubusercontent.com/assets/16703873/13141885/f1d122b4-d639-11e5-8f00-120f7d822ab9.png)

![Alt text](https://cloud.githubusercontent.com/assets/16703873/13141889/f369506a-d639-11e5-982f-ce2529ac1bdd.png)

## Frameworks & libraries

- [Spring MVC]
- [Spring Security]
- [Spring Data JPA]
- [Hibernate]
- [Waffle]
- [Twitter Bootstrap]
- [Bootstrap Dialog]

## Profiles

### MEM [default]

This will load `SecurityMEMConfiguration`.
Authentication will be based on 'in memory' users.

### DB

This will load `SecurityDBConfiguration`.
Authentication will be based on database users.
Connection & Hibernate information are set into _environment.properties_ file.

### LDAP

This will load `SecurityLDAPConfiguration`.
Authentication will be based on LDAP users.
Connection & Filter information are set into _environment.properties_ file.

### NTLM

This will load `SecurityNTLMConfiguration`.
Authentication will be based on NTLM (with [Waffle]).

## Run
To run this sample :

1. Do `mvn package` in order to build springsecurity.war
2. Add `-Dspring.profiles.active=?` to your Tomcat Java options (if you want to set your active profile)
3. Deploy the war on a Tomcat
4. Navigate to `http://localhost:8080/springsecurity`

   [Spring MVC]: <http://projects.spring.io/spring-framework/>
   [Spring Security]: <http://projects.spring.io/spring-security/>
   [Spring Data JPA]: <http://projects.spring.io/spring-data-jpa/>
   [Hibernate]: <http://hibernate.org/>
   [Waffle]: <http://dblock.github.io/waffle/>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [Bootstrap Dialog]: <https://nakupanda.github.io/bootstrap3-dialog/>
   [H2]: <http://www.h2database.com/>