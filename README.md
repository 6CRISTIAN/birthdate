# birthdate
This project calculates the age and days remaining to a person's birthday; if it's the birthday, the program gives a sweet poem.

1. the back-end project is made with Java-8 using SpringBoot
    - path: birthdate/back-end/birthdate

2. the front-end project is made with TypeScript using Angular
    - path: birthdate/front-end/birthdate

## How to deploy back-end

* steps:
    1. you need to be in the birthdate/back-end/birthdate folder from terminal.
    2. run this command: "mvn spring-boot:run".

## How to deploy front-end

* steps:
    1. you need to be in the birthdate/front-end/birthdate folder from terminal.
    2. run this command: "ng s".

## constrains:

* backend: -> http://localhost:8080/
    - make sure the back-end service is :8080 port.
    - this service only has one controller with one method with the next uri: "http://localhost:8080/api/v1/birthdate".
