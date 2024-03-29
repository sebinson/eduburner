/*
 * Copyright (C) 2006 Google Inc.
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

package com.google.common.io;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * File name filter that only accepts files matching a regular expression.
 *
 * @author Apple Chow
 * @since 9.09.15 <b>tentative</b>
 */
public final class PatternFilenameFilter implements FilenameFilter {

  private final Pattern pattern;

  /**
   * Constructs a pattern file name filter object.
   * @param patternStr the pattern string on which to filter file names
   *
   * @throws java.util.regex.PatternSyntaxException if pattern compilation fails (runtime)
   */
  public PatternFilenameFilter(String patternStr) {
    this(Pattern.compile(patternStr));
  }

  /**
   * Constructs a pattern file name filter object.
   * @param pattern the pattern on which to filter file names
   */
  public PatternFilenameFilter(Pattern pattern) {
    this.pattern = Preconditions.checkNotNull(pattern);
  }

  @Override public boolean accept(File dir, String fileName) {
    return pattern.matcher(fileName).matches();
  }
}
