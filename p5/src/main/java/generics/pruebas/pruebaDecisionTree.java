package generics.pruebas;

import generics.dataset.*;
import generics.decisionTree.*;
import generics.person.*;;

public class pruebaDecisionTree {
  public static void main(String[] args) {
    Dataset<Person> dataSet = pruebaDataset.buildDataSet(); // like in previous listing
    DecisionTree<Person> dt = buildPersonDecisionTree();

    System.out.println(dt.predict(dataSet));
    System.out.println(dt.predict(new Person("Miguel", 85, 72, 165, true), new Person("Clara", 42, 59, 162, false)));
  }

  public static DecisionTree<Person> buildPersonDecisionTree() {
    DecisionTree<Person> dt = new DecisionTree<>();
    dt.node("root") // nodo raíz, al ser el primero que se añade
        .withCondition("male", p -> p.isMale())
        .otherwise("female");

    dt.node("male") // como el nodo ya existe, se añaden condiciones sobre él
        .withCondition("old male", p -> p.getAge() > 65)
        .withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34)
        .otherwise("young male");

    return dt;
  }
}
