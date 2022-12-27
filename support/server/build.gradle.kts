val ktorVersion: String by project

dependencies {
    api(platform("io.ktor:ktor-bom:$ktorVersion"))
    api("io.ktor:ktor-server-netty")
    api("io.ktor:ktor-server-content-negotiation")
    api("io.ktor:ktor-serialization-kotlinx-json")
}
