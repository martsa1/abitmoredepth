// Performs various bits of basic setup on the Jenkins host.

import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy

// Get access to the global jenkins instance
Jenkins jenkins = Jenkins.get()

// Configure the Jenkins security settings, see more here:
// https://pghalliday.com/jenkins/groovy/sonar/chef/configuration/management/2014/09/21/some-useful-jenkins-groovy-scripts.html
HudsonPrivateSecurityRealm hudsonRealm = new HudsonPrivateSecurityRealm(false) //false here prevents user sign-up
jenkins.setSecurityRealm(hudsonRealm)

// Setup jenkins default user - uses docker secrets
def userName = new File('/var/lib/usr').text
def userPass = new File('/var/lib/passwd').text
jenkins.securityRealm.createAccount(userName, userPass)

// Allow any logged in user to do whatever (might change this in future).
// Allow anonymous read-only access to the system
FullControlOnceLoggedInAuthorizationStrategy authStrategy = new FullControlOnceLoggedInAuthorizationStrategy()
authStrategy.setAllowAnonymousRead(true) // Enable read-only access by non-logged in users

jenkins.setAuthorizationStrategy(authStrategy)

// Configure the Jenkins remoting settings
jenkins.setSlaveAgentPort(50000)
jenkins.setAgentProtocols(['JNLP4-connect', 'Ping'] as Set<String>)

// Disable old remote CLI subsystem to shut up a security warning.
jenkins.CLI cli = jenkins.CLI.get()
cli.setEnabled(false)
cli.save()

// Set the Jenkins URL
JenkinsLocationConfiguration locationConf = JenkinsLocationConfiguration.get()
locationConf.setUrl('https://ci.abitmoredepth.com')
locationConf.save()

// Set the build executors to 0 on the master, so that we only build jobs in docker containers.
jenkins.setNumExecutors(0)

jenkins.setSystemMessage('Welcome to Jenkins! Bear in mind that manual configuration is not persisted across restarts!')

// save current Jenkins state to disk
jenkins.save()

