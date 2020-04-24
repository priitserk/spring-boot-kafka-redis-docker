Experimental PoC playing around with Spring Boot 2 microservices, Apache Kafka as a Queue and REDIS as a cache all built and run with Docker. Inclues OpenAPI spec APIs and Swagger-UI for testing + Embedded Kafka in IT tests.

## PROJECT KEY POINTS

NB: project dependencies must be first built before running docker-compose up --build.
Please execute mvn clean package in prior to that (Step by step example in the PROJECT STARTUP WITH DOCKER-COMPOSE section )

* Apache Maven project (always import project by pom.xml)
* JDK 11
* Dependencies:
    Apache Kafka;
    Zookeeper;
    REDIS
    Spring Boot & Swagger
* APISs by OpenAPI 3.0 standard
* all configuration externalized, both logback and application.properties for microservices.
* No sout as specified in the assignment - logging to console done through logback configuration
* Developed and tested on Windows using  
-- OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.3+7)  
-- Apache Maven 3.6.2  
-- docker-compose version 1.25.4, build 8d51620a  


## PROJECT STRUCTURE

├── [config]           
├── [common]            
├── [consumer]         
├── [daemon]           
├── pom.xml            
└── docker-compose.yml
 
- [config]            "prod" configuration files that are copied to the unix images on startup. No configuration is assembled to jar files.
- [common]            - common code used by both modules consumer and daemon. Imported to dependent modules through pom.xml 
- [consumer]          - serves as microservice 1 - produces Swagger UI, APIs and implements Kafka producer logic
- [daemon]            - serves as microservice 2- listens and processes messages from Kafka; REDIS integration


## TESTS
Demonstrational tests have been created under [daemon] module. Both simple unit test for Monitoring Service and SocialRatingCalculator
and additionally to Kafka listener. Tests are automatically executed on mvn clean package command.


## IMPORTANT ENDPOINTS
- [daemon] up and running: http://localhost:9002/daemon-service/  
If service is up and running the following response should be there:  
{"status":"UP","version":"1.0","name":"daemon-service","buildTime":"2020-04-20T17:18:41+0000","currentTime":"2020-04-22T03:23:23+0000"}

- [consumer] up and running: http://localhost:9001/consumer-service/  
If service is up and running the following response should be there:  
{"status":"UP","version":"1.0","name":"consumer-service","buildTime":"2020-04-20T17:18:41+0000","currentTime":"2020-04-22T03:23:23+0000"}

- [SWAGGER UI] http://localhost:9001/consumer-service/swagger-ui.html  
User interface for testing provided APIs. 


## PROJECT STARTUP WITH DOCKER-COMPOSE
1. navigate to project root (same directory where docker-compose.yml and pom.xml is located)
2. verify versions and availability of docker and maven  
    $ docker-compose -version  
       *Output: docker-compose version 1.25.4, build 8d51620a*  
    $ mvn -version  
        *Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T18:06:16+03:00)*  
        *Maven home: C:\apache-maven-3.6.2\bin\..*  
        *Java version: 11.0.3, vendor: AdoptOpenJDK, runtime: C:\Program Files\AdoptOpenJDK\jdk-11.0.3.7-hotspot*  
3. Build dependencies (build the project and related dependencies)  
    $ mvn clean package  
    *results in 2 jars with dependencies under target directories of consumer and daemon module and tests are executed under daemon module*
4. Start services  
    $ docker-compose up --build
5. Verify service are running  
    After the components are built and deployed, verify that monitoring endpoints respond:  
        * http://localhost:9002/daemon-service/  
        * http://localhost:9001/consumer-service/  
6. Open up Swagger-UI  
    * http://localhost:9001/consumer-service/swagger-ui.html  
7. Try out /v1/person-data/collect service.  
    * Consider business logic constraint on Age (@Range(min = 18, max = 70))  
    * example curl: curl -X POST "http://localhost:9001/consumer-service/v1/person-data/collect" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"age\": 66, \"firstName\": \"string\", \"lastName\": \"string\"}"  

If proper age range is used, results are printed to docker console.
Information about REDIS cache entry is logged aswell to console with every response. 