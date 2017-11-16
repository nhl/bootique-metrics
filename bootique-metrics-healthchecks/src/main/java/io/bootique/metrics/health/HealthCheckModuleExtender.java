package io.bootique.metrics.health;

import com.google.inject.Binder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import io.bootique.ModuleExtender;
import io.bootique.metrics.health.heartbeat.HeartbeatListener;

/**
 * @since 0.25
 */
public class HealthCheckModuleExtender extends ModuleExtender<HealthCheckModuleExtender> {

    private MapBinder<String, HealthCheck> healthChecks;
    private Multibinder<HealthCheckGroup> healthCheckGroups;
    private Multibinder<HeartbeatListener> heartbeatListeners;

    public HealthCheckModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public HealthCheckModuleExtender initAllExtensions() {
        getOrCreateHealthCheckGroups();
        getOrCreateHealthChecks();
        getOrCreateHeartbeatListeners();
        return this;
    }

    public HealthCheckModuleExtender addHeartbeatListener(HeartbeatListener listener) {
        getOrCreateHeartbeatListeners().addBinding().toInstance(listener);
        return this;
    }

    public HealthCheckModuleExtender addHeartbeatListener(Class<? extends HeartbeatListener> listenerType) {
        getOrCreateHeartbeatListeners().addBinding().to(listenerType);
        return this;
    }

    public HealthCheckModuleExtender addHealthCheck(String name, HealthCheck healthCheck) {
        getOrCreateHealthChecks().addBinding(name).toInstance(healthCheck);
        return this;
    }

    public <T extends HealthCheck> HealthCheckModuleExtender addHealthCheck(String name, Class<T> healthCheckType) {
        getOrCreateHealthChecks().addBinding(name).to(healthCheckType);
        return this;
    }

    public HealthCheckModuleExtender addHealthCheckGroup(HealthCheckGroup healthCheckGroup) {
        getOrCreateHealthCheckGroups().addBinding().toInstance(healthCheckGroup);
        return this;
    }

    public <T extends HealthCheckGroup> HealthCheckModuleExtender addHealthCheckGroup(Class<T> healthCheckGroupType) {
        getOrCreateHealthCheckGroups().addBinding().to(healthCheckGroupType);
        return this;
    }

    protected MapBinder<String, HealthCheck> getOrCreateHealthChecks() {
        if (healthChecks == null) {
            healthChecks = MapBinder.newMapBinder(binder, String.class, HealthCheck.class);
        }

        return healthChecks;
    }

    protected Multibinder<HealthCheckGroup> getOrCreateHealthCheckGroups() {
        if (healthCheckGroups == null) {
            healthCheckGroups = Multibinder.newSetBinder(binder, HealthCheckGroup.class);
        }

        return healthCheckGroups;
    }

    protected Multibinder<HeartbeatListener> getOrCreateHeartbeatListeners() {
        if (heartbeatListeners == null) {
            heartbeatListeners = Multibinder.newSetBinder(binder, HeartbeatListener.class);
        }

        return heartbeatListeners;
    }
}