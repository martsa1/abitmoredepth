FROM jenkins/jenkins:lts

USER root

# Define some env variables
ENV JENKINS_HOME=/var/jenkins_home

#RUN apt-get update && apt-get install -y python-pip git gcc python-dev
RUN apt-get update && apt-get install -y git gcc python-dev curl
RUN curl https://bootstrap.pypa.io/get-pip.py | python

RUN pip install --upgrade pip && pip install PyYaml click ruamel.yaml

RUN curl -fsSL https://get.docker.com/ | sh

COPY config/build-everything-strategy.hpi /var/jenkins_home/plugins/build-everything-strategy.hpi

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# Copy in Jenkins reference configuration (config.xml, etc.)
COPY config /usr/share/jenkins/ref

COPY init_scripts/*.groovy /var/jenkins_home/init.groovy.d/
COPY *.groovy /var/jenkins_home/
