package toolbox.gateway.sample

import com.jetbrains.toolbox.api.core.ServiceLocator
import com.jetbrains.toolbox.api.remoteDev.RemoteDevExtension
import com.jetbrains.toolbox.api.remoteDev.RemoteEnvironmentConsumer
import com.jetbrains.toolbox.api.remoteDev.RemoteProvider
import kotlinx.coroutines.CoroutineScope

class SampleRemoteDevExtension : RemoteDevExtension {
    override fun createRemoteProviderPluginInstance(serviceLocator: ServiceLocator): RemoteProvider {
        return SampleRemoteProvider(
//            serviceLocator.getService(OkHttpClient::class.java),
            serviceLocator.getService(RemoteEnvironmentConsumer::class.java),
            serviceLocator.getService(CoroutineScope::class.java),
            serviceLocator
        )
    }
}