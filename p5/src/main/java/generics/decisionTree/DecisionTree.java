package generics.decisionTree;

import generics.dataset.Dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

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
