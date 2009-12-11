package torch.analysis.algorithm;

import torch.analysis.model.Chunk;
import torch.analysis.model.TextFragment;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-9
 * Time: 0:01:44
 */
public interface IAlgorithm {

	public Chunk doSegment(TextFragment textFragment);
}
