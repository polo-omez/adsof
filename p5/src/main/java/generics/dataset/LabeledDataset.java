package generics.dataset;

import generics.Featurizer;

public class LabeledDataset<T, L> extends Dataset<T> {
  private LabelProvider<L> labelProvider;

  public <F extends Featurizer<T>, P extends LabelProvider<L>> LabeledDataset(F featurizer, P labelProvider) {
    super(featurizer);
    this.labelProvider = labelProvider;
  }

  public LabelProvider<L> getLabelProvider() {
    return labelProvider;
  }

}
