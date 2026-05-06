package generics.decisionTree;

import generics.dataset.LabeledDataset;

public interface TreeLearner<T, L> {
  public DecisionTree<T> learn(LabeledDataset<T, L> dataSet);
}
