package generics.person;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import generics.Feature;
import generics.Featurizer;

public class PersonFeaturizer implements Featurizer<Person> {
  private final Map<String, Function<Person, ? extends Comparable<?>>> featureGetters = new HashMap<>();

  public PersonFeaturizer() {
    featureGetters.put("age", p -> p.getAge());
    featureGetters.put("weight", p -> p.getWeight());
    featureGetters.put("gender", p -> {
      if (p.isMale())
        return Gender.MALE;
      return Gender.FEMALE;
    });
  }

  @Override
  public <E extends Comparable<E>> Feature<E> feature(List<Person> personList, String field) {
    Feature<E> feature = new Feature<E>();
    for (Person person : personList) {
      feature.add(extractValue(person, field));
    }
    return feature;
  }

  @Override
  public List<String> featurize() {
    return List.of("age", "weight", "gender");
  }

  @Override
  @SuppressWarnings("unchecked")
  public <E extends Comparable<E>> E extractValue(Person person, String field) {
    return (E) featureGetters.get(field).apply(person);
  }

}
