#Stage 1
# initialize build and set base image for first stage
FROM maven:3.9-eclipse-temurin-17 as builder

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# set working directory
WORKDIR /app

# copy just pom.xml
COPY pom.xml .

# go-offline using the pom.xml
RUN mvn dependency:go-offline

# copy your other files
COPY ./src ./src

# compile the source code and package it in a jar file
RUN mvn clean package -DskipTests

#Stage 2

# set base image for second stage
FROM eclipse-temurin:17.0.7_7-jre-jammy

# set deployment directory
WORKDIR /app

# copy over the built artifact from the maven image
COPY --from=builder /app/target/discount-service-0.0.1-SNAPSHOT.jar /app

# Execute the jar
ENTRYPOINT ["java", "-jar", "/app/discount-service-0.0.1-SNAPSHOT.jar"]




