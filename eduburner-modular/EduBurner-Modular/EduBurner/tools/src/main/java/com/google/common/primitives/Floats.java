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
 * Static utility methods pertaining to {@code float} primitives, that are not
 * already found in either {@link Float} or {@link java.util.Arrays}.
 *
 * @author Kevin Bourrillion
 * @since 9.09.15 <b>tentative</b>
 */
@GwtCompatible
public final class Floats {
  private Floats() {}

  /**
   * Returns a hash code for {@code value}; equal to the result of invoking
   * {@code ((Float) value).hashCode()}.
   *
   * @param value a primitive {@code float} value
   * @return a hash code for the value
   */
  public static int hashCode(float value) {
    // TODO: is there a better way, that's still gwt-safe?
    return ((Float) value).hashCode();
  }

  /**
   * Compares the two specified {@code float} values using {@link
   * Float#compare(float, float)}. You may prefer to invoke that method
   * directly; this method exists only for consistency with the other utilities
   * in this package.
   *
   * @param a the first {@code float} to compare
   * @param b the second {@code float} to compare
   * @return the result of invoking {@link Float#compare(float, float)}
   */
  public static int compare(float a, float b) {
    return Float.compare(a, b);
  }

  /**
   * Returns {@code true} if {@code target} is present as an element anywhere in
   * {@code array}. Note that this always returns {@code false} when {@code
   * target} is {@code NaN}.
   *
   * @param array an array of {@code float} values, possibly empty
   * @param target a primitive {@code float} value
   * @return {@code true} if {@code array[i] == target} for some value of {@code
   *     i}
   */
  public static boolean contains(float[] array, float target) {
    for (float value : array) {
      if (value == target) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the index of the first appearance of the value {@code target} in
   * {@code array}. Note that this always returns {@code -1} when {@code target}
   * is {@code NaN}.
   *
   * @param array an array of {@code float} values, possibly empty
   * @param target a primitive {@code float} value
   * @return the least index {@code i} for which {@code array[i] == target}, or
   *     {@code -1} if no such index exists.
   */
  public static int indexOf(float[] array, float target) {
    return indexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int indexOf(
      float[] array, float target, int start, int end) {
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
   * <p>Note that this always returns {@code -1} when {@code target} contains
   * {@code NaN}.
   *
   * @param array the array to search for the sequence {@code target}
   * @param target the array to search for as a sub-sequence of {@code array}
   */
  public static int indexOf(float[] array, float[] target) {
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
   * {@code array}. Note that this always returns {@code -1} when {@code target}
   * is {@code NaN}.
   *
   * @param array an array of {@code float} values, possibly empty
   * @param target a primitive {@code float} value
   * @return the greatest index {@code i} for which {@code array[i] == target},
   *     or {@code -1} if no such index exists.
   */
  public static int lastIndexOf(float[] array, float target) {
    return lastIndexOf(array, target, 0, array.length);
  }

  // TODO: consider making this public
  private static int lastIndexOf(
      float[] array, float target, int start, int end) {
    for (int i = end - 1; i >= start; i--) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the least value present in {@code array}, using the same rules of
   * comparison as {@link Math#min(float, float)}.
   *
   * @param array a <i>nonempty</i> array of {@code float} values
   * @return the value present in {@code array} that is less than or equal to
   *     every other value in the array
   * @throws IllegalArgumentException if {@code array} is empty
   */
  public static float min(float... array) {
    checkArgument(array.length > 0);
    float min = array[0];
    for (int i = 1; i < array.length; i++) {
      min = Math.min(min, array[i]);
    }
    return min;
  }

  /**
   * Returns the greatest value present in {@code array}, using the same rules
   * of comparison as {@link Math#min(float, float)}.
   *
   * @param array a <i>nonempty</i> array of {@code float} values
   * @return the value present in {@code array} that is greater than or equal to
   *     every other value in the array
   * @throws IllegalArgumentException if {@code array} is empty
   */
  public static float max(float... array) {
    checkArgument(array.length > 0);
    float max = array[0];
    for (int i = 1; i < array.length; i++) {
      max = Math.max(max, array[i]);
    }
    return max;
  }

  /**
   * Returns the values from each provided array combined into a single array.
   * For example, {@code concat(new float[] {a, b}, new float[] {}, new
   * float[] {c}} returns the array {@code {a, b, c}}.
   *
   * @param arrays zero or more {@code float} arrays
   * @return a single array containing all the values from the source arrays, in
   *     order
   */
  public static float[] concat(float[]... arrays) {
    int length = 0;
    for (float[] array : arrays) {
      length += array.length;
    }
    float[] result = new float[length];
    int pos = 0;
    for (float[] array : arrays) {
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
  public static float[] ensureCapacity(
      float[] array, int minLength, int padding) {
    checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
    checkArgument(padding >= 0, "Invalid padding: %s", padding);
    return (array.length < minLength)
        ? copyOf(array, minLength + padding)
        : array;
  }

  // Arrays.copyOf() requires Java 6
  private static float[] copyOf(float[] original, int length) {
    float[] copy = new float[length];
    System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
    return copy;
  }

  /**
   * Returns a string containing the supplied {@code float} values, converted
   * to strings as specified by {@link Float#toString(float)}, and separated by
   * {@code separator}. For example, {@code join("-", 1.0f, 2.0f, 3.0f)}
   * returns the string {@code "1.0-2.0-3.0"}.
   *
   * @param separator the text that should appear between consecutive values in
   *     the resulting string (but not at the start or end)
   * @param array an array of {@code float} values, possibly empty
   */
  public static String join(String separator, float... array) {
    checkNotNull(separator);
    if (array.length == 0) {
      return "";
    }

    // For pre-sizing a builder, just get the right order of magnitude
    StringBuilder builder = new StringBuilder(array.length * 12);
    builder.append(array[0]);
    for (int i = 1; i < array.length; i++) {
      builder.append(separator).append(array[i]);
    }
    return builder.toString();
  }

  /**
   * Copies a collection of {@code Float} instances into a new array of
   * primitive {@code float} values.
   *
   * @param collection a collection of {@code Float} objects
   * @return an array containing the same values as {@code collection}, in the
   *     same order, converted to primitives
   * @throws NullPointerException if {@code collection} or any of its elements
   *     is null
   */
  public static float[] toArray(Collection<Float> collection) {
    if (collection instanceof FloatArrayAsList) {
      return ((FloatArrayAsList) collection).toFloatArray();
    }

    // TODO: handle collection being concurrently modified
    int counter = 0;
    float[] array = new float[collection.size()];
    for (Float value : collection) {
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
   * {@code Float} objects written to or read from it.  For example, whether
   * {@code list.get(0) == list.get(0)} is true for the returned list is
   * unspecified.
   *
   * <p>The returned list may have unexpected behavior if it contains {@code
   * NaN}, or if {@code NaN} is used as a parameter to any of its methods.
   *
   * @param backingArray the array to back the list
   * @return a list view of the array
   */
  public static List<Float> asList(float... backingArray) {
    if (backingArray.length == 0) {
      return Collections.emptyList();
    }
    return new FloatArrayAsList(backingArray);
  }

  @GwtCompatible
  private static class FloatArrayAsList extends AbstractList<Float>
      implements RandomAccess, Serializable {
    final float[] array;
    final int start;
    final int end;

    FloatArrayAsList(float[] array) {
      this(array, 0, array.length);
    }

    FloatArrayAsList(float[] array, int start, int end) {
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

    @Override public Float get(int index) {
      checkElementIndex(index, size());
      return array[start + index];
    }

    @Override public boolean contains(Object target) {
      // Overridden to prevent a ton of boxing
      return (target instanceof Float)
          && Floats.indexOf(array, (Float) target, start, end) != -1;
    }

    @Override public int indexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Float) {
        int i = Floats.indexOf(array, (Float) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public int lastIndexOf(Object target) {
      // Overridden to prevent a ton of boxing
      if (target instanceof Float) {
        int i = Floats.lastIndexOf(array, (Float) target, start, end);
        if (i >= 0) {
          return i - start;
        }
      }
      return -1;
    }

    @Override public Float set(int index, Float element) {
      checkElementIndex(index, size());
      float oldValue = array[start + index];
      array[start + index] = element;
      return oldValue;
    }

    /** In GWT, List and AbstractList do not have the subList method. */
    /*@Override*/ public List<Float> subList(int fromIndex, int toIndex) {
      int size = size();
      checkPositionIndexes(fromIndex, toIndex, size);
      if (fromIndex == toIndex) {
        return Collections.emptyList();
      }
      return new FloatArrayAsList(array, start + fromIndex, start + toIndex);
    }

    @Override public boolean equals(Object object) {
      if (object == this) {
        return true;
      }
      if (object instanceof FloatArrayAsList) {
        FloatArrayAsList that = (FloatArrayAsList) object;
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
        result = 31 * result + Floats.hashCode(array[i]);
      }
      return result;
    }

    @Override public String toString() {
      StringBuilder builder = new StringBuilder(size() * 12);
      builder.append('[').append(array[start]);
      for (int i = start + 1; i < end; i++) {
        builder.append(", ").append(array[i]);
      }
      return builder.append(']').toString();
    }

    float[] toFloatArray() {
      // Arrays.copyOfRange() requires Java 6
      int size = size();
      float[] result = new float[size];
      System.arraycopy(array, start, result, 0, size);
      return result;
    }

    private static final long serialVersionUID = 0;
  }
}
