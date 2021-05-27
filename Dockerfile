FROM harbor.octanner.io/base/oct-maven:3.6-jdk11 as builder

# Add a volume pointing to /tmp
VOLUME /tmp

WORKDIR /app

COPY . /app/

RUN mvn clean package -DskipTests -s settings.xml -l /dev/stdout

FROM harbor.octanner.io/base/oct-maven:3.6-jdk11

WORKDIR /app

COPY --from=builder /app/target/pdfsso.jar /app/target/pdfsso.jar

# Make port 8080 available to the world outside this container
EXPOSE 9000

# Copy script file and give permissions
COPY --from=builder /app/transmogriphyAndRun.sh /app/transmogriphyAndRun.sh
RUN chmod +x ./transmogriphyAndRun.sh

# Run script
ENTRYPOINT ["./transmogriphyAndRun.sh"]

