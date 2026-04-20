package generics.dataset;

import java.util.Set;

public interface Featurizer {

  public <E extends Comparable<E>> Feature<E> feature(String field);

  public Set<String> freaturizeDataSet();

}
