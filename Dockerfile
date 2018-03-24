FROM openjdk:8u151-jdk-alpine AS BUILD_IMAGE
WORKDIR /
COPY . /
RUN ./gradlew build

FROM openjdk:8u151-jre-alpine
COPY --from=BUILD_IMAGE /build/libs/oktomeme.jar .
EXPOSE 8080
CMD ["java", "-Xmx2g", "-Xms2g", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "oktomeme.jar"]
