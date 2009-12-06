package torch.analysis.rule;

import analysis.model.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-12-3
 * Time: 22:03:27
 *
 * 取平均词长最大的chunk (Rule 2: Largest average word length) 
 */
public class LagestAvgWordLenRule implements IRule {

    public final Chunk[] applyRule(final Chunk[] chunks) {
		LAWLRuleComparator[] orderedChunks = new LAWLRuleComparator[chunks.length];

		for (int i = 0; i < chunks.length; i++) {
			orderedChunks[i] = new LAWLRuleComparator(chunks[i]);
		}

		Arrays.sort(orderedChunks);

		int index = 0;
		double largestAverageLength = orderedChunks[index].getChunk()
				.getAverageLength();

		List list = new ArrayList(1);
		list.add(orderedChunks[index].getChunk());

		index++;

		while (index < orderedChunks.length) {

			if (orderedChunks[index].getChunk().getAverageLength() == largestAverageLength) {
				list.add(orderedChunks[index].getChunk());
			} else {
				break;
			}
			index++;
		}

		Chunk[] largestAverageLengthChunks = new Chunk[list.size()];
		list.toArray(largestAverageLengthChunks);

		return largestAverageLengthChunks;
	}

	static class LAWLRuleComparator implements Comparable {

		private Chunk chunk;

		public LAWLRuleComparator(Chunk chunk) {
			this.chunk = chunk;
		}

		public Chunk getChunk() {
			return chunk;
		}

		public int compareTo(Object obj) {
			Chunk another = ((LAWLRuleComparator) obj).getChunk();

			double temp = another.getAverageLength()
					- chunk.getAverageLength();

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
