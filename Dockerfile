# syntax=docker/dockerfile:experimental
FROM arm64v8/eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace/microservices
COPY . /workspace/microservices
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build -x test
RUN mkdir -p server-discovery/build/dependencies && (cd server-discovery/build/dependencies; jar -xf ../libs/*-SNAPSHOT.jar)
RUN mkdir -p server-configuration/build/dependencies && (cd server-configuration/build/dependencies; jar -xf ../libs/*-SNAPSHOT.jar)
RUN mkdir -p api-gateway/build/dependencies && (cd api-gateway/build/dependencies; jar -xf ../libs/*-SNAPSHOT.jar)
RUN mkdir -p service-file/build/dependencies && (cd service-file/build/dependencies; jar -xf ../libs/*-SNAPSHOT.jar)
RUN mkdir -p service-folder/build/dependencies && (cd service-folder/build/dependencies; jar -xf ../libs/*-SNAPSHOT.jar)

FROM arm64v8/eclipse-temurin:17-jdk-jammy AS server-discovery
VOLUME /tmp
ARG DEPENDENCIES=/workspace/microservices/server-discovery/build/dependencies
ARG SPRING_BOOT_APP=/workspace/microservices/server-discovery
COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib      ${SPRING_BOOT_APP}/lib
COPY --from=build ${DEPENDENCIES}/META-INF          ${SPRING_BOOT_APP}/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes  ${SPRING_BOOT_APP}
ENTRYPOINT ["java","-cp","/workspace/microservices/server-discovery:/workspace/microservices/server-discovery/lib/*","eu.pedrazamiguez.microservices.server.discovery.ServerDiscoveryApplicationKt"]

FROM arm64v8/eclipse-temurin:17-jdk-jammy AS server-configuration
VOLUME /tmp
RUN mkdir -p /var/microservices/config
ARG DEPENDENCIES=/workspace/microservices/server-configuration/build/dependencies
ARG SPRING_BOOT_APP=/workspace/microservices/server-configuration
COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib      ${SPRING_BOOT_APP}/lib
COPY --from=build ${DEPENDENCIES}/META-INF          ${SPRING_BOOT_APP}/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes  ${SPRING_BOOT_APP}
ENTRYPOINT ["java","-cp","/workspace/microservices/server-configuration:/workspace/microservices/server-configuration/lib/*","eu.pedrazamiguez.microservices.server.configuration.ServerConfigurationApplicationKt"]

FROM arm64v8/eclipse-temurin:17-jdk-jammy AS api-gateway
VOLUME /tmp
ARG DEPENDENCIES=/workspace/microservices/api-gateway/build/dependencies
ARG SPRING_BOOT_APP=/workspace/microservices/api-gateway
COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib      ${SPRING_BOOT_APP}/lib
COPY --from=build ${DEPENDENCIES}/META-INF          ${SPRING_BOOT_APP}/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes  ${SPRING_BOOT_APP}
ENTRYPOINT ["java","-cp","/workspace/microservices/api-gateway:/workspace/microservices/api-gateway/lib/*","eu.pedrazamiguez.microservices.api.gateway.ApiGatewayApplicationKt"]

FROM arm64v8/eclipse-temurin:17-jdk-jammy AS service-file
VOLUME /tmp
ARG DEPENDENCIES=/workspace/microservices/service-file/build/dependencies
ARG SPRING_BOOT_APP=/workspace/microservices/service-file
COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib      ${SPRING_BOOT_APP}/lib
COPY --from=build ${DEPENDENCIES}/META-INF          ${SPRING_BOOT_APP}/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes  ${SPRING_BOOT_APP}
ENTRYPOINT ["java","-cp","/workspace/microservices/service-file:/workspace/microservices/service-file/lib/*","eu.pedrazamiguez.microservices.service.file.ServiceFileApplicationKt"]

FROM arm64v8/eclipse-temurin:17-jdk-jammy AS service-folder
VOLUME /tmp
ARG DEPENDENCIES=/workspace/microservices/service-folder/build/dependencies
ARG SPRING_BOOT_APP=/workspace/microservices/service-folder
COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib      ${SPRING_BOOT_APP}/lib
COPY --from=build ${DEPENDENCIES}/META-INF          ${SPRING_BOOT_APP}/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes  ${SPRING_BOOT_APP}
ENTRYPOINT ["java","-cp","/workspace/microservices/service-folder:/workspace/microservices/service-folder/lib/*","eu.pedrazamiguez.microservices.service.folder.ServiceFolderApplicationKt"]
