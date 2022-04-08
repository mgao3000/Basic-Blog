### This is a basic blog application built with Spring Boot

### How to start this application 
#### 1. Open BolgApplication file, right click it and choose "run BlogApplication.main()", if the application is started successgully, you should find the message in the console similar like the following: 
```aidl
2022-04-08 19:19:57.235  INFO 3661521 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-04-08 19:19:57.301  INFO 3661521 --- [  restartedMain] com.devmountain.blog.BlogApplication     : Started BlogApplication in 19.168 seconds (JVM running for 22.245)
2022-04-08 19:20:01.441  INFO 3661521 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-04-08 19:20:01.443  INFO 3661521 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-04-08 19:20:01.451  INFO 3661521 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 8 ms
```
#### 2. After the application is started, go to http://localhost:8080/
#### 3. There are several pre-populated users, you can login using the following user/password
```aidl
1. admin/admin
2. David/David
3. Jordon/Jordon
4. Mike/Mike
5. Clinton/clinton
6. John/John
7. Frank/Frank
```
#### 4. H2 database is used. After the application is started, you can visit the H2 database using http://localhost:8080/h2-console








