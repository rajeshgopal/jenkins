import java.lang.System
import hudson.model.*
import jenkins.model.*
import org.jfrog.*
import org.jfrog.hudson.*
import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.common.StandardUsernameCredentials

def getPasswordCredentials(String id) {
  def all = CredentialsProvider.lookupCredentials(StandardUsernameCredentials.class)
  return all.findResult { it.id == id ? it : null }
}

    def inst = Jenkins.getInstance()
    def desc = inst.getDescriptor("org.jfrog.hudson.ArtifactoryBuilder")
    CredentialsConfig deployerCredentials = new CredentialsConfig(getPasswordCredentials(serverConfig.deployerCredentialsId),
                                                                  serverConfig.deployerCredentialsId,
                                                                  serverConfig.overridingCredentials)
    CredentialsConfig resolverCredentials = new CredentialsConfig(getPasswordCredentials(serverConfig.deployerCredentialsId),
                                                                  serverConfig.deployerCredentialsId,
                                                                  serverConfig.overridingCredentials)
    List<ArtifactoryServer> servers =  desc.getArtifactoryServers()
    ArtifactoryServer server = new ArtifactoryServer(serverConfig.serverName,
                                                     serverConfig.serverUrl,
                                                     deployerCredentials,
                                                     resolverCredentials,
                                                     serverConfig.connectionTimeOut,
                                                     serverConfig.bypassProxy,
                                                     serverConfig.connectionRetry)
    if (servers == null || servers.empty) {
      servers = [server]
    } else {
      servers.push(server)
    }
    desc.setUseCredentialsPlugin(true)
    desc.setArtifactoryServers(servers)
    desc.save()
