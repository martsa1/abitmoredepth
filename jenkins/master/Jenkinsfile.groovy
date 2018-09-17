pipeline {
  agent {
    docker {
      image 'ci-agent'
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
          dir('jenkins/master') {
            docker.build('registry.abitmoredepth.com/ci-master', '--pull --no-cache .')
          }
        }
      }
    }

    stage('Publish') {
      steps {
        script {
          dir('jenkins/agent') {
            docker.withRegistry('https://registry.abitmoredepth.com') {
              docker.image('registry.abitmoredepth.com/ci-master:latest').push('latest')
            }
          }
        }
      }
    }
  }
}

