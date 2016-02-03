# Sample Spring LDAP

This sample is a Directory portal based on [Spring LDAP].

![Alt text](https://cloud.githubusercontent.com/assets/16703873/12781330/01d25614-ca74-11e5-8d27-e880f83d311b.png)

## Frameworks & librairies

- [Spring MVC]
- [Spring LDAP]
- [Twitter Bootstrap]
- [Bootstrap Dialog]
- [jQuery DataTables]

## Attributes mapping

LDAP entry mapping is described with `@LdapEntry` annotation. 

``` java
@LdapEntry({"organizationalPerson", "person"})
public class Person ...
```

LDAP attribute mapping is described with `@LdapAttribute` annotation.

``` java
@LdapAttribute("givenName")
private String firstname;
```

## Profiles

### std [default]
This profile will directly connect to a remote LDAP using connection information from _ldap.properties_ file

### demo
This profile will start an embedded LDAP server ([Apache DS]) populated with _ldap.ldif_ file.

Connection information for the embedded LDAP server :
```
ldap.url=ldap://127.0.0.1:389
ldap.base=ou=users,o=sample
ldap.userDn=
ldap.password=
```

## Run
To run this sample :

1. Do `mvn package` in order to build springldap.war
2. Add `-Dspring.profiles.active=demo` to your Tomcat Java options (if you want to use embedded LDAP server)
3. Deploy the war on a Tomcat
4. Navigate to `http://localhost:8080/springldap`

   [Spring MVC]: <http://projects.spring.io/spring-framework/>
   [Spring LDAP]: <http://projects.spring.io/spring-ldap/>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [Bootstrap Dialog]: <https://nakupanda.github.io/bootstrap3-dialog/>
   [jQuery DataTables]: <http://datatables.net/>
   [Apache DS]: <https://directory.apache.org/apacheds/>
