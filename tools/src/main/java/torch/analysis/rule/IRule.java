package torch.analysis.rule;

import torch.analysis.model.Chunk;

/**
 * rule rule?
 */
public interface IRule {
    Chunk[] applyRule(final Chunk[] chunks);
}
