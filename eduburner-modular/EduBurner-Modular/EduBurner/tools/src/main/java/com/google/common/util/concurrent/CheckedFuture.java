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

package com.google.common.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A {@code CheckedFuture} is an extension of {@link java.util.concurrent.Future} that includes
 * versions of the {@code get} methods that can throw a checked exception and
 * allows listeners to be attached to the future.  This makes it easier to
 * create a future that executes logic which can throw an exception.
 * 
 * <p>Implementations of this interface must adapt the exceptions thrown by
 * {@code Future#get()}: {@link java.util.concurrent.CancellationException},
 * {@link java.util.concurrent.ExecutionException} and {@link InterruptedException} into the type
 * specified by the {@code E} type parameter.
 * 
 * <p>This interface also extends the ListenableFuture interface to allow
 * listeners to be added. This allows the future to be used as a normal
 * {@link java.util.concurrent.Future} or as an asynchronous callback mechanism as needed. This
 * allows multiple callbacks to be registered for a particular task, and the
 * future will guarantee execution of all listeners when the task completes.
 * 
 * @author Sven Mawson
 * @since 9.09.15 <b>tentative</b>
 */
public interface CheckedFuture<V, E extends Exception>
    extends ListenableFuture<V> {

  /**
   * Exception checking version of {@link java.util.concurrent.Future#get()} that will translate
   * {@link InterruptedException}, {@link java.util.concurrent.CancellationException} and
   * {@link java.util.concurrent.ExecutionException} into application-specific exceptions.
   * 
   * @return the result of executing the future.
   * @throws E on interruption, cancellation or execution exceptions.
   */
  public V checkedGet() throws E;
  
  /**
   * Exception checking version of {@link java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)} that will
   * translate {@link InterruptedException}, {@link java.util.concurrent.CancellationException} and
   * {@link java.util.concurrent.ExecutionException} into application-specific exceptions.  On
   * timeout this method throws a normal {@link java.util.concurrent.TimeoutException}.
   * 
   * @return the result of executing the future.
   * @throws java.util.concurrent.TimeoutException if retrieving the result timed out.
   * @throws E on interruption, cancellation or execution exceptions.
   */
  public V checkedGet(long timeout, TimeUnit unit)
      throws TimeoutException, E;
}
