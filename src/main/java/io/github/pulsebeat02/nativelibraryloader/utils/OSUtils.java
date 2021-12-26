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

import static io.github.pulsebeat02.nativelibraryloader.os.Bits.BITS_32;
import static io.github.pulsebeat02.nativelibraryloader.os.OS.FREEBSD;
import static io.github.pulsebeat02.nativelibraryloader.os.OS.OSX;
import static io.github.pulsebeat02.nativelibraryloader.os.OS.UNIX;
import static io.github.pulsebeat02.nativelibraryloader.os.OS.WIN;

import io.github.pulsebeat02.nativelibraryloader.os.Arch;
import io.github.pulsebeat02.nativelibraryloader.os.Bits;
import io.github.pulsebeat02.nativelibraryloader.os.OS;
import java.util.Locale;

public final class OSUtils {

  private static final String OS_ARCH;
  private static final OS CURRENT;
  private static final Bits BITS_64;
  private static final Arch ARM;

  static {
    OS_ARCH = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
    CURRENT = getOperatingSystem0();
    BITS_64 = is64Bits0();
    ARM = isArm0();
  }

  private static OS getOperatingSystem0() {
    final String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
      return WIN;
    } else if (os.contains("mac")) {
      return OSX;
    } else if (os.contains("freebsd")) {
      return FREEBSD;
    } else {
      return UNIX;
    }
  }

  private static Bits is64Bits0() {
    if (CURRENT == WIN) {
      final String arch = System.getenv("PROCESSOR_ARCHITECTURE");
      final String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
      final boolean assertion =
          arch != null && arch.endsWith("64") || wow64Arch != null && wow64Arch.endsWith("64");
      return assertion ? BITS_64 : BITS_32;
    } else {
      return OS_ARCH.contains("64") ? BITS_64 : BITS_32;
    }
  }

  private static Arch isArm0() {
    return OS_ARCH.contains("arm") ? Arch.IS_ARM : Arch.NOT_ARM;
  }

  public static OS getOS() {
    return CURRENT;
  }

  public static Bits getBits() {
    return BITS_64;
  }

  public static Arch getArm() {
    return ARM;
  }
}
