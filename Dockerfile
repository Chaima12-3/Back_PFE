## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/ubi-quarkus-native-image:21.2.0-java11 AS build
COPY ./pom.xml /code/
COPY ./mvnw /code/mvnw
COPY ./.mvn /code/.mvn
USER root
WORKDIR /code
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY ./src /code/src
USER root
RUN ./mvnw package -Pnative -Dquarkus.profile=dev -DskipTests=true -Dnative-image.xmx=8g
USER quarkus

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.4
WORKDIR /work/
COPY --from=build /code/target/*-runner /work/application
# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work


EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]