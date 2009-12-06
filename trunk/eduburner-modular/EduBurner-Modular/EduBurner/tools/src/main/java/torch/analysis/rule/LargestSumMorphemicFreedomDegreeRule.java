package torch.analysis.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import torch.analysis.model.Chunk;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-12-3
 * Time: 22:05:15
 *
 * 取单字词自由语素度之和最大的chunk (Rule 4: Largest sum of degree of morphemic freedom of one-character words) 
 */
public class LargestSumMorphemicFreedomDegreeRule implements IRule {

    public final Chunk[] applyRule(final Chunk[] chunks) {
        LSDMFOCWRuleComparator[] orderedChunks = new LSDMFOCWRuleComparator[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            orderedChunks[i] = new LSDMFOCWRuleComparator(chunks[i]);
        }

        Arrays.sort(orderedChunks);

        int index = 0;
        double degreeOfMorphemicFreedom = orderedChunks[index].getChunk().getDegreeOfMorphemicFreedom();

        List list = new ArrayList(1);
        list.add(orderedChunks[index].getChunk());

        index++;

        while (index < orderedChunks.length) {

            if (orderedChunks[index].getChunk().getDegreeOfMorphemicFreedom() == degreeOfMorphemicFreedom) {
                list.add(orderedChunks[index].getChunk());
            } else {
                break;
            }
            index++;
        }

        Chunk[] degreeOfMorphemicFreedomChunks = new Chunk[list.size()];
        list.toArray(degreeOfMorphemicFreedomChunks);

        return degreeOfMorphemicFreedomChunks;
    }

    static class LSDMFOCWRuleComparator implements Comparable {

        private Chunk chunk;

        public LSDMFOCWRuleComparator(Chunk chunk) {
            this.chunk = chunk;
        }

        public Chunk getChunk() {
            return chunk;
        }

        public int compareTo(Object obj) {
            Chunk another = ((LSDMFOCWRuleComparator) obj).getChunk();

            double temp = another.getDegreeOfMorphemicFreedom()
                    - chunk.getDegreeOfMorphemicFreedom();

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
