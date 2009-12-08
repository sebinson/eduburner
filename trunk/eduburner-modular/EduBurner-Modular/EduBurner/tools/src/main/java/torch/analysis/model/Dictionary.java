package torch.analysis.model;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import torch.analysis.SegmentModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class Dictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private Map<Character, CharNode> dict;
    private String wordFiles;
    private String charFiles;
    private Charset charset = Charsets.UTF_8;
    private int maxWordLength = 4;


    private enum DictType{
        CHARS, WORDS, UNIT
    }

    @Inject
    public Dictionary(@Named("wordFiles") String dictFiles,
                      @Named("charFiles") String charFiles,
                      @Named("maxWordLength") int maxWordLength) {
        this.wordFiles = dictFiles;
        this.charFiles = charFiles;
        this.maxWordLength = maxWordLength;
        dict = Maps.newHashMap();
        try {
            loadDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDictionary() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Iterable<String> filePaths = Splitter.on(",").trimResults().split(wordFiles);
        for (String filePath : filePaths) {
            loadDict(resourceLoader.getResource(filePath).getFile(), DictType.WORDS);
        }

        /*
        Iterable<String> charFilePaths = Splitter.on(",").trimResults().split(charFiles);
        for (String charFile : charFilePaths) {
            loadDict(resourceLoader.getResource(charFile).getFile(), DictType.CHARS);
        }*/
    }

    private void loadDict(File file, final DictType type) throws IOException {
        Files.readLines(file, charset, new LineProcessor<Map<Character, CharNode>>() {
            @Override
            public boolean processLine(String line) throws IOException {
                if(line == null){
                    return false;
                }
                if (line.indexOf("#") == -1) {
                    if(type == DictType.WORDS){
                        loadWords(line);
                    }else if(type == DictType.CHARS){
                        loadChars(line);
                    }
                }
                return true;
            }

            @Override
            public Map<Character, CharNode> getResult() {
                return dict;
            }
        });
    }

    private void loadChars(String line) {
        if (line.length() < 1) {
            return;
        }
        String[] w = line.split(" ");
        CharNode cn = new CharNode();
        switch (w.length) {
            case 2:
                try {
                    cn.setFrequency((int) (Math.log(Integer.parseInt(w[1])) * 100));//字频计算出自由度
                } catch (NumberFormatException e) {
                    logger.warn("failed to load chars at line: " + line, e);
                }
            case 1:
                dict.put(w[0].charAt(0), cn);
        }
    }

    private void loadWords(String line) {
        CharNode cn = dict.get(line.charAt(0));
        if(cn == null) {
            cn = new CharNode();
            dict.put(line.charAt(0), cn);
        }
        cn.addWordTail(tail(line));
    }

    private char[] tail(String str){
        char[] cs = new char[str.length()-1];
		str.getChars(1, str.length(), cs, 0);
		return cs;
    }

    public Word[] findMatchWords(String textFragment, int offset){
         char c = textFragment.charAt(offset);
         CharNode cn = dict.get(c);
         return cn.findMatchWords(textFragment, offset);
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

        Word[] word = dict.findMatchWords("研究生命科学", 0);

        System.out.println("word length: " + word.length);
    }
}
