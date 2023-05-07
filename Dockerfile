FROM arm64v8/amazoncorretto:11
EXPOSE 8080
ADD target/controle-financeiro-0.0.1.jar controle-financeiro-0.0.1.jar
ENTRYPOINT ["java", "-jar", "controle-financeiro-0.0.1.jar"]