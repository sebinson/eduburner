package torch.analysis.rule;

import analysis.model.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-12-3
 * Time: 21:56:43
 *
 * 取最大匹配的chunk (Rule 1: Maximum matching) 
 */
public class MaxMatchRule implements IRule {

    public final Chunk[] applyRule(final Chunk[] chunks) {
        
        MMRuleComparator[] orderedChunks = new MMRuleComparator[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            orderedChunks[i] = new MMRuleComparator(chunks[i]);
        }

        Arrays.sort(orderedChunks);

        int index = 0;
        int maxLength = orderedChunks[index].getChunk().getLength();

        List list = new ArrayList(1);
        list.add(orderedChunks[index].getChunk());

        index++;

        while (index < orderedChunks.length) {

            if (orderedChunks[index].getChunk().getLength() == maxLength) {
                list.add(orderedChunks[index].getChunk());
            } else {
                break;
            }
            index++;
        }

        Chunk[] maxLengthChunks = new Chunk[list.size()];
        list.toArray(maxLengthChunks);

        return maxLengthChunks;
    }

    static class MMRuleComparator implements Comparable {

        private Chunk chunk;

        public MMRuleComparator(Chunk chunk) {
            this.chunk = chunk;
        }

        public Chunk getChunk() {
            return chunk;
        }

        public int compareTo(Object obj) {
            Chunk another = ((MMRuleComparator) obj).getChunk();
            return another.getLength() - chunk.getLength();
        }

    }


}
