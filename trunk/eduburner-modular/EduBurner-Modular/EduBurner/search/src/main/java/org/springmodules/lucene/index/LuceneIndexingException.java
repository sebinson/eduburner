/*
 * Copyright 2002-2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springmodules.lucene.index;

import org.springmodules.lucene.LuceneException;

/**
 * This exception encapsulates errors during the indexing other than index
 * manipulation.
 * 
 * @author Thierry Templier
 */
public class LuceneIndexingException extends LuceneException {

	private static final long serialVersionUID = -8967622950748744803L;

	/**
	 * Constructor for LuceneIndexingException.
	 * @param msg message
	 */
	public LuceneIndexingException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for LuceneIndexingException.
	 * @param msg message
	 * @param ex root cause
	 */
	public LuceneIndexingException(String msg,Exception ex) {
		super(msg,ex);
	}

}
