Welcome to my sample application CloudCalc
==================================================

This sample code was a coding challenge given to me to evaluate my Senior Java Full Stack design and coding ability.

Live Demo
----------
http://ec2-18-207-102-200.compute-1.amazonaws.com/

The Challenge
----------
Create an online calculator which keeps a history of calculated values and can use the previously calculated items as labels in new calculations.

Technologies in Solution
----------

FRONTEND ( Javascript / HTML )
- Material Design Components for Web ( CSS Framework )
- Material Icons ( Icons Artifacts )
- Handlebars.js ( Client-side Template Engine )
- Redux.js ( Application State Container )
- Fetch.js ( HTTP Request API )
- MathJax.js  ( Mathematical Notation Renderer )

BACKEND ( Java )
- Spring Boot
- Spring MVC 
- Thymleaf ( Server-side Template Engine )
- Spring Data ( JPA,/Hibernate )
- Spring Rest ( JAXB )
- H2 Database


How do I build and run application?
---------------
You'll need to clone your project's repository to your
local computer. If you haven't, do that first. You can find instructions in the
AWS CodeStar user guide.

1. Install maven.  See https://maven.apache.org/install.html for details.

2. Install tomcat.  See https://tomcat.apache.org/tomcat-8.0-doc/setup.html for
   details.

3. While in development you can run the following on base of your project folder:
        $ mvn spring-boot:run

4. Build the application.

        $ mvn -f pom.xml compile
        $ mvn -f pom.xml package

5. Copy the built application to the Tomcat webapp directory.  The actual
   location of that directory will vary depending on your platform and
   installation.

        $ cp target/ROOT.war <tomcat webapp directory>

6. Restart your tomcat server

5. Open http://127.0.0.1:8080/ in a web browser to view your application.
