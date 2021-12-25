/**
 * MIT License
 *
 * <p>Copyright (c) 2021 Brandon Li
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.pulsebeat02.nativelibraryloader.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public final class NativeUtils {

  private static final Path TEMPORARY_DIRECTORY;

  static {
    try {
      TEMPORARY_DIRECTORY = createTempDirectory("native-library-loader");
      deleteOnExit(TEMPORARY_DIRECTORY);
    } catch (final IOException e) {
      throw new AssertionError(e);
    }
  }

  private NativeUtils() {}

  public static void loadLibraryFromUrl(final String url) throws IOException {
    System.load(downloadFile(url).toString());
  }

  private static Path downloadFile(final String url) throws IOException {
    final URL website = new URL(url);
    final Path target = TEMPORARY_DIRECTORY.resolve(getFileName(url));
    try (final InputStream in = website.openStream()) {
      Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
    }
    return target;
  }

  private static String getFileName(final String url) {
    return url.substring(url.lastIndexOf('/') + 1);
  }

  public static void loadLibraryFromJar(final String path) throws IOException {

    if (!isAbsolute(path)) {
      throw new IllegalArgumentException("The path has to be absolute (start with '/').");
    }

    final String[] parts = path.split("/");
    final String filename = (parts.length > 1) ? parts[parts.length - 1] : null;

    if (!isValidName(filename)) {
      throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
    }

    loadNativeBinary(copyNativeLibrary(path, filename));
  }

  private static boolean isAbsolute(final String path) {
    return path != null && path.startsWith("/");
  }

  private static boolean isValidName(final String name) {
    return name != null && name.length() >= 3;
  }

  private static void loadNativeBinary(final Path temp) throws IOException {
    try {
      System.load(temp.toString());
    } finally {
      if (isPosixCompliant()) {
        Files.deleteIfExists(temp);
      } else {
        deleteOnExit(temp);
      }
    }
  }

  private static Path copyNativeLibrary(final String path, final String filename)
      throws IOException {
    final Path temp = TEMPORARY_DIRECTORY.resolve(filename);
    try (final InputStream is = NativeUtils.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Native library stream cannot be null!");
      }
      Files.copy(is, temp, StandardCopyOption.REPLACE_EXISTING);
    }
    return temp;
  }

  private static void deleteOnExit(final Path path) throws IOException {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> deleteExceptionally(path)));
  }

  private static void deleteExceptionally(final Path path) {
    try (final Stream<Path> stream = Files.walk(path).parallel()) {
      stream.filter(Files::isRegularFile).forEach(NativeUtils::deleteFileExceptionally);
      Files.deleteIfExists(path);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private static void deleteFileExceptionally(final Path path) {
    try {
      Files.deleteIfExists(path);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isPosixCompliant() {
    try {
      return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
    } catch (final FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) {
      return false;
    }
  }

  private static Path createTempDirectory(final String prefix) throws IOException {
    final String temp = System.getProperty("java.io.tmpdir");
    final Path directory = Paths.get(temp, prefix + System.nanoTime());
    Files.createDirectory(directory);
    return directory;
  }
}
