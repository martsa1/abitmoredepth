//Setup the various view collections for Jenkins

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

def jenkins_home = '/var/jenkins_home/'

def workspace = new File(jenkins_home)

def jobManagement = new JenkinsJobManagement(
  System.out,  // Output logger
  [:],         // Environment Variables
  workspace    // Workspace to operate within
)

DslScriptLoader scriptRunner = new DslScriptLoader(jobManagement)

def views = """
listView('Maintenance Builds') {
  description('Maintenance builds, which maintain the Shepherd containers and this jenkins instance')

  recurse()

  jobs {
    regex('jenkins.*|registry.*')
  }

  columns {
    name()
    status()
    weather()
    lastSuccess()
    lastFailure()
    lastDuration()
    lastBuildConsole()
    progressBar()
    buildButton()
  }
}
"""

scriptRunner.runScript(views)
