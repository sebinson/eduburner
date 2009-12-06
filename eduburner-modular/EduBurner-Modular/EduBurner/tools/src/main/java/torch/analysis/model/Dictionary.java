package torch.analysis.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import torch.analysis.SegmentModule;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

public class Dictionary {
    
    private Map<String, Word> map;
    private String dictFiles;
    private Charset charset = Charsets.UTF_8;
    private int maxWordLength = 4;

    @Inject
    public Dictionary(@Named("dictFiles") String dictFiles, @Named("maxWordLength") int maxWordLength){
        this.dictFiles = dictFiles;
        this.maxWordLength = maxWordLength;
        map = Maps.newHashMap();
        try {
            loadDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDictionary() throws IOException {
    	ResourceLoader resourceLoader = new DefaultResourceLoader();
    	Iterable<String> filePaths = Splitter.on(",").trimResults().split(dictFiles);
    	for(String filePath : filePaths){
    		loadWords(resourceLoader.getResource(filePath).getFile());
    	}
    }

    private void loadWords(File file) throws IOException {
        Files.readLines(file, charset, new LineProcessor<Map<String, Word>>(){
            @Override
            public boolean processLine(String word) throws IOException {
                if (word.indexOf("#") == -1) {
					if (word.indexOf(" ") == -1) {
						if (word.length() <= maxWordLength) {
                            map.put(word, new Word(word, Word.Type.CJK_WORD));
						}
					} else {
						String value = word.substring(0, word.indexOf(" "));
						if (value.length() <= maxWordLength) {
							int frequency = Integer.parseInt(word
									.substring(word.indexOf(" ") + 1,word.lastIndexOf(" ")));
                            map.put(word, new Word(word, Word.Type.CJK_WORD, frequency));
						}
					}
				}
                return true;
            }
            @Override
            public Map<String, Word> getResult() {
                return map;
            }
        });
    }

    public boolean isMatched(String value) {
        return map.containsKey(value);
    }

    public void addWord(String value, Word.Type type) {
        map.put(value, new Word(value, type));
    }

    public void addWord(String value, int frequency, Word.Type type) {
        map.put(value, new Word(value, type, frequency));
    }

    public Word getWord(String value) {
        return (Word) map.get(value);
    }

    public void removeWord(String value) {
        map.remove(value);
    }

    public int getLength() {
        return map.size();

    }

    public static void main(String... args) throws IOException {
        /*String s = "a b";
        Iterables.all(Splitter.on(" ").split(s),new Predicate<String>(){
            @Override
            public boolean apply(@Nullable String o) {
                 System.out.println("s: " + o);
                return true;
            }
        });*/

        Injector injector = Guice.createInjector(new SegmentModule());
        Dictionary dict = injector.getInstance(Dictionary.class);
        System.out.println("dict length: " + dict.getLength());
    }
}
