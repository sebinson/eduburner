package torch.analysis.model;

import com.google.inject.internal.Maps;

import java.util.ArrayList;
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

    /**
     * @param sen     句子, 一串文本.
     * @param offset  词在句子中的位置
     * @param tailLen 词尾的长度, 实际是去掉词的长度.
     */
    public int indexOf(char[] sen, int offset, int tailLen) {
        return wordTails.match(sen, offset + 1, tailLen) ? 1 : -1;
    }

    /**
     * @param sen            句子, 一串文本.
     * @param wordTailOffset 词在句子中的位置, 实际是 offset 后面的开始找.
     * @return 返回词尾长, 没有就是 0
     */
    public int maxMatch(char[] sen, int wordTailOffset) {
        return wordTails.maxMatch(sen, wordTailOffset);
    }


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


    public static class KeyTree {
        TreeNode head = new TreeNode(' ');

        public void add(char[] w) {
            if (w.length < 1) {
                return;
            }
            torch.analysis.model.CharNode.TreeNode p = head;
            for (int i = 0; i < w.length; i++) {
                TreeNode n = p.getSubNode(w[i]);
                if (n == null) {
                    n = new torch.analysis.model.CharNode.TreeNode(w[i]);
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
            torch.analysis.model.CharNode.TreeNode node = head;
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
            torch.analysis.model.CharNode.TreeNode node = head;
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
            torch.analysis.model.CharNode.TreeNode node = head;
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