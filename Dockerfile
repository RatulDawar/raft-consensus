# Copyright (c) 2022, 2023 Oracle and/or its affiliates.
#
# Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
#
# ORACLE DOCKERFILES PROJECT
# --------------------------
# This is the Dockerfile for Oracle JDK 21 on Oracle Linux 8
#
# REQUIRED FILES TO BUILD THIS IMAGE
# ----------------------------------
# This dockerfile will download a copy of JDK 21 from
#	https://download.oracle.com/java/21/latest/jdk-21_linux-<ARCH>_bin.tar.gz  
# 
# It will use either x64 or aarch64 depending on the target platform
#
# HOW TO BUILD THIS IMAGE
# -----------------------
# Run:
#      $ docker build -t oracle/jdk:21 .
#
# This command is already scripted in build.sh so you can alternatively run
#		$ bash build.sh
#
# The builder image will be used to uncompress the tar.gz file with the Java Runtime.

FROM oraclelinux:8 as builder

LABEL maintainer="Aurelio Garcia-Ribeyro <aurelio.garciaribeyro@oracle.com>"

# Since the files are compressed as tar.gz first dnf install tar. gzip is already in oraclelinux:8
RUN set -eux; \
	dnf install -y tar; 
	
# Default to UTF-8 file.encoding
ENV LANG en_US.UTF-8

# Environment variables for the builder image.
# Required to validate that you are using the correct file
	
ENV JAVA_URL=https://download.oracle.com/java/21/latest \
	JAVA_HOME=/usr/java/jdk-21

##
SHELL ["/bin/bash", "-o", "pipefail", "-c"]
RUN set -eux; \
	ARCH="$(uname -m)" && \
	# Java uses just x64 in the name of the tarball
    if [ "$ARCH" = "x86_64" ]; \
        then ARCH="x64"; \
    fi && \
    JAVA_PKG="$JAVA_URL"/jdk-21_linux-"${ARCH}"_bin.tar.gz ; \
	JAVA_SHA256=$(curl "$JAVA_PKG".sha256) ; \ 
	curl --output /tmp/jdk.tgz "$JAVA_PKG" && \
	echo "$JAVA_SHA256" */tmp/jdk.tgz | sha256sum -c; \
	mkdir -p "$JAVA_HOME"; \
	tar --extract --file /tmp/jdk.tgz --directory "$JAVA_HOME" --strip-components 1
	
## Get a fresh version of OL8 for the final image	
FROM oraclelinux:8

# Default to UTF-8 file.encoding
ENV LANG en_US.UTF-8
ENV	JAVA_HOME=/usr/java/jdk-21
ENV	PATH $JAVA_HOME/bin:$PATH	

# If you need the Java Version you can read it from the release file with 
# JAVA_VERSION=$(sed -n '/^JAVA_VERSION="/{s///;s/"//;p;}' "$JAVA_HOME"/release);

# Copy the uncompressed Java Runtime from the builder image
COPY --from=builder $JAVA_HOME $JAVA_HOME

RUN mkdir -p /var/project-store/raft-consensus

# Copy the JAR file into the image
COPY /target target

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-cp", "/target/raft-consensus-1.0-SNAPSHOT-jar-with-dependencies.jar", "org.butterchicken.raft.ServerStarter"]
