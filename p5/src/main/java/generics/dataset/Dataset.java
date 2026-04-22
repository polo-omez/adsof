package generics.dataset;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class Dataset<T> {
  private List<T> data;
  private Featurizer<T> featurizer;

  public <F extends Featurizer<T>> Dataset(F featurizer) {
    this.featurizer = featurizer;
  }

  public boolean addAll(Collection<? extends T> c) {
    return data.addAll(c);
  }

  public boolean add(T e) {
    return data.add(e);
  }

  public <E extends Comparable<E>> Feature<E> feature(String field) {
    return featurizer.feature(data, field);
  }

  public Map<String, Feature<?>> datasetByFeatures() {
    Map<String, Feature<?>> featureByField = new HashMap<>();
    for (String field : this.featurizer.featurize()) {
      featureByField.putIfAbsent(field, this.feature(field));
    }
    return featureByField;

  }

  public void removeDuplicates() {

  }

}
