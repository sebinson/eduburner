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
 * Static utility methods pertaining to {@code byte} primitives, that are not
 * already found in either {@link Byte} or {@link java.util.Arrays}, <i>and interpret
 * bytes as neither signed nor unsigned</i>. The methods which specifically
 * treat bytes as signed or unsigned are found in {@link SignedBytes} and {@link
 * UnsignedBytes}.
 *
 * @author Kevin Bourrillion
 * @since 9.09.15 <b>tentative</b>
 */
@GwtCompatible
public final class Bytes {
  private Bytes() {}

  /**
   * Returns a hash code for {@code value}; equal to the result of invoking
   * {@code ((Byte) value).hashCode()}.
   *
   * @param value a primitive {@code byte} value
   * @return a hash code for the value
   */
  public static int hashCode(byte value) {
    return value;
  }

  /**
   * Returns {@code true} if {@code target} is present as an element anywhere in
   * {@code array}.
   *
   * @param array an array of {@code byte} values, possibly empty
   * @param target a primitive {@code byte} value
   * @return {@code true} if {@code array[i] == target} for some value of {@code
   *     i}
   */
  public static boolean contains(byte[] array, byte target) {
    for (byte value : array) {
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
   * @param array an array of {@code byte} values, possibly empty
   * @param target a primitive {@code byte} value
   * @return the least index {@code i} for which {@code array[i] == target}, or
   *     {@code -1} if no such index exists.
   */
  public static int indexOf(byte[] array, byte target) {
    return indexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int indexOf(
      byte[] array, byte target, int start, int end) {
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
  public static int indexOf(byte[] array, byte[] target) {
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
   * @param array an array of {@code byte} values, possibly empty
   * @param target a primitive {@code byte} value
   * @return the greatest index {@code i} for which {@code array[i] == target},
   *     or {@code -1} if no such index exists.
   */
  public static int lastIndexOf(byte[] array, byte target) {
    return lastIndexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int lastIndexOf(
      byte[] array, byte target, int start, int end) {
    for (int i = end - 1; i >= start; i--) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the values from each provided array combined into a single array.
   * For example, {@code concat(new byte[] {a, b}, new byte[] {}, new
   * byte[] {c}} returns the array {@code {a, b, c}}.
   *
   * @param arrays zero or more {@code byte} arrays
   * @return a single array containing all the values from the source arrays, in
   *     order
   */
  public static byte[] concat(byte[]... arrays) {
    int length = 0;
    for (byte[] array : arrays) {
      length += array.length;
    }
    byte[] result = new byte[length];
    int pos = 0;
    for (byte[] array : arrays) {
      System.arraycopy(array, 0, result, pos, array.length);
      pos += array.length;
    }
    return result;
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
  public static byte[] ensureCapacity(
      byte[] array, int minLength, int padding) {
    checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
    checkArgument(padding >= 0, "Invalid padding: %s", padding);
    return (array.length < minLength)
        ? copyOf(array, minLength + padding)
        : array;
  }

  // Arrays.copyOf() requires Java 6
  private static byte[] copyOf(byte[] original, int length) {
    byte[] copy = new byte[length];
    System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
    return copy;
  }

  /**
   * Copies a collection of {@code Byte} instances into a new array of
   * primitive {@code byte} values.
   *
   * @param collection a collection of {@code Byte} objects
   * @return an array containing the same values as {@code collection}, in the
   *     same order, converted to primitives
   * @throws NullPointerException if {@code collection} or any of its elements
   *     is null
   */
  public static byte[] toArray(Collection<Byte> collection) {
    if (collection instanceof ByteArrayAsList) {
      return ((ByteArrayAsList) collection).toByteArray();
    }

    // TODO: handle collection being concurrently modified
    int counter = 0;
    byte[] array = new byte[collection.size()];
    for (Byte value : collection) {
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
   * {@code Byte} objects written to or read from it.  For example, whether
   * {@code list.get(0) == list.get(0)} is true for the returned list is
   * unspecified.
   *
   * @param backingArray the array to back the list
   * @return a list view of the array
   */
  public static List<Byte> asList(byte... backingArray) {
    if (backingArray.length == 0) {
      return Collections.emptyList();
    }
    return new ByteArrayAsList(backingArray);
  }

  @GwtCompatible
  private static class ByteArrayAsList extends AbstractList<Byte>
      implements RandomAccess, Serializable {
    final byte[] array;
    final int start;
    final int end;

    ByteArrayAsList(byte[] array) {
      this(array, 0, array.length);
    }

    ByteArrayAsList(byte[] array, int start, int end) {
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

    @Override public Byte get(int index) {
      checkElementIndex(index, size());
      return array[start + index];
    }

    @Override public boolean contains(Object target) {
      // Overridden to prevent a ton of boxing
      return (target instanceof Byte)
          && Bytes.indexOf(array, (Byte) target, start, end) != -1;
    }

    @Override public int indexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Byte) {
        int i = Bytes.indexOf(array, (Byte) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public int lastIndexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Byte) {
        int i = Bytes.lastIndexOf(array, (Byte) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public Byte set(int index, Byte element) {
      checkElementIndex(index, size());
      byte oldValue = array[start + index];
      array[start + index] = element;
      return oldValue;
    }

    /** In GWT, List and AbstractList do not have the subList method. */
    /*@Override*/ public List<Byte> subList(int fromIndex, int toIndex) {
      int size = size();
      checkPositionIndexes(fromIndex, toIndex, size);
      if (fromIndex == toIndex) {
        return Collections.emptyList();
      }
      return new ByteArrayAsList(array, start + fromIndex, start + toIndex);
    }

    @Override public boolean equals(Object object) {
      if (object == this) {
        return true;
      }
      if (object instanceof ByteArrayAsList) {
        ByteArrayAsList that = (ByteArrayAsList) object;
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
        result = 31 * result + Bytes.hashCode(array[i]);
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

    byte[] toByteArray() {
      // Arrays.copyOfRange() requires Java 6
      int size = size();
      byte[] result = new byte[size];
      System.arraycopy(array, start, result, 0, size);
      return result;
    }

    private static final long serialVersionUID = 0;
  }
}
