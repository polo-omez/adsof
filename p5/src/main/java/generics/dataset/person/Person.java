package generics.dataset.person;

public class Person {
  private String name;
  private int age;
  private double weight;
  private boolean isMale;

  public Person(String name, int age, double weight, boolean isMale) {
    this.name = name;
    this.age = age;
    this.weight = weight;
    this.isMale = isMale;
  }

  public String getName() {
    return name;
  }

  public double getWeight() {
    return weight;
  }

  public Integer getAge() {
    return age;
  }

  public boolean isMale() {
    return isMale;
  }

}
