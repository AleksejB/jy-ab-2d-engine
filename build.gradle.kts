plugins {
    kotlin("jvm") version "2.1.20"
}

val lwjglNatives = "natives-macos-arm64"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.lwjgl.bom))
    implementation(libs.lwjgl)
    implementation(libs.lwjgl.assimp)
    implementation(libs.lwjgl.bgfx)
    implementation(libs.lwjgl.cuda)
    implementation(libs.lwjgl.egl)
    implementation(libs.lwjgl.fmod)
    implementation(libs.lwjgl.freetype)
    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.harfbuzz)
    implementation(libs.lwjgl.hwloc)
    implementation(libs.lwjgl.jawt)
    implementation(libs.lwjgl.jemalloc)
    implementation(libs.lwjgl.ktx)
    implementation(libs.lwjgl.libdivide)
    implementation(libs.lwjgl.llvm)
    implementation(libs.lwjgl.lmdb)
    implementation(libs.lwjgl.lz4)
    implementation(libs.lwjgl.meow)
    implementation(libs.lwjgl.meshoptimizer)
    implementation(libs.lwjgl.msdfgen)
    implementation(libs.lwjgl.nanovg)
    implementation(libs.lwjgl.nfd)
    implementation(libs.lwjgl.nuklear)
    implementation(libs.lwjgl.odbc)
    implementation(libs.lwjgl.openal)
    implementation(libs.lwjgl.opencl)
    implementation(libs.lwjgl.opengl)
    implementation(libs.lwjgl.opengles)
    implementation(libs.lwjgl.opus)
    implementation(libs.lwjgl.par)
    implementation(libs.lwjgl.remotery)
    implementation(libs.lwjgl.rpmalloc)
    implementation(libs.lwjgl.shaderc)
    implementation(libs.lwjgl.spvc)
    implementation(libs.lwjgl.stb)
    implementation(libs.lwjgl.tinyexr)
    implementation(libs.lwjgl.tinyfd)
    implementation(libs.lwjgl.vma)
    implementation(libs.lwjgl.vulkan)
    implementation(libs.lwjgl.xxhash)
    implementation(libs.lwjgl.yoga)
    implementation(libs.lwjgl.zstd)
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-bgfx", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-freetype", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-harfbuzz", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-hwloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-jemalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-ktx", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-libdivide", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-llvm", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lmdb", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lz4", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-meow", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-meshoptimizer", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-msdfgen", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nuklear", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengles", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opus", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-par", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-remotery", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-rpmalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-spvc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyexr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-xxhash", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-yoga", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-zstd", classifier = lwjglNatives)
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}