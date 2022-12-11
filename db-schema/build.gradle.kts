
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Matchers
import org.jooq.meta.jaxb.MatchersTableType
import java.io.IOException
import java.net.Socket

plugins {
    id("com.palantir.docker-run") version "0.34.0"
    id("org.flywaydb.flyway") version "9.8.1"
    id("nu.studer.jooq") version "8.0"
}

val jooqVersion: String by project
val postgresVersion: String by project

dependencies {
    api(project(":aperol"))
    implementation("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
}

// https://github.com/palantir/gradle-docker#docker-run-plugin
dockerRun {
    name = "matvey-uk-pg"
    image = "postgres:latest"
    ports("55000:5432")
    daemonize = true
    env(mapOf("POSTGRES_USER" to "postgres", "POSTGRES_PASSWORD" to "postgres", "POSTGRES_DB" to "postgres"))
}

tasks.dockerRun {
    onlyIf {
        !portIsInUse(55000)
    }
}

// https://flywaydb.org/documentation/usage/gradle/
flyway {
    url = "jdbc:postgresql://localhost:55000/postgres"
    user = "postgres"
    password = "postgres"
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:${projectDir.absolutePath}/src/main/resources/db/migration")
}

tasks.flywayMigrate {
    dependsOn("dockerRun")
    doFirst {
        Thread.sleep(2000)
    }
}

// https://github.com/etiennestuder/gradle-jooq-plugin
jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:55000/postgres"
                    user = "postgres"
                    password = "postgres"
                }
                generator.apply {
                    database.apply {
                        inputSchema = "public"
                        forcedTypes.add(
                            ForcedType().apply {
                                userType = "java.time.Instant"
                                converter = "uk.matvey.aperol.jooq.InstantConverter"
                                includeExpression = ".*.CREATED_AT|.*.UPDATED_AT"
                            }
                        )
                    }
                    strategy.apply {
                        matchers = Matchers().withTables(
                            MatchersTableType()
                                .withRecordImplements("uk.matvey.aperol.jooq.EntityRecord<CardRecord, UUID>")
                                .withExpression("card")
                        )
                    }
                    generate.apply {
                        isFluentSetters = true
                        isJavaTimeTypes = false
                    }
                }
            }
        }
    }
}

tasks.getByName("generateJooq") {
    dependsOn("flywayMigrate")
}

tasks.dockerStop {
    onlyIf {
        portIsInUse(55000)
    }
}

tasks.dockerRemoveContainer {
    dependsOn("dockerStop")
}

tasks.clean {
    dependsOn("dockerRemoveContainer")
}

fun portIsInUse(port: Int) = try {
    Socket("localhost", port).close()
    true
} catch (e: IOException) {
    false
}