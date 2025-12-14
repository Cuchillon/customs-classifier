plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "backend"
include("store")
include("auth")
include("auth:api-key-common")
findProject(":auth:api-key-common")?.name = "api-key-common"
