plugins {
    application
}

val ktorVersion: String by project
val hikariCpVersion: String by project

dependencies {
    implementation(project(":support:server"))

    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation(project(":db-schema"))
    implementation(project(":cards"))
}

application {
    mainClass.set("uk.matvey.AppKt")
}
