FROM adoptopenjdk/openjdk11:alpine-jre
RUN addgroup -S springgroup && adduser -S eshopuser -G springgroup
USER eshopuser
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.spring.eshop.EshopApplication"]