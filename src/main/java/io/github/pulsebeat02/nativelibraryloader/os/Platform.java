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
package io.github.pulsebeat02.nativelibraryloader.os;

import io.github.pulsebeat02.nativelibraryloader.utils.OSUtils;

/** Platform containing Operating System and CPU Architecture for a specific binary to be loaded. */
public final class Platform {

  private final OS os;
  private final Arch arch;
  private final Bits bits;
  private final boolean matches;

  Platform(final OS os, final Arch arch, final Bits bits) {
    this.os = os;
    this.arch = arch;
    this.bits = bits;
    this.matches = OSUtils.getOS() == os && OSUtils.getArm() == arch && OSUtils.getBits() == bits;
  }

  /**
   * Creates a new Platform.
   *
   * @param os the operating system
   * @param arch the cpu architecture (arm or no arm)
   * @param bits the cpu architecture (32-bit or 64-bit)
   * @return new operating system specific
   */
  public static Platform ofPlatform(final OS os, final Arch arch, final Bits bits) {
    return new Platform(os, arch, bits);
  }

  public boolean matchesCurrentOS() {
    return this.matches;
  }

  public OS getOS() {
    return this.os;
  }

  public Arch getArch() {
    return this.arch;
  }

  public Bits getBits() {
    return this.bits;
  }
}
