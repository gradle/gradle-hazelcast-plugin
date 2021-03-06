# The plugin has moved

The Gradle Hazelcast cache backend plugin is no longer maintained by Gradle. Its new home is at https://github.com/sinwe/gradle-hazelcast-plugin.

----

# Gradle Hazelcast cache backend plugin

[![Build Status](https://travis-ci.org/gradle/gradle-hazelcast-plugin.svg?branch=master)](https://travis-ci.org/gradle/gradle-hazelcast-plugin)

A simple [settings plugin](https://docs.gradle.org/current/dsl/org.gradle.api.initialization.Settings.html) that enables [build caching](https://guides.gradle.org/using-build-cache/) in Gradle with a [Hazelcast](http://hazelcast.org) node as the backend. The Hazelcast node itself needs to be set up separately.

For a production-ready build cache implementation (featuring node management, usage statistics, health monitoring, replication, access control and more), see [Gradle Enteprise](https://gradle.com/build-cache).

## How to use

Add this to your `settings.gradle`:

```groovy
buildscript {
  repositories {
    maven { url "https://repo.gradle.org/gradle/libs/" }
    mavenCentral()
  }

  dependencies {
    classpath "org.gradle.caching.hazelcast:gradle-hazelcast-plugin:0.12"
  }
}

apply plugin: org.gradle.caching.hazelcast.HazelcastPlugin

buildCache {
  // Note: the local cache is disabled by default when applying the plugin
  remote(org.gradle.caching.hazelcast.HazelcastBuildCache) {
    host = "127.0.0.1"
    port = 5701
    name = "gradle-build-cache"
    enabled = true
    push = true
  }
}
```

You can also specify the location and name of the Hazelcast cache via system properties (though values specified in the `settings.gradle` override the ones specified by system properties):

System property                     | Function                        | Default value
----------------------------------- | ------------------------------- | ------------
`org.gradle.caching.hazelcast.host` | host name of the Hazelcast node | `127.0.0.1`
`org.gradle.caching.hazelcast.port` | TCP port of the Hazelcast node  | `5701`
`org.gradle.caching.hazelcast.name` | name of the cache               | `gradle-task-cache`
