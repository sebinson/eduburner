package torch.analysis.model;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Map;

public class Dictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private Map<Character, CharNode> dict;
    private String wordFiles;
    private String charFiles;
    private Charset charset = Charsets.UTF_8;
    private int maxWordLength = 4;


    private enum DictType {
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
                if (line == null) {
                    return false;
                }
                if (line.indexOf("#") == -1) {
                    if (type == DictType.WORDS) {
                        loadWords(line);
                    } else if (type == DictType.CHARS) {
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
        if (cn == null) {
            cn = new CharNode();
            dict.put(line.charAt(0), cn);
        }
        cn.addWordTail(tail(line));
    }

    private char[] tail(String str) {
        char[] cs = new char[str.length() - 1];
        str.getChars(1, str.length(), cs, 0);
        return cs;
    }

    public Word[] findMatchWords(char[] chars, int offset) {
        char c = chars[offset];
        CharNode cn = dict.get(c);
        if (cn != null) {
            return cn.findMatchWords(chars, offset);
        } else {
            Word[] words = new Word[1];
            words[0] = new Word(String.valueOf(chars[offset]), Word.Type.UNRECOGNIZED);
            return words;
        }
    }


    /**
     * char node. inspired by and most codes taken from mmseg4j
     * http://code.google.com/p/mmseg4j/
     */
    public static class CharNode {
        private int frequency = -1;    //Degree of Morphemic Freedom of One-Character, 单字才需要
        private int maxLength = 0;    //wordTail的最长

        private KeyTree wordTails = new KeyTree();
        private int wordNum = 0;

        public void addWordTail(char[] wordTail) {
            wordTails.add(wordTail);
            wordNum++;
            if (wordTail.length > maxLength) {
                maxLength = wordTail.length;
            }
        }


        public Word[] findMatchWords(char[] chars, int offset) {
            List<Integer> l = wordTails.findMatchWords(chars, offset);
            if (l.size() == 0) {
                Word[] words = new Word[1];
                words[0] = new Word(String.valueOf(chars[offset]), Word.Type.CJK_WORD, frequency);
                return words;
            }

            Word[] words = new Word[l.size()];
            for (int i = 0; i < l.size(); i++) {
                int p = l.get(i);
                StringBuilder sb = new StringBuilder();
                for (int j = offset; j <= p; j++) {
                    sb.append(chars[j]);
                }
                words[i] = new Word(sb.toString(), Word.Type.CJK_WORD);
            }
            return words;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int wordNum() {
            return wordNum;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

    }

    private static class KeyTree {
        TreeNode head = new TreeNode(' ');

        public void add(char[] w) {
            if (w.length < 1) {
                return;
            }
            TreeNode p = head;
            for (char aW : w) {
                TreeNode n = p.getSubNode(aW);
                if (n == null) {
                    n = new TreeNode(aW);
                    p.addSubNode(aW, n);
                }
                p = n;
            }
            p.leaf = true;
        }

        public List<Integer> findMatchWords(final char[] chars, final int offset) {
            TreeNode node = head;
            List<Integer> l = Lists.newArrayList();
            for (int i = offset + 1; i < chars.length; i++) {
                char aChar = chars[i];
                node = node.getSubNode(aChar);
                if (node != null) {
                    if (node.isLeaf()) {
                        l.add(i);
                    }
                } else {
                    break;
                }
            }
            return l;
        }
    }


    private static class TreeNode {
        char key;
        Map<Character, TreeNode> subNodes;
        boolean leaf;

        public TreeNode(char key) {
            this.key = key;
            subNodes = com.google.inject.internal.Maps.newHashMap();
        }

        public void addSubNode(char k, TreeNode sub) {
            subNodes.put(k, sub);
        }

        public TreeNode getSubNode(char k) {
            return subNodes.get(k);
        }

        public boolean isLeaf() {
            return leaf;
        }
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

        Word[] word = dict.findMatchWords("研究生命起源".toCharArray(), 0);

        System.out.println("word length: " + word.length);
    }
}
