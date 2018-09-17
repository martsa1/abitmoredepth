// Groovy script that sets up various bits of script approval in order to allow various bits of the UMC Build system to run effectively.

scripts = [
  // 'field hudson.plugins.git.GitSCM GIT_COMMIT',
  // 'method hudson.model.AbstractProject getScm',
  // 'method hudson.model.AbstractProject getWorkspace',
  // 'method hudson.model.Cause getShortDescription',
  // 'method hudson.model.Job getLastSuccessfulBuild',
  // 'method hudson.model.Job getProperty java.lang.String',
  // 'method hudson.model.Run getCauses',
  // 'method hudson.scm.PollingResult hasChanges',
  // 'method hudson.scm.SCM calcRevisionsFromBuild hudson.model.AbstractBuild hudson.Launcher hudson.model.TaskListener',
  // 'method hudson.scm.SCM compareRemoteRevisionWith hudson.model.AbstractProject hudson.Launcher hudson.FilePath hudson.model.TaskListener hudson.scm.SCMRevisionState',
  // 'method java.util.regex.Matcher find',
  // 'method jenkins.model.Jenkins getItemByFullName java.lang.String',
  // 'method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild',
  // 'new hudson.Launcher$LocalLauncher hudson.model.TaskListener',
  // 'new hudson.model.StreamBuildListener java.io.OutputStream',
  // 'new java.io.ByteArrayOutputStream',
  // 'staticMethod jenkins.model.Jenkins getInstance',
  // 'staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getAt java.util.Collection java.lang.String',
]

for (script in scripts) {
  org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval.get().approveSignature(script)
}

