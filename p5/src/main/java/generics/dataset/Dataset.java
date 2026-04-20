package generics.dataset;

import java.util.Iterator;
import java.util.List;
import java.util.AbstractSet;

public class Dataset<T> extends AbstractSet<T> {
  private List<T> data;
  private Featurizer featurizer;

  public <F extends Featurizer> Dataset(F featurizer) {
    this.featurizer = featurizer;
  }

  @Override
  public Iterator<T> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

}
