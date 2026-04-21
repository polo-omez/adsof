package generics.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feature<E extends Comparable<E>> extends ArrayList<E> {
  E elementType;

  public E getMin() {
    if (this.isEmpty()) {
      return null;
    }

    E min = this.getFirst();
    for (E element : this) {
      if (element.compareTo(min) < 0)
        min = element;
    }

    return min;
  }

  public E getMax() {
    if (this.isEmpty()) {
      return null;
    }

    E max = this.getFirst();
    for (E element : this) {
      if (element.compareTo(max) > 0)
        max = element;
    }

    return max;
  }

  public Map<E, Integer> getDistribution() {
    Map<E, Integer> distribution = new HashMap<>();

    for (E element : this) {
      Integer currentValue = distribution.putIfAbsent(element, 1);
      if (currentValue != null)
        distribution.replace(element, currentValue + 1);
    }

    return distribution;
  }

}
