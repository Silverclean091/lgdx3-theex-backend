FROM eclipse-temurin:17-jdk
RUN apt-get update \
    && apt-get install -y --no-install-recommends python3 python3-pip \
    && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY requirements.txt /app/requirements.txt
RUN pip3 install --no-cache-dir -r /app/requirements.txt --break-system-packages
COPY build/libs/*.jar /app/app.jar
ENV COFFEE_MODEL_PYTHON_COMMAND=python3
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
