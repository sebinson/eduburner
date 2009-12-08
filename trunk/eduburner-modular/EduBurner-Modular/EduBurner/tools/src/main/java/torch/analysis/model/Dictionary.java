package torch.analysis.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

    private Map<Character, CharNode> dict;
    private String dictFiles;
    private Charset charset = Charsets.UTF_8;
    private int maxWordLength = 4;



    private enum DictType{
        CHARS, WORDS, UNIT
    }

    @Inject
    public Dictionary(@Named("dictFiles") String dictFiles, @Named("maxWordLength") int maxWordLength) {
        this.dictFiles = dictFiles;
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
        Iterable<String> filePaths = Splitter.on(",").trimResults().split(dictFiles);
        for (String filePath : filePaths) {
            loadDict(resourceLoader.getResource(filePath).getFile(), DictType.WORDS);
        }
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
                    //eat...
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

    public boolean isMatched(String word) {
        if(word == null || word.length() < 2) {
			return false;
		}
		CharNode cn = dict.get(word.charAt(0));
		return search(cn, word.toCharArray(), 0, word.length()-1) >= 0;
    }

    public Word getWord(String value){
        return null;
    }

    public int search(CharNode node, char[] sen, int offset, int tailLen) {
		if(node != null) {
			return node.indexOf(sen, offset, tailLen);
		}
		return -1;
	}

    public int maxMatch(char[] sen, int offset) {
		CharNode node = dict.get(sen[offset]);
		return maxMatch(node, sen, offset);
	}

	public int maxMatch(CharNode node, char[] sen, int offset) {
		if(node != null) {
			return node.maxMatch(sen, offset+1);
		}
		return 0;
	}

	public ArrayList<Integer> maxMatch(CharNode node, ArrayList<Integer> tailLens, char[] sen, int offset) {
		tailLens.clear();
		tailLens.add(0);
		if(node != null) {
			return node.maxMatch(tailLens, sen, offset+1);
		}
		return tailLens;
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
    }
}
