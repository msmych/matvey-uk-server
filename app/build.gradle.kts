plugins {
    application
}

val ktorVersion: String by project
val hikariCpVersion: String by project

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation(project(":db-schema"))
    implementation(project(":cards"))
}

application {
    mainClass.set("uk.matvey.AppKt")
}
