# ADSOF - Software Analysis & Design Labs

This repository contains the practical assignments developed for the **Software Analysis and Design (ADSOF)** course. Implemented in **Java**, each laboratory project focuses on object-oriented programming (OOP) principles, design patterns, generic programming, data modeling, and unit testing using **Gradle**.

---

## Laboratory Projects Overview

### **P1: Library System (`p1/`)**
* **Focus:** OOP fundamentals, class design, encapsulation, and object interaction.
* **Core Entities:** `Libro`, `Biblioteca`, `BibliotecaApp`, and validation testers.

### **P2: Journey & Transit Planner (`p2/`)**
* **Focus:** Inheritance, polymorphism, and abstract classes.
* **Core Entities:** Modeling travel routes composed of foot journeys (`TramoAPie`), walking rhythms (`Ritmo`), and railway lines (`TramoTren`).

### **P3: Social Network Simulation (`p3/`)**
* **Focus:** Complex relationship networks, message propagation, and controlled information dissemination.
* **Core Entities:**
  * User roles: standard users (`Usuario`) and interested users (`UsuarioInteresado`).
  * Message delivery and decoy mechanics (`MensajeControlado`, `EnlaceSeñuelo`, `Exposicion`).
  * State import/export and backup utilities (`.txt` files).

### **P4: Weather Station System (`p4/`)**
* **Focus:** Advanced design patterns (Strategy, Factory, Chain of Responsibility, and Formatter).
* **Key Components:**
  * **Sensors & Units:** Modular sensors for temperature, humidity, and atmospheric pressure with measurement ranges.
  * **Processing & Conversions:** Configurable unit conversion pipelines (`ConversorTemperatura`, `ConversorPresion`, `ConversorConcatenado`).
  * **Data Generation Strategies:** `IEstrategia` implementations (range-based, average, and trend-similar data synthesis).
  * **Alert Engine:** Exception-driven thresholds for rapid value swings, out-of-bounds readings, and expired sensor calibration.
  * **Reporting:** Document exporters generating reports in HTML (`FormateadorHTML`) and Markdown (`FormateadorMarkdown`).

### **P5: Generic Datasets & Decision Trees (`p5/`)**
* **Focus:** Advanced Java Generics (`<T, E>`), functional interfaces, and machine learning structures.
* **Key Components:**
  * Generic feature extraction (`Feature`, `Featurizer`, `PersonFeaturizer`).
  * Generic dataset representation and label providers (`Dataset`, `LabeledDataset`, `LabelProvider`).
  * Recursive classification tree structures (`DecisionNode`, `DecisionTree`).
  * Decision tree training algorithm (`GreedyTreeLearner`, `TreeLearner`).

---

## Diagrams & Documentation

The repository includes UML class and entity-relationship diagrams located in `diagrams/` (available in `.puml` PlantUML source files and rendered `.png` images):
* `trayectos.puml` (P2)
* `redsocial.puml` & `redfinal.puml` (P3)
* `estacionMetereologica.puml` (P4)

Detailed design decisions, questions, and lab reports are documented within each assignment's folder (`memoria.pdf`, `enunciado*.md`, and generated `doc/` Javadocs).

---

## Build & Execution

The projects use **Gradle** as the build automation tool.

### Prerequisites
* Java Development Kit (JDK 17 or higher recommended).

### Running Tasks
From any project directory containing a Gradle wrapper (`p2/apartado1/`, `p3/`, `p4/`, or `p5/`):

* **Compile the project:**
  ```bash
  ./gradlew build
  ```

* **Run test suites (JUnit):**
  ```bash
  ./gradlew test
  ```

* **Clean build artifacts:**
  ```bash
  ./gradlew clean
  ```
