# Repository Summary
* Docker
* Docker Compose
* Microservices-architecture (Spring Boot - Java Framework)
* Each Microservice has its database (DATABASE_SCHEMA)
* Microservices Communication using Feign client as REST client
* Docker staging and pom.xml profiles (dev, test, prod environments)
* Mysql-database
* Postgresql-database
* Spring Boot (back-end)
* Angular (front-end)
* Platform-Provided Service Discovery (Deployment infrastructure take care of service discovery)
* Service-discovery (Docker-DNS)
* API-gateway (NGINX)
* Using NGINX in the backend as a reverse proxy
* Using NGINX in the frontend as a web server for displaying the static content of the dist folder
* Shell Scripting
* 3 types of artifacts:
  * The first type of artifacts: **mvn base image** (mvn dependencies download acceleration) published on **Docker Hub**
  * The second type of artifacts: publish the **application as a runnable JAR file** on the **GitHub Packages repository**
  * The third type of artifacts: publishing the **application’s Docker image** on **Docker Hub**
  
# Nistagram User Microservice Instructions

# Artifacts
## The first type of artifacts: mvn base image (mvn dependencies download acceleration) published on Docker Hub
Build mvn base image
```shell
docker image build -f Dockerfile.base --build-arg STAGE=dev -t stevicdule/mvn-base-nistagram-user:1.0.0-dev .
```
Push your mvn base image to Docker Hub
```shell
docker push stevicdule/mvn-base-nistagram-user:1.0.0-dev
```
## The second type of artifacts: publish the application as a runnable JAR file on the GitHub Packages repository
## The third type of artifacts: publishing the application’s Docker image on Docker Hub
