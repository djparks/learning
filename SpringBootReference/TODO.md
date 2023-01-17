# TODOs

- [X] Add Spring Developer
- [X] Default Page with links to others
- [X] End point Types
  - [X] GET
  - [X] PUT
  - [X] POST
  - [X] DELETE

- [ ] OpenAPI / Swagger
  - [X] Added and default documentation created.
  - [ ] Add some specific @ApiResonses and @Operation docs.
  - <https://www.udemy.com/course-dashboard-redirect/?course_id=2640970>
  - <https://www.baeldung.com/spring-rest-openapi-documentation>

- [ ] Testing
  - [ ] JUnit 5
    - [ ] Parameter
  - [ ] Test Containers
  - [ ] Karate or similar

- [ ] Docker-composerize
  - [ ] <https://www.baeldung.com/java-dockerize-app>

- [ ] dotenv
  - [ ] <https://dzone.com/articles/dotenv-for-java-and-the-jvm>
  - [ ] Add .env
  - [ ] Also look at: <https://infisical.com/>

- [ ] Kafka
  - [ ] Kafka Producer
  - [ ] Kafka Consumer
  - [ ] Unit Tests of Kafka
  - [ ] Integratin Tests for Kafka
    - [ ] Mock
    - [ ] Data checking
- [ ] Validation: <https://medium.com/@tericcabrel/validate-request-body-and-parameter-in-spring-boot-53ca77f97fe9>
- [ ] Prettify Index.html
- [ ] Integration Tests of Kafka
- [ ] Flag like from Work! (See below)
- [ ] Validation:
  - <https://www.baeldung.com/introduction-to-json-schema-in-java>
- [ ] Optionals: exploration
- [ ] Fix issue with DB save, think it's due to version?
- [ ] Do I need to turn off dev tools if in a production mode? More informational.
- [ ] Feature Flags:
  - <https://www.baeldung.com/spring-tests-override-properties>
  - <https://www.baeldung.com/spring-feature-flags>

## Lower Priority; or later phase

- [ ] Do React Front-end
  - <https://translate.i18next.com/>
- [ ] Acuator: <https://www.baeldung.com/spring-boot-info-actuator-custom>
  - <https://www.baeldung.com/spring-boot-info-actuator-custom>
- [ ] Add Renovate: <https://medium.com/javarevisited/renovate-dependency-management-c1f0a9072e47>
- [ ] Install Kubernetes Dashboard in Docker Desktop <https://medium.com/@teten.nugraha/install-kubernetes-dashboard-in-docker-desktop-469a2fb7c87>
- [ ] <https://www.baeldung.com/jacoco-report-exclude>
- [ ] Speed: <https://medium.com/sogetilabs-france/guess-js-how-to-speed-up-your-web-apps-b996a87fc6e>

<https://stackoverflow.com/questions/69042695/how-to-test-feature-toggle-with-mockito>

- [ ] Caching
  - <https://www.baeldung.com/spring-boot-caffeine-cache>
    - <https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching.provider>
    - Spring Boot Caching Mechanism <https://medium.com/vedity/spring-boot-caching-mechanism-8ef901147e60>

## ConditionalOnProperty

```JAVA
@ConditionalOnProperty(name = "spring.application.dataSources.postgres-datasource.enabled")
* on Configs

  @Value("${spring.application.dataSources.postgres-datasource.enabled:false}")
  private boolean postgresEnabledProperty;

  if (postgresEnabledProperty) {

      @Autowired(required = false) @Qualifier("appPostgresJdbcTemplate")
        NamedParameterJdbcTemplate postgresJdbcTemplate

spring:
application:
dataSources:
postgres-datasource:
enabled: true 
```

<https://www.howtogeek.com/devops/how-to-simplify-docker-compose-files-with-yaml-anchors-and-extensions/>
<https://onlineyamltools.com/convert-yaml-to-json>
