FROM --platform=linux/amd64 ubuntu:24.04
COPY ml-service-app-ktor.kexe /
COPY application.yaml /
EXPOSE 8080
WORKDIR /
ENTRYPOINT ["/ml-service-app-ktor.kexe", "-config=./application.yaml"]
