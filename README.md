# JHipster-MicroService with ActiveMQ

It's a JHipster Micro service for Consuming messages from ActiveMQ Queues and Publish it to ActiveMQ Topics. I'm using MarshallingMessageConverter with Jaxb2Marshaller for both consuming and publishing XML message.

I have also created Unit test and EmbeddedActiveMQ integration test.


* Run ActiveMQ.
* Default url:  http://localhost:8161/
* Login by default user **(admin/admin)**
* Create Queue with name **source** in ActiveMQ-Queues. 
* Please set activeMQ url, inbound.endpoing and outbound.endpoint in **properties.yml**

```
spring:
  activemq:
    broker-url: tcp://localhost:61616
inbound:
  endpoint: source
outbound:
  endpoint: destination
  
```

* Now send message from created **source** queue. (past bellow sample XML in message body)

```
<UC_STOCK_LEVEL_IFD>
	<CTRL_SEG>
		<TRNNAM>UU_SSSS_LEVEL</TRNNAM>
		<TRNVER>20180100</TRNVER>
		<UUID>0de01919-81eb-4cc7-a51d-15f6085fc1a4</UUID>
		<WH_ID>WHHHH</WH_ID>
		<CLIENT_ID>CLI</CLIENT_ID>
		<ISO_2_CTRY_NAME>xxxx</ISO_2_CTRY_NAME>
		<REQUEST_ID>bcccc8-5a07-4hi6-8yyy-8290d3ccfb51</REQUEST_ID>
		<ROUTE_ID>6543</ROUTE_ID>
	</CTRL_SEG>
</UC_STOCK_LEVEL_IFD>
```

#JHipster Default Detail
**Start from bellow:**

This application was generated using JHipster 5.7.2, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.7.2](https://www.jhipster.tech/documentation-archive/v5.7.2).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Development

To start your application in the dev profile, simply run:

    ./mvnw

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the AzTask application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./mvnw -Pprod clean test sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.7.2 archive]: https://www.jhipster.tech/documentation-archive/v5.7.2
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v5.7.2/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.7.2/development/
[service discovery and configuration with the jhipster-registry]: https://www.jhipster.tech/documentation-archive/v5.7.2/microservices-architecture/#jhipster-registry
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.7.2/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.7.2/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.7.2/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.7.2/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.7.2/setting-up-ci/
