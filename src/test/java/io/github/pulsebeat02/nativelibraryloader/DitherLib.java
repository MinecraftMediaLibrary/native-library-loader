package io.github.pulsebeat02.nativelibraryloader;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface DitherLib extends Library {

  DitherLib INSTANCE = Native.load("dither", DitherLib.class);

  Pointer filterLiteDither(
      final int[] colors, final byte[] fullColors, final int[] buffer, final int width);

  Pointer floydSteinbergDither(
      final int[] colors, final byte[] fullColors, final int[] buffer, final int width);

  Pointer randomDither(
      final int[] colors, final byte[] fullColors, final int[] buffer, final int width, final int weight);

  Pointer simpleDither(
      final int[] colors, final byte[] fullColors, final int[] buffer, final int width);
}
