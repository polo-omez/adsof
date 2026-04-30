package generics.decisionTree;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class DecisionNode<T> {
  private String nodeName;
  private Map<Predicate<T>, String> descendants;
  private String leftoverNode;

  public DecisionNode(String nodeName) {
    this.nodeName = nodeName;
    this.descendants = new LinkedHashMap<>();
    this.leftoverNode = nodeName;
  }

  public DecisionNode<T> withCondition(String targetName, Predicate<T> condition) {
    this.descendants.putIfAbsent(condition, targetName);

    return this;
  }

  public Map<Predicate<T>, String> getDescendants() {
    return descendants;
  }

  public void otherwise(String targetName) {
    this.leftoverNode = targetName;
  }

  public String getNodeName() {
    return nodeName;
  }

  public String getLeftoverName() {
    return leftoverNode;
  }

  public String clasify(T object) {
    for (Predicate<T> p : this.descendants.keySet()) {
      if (p.test(object)) {
        return this.descendants.get(p);
      }
    }

    if (this.leftoverNode != null) {
      return this.leftoverNode;
    }

    System.err.println("[WARNING] " + object + " did not match any condition in node " + this.nodeName + "'.");

    return this.nodeName;
  }

  public Predicate<T> getPredicate(String label) {
    for (Map.Entry<Predicate<T>, String> entry : this.descendants.entrySet()) {
      if (label.equals(entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }

}
