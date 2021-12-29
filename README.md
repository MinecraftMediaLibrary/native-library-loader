# Native Library Loader

## What is the Native Library Loader?

The Native Library Loader is a very lightweight library which allows you to
load native libraries into the classpath from many sources. You specify the
location of each of the binaries, and it will load them accordingly. Here are
some examples to get you started:

```java

    final String local = Paths.get(System.getProperty("user.dir"), "/native/native.dylib").toString();
    final String resource = "/natives/native.dylib";
    final String url = "https://github.com/MinecraftMediaLibrary/native-library-testing/raw/main/native.dylib";

    final NativeLibraryLoader loader =
        NativeLibraryLoader.builder()
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.LOCAL_RESOURCE.create(local))
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.JAR_RESOURCE.create(resource))
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.URL_RESOURCE.create(url)).build();

    loader.load();

```

There are currently 3 supported options for LibraryLocation. You can either provide a local file,
a resource from inside your jar, or a URL. You must also provide the type of operating system that
you want to associate the binary.

## Setup

1) Add the repository:
```kotlin
repositories { 
    maven("https://pulsebeat02.jfrog.io/artifactory/pulse-gradle-release-local/");
}
```

2) Add the dependency:
```kotlin
dependencies {
    implementation("io.github.pulsebeat02:native-library-loader:v1.0.1")
}
```
