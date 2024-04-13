plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
}

// 项目信息
group = "cn.butterfly"
version = "1.0.0"

repositories {
    mavenCentral()
}

// 配置开发过程中运行的 IDEA 沙盒信息
intellij {
    // IDEA 的版本
    version.set("2023.2.5")
    // 这里 IU 是指付费版, 也可以选择 IC 对应社区版
    type.set("IU")
    
    // 用到的插件
    plugins.set(listOf("com.intellij.properties"))
}

tasks {
    // 设置 Java 版本
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    // 设置插件兼容的版本
    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("999.*")
    }
}
