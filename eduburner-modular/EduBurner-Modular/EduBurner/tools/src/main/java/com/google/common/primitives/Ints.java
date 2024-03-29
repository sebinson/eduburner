/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndexes;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/**
 * Static utility methods pertaining to {@code int} primitives, that are not
 * already found in either {@link Integer} or {@link java.util.Arrays}.
 *
 * @author Kevin Bourrillion
 * @since 9.09.15 <b>tentative</b>
 */
@GwtCompatible
public final class Ints {
  private Ints() {}

  /**
   * The number of bytes required to represent a primitive {@code int}
   * value.
   */
  public static final int BYTES = Integer.SIZE / Byte.SIZE;

  /**
   * Returns a hash code for {@code value}; equal to the result of invoking
   * {@code ((Integer) value).hashCode()}.
   *
   * @param value a primitive {@code int} value
   * @return a hash code for the value
   */
  public static int hashCode(int value) {
    return value;
  }

  /**
   * Returns the {@code int} value that is equal to {@code value}, if possible.
   *
   * @param value any value in the range of the {@code int} type
   * @return the {@code int} value that equals {@code value}
   * @throws IllegalArgumentException if {@code value} is greater than {@link
   *     Integer#MAX_VALUE} or less than {@link Integer#MIN_VALUE}
   */
  public static int checkedCast(long value) {
    int result = (int) value;
    checkArgument(result == value, "Out of range: %s", value);
    return result;
  }

  /**
   * Returns the {@code int} nearest in value to {@code value}.
   *
   * @param value any {@code long} value
   * @return the same value cast to {@code int} if it is in the range of the
   *     {@code int} type, {@link Integer#MAX_VALUE} if it is too large,
   *     or {@link Integer#MIN_VALUE} if it is too small
   */
  public static int saturatedCast(long value) {
    if (value > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }
    if (value < Integer.MIN_VALUE) {
      return Integer.MIN_VALUE;
    }
    return (int) value;
  }

  /**
   * Compares the two specified {@code int} values. The sign of the value
   * returned is the same as that of {@code ((Integer) a).compareTo(b)}.
   *
   * @param a the first {@code int} to compare
   * @param b the second {@code int} to compare
   * @return a negative value if {@code a} is less than {@code b}; a positive
   *     value if {@code a} is greater than {@code b}; or zero if they are equal
   */
  public static int compare(int a, int b) {
    return (a < b) ? -1 : ((a > b) ? 1 : 0);
  }

  /**
   * Returns {@code true} if {@code target} is present as an element anywhere in
   * {@code array}.
   *
   * @param array an array of {@code int} values, possibly empty
   * @param target a primitive {@code int} value
   * @return {@code true} if {@code array[i] == target} for some value of {@code
   *     i}
   */
  public static boolean contains(int[] array, int target) {
    for (int value : array) {
      if (value == target) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the index of the first appearance of the value {@code target} in
   * {@code array}.
   *
   * @param array an array of {@code int} values, possibly empty
   * @param target a primitive {@code int} value
   * @return the least index {@code i} for which {@code array[i] == target}, or
   *     {@code -1} if no such index exists.
   */
  public static int indexOf(int[] array, int target) {
    return indexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int indexOf(
      int[] array, int target, int start, int end) {
    for (int i = start; i < end; i++) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the start position of the first occurrence of the specified {@code
   * target} within {@code array}, or {@code -1} if there is no such occurrence.
   *
   * <p>More formally, returns the lowest index {@code i} such that {@code
   * java.util.Arrays.copyOfRange(array, i, i + target.length)} contains exactly
   * the same elements as {@code target}.
   *
   * @param array the array to search for the sequence {@code target}
   * @param target the array to search for as a sub-sequence of {@code array}
   */
  public static int indexOf(int[] array, int[] target) {
    checkNotNull(array, "array");
    checkNotNull(target, "target");
    if (target.length == 0) {
      return 0;
    }

    outer:
    for (int i = 0; i < array.length - target.length + 1; i++) {
      for (int j = 0; j < target.length; j++) {
        if (array[i + j] != target[j]) {
          continue outer;
        }
      }
      return i;
    }
    return -1;
  }

  /**
   * Returns the index of the last appearance of the value {@code target} in
   * {@code array}.
   *
   * @param array an array of {@code int} values, possibly empty
   * @param target a primitive {@code int} value
   * @return the greatest index {@code i} for which {@code array[i] == target},
   *     or {@code -1} if no such index exists.
   */
  public static int lastIndexOf(int[] array, int target) {
    return lastIndexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int lastIndexOf(
      int[] array, int target, int start, int end) {
    for (int i = end - 1; i >= start; i--) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the least value present in {@code array}.
   *
   * @param array a <i>nonempty</i> array of {@code int} values
   * @return the value present in {@code array} that is less than or equal to
   *     every other value in the array
   * @throws IllegalArgumentException if {@code array} is empty
   */
  public static int min(int... array) {
    checkArgument(array.length > 0);
    int min = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] < min) {
        min = array[i];
      }
    }
    return min;
  }

  /**
   * Returns the greatest value present in {@code array}.
   *
   * @param array a <i>nonempty</i> array of {@code int} values
   * @return the value present in {@code array} that is greater than or equal to
   *     every other value in the array
   * @throws IllegalArgumentException if {@code array} is empty
   */
  public static int max(int... array) {
    checkArgument(array.length > 0);
    int max = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
      }
    }
    return max;
  }

  /**
   * Returns the values from each provided array combined into a single array.
   * For example, {@code concat(new int[] {a, b}, new int[] {}, new
   * int[] {c}} returns the array {@code {a, b, c}}.
   *
   * @param arrays zero or more {@code int} arrays
   * @return a single array containing all the values from the source arrays, in
   *     order
   */
  public static int[] concat(int[]... arrays) {
    int length = 0;
    for (int[] array : arrays) {
      length += array.length;
    }
    int[] result = new int[length];
    int pos = 0;
    for (int[] array : arrays) {
      System.arraycopy(array, 0, result, pos, array.length);
      pos += array.length;
    }
    return result;
  }

  /**
   * Returns a big-endian representation of {@code value} in a 4-element byte
   * array; equivalent to {@code ByteBuffer.allocate(4).putInt(value).array()}.
   * For example, the input value {@code 0x12131415} would yield the byte array
   * {@code {0x12, 0x13, 0x14, 0x15}}.
   *
   * <p>If you need to convert and concatenate several values (possibly even of
   * different types), use a shared {@link java.nio.ByteBuffer} instance, or use
   * {@link com.google.common.io.ByteStreams#newDataOutput()} to get a growable
   * buffer.
   *
   * <p><b>Warning:</b> do not use this method in GWT. It returns wrong answers.
   */
  @GwtIncompatible("doesn't work")
  public static byte[] toByteArray(int value) {
    return new byte[] {
        (byte) (value >> 24),
        (byte) (value >> 16),
        (byte) (value >> 8),
        (byte) value};
  }

  /**
   * Returns the {@code int} value whose big-endian representation is stored in
   * the first 4 bytes of {@code bytes}; equivalent to {@code
   * ByteBuffer.wrap(bytes).getInt()}. For example, the input byte array {@code
   * {0x12, 0x13, 0x14, 0x15, 0x33}} would yield the {@code int} value {@code
   * 0x12131415}.
   *
   * <p>Arguably, it's preferable to use {@link java.nio.ByteBuffer}; that
   * library exposes much more flexibility at little cost in readability.
   *
   * <p><b>Warning:</b> do not use this method in GWT. It returns wrong answers.
   *
   * @throws IllegalArgumentException if {@code bytes} has fewer than 4 elements
   */
  @GwtIncompatible("doesn't work")
  public static int fromByteArray(byte[] bytes) {
    checkArgument(bytes.length >= BYTES,
        "array too small: %s < %s", bytes.length, BYTES);
    return bytes[0] << 24
        | (bytes[1] & 0xFF) << 16
        | (bytes[2] & 0xFF) << 8
        | (bytes[3] & 0xFF);
  }

  /**
   * Returns an array containing the same values as {@code array}, but
   * guaranteed to be of a specified minimum length. If {@code array} already
   * has a length of at least {@code minLength}, it is returned directly.
   * Otherwise, a new array of size {@code minLength + padding} is returned,
   * containing the values of {@code array}, and zeroes in the remaining places.
   *
   * @param array the source array
   * @param minLength the minimum length the returned array must guarantee
   * @param padding an extra amount to "grow" the array by if growth is
   *     necessary
   * @throws IllegalArgumentException if {@code minLength} or {@code padding} is
   *     negative
   * @return an array containing the values of {@code array}, with guaranteed
   *     minimum length {@code minLength}
   */
  public static int[] ensureCapacity(
      int[] array, int minLength, int padding) {
    checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
    checkArgument(padding >= 0, "Invalid padding: %s", padding);
    return (array.length < minLength)
        ? copyOf(array, minLength + padding)
        : array;
  }

  // Arrays.copyOf() requires Java 6
  private static int[] copyOf(int[] original, int length) {
    int[] copy = new int[length];
    System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
    return copy;
  }

  /**
   * Returns a string containing the supplied {@code int} values separated
   * by {@code separator}. For example, {@code join("-", 1, 2, 3)} returns
   * the string {@code "1-2-3"}.
   *
   * @param separator the text that should appear between consecutive values in
   *     the resulting string (but not at the start or end)
   * @param array an array of {@code int} values, possibly empty
   */
  public static String join(String separator, int... array) {
    checkNotNull(separator);
    if (array.length == 0) {
      return "";
    }

    // For pre-sizing a builder, just get the right order of magnitude
    StringBuilder builder = new StringBuilder(array.length * 5);
    builder.append(array[0]);
    for (int i = 1; i < array.length; i++) {
      builder.append(separator).append(array[i]);
    }
    return builder.toString();
  }

  /**
   * Copies a collection of {@code Integer} instances into a new array of
   * primitive {@code int} values.
   *
   * @param collection a collection of {@code Integer} objects
   * @return an array containing the same values as {@code collection}, in the
   *     same order, converted to primitives
   * @throws NullPointerException if {@code collection} or any of its elements
   *     is null
   */
  public static int[] toArray(Collection<Integer> collection) {
    if (collection instanceof IntArrayAsList) {
      return ((IntArrayAsList) collection).toIntArray();
    }

    // TODO: handle collection being concurrently modified
    int counter = 0;
    int[] array = new int[collection.size()];
    for (Integer value : collection) {
      array[counter++] = value;
    }
    return array;
  }

  /**
   * Returns a fixed-size list backed by the specified array, similar to {@link
   * java.util.Arrays#asList(Object[])}. The list supports {@link java.util.List#set(int, Object)},
   * but any attempt to set a value to {@code null} will result in a {@link
   * NullPointerException}.
   *
   * <p>The returned list maintains the values, but not the identities, of
   * {@code Integer} objects written to or read from it.  For example, whether
   * {@code list.get(0) == list.get(0)} is true for the returned list is
   * unspecified.
   *
   * @param backingArray the array to back the list
   * @return a list view of the array
   */
  public static List<Integer> asList(int... backingArray) {
    if (backingArray.length == 0) {
      return Collections.emptyList();
    }
    return new IntArrayAsList(backingArray);
  }

  @GwtCompatible
  private static class IntArrayAsList extends AbstractList<Integer>
      implements RandomAccess, Serializable {
    final int[] array;
    final int start;
    final int end;

    IntArrayAsList(int[] array) {
      this(array, 0, array.length);
    }

    IntArrayAsList(int[] array, int start, int end) {
      this.array = array;
      this.start = start;
      this.end = end;
    }

    @Override public int size() {
      return end - start;
    }

    @Override public boolean isEmpty() {
      return false;
    }

    @Override public Integer get(int index) {
      checkElementIndex(index, size());
      return array[start + index];
    }

    @Override public boolean contains(Object target) {
      // Overridden to prevent a ton of boxing
      return (target instanceof Integer)
          && Ints.indexOf(array, (Integer) target, start, end) != -1;
    }

    @Override public int indexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Integer) {
        int i = Ints.indexOf(array, (Integer) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public int lastIndexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Integer) {
        int i = Ints.lastIndexOf(array, (Integer) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public Integer set(int index, Integer element) {
      checkElementIndex(index, size());
      int oldValue = array[start + index];
      array[start + index] = element;
      return oldValue;
    }

    /** In GWT, List and AbstractList do not have the subList method. */
    /*@Override*/ public List<Integer> subList(int fromIndex, int toIndex) {
      int size = size();
      checkPositionIndexes(fromIndex, toIndex, size);
      if (fromIndex == toIndex) {
        return Collections.emptyList();
      }
      return new IntArrayAsList(array, start + fromIndex, start + toIndex);
    }

    @Override public boolean equals(Object object) {
      if (object == this) {
        return true;
      }
      if (object instanceof IntArrayAsList) {
        IntArrayAsList that = (IntArrayAsList) object;
        int size = size();
        if (that.size() != size) {
          return false;
        }
        for (int i = 0; i < size; i++) {
          if (array[start + i] != that.array[that.start + i]) {
            return false;
          }
        }
        return true;
      }
      return super.equals(object);
    }

    @Override public int hashCode() {
      int result = 1;
      for (int i = start; i < end; i++) {
        result = 31 * result + Ints.hashCode(array[i]);
      }
      return result;
    }

    @Override public String toString() {
      StringBuilder builder = new StringBuilder(size() * 5);
      builder.append('[').append(array[start]);
      for (int i = start + 1; i < end; i++) {
        builder.append(", ").append(array[i]);
      }
      return builder.append(']').toString();
    }

    int[] toIntArray() {
      // Arrays.copyOfRange() requires Java 6
      int size = size();
      int[] result = new int[size];
      System.arraycopy(array, start, result, 0, size);
      return result;
    }

    private static final long serialVersionUID = 0;
  }
}
