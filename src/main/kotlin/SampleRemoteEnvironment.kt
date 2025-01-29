package toolbox.gateway.sample

import com.jetbrains.toolbox.api.remoteDev.AbstractRemoteProviderEnvironment
import com.jetbrains.toolbox.api.remoteDev.EnvironmentVisibilityState
import com.jetbrains.toolbox.api.remoteDev.environments.EnvironmentContentsView
import java.util.concurrent.CompletableFuture

class SampleRemoteEnvironment(
    private val environment: EnvironmentDTO
) : AbstractRemoteProviderEnvironment() {
    override fun getId(): String = environment.id
    override fun getName(): String = environment.name
    override fun getContentsView(): CompletableFuture<EnvironmentContentsView> {
        return CompletableFuture.completedFuture(SampleEnvironmentContentsView())
    }

    override fun setVisible(visibilityState: EnvironmentVisibilityState) {
    }

    override fun onDelete() {
    }
}