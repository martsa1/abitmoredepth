FROM jenkins/jnlp-slave:latest

USER root

RUN apt-get update && apt-get install -y git gcc python-dev curl
RUN curl https://bootstrap.pypa.io/get-pip.py | python

RUN pip install --upgrade pip && pip install PyYaml click ruamel.yaml

RUN curl -fsSL https://get.docker.com/ | sh

#RUN usermod -aG docker jenkins

#USER jenkins
