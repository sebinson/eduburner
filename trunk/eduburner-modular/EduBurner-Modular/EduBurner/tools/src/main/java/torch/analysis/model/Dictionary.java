package torch.analysis.model;

import analysis.Config;
import analysis.SegmentModule;
import analysis.model.Word;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class Dictionary {
    
    private Map<String, Word> map;
    private String dictDirPath;
    private Charset charset = Charsets.UTF_8;

    @Inject
    public Dictionary(@Named("dictDirPath") String dictDirPath){
        this.dictDirPath = dictDirPath;
        map = Maps.newHashMap();
        try {
            loadDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDictionary() throws IOException {
        File chars = new File(dictDirPath + "/chars.lex");
        File words = new File(dictDirPath + "/words.lex");
        loadWords(chars);
        loadWords(words);
    }

    private void loadWords(File file) throws IOException {
        Files.readLines(file, charset, new LineProcessor<Map<String, Word>>(){
            @Override
            public boolean processLine(String word) throws IOException {
                if (word.indexOf("#") == -1) {
					if (word.indexOf(" ") == -1) {
						if (word.length() <= Config.WORD_MAX_LENGTH) {
                            map.put(word, new Word(word, Word.CJK_WORD));
						}
					} else {
						String value = word.substring(0, word.indexOf(" "));
						if (value.length() <= Config.WORD_MAX_LENGTH) {
							int frequency = Integer.parseInt(word
									.substring(word.indexOf(" ") + 1,word.lastIndexOf(" ")));
                            map.put(word, new Word(word, frequency, Word.CJK_WORD));
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

    public void addWord(String value, int type) {
        map.put(value, new Word(value, type));
    }

    public void addWord(String value, int frequency, int type) {
        map.put(value, new Word(value, frequency, type));
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
        File f = new File("E:\\mmseg-v0.3\\MMSeg\\wordlist\\words.lex");

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
