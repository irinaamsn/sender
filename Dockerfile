FROM openjdk:20
LABEL authors="irinaamsn"

WORKDIR /app

COPY build/libs/sender-0.0.1-SNAPSHOT.jar sender.jar

CMD ["java", "-jar", "sender.jar"]