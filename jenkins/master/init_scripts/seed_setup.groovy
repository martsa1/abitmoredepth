// Creates initial seed jobs for the Jenkins instance
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

def jenkins_home = '/var/jenkins_home/'

def seedScript = new File(jenkins_home + 'seed.groovy')
def workspace = new File(jenkins_home)

def jobManagement = new JenkinsJobManagement(
  System.out,  // Output logger
  [:],         // Environment Variables
  workspace    // Workspace to operate within
)

DslScriptLoader scriptRunner = new DslScriptLoader(jobManagement)

scriptRunner.runScript(seedScript.text)
