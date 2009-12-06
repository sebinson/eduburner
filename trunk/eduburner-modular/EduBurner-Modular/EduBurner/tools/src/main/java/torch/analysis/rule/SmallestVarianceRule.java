package torch.analysis.rule;

import analysis.model.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-12-3
 * Time: 22:01:10
 *
 * 取词长标准差最小的chunk (Rule 3: Smallest variance of word lengths)  
 */
public class SmallestVarianceRule implements IRule {

    public final Chunk[] applyRule(final Chunk[] chunks) {

        SVWLRuleComparator[] orderedChunks = new SVWLRuleComparator[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            orderedChunks[i] = new SVWLRuleComparator(chunks[i]);
        }

        Arrays.sort(orderedChunks);

        int index = 0;
        double smallestVariance = orderedChunks[index].getChunk()
                .getVariance();

        List list = new ArrayList(1);
        list.add(orderedChunks[index].getChunk());

        index++;

        while (index < orderedChunks.length) {

            if (orderedChunks[index].getChunk().getVariance() == smallestVariance) {
                list.add(orderedChunks[index].getChunk());
            } else {
                break;
            }
            index++;
        }

        Chunk[] smallestVarianceChunks = new Chunk[list.size()];
        list.toArray(smallestVarianceChunks);

        return smallestVarianceChunks;
    }

    static class SVWLRuleComparator implements Comparable {

        private Chunk chunk;

        public SVWLRuleComparator(Chunk chunk) {
            this.chunk = chunk;
        }

        public Chunk getChunk() {
            return chunk;
        }

        public int compareTo(Object obj) {
            Chunk another = ((SVWLRuleComparator) obj).getChunk();
            double temp = chunk.getVariance() - another.getVariance();

            if (temp > 0D) {
                return 1;
            } else if (temp < 0D) {
                return -1;
            } else {
                return 0;
            }
        }

    }
}
