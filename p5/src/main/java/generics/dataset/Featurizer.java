package generics.dataset;

import java.util.Set;
import java.util.List;

public interface Featurizer<T> {

  public <E extends Comparable<E>> Feature<E> feature(List<T> objects, String field);

  public Set<String> featurize();

  public <E extends Comparable<E>> E extractValue(T object, String field);

}
