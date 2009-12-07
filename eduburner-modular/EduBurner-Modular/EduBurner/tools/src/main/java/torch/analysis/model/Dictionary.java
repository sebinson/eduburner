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

    /**
     * charnode. inspired by mmseg4j
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

        /**
         * @param sen     句子, 一串文本.
         * @param offset  词在句子中的位置
         * @param tailLen 词尾的长度, 实际是去掉词的长度.
         * @author chenlb 2009-4-8 下午11:10:30
         */
        public int indexOf(char[] sen, int offset, int tailLen) {
            return wordTails.match(sen, offset + 1, tailLen) ? 1 : -1;
        }

        /**
         * @param sen            句子, 一串文本.
         * @param wordTailOffset 词在句子中的位置, 实际是 offset 后面的开始找.
         * @return 返回词尾长, 没有就是 0
         * @author chenlb 2009-4-10 下午10:45:51
         */
        public int maxMatch(char[] sen, int wordTailOffset) {
            return wordTails.maxMatch(sen, wordTailOffset);
        }

        /**
         * @return 至少返回一个包括 0的int
         * @author chenlb 2009-4-12 上午10:01:35
         */
        public ArrayList<Integer> maxMatch(ArrayList<Integer> tailLens, char[] sen, int wordTailOffset) {
            return wordTails.maxMatch(tailLens, sen, wordTailOffset);
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

    public static class KeyTree {
        TreeNode head = new TreeNode(' ');

        public void add(char[] w) {
            if (w.length < 1) {
                return;
            }
            TreeNode p = head;
            for (int i = 0; i < w.length; i++) {
                TreeNode n = p.getSubNode(w[i]);
                if (n == null) {
                    n = new TreeNode(w[i]);
                    p.addSubNode(w[i], n);
                }
                p = n;
            }
            p.leaf = true;
        }

        /**
         * @return 返回匹配最长词的长度, 没有找到返回 0.
         */
        public int maxMatch(char[] sen, int offset) {
            int idx = offset - 1;
            TreeNode node = head;
            for (int i = offset; i < sen.length; i++) {
                node = node.getSubNode(sen[i]);
                if (node != null) {
                    if (node.isLeaf()) {
                        idx = i;
                    }
                } else {
                    break;
                }
            }
            return idx - offset + 1;
        }

        public ArrayList<Integer> maxMatch(ArrayList<Integer> tailLens, char[] sen, int offset) {
            TreeNode node = head;
            for (int i = offset; i < sen.length; i++) {
                node = node.getSubNode(sen[i]);
                if (node != null) {
                    if (node.isLeaf()) {
                        tailLens.add(i - offset + 1);
                    }
                } else {
                    break;
                }
            }
            return tailLens;
        }

        public boolean match(char[] sen, int offset, int len) {
            TreeNode node = head;
            for (int i = 0; i < len; i++) {
                node = node.getSubNode(sen[offset + i]);
                if (node == null) {
                    return false;
                }
            }
            return node.isLeaf();
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
    }
}
