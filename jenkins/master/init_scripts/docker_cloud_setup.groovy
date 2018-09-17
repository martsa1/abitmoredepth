// Inspired by suggested Groovy setup script on the plugin documentation here:
// https://wiki.jenkins.io/display/JENKINS/Docker+Plugin

// This file sets up the Docker cloud settings in Jenkins, allowing us to spawn
// containers for use a executors.

import hudson.slaves.JNLPLauncher
import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase
import com.nirima.jenkins.plugins.docker.launcher.AttachedDockerComputerLauncher
import io.jenkins.docker.connector.DockerComputerAttachConnector
import io.jenkins.docker.connector.DockerComputerJNLPConnector
import jenkins.model.Jenkins

// Defines base docker container template & associated settings
// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplateBase.java
DockerTemplateBase dockerTemplateBase = new DockerTemplateBase('registry.abitmoredepth.com/ci-agent:latest')

// Setup docker template base parameters
dockerTemplateBase.setBindAllPorts(false)
dockerTemplateBase.setBindPorts('')
dockerTemplateBase.setCpuShares(500)
dockerTemplateBase.setDockerCommand('')
dockerTemplateBase.setEnvironmentsString('')
dockerTemplateBase.setExtraHostsString('')
dockerTemplateBase.setHostname('')
dockerTemplateBase.setMacAddress('')
dockerTemplateBase.setMemoryLimit(1024)
dockerTemplateBase.setMemorySwap(1024)
dockerTemplateBase.setNetwork('abitmoredepth')
dockerTemplateBase.setPrivileged(false)
dockerTemplateBase.setPullCredentialsId('')
dockerTemplateBase.setTty(false)
dockerTemplateBase.setVolumesFromString('')
dockerTemplateBase.setVolumesString('/var/run/docker.sock:/var/run/docker.sock:z')


// We connect to docker build agents via JNLP, as its reliable over network
// connections, and quicker than SSH.  This connection type should also connect
// across to remote build servers without too much issue.
dockerJNLPConnector = new DockerComputerJNLPConnector(new JNLPLauncher())
dockerJNLPConnector.setJenkinsUrl('http://ci:8080')
dockerJNLPConnector.setUser('root')

// Defines an overall docker template used when spawning containers.
// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplate.java
DockerTemplate dockerTemplate = new DockerTemplate(
  dockerTemplateBase,      // Base template properties, defined above
  dockerJNLPConnector,     //Attachement Method - How Jenkins Master connects to agent
  'docker',                // labelString
  '/home/jenkins/agent',   // Remote File System - Where Agent should execute builds, useful if putting into a volume.
  ''                       // InstanceCapStr - deprecated
)

// Set various settings on the Docker template
dockerTemplate.setRemoveVolumes(true)


// Setup a Docker Cloud, allowing us to spawn container based build executors
// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerCloud.java
DockerCloud dockerCloud = new DockerCloud(
  'ABitMoreDepth-CI',             // Name of the docker cloud
  [dockerTemplate],               // List of dockerTemplate objects - provides various container configurations
  'unix:///var/run/docker.sock',  // URL to the docker server, we are using a local unix socket here
  '2',                            // Max Number of containers to run on this cloud
  60,                             // Connection Timeout in seconds
  60,                             // Read Timeoue in seconds
  '',                             // Credentials ID
  '',                             // Cloud version
  '',                             // docker Hostname
)

// Make the docker socket available as an environment variable on the executor
dockerCloud.setExposeDockerHost(true as Boolean)

// get Jenkins instance
Jenkins jenkins = Jenkins.get()

// add cloud configuration to Jenkins
jenkins.clouds.add(dockerCloud)

// save current Jenkins state to disk
jenkins.save()
