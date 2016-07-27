package org.gradle.cache.tasks.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.gradle.StartParameter;
import org.gradle.api.Plugin;
import org.gradle.api.internal.tasks.cache.MapBasedTaskOutputCache;
import org.gradle.api.internal.tasks.cache.TaskOutputCache;
import org.gradle.api.internal.tasks.cache.TaskOutputCacheFactory;
import org.gradle.api.invocation.Gradle;

public class HazelcastPlugin implements Plugin<Gradle> {
    @Override
    public void apply(Gradle gradle) {
        gradle.getTaskCaching().useCacheFactory(new TaskOutputCacheFactory() {
            @Override
            public TaskOutputCache createCache(StartParameter startParameter) {
                ClientConfig config = new ClientConfig();
                String host = getSystemProperty(startParameter, "org.gradle.cache.tasks.hazelcast.host", "127.0.0.1");
                String port = getSystemProperty(startParameter, "org.gradle.cache.tasks.hazelcast.port", "5701");
                String name = getSystemProperty(startParameter, "org.gradle.cache.tasks.hazelcast.name", "gradle-task-cache");
                String address = host + ":" + port;
                config.getNetworkConfig().addAddress(address);
                final HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
                return new MapBasedTaskOutputCache(
                    String.format("Hazelcast cache '%s' at %s", name, address),
                    instance.<String, byte[]>getMap(name)
                );
            }
        });
    }

    private static String getSystemProperty(StartParameter startParameter, String property, String defaultValue) {
        String value = startParameter.getSystemPropertiesArgs().get(property);
        return value == null || value.isEmpty() ? defaultValue : value;
    }
}
