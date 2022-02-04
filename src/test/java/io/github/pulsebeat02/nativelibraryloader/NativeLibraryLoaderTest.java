package io.github.pulsebeat02.nativelibraryloader;

import io.github.pulsebeat02.nativelibraryloader.os.Arch;
import io.github.pulsebeat02.nativelibraryloader.os.Bits;
import io.github.pulsebeat02.nativelibraryloader.os.OS;
import io.github.pulsebeat02.nativelibraryloader.strategy.LibraryLocation;
import org.junit.jupiter.api.Test;

public final class NativeLibraryLoaderTest {

  @Test
  public void testNativeLoad() {

    final NativeLibraryLoader loader =
        NativeLibraryLoader.builder()
            .addNativeLibrary(
                OS.OSX,
                Arch.NOT_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/darwin/amd64/libdither.dylib"))
            .addNativeLibrary(
                OS.OSX,
                Arch.IS_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/darwin/arm64/libdither.dylib"))
            .addNativeLibrary(
                OS.WIN,
                Arch.NOT_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/windows/amd64/dither.dll"))
            .addNativeLibrary(
                OS.WIN,
                Arch.NOT_ARM,
                Bits.BITS_32,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/windows/i386/dither.dll"))
            .addNativeLibrary(
                OS.UNIX,
                Arch.NOT_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/linux/amd64/libdither.so"))
            .addNativeLibrary(
                OS.UNIX,
                Arch.IS_ARM,
                Bits.BITS_32,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/linux/arm/libdither.so"))
            .addNativeLibrary(
                OS.UNIX,
                Arch.IS_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/linux/arm64/libdither.so"))
            .addNativeLibrary(
                OS.UNIX,
                Arch.NOT_ARM,
                Bits.BITS_32,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/linux/i386/libdither.so"))
            .addNativeLibrary(
                OS.FREEBSD,
                Arch.NOT_ARM,
                Bits.BITS_64,
                LibraryLocation.URL_RESOURCE.create(
                    "https://github.com/MinecraftMediaLibrary/EzMediaCore-Native-Go/raw/master/binary/freebsd/amd64/libdither.so"))
            .build();
    loader.load(true);

    System.out.println(
        DitherLib.INSTANCE.filterLiteDither(
            new int[] {5, 2}, new byte[] {}, new int[] {5, 3, 3}, 1));
  }
}
