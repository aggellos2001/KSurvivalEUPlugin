import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    java
    kotlin("jvm") version "1.4.31"
    id("com.github.ben-manes.versions") version ("0.38.0")
    id("org.jetbrains.dokka") version "1.4.30"
    id ("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "me.aggellos2001.ksurvivaleuplugin"
version = "1.1.0"
val projectVersion = version

repositories {

    mavenCentral()
    mavenLocal()
    jcenter()

    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://repo.mattstudios.me/artifactory/public")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://maven.playpro.com")

}

dependencies {

    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("net.ess3:EssentialsX:2.17.2")
    compileOnly("net.coreprotect:coreprotect:19.5")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    implementation("co.aikar:taskchain-bukkit:3.7.2")
    implementation("me.mattstudios.utils:matt-framework-gui:2.0.3.3")
    implementation("com.github.linkedin:URL-Detector:-SNAPSHOT")
    implementation("me.mattstudios:mf-msg-bukkit:2.0.2")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.1")

}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        useIR = true
        jvmTarget = "1.8"
        sourceCompatibility = "1.8"
        languageVersion = "1.4"
        apiVersion = "1.4"
        javaParameters = true
        freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>().configureEach {

    inputs.file("${projectDir}/build.gradle.kts")

    this.dependsOn("assemble")

    relocate("kotlin","me.aggellos2001.kotlin")
    relocate("co.aikar.commands","me.aggellos2001.acf")
    relocate("co.aikar.locales","me.aggellos2001.locales")
    relocate("co.aikar.taskchain","me.aggellos2001.taskchain")
    relocate("co.aikar.mfgui","me.aggellos2001.mattlib")
    relocate("co.aikar.mfmsg","me.aggellos2001.mfmsg")

    archiveFileName.set("KSurvivalEUPlugin-$projectVersion.jar")
    destinationDirectory.set(file("C:\\Users\\aggel\\Desktop\\Minecraft_Test_Servers\\Test_server\\plugins"))
}


tasks.processResources {
    expand("version" to version)
}