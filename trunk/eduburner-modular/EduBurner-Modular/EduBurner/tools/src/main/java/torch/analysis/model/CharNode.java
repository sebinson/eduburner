package torch.analysis.model;

import com.google.common.collect.Lists;
import com.google.inject.internal.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * char node. inspired by and most codes taken from mmseg4j
 * http://code.google.com/p/mmseg4j/
 */
public class CharNode {
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


    public Word[] findMatchWords(String textFragment, int offset){
        List<Integer> l = wordTails.findMatchWords(textFragment.toCharArray(), offset);
        if(l == null || l.size() == 0){
            Word[] words = new Word[1];
            words[0] = new Word(textFragment.substring(offset, offset+1), Word.Type.UNRECOGNIZED);
            return words;
        }

        Word[] words = new Word[l.size()];
        for(int i=0; i<l.size(); i++){
            int p = l.get(i);
            String w = textFragment.substring(offset, p + 1);
            if(p == offset){
                 words[i] = new Word(w, Word.Type.CJK_WORD, frequency);
            }else{
                 words[i] = new Word(w, Word.Type.CJK_WORD);
            }
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

        public List<Integer> findMatchWords(final char[] chars, final int offset){
            if(offset >= chars.length-1){
                return null;
            }
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
            subNodes = Maps.newHashMap();
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
}