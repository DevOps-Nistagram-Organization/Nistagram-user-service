# The final image build process will use this build image as a base image
# that will already have all the dependencies inside it. Even if there is a new dependency
# added in pom.xml the final image will only download that delta dependency as the rest of the
# dependencies would already be present via base builder image.
FROM stevicdule/mvn-base-nistagram-user:1.0.0-dev AS appBuild
ARG STAGE=dev
LABEL maintainer="stevicdule@gmail.com"

WORKDIR /usr/src/user_service
COPY . .
RUN mvn package -P${STAGE} -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/user_service/target/user_service.jar ./
EXPOSE 8080
CMD java -jar user_service.jar