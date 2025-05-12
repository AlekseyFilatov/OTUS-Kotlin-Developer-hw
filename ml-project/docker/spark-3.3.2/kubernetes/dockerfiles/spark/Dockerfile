ARG java_image_tag=17-jre
FROM ${java_image_tag}

ARG spark_uid=1001
ARG UID_GID=1001
ENV UID=${UID_GID}
ENV GID=${UID_GID}

ENV SPARK_RAPIDS_DIR=/opt/sparkRapidsPlugin
ENV SPARK_RAPIDS_PLUGIN_JAR=${SPARK_RAPIDS_DIR}/rapids-4-spark_2.12-25.02.0.jar

RUN set -ex && \
    sed -i 's/http:\/\/deb.\(.*\)/https:\/\/deb.\1/g' /etc/apt/sources.list && \
    apt-get update && \
    ln -s /lib /lib64 && \
    apt install -y bash tini libc6 libpam-modules libnss3 procps nano iputils-ping net-tools iptables sudo \
    wget software-properties-common build-essential libnss3-dev zlib1g-dev libgdbm-dev libncurses5-dev \
    libssl-dev libffi-dev libreadline-dev libsqlite3-dev libbz2-dev python3 && \
    mkdir -p /opt/spark && \
    mkdir -p /opt/spark/examples && \
    mkdir -p /opt/spark/conf && \
    mkdir -p /opt/spark/work-dir && \
    mkdir -p /opt/sparkRapidsPlugin && \
    touch /opt/spark/RELEASE && \
    rm /bin/sh && \
    ln -sv /bin/bash /bin/sh && \
    echo "auth required pam_wheel.so use_uid" >> /etc/pam.d/su && \
    chgrp root /etc/passwd && chmod ug+rw /etc/passwd && \
    rm -rf /var/cache/apt/*
RUN apt-get install libnccl2 libnccl-dev -y --allow-change-held-packages && rm -rf /var/cache/apt/*

COPY jars /opt/spark/jars
COPY rapids ${SPARK_RAPIDS_DIR}
COPY bin /opt/spark/bin
COPY sbin /opt/spark/sbin
COPY conf /opt/spark/conf
COPY kubernetes/dockerfiles/spark/entrypoint.sh /opt/
COPY kubernetes/dockerfiles/spark/decom.sh /opt/
COPY kubernetes/tests /opt/spark/tests
COPY data /opt/spark/data
COPY datasets /opt/spark/

ENV SPARK_HOME /opt/spark

WORKDIR /opt/spark/work-dir
RUN chmod g+w /opt/spark/work-dir
RUN chmod a+x /opt/decom.sh
RUN chmod a+x /opt/entrypoint.sh
RUN chmod a+x /opt/sparkRapidsPlugin/getGpusResources.sh
RUN ls -lah /opt

RUN groupadd --gid $UID appuser && useradd --uid $UID --gid appuser --shell /bin/bash --create-home appuser
RUN mkdir /var/logs && chown -R appuser:appuser /var/logs
RUN mkdir /opt/spark/logs && chown -R appuser:appuser /opt/spark/
RUN chown -R appuser:appuser /tmp

RUN ls -lah /home/appuser
RUN touch /home/appuser/.bashrc

RUN echo -e '\
export SPARK_HOME=/opt/spark\n\
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin\
' > /home/appuser/.bashrc

RUN chown -R appuser:appuser /home/appuser

EXPOSE 4040
EXPOSE 8081

USER ${spark_uid}

ENTRYPOINT [ "/opt/entrypoint.sh" ]
