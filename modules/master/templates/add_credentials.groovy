import java.lang.System
import jenkins.*
import hudson.model.*
import jenkins.model.*
// Plugins for SSH credentials
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*

global_domain = Domain.global()
credentials_store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

      println "--> Create credentials for user  with the password from"
      creds = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,
                                                  'artifactoryuser',
                                                  'readonlyuserforartifactory',
                                                  'artifactoryuser',
                                                  'jumboma')
      credentials_store.addCredentials(global_domain, creds)
