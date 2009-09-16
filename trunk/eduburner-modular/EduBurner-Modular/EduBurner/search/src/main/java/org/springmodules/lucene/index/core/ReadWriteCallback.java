package org.springmodules.lucene.index.core;

import org.springmodules.lucene.index.factory.LuceneIndexReader;
import org.springmodules.lucene.index.factory.LuceneIndexWriter;

public interface ReadWriteCallback {

	Object doWithReaderWriter(LuceneIndexReader reader, LuceneIndexWriter writer);

}
