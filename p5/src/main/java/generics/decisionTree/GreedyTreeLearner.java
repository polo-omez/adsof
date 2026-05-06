package generics.decisionTree;

import generics.dataset.LabeledDataset;
import generics.Featurizer;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class GreedyTreeLearner<T, L> implements TreeLearner<T, L> {

  @Override
  public DecisionTree<T> learn(LabeledDataset<T, L> dataSet) {
    List<T> data = dataSet.getData();
    List<String> availableFeatures = new ArrayList<>(dataSet.getFeaturizer().featurize());
    DecisionTree<T> learnTree = new DecisionTree<>();

    return learnTree;
  }

  public String buildTree(DecisionTree<T> learnTree, List<T> data, List<String> availableFeatures,
      Featurizer<T> featurizer) {

    String feat = availableFeatures.getFirst();
    availableFeatures.remove(feat);
    Map<Object, List<T>> split = splitData(data, feat, featurizer);
    for (Object value : split.keySet()) {
      DecisionNode<T> currentNode = learnTree.node(learnTree.getRootName());
      currentNode.withCondition(feat + " " + value.toString(), condition);

      buildTree(learnTree., data, availableFeatures, featurizer)

    }

    return null;

  }

  public Map<Object, List<T>> splitData(List<T> data, String feat, Featurizer<T> featurizer) {
    Map<Object, List<T>> splits = new LinkedHashMap<>();
    for (T elem : data) {
      Object value = featurizer.extractValue(elem, feat);
      splits.putIfAbsent(value, new ArrayList<>());
      splits.get(value).add(elem);
    }

    return splits;
  }

}
