package io.github.pulsebeat02.nativelibraryloader;

import io.github.pulsebeat02.nativelibraryloader.os.Arch;
import io.github.pulsebeat02.nativelibraryloader.os.Bits;
import io.github.pulsebeat02.nativelibraryloader.os.OS;
import io.github.pulsebeat02.nativelibraryloader.strategy.LibraryLocation;
import java.nio.file.Paths;

public final class NativeLibraryLoaderTest {

  public static void main(final String[] args) {

    final String local = Paths.get(System.getProperty("user.dir"), "/native/native.dylib").toString();
    final String resource = "/natives/native.dylib";
    final String url = "https://github.com/MinecraftMediaLibrary/native-library-testing/raw/main/native.dylib";

    final NativeLibraryLoader loader =
        NativeLibraryLoader.builder()
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.LOCAL_RESOURCE.create(local))
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.JAR_RESOURCE.create(resource))
            .addNativeLibrary(OS.OSX, Arch.NOT_ARM, Bits.BITS_64, LibraryLocation.URL_RESOURCE.create(url)).build();

    loader.load();

  }
}
