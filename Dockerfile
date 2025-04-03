# Step 1: Application build
FROM maven:3.9.9-eclipse-temurin-21 AS jar-build
WORKDIR /app

RUN apt-get update && apt-get install unzip

# Copy maven config file
COPY pom.xml .
RUN mvn dependency:go-offline

# Copying source code
COPY src ./src

# Compile project
RUN mvn clean package -DskipTests

# Get project dependencies
RUN mkdir ./target/extracted
RUN unzip  ./target/cards-0.0.1-SNAPSHOT.jar -d ./target/extracted

RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 21  \
    --print-module-deps  \
    --class-path './target/extracted/BOOT-INF/lib/*'  \
    ./target/cards-0.0.1-SNAPSHOT.jar > deps.info

# Step 2: Custom jre build
FROM eclipse-temurin:21 AS jre-build

COPY --from=jar-build /app/deps.info /opt/app/

# Create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules $(cat /opt/app/deps.info) \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Step 3: Final image
FROM debian:buster-slim
LABEL authors="@mj2silva"

ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=jre-build /javaruntime $JAVA_HOME

# Continue with your application deployment
RUN mkdir /opt/app
RUN apt update && apt install curl -y
COPY --from=jar-build /app/target/cards-0.0.1-SNAPSHOT.jar /opt/app/
CMD ["java", "-jar", "/opt/app/cards-0.0.1-SNAPSHOT.jar"]
