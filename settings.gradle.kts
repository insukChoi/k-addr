rootProject.name = "k-addr"

pluginManagement {
    val properties: Map<String, Any> = extra.properties

    val pluginVersion: Map<String, String> = mapOf(
        "org.springframework" to properties["springBoot"].toString(),
        "io.spring" to properties["springDependency"].toString(),
        "com.fasterxml.jackson" to properties["jackson"].toString(),
        "org.projectlombok" to properties["lombok"].toString()
    )

    resolutionStrategy {
        eachPlugin {
            if (pluginVersion.containsKey(requested.id.namespace)) {
                useVersion(pluginVersion[requested.id.namespace])
            }
        }
    }
}