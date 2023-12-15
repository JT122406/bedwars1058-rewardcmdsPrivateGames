plugins {
    java
    idea
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://repo.andrei1058.dev/releases")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.andrei1058.bedwars:bedwars-api:22.3.4")
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

group = "com.andrei1058.bedwars"
version = "2.0.0"
description = "reward-cmds"
java.sourceCompatibility = JavaVersion.VERSION_1_8

