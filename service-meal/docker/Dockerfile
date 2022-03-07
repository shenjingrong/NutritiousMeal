ARG tag=16-alpine3.13
FROM openjdk:${tag}
COPY service-meal.jar ./service-meal.jar
EXPOSE 18300
ENTRYPOINT ["java", "-jar"]
CMD ["service-meal.jar"]