package generics.person;

public class Person implements Comparable<Person> {
  private String name;
  private int age;
  private double weight;
  private double height;
  private boolean isMale;

  public Person(String name, int age, double weight, double height, boolean isMale) {
    this.name = name;
    this.age = age;
    this.weight = weight;
    this.height = height;
    this.isMale = isMale;
  }

  public String getName() {
    return name;
  }

  public Double getWeight() {
    return weight;
  }

  public Double getHeight() {
    return height;
  }

  public Integer getAge() {
    return age;
  }

  public Boolean isMale() {
    return isMale;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Person person = (Person) obj;
    return age == person.age &&
        Double.compare(person.weight, weight) == 0 &&
        Double.compare(person.height, height) == 0 &&
        isMale == person.isMale;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(age, weight, height, isMale);
  }

  @Override
  public int compareTo(Person p) {
    int cmp = this.getAge().compareTo(p.getAge());
    if (cmp != 0)
      return cmp;

    cmp = this.getWeight().compareTo(p.getWeight());
    if (cmp != 0)
      return cmp;

    cmp = this.getHeight().compareTo(p.getHeight());
    if (cmp != 0)
      return cmp;

    return this.isMale().compareTo(p.isMale());
  }

  @Override
  public String toString() {
    String string = this.name + "(age: " + this.age;
    if (this.isMale) {
      string += ", male)";
    } else {
      string += ", female)";
    }
    return string;
  }

}
