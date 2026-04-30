package generics.decisionTree;

import generics.dataset.Dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class DecisionTree<T> {
  private Map<String, DecisionNode<T>> nodes;
  private String rootName;

  public DecisionTree() {
    this.nodes = new LinkedHashMap<>();
  }

  public DecisionNode<T> node(String nodeName) {
    if (this.nodes.isEmpty()) {
      this.rootName = nodeName;
    }
    return this.nodes.computeIfAbsent(nodeName, k -> new DecisionNode<>(k));
  }

  public String getRootName() {
    return rootName;
  }

  public Predicate<T> getPredicate(String label) {
    if (this.rootName == null || !this.nodes.containsKey(this.rootName)) {
      return null;
    }
    return findPath(this.rootName, label);
  }

  private Predicate<T> findPath(String currentNodeName, String targetLabel) {
    if (currentNodeName.equals(targetLabel)) {
      return t -> true;
    }

    DecisionNode<T> node = this.nodes.get(currentNodeName);
    if (node == null) {
      return null;
    }

    Predicate<T> otherwiseCondition = t -> true;

    for (Map.Entry<Predicate<T>, String> branch : node.getDescendants().entrySet()) {
      Predicate<T> branchCondition = branch.getKey();
      String nextNodeName = branch.getValue();

      Predicate<T> pathFromChild = findPath(nextNodeName, targetLabel);

      if (pathFromChild != null) {
        return branchCondition.and(pathFromChild);
      }

      otherwiseCondition = otherwiseCondition.and(branchCondition.negate());
    }

    if (node.getLeftoverName() != null) {
      Predicate<T> pathFromOtherwise = findPath(node.getLeftoverName(), targetLabel);

      if (pathFromOtherwise != null) {
        return otherwiseCondition.and(pathFromOtherwise);
      }
    }

    return null;
  }

  public Map<String, List<T>> predict(Iterable<T> objects) {
    Map<String, List<T>> predictedTree = new LinkedHashMap<>();

    for (T object : objects) {
      String destinationNode = this.nodes.get(rootName).clasify(object);

      while (nodes.containsKey(destinationNode)) {
        String nextNode = this.nodes.get(destinationNode).clasify(object);

        if (nextNode.equals(destinationNode)) {
          break;
        }
        destinationNode = nextNode;
      }

      predictedTree.putIfAbsent(destinationNode, new ArrayList<T>());
      predictedTree.get(destinationNode).add(object);
    }

    return predictedTree;
  }

  @SafeVarargs
  public final Map<String, List<T>> predict(T... objects) {
    return this.predict(Arrays.asList(objects));
  }

  public Map<String, List<T>> predict(Dataset<T> dataSet) {
    return this.predict(dataSet.getData());
  }
}
