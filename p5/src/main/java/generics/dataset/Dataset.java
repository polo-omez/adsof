package generics.dataset;

import generics.Feature;
import generics.Featurizer;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;

public class Dataset<T> {
  private List<T> data;
  private Featurizer<T> featurizer;
  private Map<String, Feature<?>> dataByFeatures;

  public <F extends Featurizer<T>> Dataset(F featurizer) {
    this.featurizer = featurizer;
    this.dataByFeatures = new LinkedHashMap<>();
    this.data = new ArrayList<>();
  }

  public List<T> getData() {
    return data;
  }

  @SafeVarargs
  public final boolean addAll(T... elements) {
    return data.addAll(List.of(elements));
  }

  public boolean add(T e) {
    return data.add(e);
  }

  public <E extends Comparable<E>> Feature<E> feature(String field) {
    return featurizer.feature(data, field);
  }

  public Map<String, Feature<?>> datasetByFeatures() {
    for (String field : this.featurizer.featurize()) {
      this.dataByFeatures.putIfAbsent(field, this.feature(field));
    }
    return this.dataByFeatures;

  }

  public void removeDuplicates() {
    Set<T> uniqueData = new LinkedHashSet<>(this.data);

    this.data = new ArrayList<>(uniqueData);

    this.dataByFeatures.clear();
  }

  @Override
  public String toString() {
    return this.datasetByFeatures().toString();
  }

}
