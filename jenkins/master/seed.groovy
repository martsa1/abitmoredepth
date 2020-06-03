//Seed job to generate all the Jenkins jobs for the rES repository
// Reference for the JOB DSL used extensively here is available at: https://jenkinsci.github.io/job-dsl-plugin/

def jenkins_image_builders = [
  [
    'jobName': 'jenkins-agent',
    'jobDescription': 'Build and Publish the Jenkins Slave Agent to ECR',
    'jenkinsFilePath': 'jenkins/agent/Jenkinsfile.groovy'
  ],
  [
    'jobName': 'jenkins-master',
    'jobDescription': 'Build and Publish the Jenkins docker image to ECR',
    'jenkinsFilePath': 'jenkins/master/Jenkinsfile.groovy'
  ],
  [
    'jobName': 'traefik',
    'jobDescription': 'Docker aware http proxy',
    'jenkinsFilePath': 'traefik/Jenkinsfile.groovy'
  ],
]

for (image in jenkins_image_builders) {
  pipelineJob(image.jobName) {
    // Retry the source code checkout up to 3 times - allows for network crappery
    checkoutRetryCount()
    concurrentBuild(false)
    description(image.jobDescription)

    definition {
      cpsScm {
        lightweight(true)
        scm {
          git {
            branch('')
            remote {
              url('https://github.com/ABitMoreDepth/abitmoredepth.git')
            }
          }
        }
        scriptPath(image.jenkinsFilePath)
      }
    }
  }
}

