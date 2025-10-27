# Banco API

API bancária desenvolvida em **Spring Boot** e empacotada com **Docker**.

---

## Como executar o projeto (Docker)

### 1. Baixar a imagem do Docker Hub

```bash
docker pull uyris/banco:latest
```

### 2. Executar o container localmente
Usando banco de dados remoto (Aiven)

```bash
docker run --rm -p 8080:8080 --env-file .env uyris/banco:latest
```

O arquivo ".env" deve conter:

```bash
SPRING_DATASOURCE_URL=SUA_URL_AIVEN
SPRING_DATASOURCE_USERNAME=avnadmin
SPRING_DATASOURCE_PASSWORD=SEU_PASSOWORD
```

