# Gradle Hazelcast cache backend plugin

An [init-script plugin](https://docs.gradle.org/current/userguide/init_scripts.html#N14C1D) that enables task output caching in Gradle with a [Hazelcast](http://hazelcast.org) node as the backend.

## How to use

Create `init-hazelcast.gradle` in your `$GRADLE_HOME/init.d` directory:

```groovy
initscript {
  repositories {
    maven { url "https://gradle.github.io/gradle-hazelcast-plugin/repo/" }
    mavenCentral()
  }

  dependencies {
    classpath "org.gradle.cache.tasks.hazelcast:gradle-hazelcast-plugin:0.1.+"
  }
}

apply plugin: org.gradle.cache.tasks.hazelcast.HazelcastPlugin
```

You can specify the location and name of the Hazelcast cache. To do this, you can use these system properties:

System property                         | Function                            | Default value
--------------------------------------- | ----------------------------------- | ------------
`org.gradle.cache.tasks.hazelcast.host` | host name of the Hazelcast node | `127.0.0.1`
`org.gradle.cache.tasks.hazelcast.port` | TCP port of the Hazelcast node  | `5701`
`org.gradle.cache.tasks.hazelcast.name` | name of the cache               | `gradle-task-cache`
