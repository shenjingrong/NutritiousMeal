ARG tag=16-alpine3.13
FROM openjdk:${tag}
COPY service-job.jar ./service-job.jar
EXPOSE 18300
ENTRYPOINT ["java", "-jar"]
CMD ["service-job.jar"]