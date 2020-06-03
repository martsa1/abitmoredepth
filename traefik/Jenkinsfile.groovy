pipeline {
  agent {
    docker {
      image '114151591996.dkr.ecr.eu-west-1.amazonaws.com/abmd/ci-agent'
      label 'docker'
    }
  }

  options {
    ansiColor('xterm')
    buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '50')
    retry(3)
    disableConcurrentBuilds()
  }

  stages {
    stage('SCM Checkout') {
      steps {
         checkout scm
      }
    }

    stage('Build') {
      steps {
        script {
          dir('traefik') {
            docker.build('114151591996.dkr.ecr.eu-west-1.amazonaws.com/traefik', '--pull --no-cache .')
          }
        }
      }
    }

    stage('Publish') {
      steps {
        script {
          dir('traefik') {
            docker.withRegistry('114151591996.dkr.ecr.eu-west-1.amazonaws.com/traefik') {
              docker.image('114151591996.dkr.ecr.eu-west-1.amazonaws.com/traefik').push('latest')
            }
          }
        }
      }
    }
  }
}

