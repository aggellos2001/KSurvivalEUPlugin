import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.5.20"
    id("com.github.ben-manes.versions") version ("0.38.0")
    id("org.jetbrains.dokka") version "1.4.32"
    id ("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.aggellos2001.ksurvivaleuplugin"
version = "1.2.5"
val projectVersion = version

repositories {

    mavenCentral()
    mavenLocal()

    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://repo.mattstudios.me/artifactory/public")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://maven.playpro.com")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://dl.bintray.com/ichbinjoe/public/")

}

dependencies {

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("net.ess3:EssentialsX:2.18.2")
    compileOnly("net.coreprotect:coreprotect:19.5")
    compileOnly("com.github.NuVotifier:NuVotifier:2.7.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    implementation("co.aikar:taskchain-bukkit:3.7.2")
    implementation("dev.triumphteam:triumph-gui:3.0.3")
    implementation("com.github.linkedin:URL-Detector:-SNAPSHOT")
    implementation("me.mattstudios:mf-msg-bukkit:2.0.2")
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.2")

}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "16"
    targetCompatibility = "16"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "16"
        sourceCompatibility = "16"
        javaParameters = true
        freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>().configureEach {

    relocate("kotlin","me.aggellos2001.kotlin")
    relocate("co.aikar.commands","me.aggellos2001.acf")
    relocate("co.aikar.locales","me.aggellos2001.locales")
    relocate("co.aikar.taskchain","me.aggellos2001.taskchain")
    relocate("me.mattstudios.mfmsg","me.aggellos2001.mfmsg")
    relocate("dev.triumphteam.gui","me.aggellos2001.triumphteam-gui")
    relocate("com.google.errorprone","me.aggellos2001.errorprone")
    relocate("com.github.benmanes.caffeine.cache","me.aggellos2001.caffeine.cache")
    relocate("com.linkedin.urls","me.aggellos2001.linkedin.urls")
    relocate("org.apache.commons.lang3","me.aggellos2001.apache.commons.lang3")
    relocate("org.jetbrains.annotations","me.aggellos2001.annotations")
    relocate("org.intellij","me.aggellos2001.intellij")
    relocate("org.checkerframework","me.aggellos2001.checkerframework")

    archiveFileName.set("KSurvivalEUPlugin-$projectVersion.jar")

}

tasks.processResources {
    expand("version" to version)
}

publishing {
    publications {
        group = project.group
    }
}