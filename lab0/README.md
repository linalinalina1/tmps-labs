# Laboratory Work 0 – SOLID Principles

### Course: Techniques and Mechanisms of Software Design (TMPS)

### Project: Travel Planner

### Student: Felicia Ojog

### Academic Group: FAF-243

### Programming Language: Java

### Development Environment: IntelliJ IDEA


## 1. Introduction

The purpose of Laboratory Work 0 is to understand and apply three SOLID principles in a Java application. The chosen project is **Travel Planner**, a simple console program that lets users add destinations, view their saved destinations, and calculate estimated travel costs.

Each destination has a city, a country, and an estimated cost. Users can calculate either the standard cost or a cost with a 10% student discount. The application also checks common invalid inputs. Data is stored in memory while the program runs; no database, external dataset, or API is required.

The application is intended as a foundation for one project that will be extended during the remaining TMPS laboratory works.

## 2. Selected SOLID Principles

### Overview of SOLID Principles

SOLID is an acronym representing five fundamental principles of object-oriented software design. These principles were introduced and popularized by Robert C. Martin and are intended to help developers create software that is easier to understand, maintain, test, and extend.

The main purpose of SOLID is to improve software architecture by encouraging clear responsibilities, reducing unnecessary dependencies, and promoting flexibility in the relationships between classes and components. By applying these principles, developers can create systems that are more adaptable to changing requirements without introducing significant modifications to existing functionality.

The five SOLID principles are:

1. **S - Single Responsibility Principle (SRP):** A class should have one responsibility and one primary reason to change. This encourages the separation of different functionalities into independent components.

2. **O - Open/Closed Principle (OCP):** Software entities should be open for extension but closed for modification. New functionality should be introduced through extensions rather than unnecessary changes to existing code.

3. **L - Liskov Substitution Principle (LSP):** Objects of a derived type should be usable in place of objects of their base type without breaking the expected behavior of the application.

4. **I - Interface Segregation Principle (ISP):** Clients should not be forced to depend on methods they do not use. Smaller, specialized interfaces are generally preferable to large interfaces containing unrelated operations.

5. **D - Dependency Inversion Principle (DIP):** High-level modules should depend on abstractions rather than concrete implementations. This reduces coupling and allows different implementations to be substituted more easily.

Although each principle addresses a different aspect of object-oriented design, they share a common objective: improving software quality through modularity, maintainability, and extensibility.

Applying SOLID principles offers several important advantages:

- **Improved maintainability:** Well-defined responsibilities make it easier to locate, understand, and modify specific parts of an application.
- **Reduced coupling:** Components depend less on the internal details of other components, reducing the impact of changes.
- **Greater extensibility:** New features can be introduced without significantly affecting existing functionality.
- **Better testability:** Independent components and abstractions make it easier to test individual parts of the system.
- **Improved reusability:** Classes and interfaces with clearly defined responsibilities can be reused in different contexts.
- **Simplified collaboration:** A modular structure allows developers to work on different components with fewer conflicts.

SOLID principles are guidelines rather than strict rules. Their application should depend on the size, complexity, and requirements of the software project. Excessive abstraction can make a simple application unnecessarily complicated, so it is important to maintain a balance between flexibility and simplicity.

For this laboratory work, three principles were selected: **Single Responsibility Principle (SRP)**, **Open/Closed Principle (OCP)**, and **Dependency Inversion Principle (DIP)**.

These principles were chosen because they demonstrate three important aspects of software design: separating responsibilities between classes, extending functionality through interfaces, and reducing dependencies on concrete implementations.

The Travel Planner application provides practical examples of these concepts through destination management, interchangeable travel cost calculation methods, and repository-based destination storage.


### 2.1 Single Responsibility Principle (SRP)

The Single Responsibility Principle states that a class should have one clearly defined responsibility and, therefore, one main reason to change.

**Where it is used:** `src/SingleResponsibility.java`

- `Destination` stores a destination's city, country, and estimated cost.
- `DestinationManager` provides destination operations, delegating storage to a repository.
- `DestinationDisplay` handles how destination information is printed in the console.

For example, the destination display can be changed without changing the destination model or the manager's operations.

### 2.2 Open/Closed Principle (OCP)

The Open/Closed Principle states that software components should be open for extension but closed for modification: new behavior should be added without changing the existing component that uses it.

**Where it is used:** `src/OpenClosed.java`

- `CostCalculator` declares the common `calculate(double baseCost)` operation.
- `StandardCostCalculator` returns the original estimated cost.
- `StudentCostCalculator` applies a 10% discount.
- `TravelCostService` performs calculations through the `CostCalculator` interface rather than depending on either specific strategy.

A new pricing strategy could implement `CostCalculator` without changing `TravelCostService`. The console menu would still need a way to let users select that new strategy.

### 2.3 Dependency Inversion Principle (DIP)

The Dependency Inversion Principle states that high-level application logic should depend on abstractions, not directly on concrete implementations.

**Where it is used:** `src/DependencyInversion.java` and `DestinationManager` in `src/SingleResponsibility.java`

- `DestinationRepository` defines operations for saving and retrieving destinations.
- `InMemoryDestinationRepository` implements those operations using an `ArrayList`.
- `DestinationManager` receives a `DestinationRepository` through its constructor and calls its methods.

This allows a different storage implementation to be introduced later without rewriting the manager.

## 3. Implementation

### 3.1 Project structure

The laboratory implementation uses exactly four Java source files:

```text
TravelPlanner/
  src/
    Main.java
    SingleResponsibility.java
    OpenClosed.java
    DependencyInversion.java
  README.md
```

`Main.java` contains the program entry point, menu, user input, and validation. The remaining three files demonstrate the selected SOLID principles. Java allows several package-private classes and interfaces to be declared in the same source file.

### 3.2 Destination management - SRP

`DestinationManager` manages destination operations but does not print destination information:

```java
class DestinationManager {
    private final DestinationRepository repository;

    public DestinationManager(DestinationRepository repository) {
        this.repository = repository;
    }

    public void addDestination(Destination destination) {
        repository.save(destination);
    }

    public List<Destination> getDestinations() {
        return repository.findAll();
    }
}
```

`DestinationDisplay` separately checks whether the list is empty and displays each destination's city, country, and estimated cost. This keeps presentation logic separate from destination operations.

### 3.3 Travel cost calculation - OCP

The calculation interface defines a common operation:

```java
interface CostCalculator {
    double calculate(double baseCost);
}
```

Two classes implement this interface:

```java
class StandardCostCalculator implements CostCalculator {
    @Override
    public double calculate(double baseCost) {
        return baseCost;
    }
}

class StudentCostCalculator implements CostCalculator {
    @Override
    public double calculate(double baseCost) {
        return baseCost * 0.90;
    }
}
```

`TravelCostService` accepts any implementation of `CostCalculator`:

```java
class TravelCostService {
    public double calculateCost(double baseCost, CostCalculator calculator) {
        return calculator.calculate(baseCost);
    }
}
```

For a base cost of 500, the standard strategy returns **500.00**, while the student strategy returns **450.00**.

### 3.4 Repository abstraction - DIP

The repository interface describes the operations needed by the destination manager:

```java
interface DestinationRepository {
    void save(Destination destination);
    List<Destination> findAll();
}
```

The in-memory implementation stores destinations in a list and returns a defensive, unmodifiable copy:

```java
class InMemoryDestinationRepository implements DestinationRepository {
    private final List<Destination> destinations = new ArrayList<>();

    @Override
    public void save(Destination destination) {
        destinations.add(destination);
    }

    @Override
    public List<Destination> findAll() {
        return List.copyOf(destinations);
    }
}
```

In `Main.java`, the repository is created and passed into the manager using **constructor injection**:

```java
DestinationRepository repository = new InMemoryDestinationRepository();
DestinationManager manager = new DestinationManager(repository);
```

The manager depends on the repository interface, not on the `ArrayList` or the specific in-memory repository class.

### 3.5 Functionality and testing

The console menu provides four options: **Add destination**, **View destinations**, **Calculate travel cost**, and **Exit**. The application validates empty city/country values, invalid or negative costs, invalid menu options, invalid destination numbers, and invalid calculation methods.

The figures below are screenshots of actual test runs of the Java application.

**Figure 1. Adding a destination**

![Successfully adding Rome as a destination](lab0/screenshots/add-destination.png)

The user enters a city, country, and estimated cost. The application confirms successful addition.

**Figure 2. Viewing destinations**

![Viewing the saved Rome and Paris destinations](lab0/screenshots/view-destinations.png)

Both destinations are displayed with their estimated costs.

**Figure 3. Standard travel cost calculation**

![Standard cost of 500 for Rome](lab0/screenshots/standard-cost.png)

The standard calculator returns the original estimated cost of **500.00**.

**Figure 4. Student discount calculation**

![Student discount returns 450 for Rome](lab0/screenshots/student-discount.png)

The student calculator applies a 10% discount, reducing **500.00** to **450.00**.

**Figure 5. Rejecting a nonnumeric cost**

![Invalid text entered for estimated cost](lab0/screenshots/invalid-cost.png)

The application rejects text input where a numerical cost is required.

**Figure 6. Rejecting a negative cost**

![Negative estimated cost rejected](lab0/screenshots/negative-cost.png)

Negative estimated costs are not accepted.

**Figure 7. Rejecting an invalid destination selection**

![Invalid destination number rejected](lab0/screenshots/invalid-destination.png)

Selecting a destination number outside the available list displays a clear error.

**Figure 8. Exiting the application**

![Successful program exit with code zero](lab0/screenshots/exit.png)

Selecting option `0` ends the program successfully.

Additional tests shown in the original testing document verify empty city and country values, invalid menu inputs, and invalid calculation methods. Destinations are stored in memory and are not preserved after the application closes.

### 3.6 How to run

1. Open the project in IntelliJ IDEA and configure a compatible JDK.
2. Open `src/Main.java`.
3. Run the `main()` method.
4. Select an option from the console menu and follow the prompts.

No external dependencies are required.

## 4. Conclusion


During this laboratory work, the main objective was to understand and apply three SOLID principles in a practical Java application. To achieve this, a console-based Travel Planner was developed, allowing users to add travel destinations, view saved destinations, and calculate estimated travel costs using different calculation methods. The application was designed with simplicity, modularity, and extensibility in mind, providing a foundation for further development in subsequent laboratory works.

The **Single Responsibility Principle (SRP)** was implemented by separating destination information, destination management, and console display into different classes. This approach demonstrated the importance of assigning clear responsibilities to individual components, making the code easier to understand, maintain, and modify independently.

The **Open/Closed Principle (OCP)** was demonstrated through the implementation of a common interface for travel cost calculations. By creating separate implementations for standard pricing and student discounts, the application supports different calculation strategies without modifying the existing calculation service. This demonstrated how interfaces and polymorphism can be used to extend application functionality while preserving existing behavior.

The **Dependency Inversion Principle (DIP)** was applied by introducing a repository interface between destination management and data storage. Instead of depending directly on a specific storage implementation, the destination manager works with an abstraction. This makes it possible to introduce alternative storage methods, such as file-based or database storage, in future laboratory works without changing the core destination management logic.

The application was tested using both valid and invalid inputs to verify its functionality and reliability. The tests covered destination creation, destination listing, standard cost calculations, student discounts, invalid menu selections, incorrect destination numbers, empty input fields, and invalid cost values. The results confirmed that the implemented functionality works as expected and that the application handles common input errors appropriately.

Throughout the implementation, practical experience was gained in working with Java classes, interfaces, encapsulation, polymorphism, constructor-based dependency injection, collections, and input validation. The laboratory also demonstrated how applying SOLID principles can improve code organization and reduce dependencies between individual components.

In conclusion, the laboratory objectives were achieved through the successful implementation of three SOLID principles in the Travel Planner application. The resulting project demonstrates the importance of well-structured object-oriented code and provides a flexible starting point for future improvements. The knowledge gained during this laboratory will be useful when introducing additional functionality, applying further design principles, and exploring software design patterns in the remaining TMPS laboratory works.