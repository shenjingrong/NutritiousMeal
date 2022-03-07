ARG tag=16-alpine3.13
FROM openjdk:${tag}
COPY service-user.jar ./service-user.jar
EXPOSE 18300
ENTRYPOINT ["java", "-jar"]
CMD ["service-user.jar"]