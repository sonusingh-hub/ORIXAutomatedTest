FROM amazoncorretto:11-al2023

ARG USERNAME
ARG PASSWORD

USER root

RUN dpkg-divert --remove /lib32 || true
RUN dpkg-divert --remove /libo32 || true

# Install necessary tools
RUN dnf update && dnf install -y \
    wget \
    gzip \
    tar \
    unzip \
    findutils \
    shadow-utils

ENV HOME=/opt/appian-selenium-api

WORKDIR ${HOME}
RUN mkdir -p ${HOME}/lib ${HOME}/screenshots ${HOME}/configs ${HOME}/downloads ${HOME}/FitNesseRoot ${HOME}/cucumber

# Setup Fitnesse
# Download the fitnesse accoutrements
COPY fitnesse-for-appian.zip ${HOME}/fitnesse-for-appian.zip
RUN unzip fitnesse-for-appian.zip -d fitnesse-for-appian && rm fitnesse-for-appian.zip
RUN cp -r fitnesse-for-appian/lib/. ${HOME}/lib && \
    cp -r fitnesse-for-appian/FitNesseRoot/. ${HOME}/FitNesseRoot
RUN FILE=$(find ./lib -name "fitnesse*standalone.jar") && \
    mv $FILE fitnesse-for-appian.jar
RUN rm -rf fitnesse-for-appian

# Setup Cucumber
# Download cucumber accoutrements
COPY cucumber-for-appian.zip ${HOME}/cucumber-for-appian.zip
RUN unzip cucumber-for-appian.zip -d cucumber-for-appian && rm cucumber-for-appian.zip
RUN cp -r cucumber-for-appian/lib/. ${HOME}/lib && \
    cp cucumber-for-appian/pom.xml ${HOME}/pom.xml && \
    cp -r cucumber-for-appian/src/. ${HOME}/src
RUN mkdir -p ${HOME}/src/test/resources/configs
RUN rm -rf cucumber-for-appian

# Install maven
RUN curl -fsSL https://downloads.apache.org/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz -o /tmp/maven.tar.gz && \
    mkdir -p /opt/maven && \
    tar -xzvf /tmp/maven.tar.gz -C /opt/maven --strip-components=1 && \
    rm -f /tmp/maven.tar.gz
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Environment setup
ENV PATH="${PATH}:${HOME}"
ENV CLASSPATH="${HOME}/lib/*:${CLASSPATH}"

COPY run_cucumber.sh run_cucumber.sh
COPY user.properties user.properties
COPY entrypoint.sh entrypoint.sh
# properties are pretty messy. We need one for selenium (/opt/configs), one for fitnesse (HOME/configs) and one for cucumber (HOME/src/test/resources/configs)
# Copy to the root and symlink everywhere so we only need one copy
COPY custom.properties /opt/configs/custom.properties
RUN echo "automated.testing.home=${HOME}" >> /opt/configs/custom.properties && \
    echo "download.directory=${HOME}/downloads" >> /opt/configs/custom.properties && \
    echo "${USERNAME}=${PASSWORD}" >> /opt/configs/custom.properties
RUN ln /opt/configs/custom.properties src/test/resources/configs/custom.properties
RUN ln /opt/configs/custom.properties configs/custom.properties

RUN groupadd -g 65533 build && useradd -u 1001 -G build -s /bin/bash fitnesse
RUN chown -R fitnesse ${HOME}
USER fitnesse

EXPOSE 8980
ENTRYPOINT ["entrypoint.sh"]
CMD [""]
HEALTHCHECK NONE
