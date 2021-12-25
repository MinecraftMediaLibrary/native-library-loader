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
package io.github.pulsebeat02.nativelibraryloader;

import io.github.pulsebeat02.nativelibraryloader.os.Arch;
import io.github.pulsebeat02.nativelibraryloader.os.Bits;
import io.github.pulsebeat02.nativelibraryloader.os.OS;
import io.github.pulsebeat02.nativelibraryloader.os.Platform;
import io.github.pulsebeat02.nativelibraryloader.strategy.implementation.ResourceLocator;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public final class NativeLibraryLoader {

  private final List<Entry<Platform, ResourceLocator>> libs;

  NativeLibraryLoader(final List<Entry<Platform, ResourceLocator>> libs) {
    this.libs = libs;
  }

  public void load() {
    this.libs.stream()
        .filter(entry -> entry.getKey().matchesCurrentOS())
        .forEach(entry -> entry.getValue().loadIntoClassloader());
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final List<Entry<Platform, ResourceLocator>> libs;

    {
      this.libs = new ArrayList<>();
    }

    private Builder() {}

    public Builder addNativeLibrary(
        final OS os, final Arch arch, final Bits bits, final ResourceLocator location) {
      this.libs.add(new SimpleImmutableEntry<>(Platform.ofPlatform(os, arch, bits), location));
      return this;
    }

    public List<Entry<Platform, ResourceLocator>> getLibraries() {
      return this.libs;
    }

    public NativeLibraryLoader build() {
      return new NativeLibraryLoader(this.libs);
    }
  }
}
