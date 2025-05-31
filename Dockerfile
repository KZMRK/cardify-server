FROM public.ecr.aws/docker/library/maven:3.8.8-amazoncorretto-21 AS build
COPY ./src /cardify/src
COPY ./pom.xml /cardify

WORKDIR /cardify
RUN mvn -f pom.xml clean install

FROM gcr.io/distroless/java21-debian12@sha256:b1129f4d80bf2d8070b4bc79dc6e4d53e57dc2844c7f7d5a3d1f00ea123b6b7a

COPY --from=build /cardify/target/*.jar cardify.jar
ENTRYPOINT ["java", "-jar", "cardify.jar"]
