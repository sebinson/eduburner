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

package org.springmodules.lucene.index.resource;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * PropertyEditor for {@link TransactionAttribute} objects. Accepts a String of form
 * <p><code>PROPAGATION_NAME,ISOLATION_NAME,readOnly,timeout_NNNN,+Exception1,-Exception2</code>
 * <p>where only propagation code is required. For example:
 * <p><code>PROPAGATION_MANDATORY,ISOLATION_DEFAULT</code>
 *
 * <p>The tokens can be in <strong>any</strong> order. Propagation and isolation codes
 * must use the names of the constants in the TransactionDefinition class. Timeout values
 * are in seconds. If no timeout is specified, the transaction manager will apply a default
 * timeout specific to the particular transaction manager.
 *
 * <p>A "+" before an exception name substring indicates that transactions should commit
 * even if this exception is thrown; a "-" that they should roll back.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 24.04.2003
 * @see org.springframework.transaction.TransactionDefinition
 * @see org.springframework.core.Constants
 */
public class ResourceAttributeEditor extends PropertyEditorSupport {

	/**
	 * Format is PROPAGATION_NAME,ISOLATION_NAME,readOnly,timeout_NNNN,+Exception1,-Exception2.
	 * Null or the empty string means that the method is non transactional.
	 * @see java.beans.PropertyEditor#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasLength(text)) {
			// tokenize it with ","
			String[] tokens = StringUtils.commaDelimitedListToStringArray(text);
			RuleBasedResourceAttribute attr = new RuleBasedResourceAttribute();
			for (int i = 0; i < tokens.length; i++) {
				// Trim leading and trailing whitespace.
				String token = StringUtils.trimWhitespace(tokens[i].trim());
				// Check whether token contains illegal whitespace within text.
				if (StringUtils.containsWhitespace(token)) {
					throw new IllegalArgumentException(
							"Transaction attribute token contains illegal whitespace: [" + token + "]");
				}
				// Check token type.
				if (token.equals(RuleBasedResourceAttribute.INDEX_READER_OPEN)) {
					attr.setIndexReaderOpen(true);
				}
				else if (token.equals(RuleBasedResourceAttribute.INDEX_WRITER_OPEN)) {
					attr.setIndexWriterOpen(true);
				}
				else if (token.equals(RuleBasedResourceAttribute.WRITE_INDEX_READER_AUTHORIZED)) {
					attr.setWriteOperationsForIndexReaderAuthorized(true);
				}
				else if (token.equals(RuleBasedResourceAttribute.WRITE_INDEX_WRITER_AUTHORIZED)) {
					attr.setWriteOperationsForIndexWriterAuthorized(true);
				}
				else {
					throw new IllegalArgumentException("Invalid transaction attribute token: [" + token + "]");
				}
			}
			setValue(attr);
		}
		else {
			setValue(null);
		}
	}

}
