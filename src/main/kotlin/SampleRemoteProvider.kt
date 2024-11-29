package toolbox.gateway.sample

import com.jetbrains.toolbox.api.core.ServiceLocator
import com.jetbrains.toolbox.api.core.diagnostics.Logger
import com.jetbrains.toolbox.api.core.ui.icons.SvgIcon
import com.jetbrains.toolbox.api.remoteDev.ProviderVisibilityState
import com.jetbrains.toolbox.api.remoteDev.RemoteEnvironmentConsumer
import com.jetbrains.toolbox.api.remoteDev.RemoteProvider
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.okio.decodeFromBufferedSource
//import okhttp3.OkHttpClient
//import okhttp3.Request
import org.intellij.lang.annotations.Language
import java.net.URI
import kotlin.time.Duration.Companion.seconds

class SampleRemoteProvider(
//    private val httpClient: OkHttpClient,
    private val consumer: RemoteEnvironmentConsumer,
    coroutineScope: CoroutineScope,
    serviceLocator: ServiceLocator,
) : RemoteProvider {
    private val logger = serviceLocator.getService(Logger::class.java)

    init {
        coroutineScope.launch {
//            val request = Request.Builder()
//                .get()
//                .url("https://my.awesome.control.server/some/logical/path/gateway.json")
////                .cacheControl(CacheControl.FORCE_NETWORK)
//                .build()
            while (true) {
                try {
                    logger.debug("Updating remote environments for Sample Plugin")
//                    val response = httpClient.newCall(request).await()
//                    val body = response.body ?: continue
                    @Language("json")
                    val body = """
                        { 
                            "environments": [
                                { 
                                    "id": "lol.kek.azaza",
                                    "name": "My shiny new environment"
                                }
                            ]
                        }
                    """.trimIndent()
                    val dto = Json.decodeFromString(EnvironmentsDTO.serializer(), body)
                    try {
                        consumer.consumeEnvironments(dto.environments.map { SampleRemoteEnvironment(serviceLocator, it) })
                    } catch (_: CancellationException) {
                        logger.debug("Environments update cancelled")
                        break
                    }
                } catch (e: Exception) {
                    logger.warn("Failed to retrieve environments: ${e.message}")
                }
                // only for demo purposes!
                delay(3.seconds)
            }
        }
    }

    override fun close() {}

    override fun getName(): String = "Sample Provider"
    override fun getSvgIcon(): SvgIcon {
        return SvgIcon(this::class.java.getResourceAsStream("/icon.svg")?.readAllBytes() ?: byteArrayOf())
    }

    override fun canCreateNewEnvironments(): Boolean = true
    override fun isSingleEnvironment(): Boolean = false

    override fun setVisible(visibilityState: ProviderVisibilityState) {}

    override fun addEnvironmentsListener(listener: RemoteEnvironmentConsumer) {}
    override fun removeEnvironmentsListener(listener: RemoteEnvironmentConsumer) {}

    override fun handleUri(uri: URI) {
        logger.debug { "External request: $uri" }
    }
}
