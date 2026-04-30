package generics;

import java.util.List;

public interface Featurizer<T> {

  public <E extends Comparable<E>> Feature<E> feature(List<T> objects, String field);

  public List<String> featurize();

  public <E extends Comparable<E>> E extractValue(T object, String field);

}
