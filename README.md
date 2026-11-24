# Travel Planner

**TMPS Java project:** Lab 0 (SOLID), Lab 1 (Creational Design Patterns), and Lab 2 (Structural Design Patterns).

The console application manages destinations and estimates trip costs with activities, insurance, and airport transfer. All information is stored in memory while the application runs.

## Source Structure

- `src/main/app` - application entry point (`app.Main`)
- `src/main/model` - destinations, trips, activities, and trip bookings
- `src/main/solid` - repository, destination, and cost-calculation services
- `src/main/patterns/creational` - Builder, Factory Method, and Singleton
- `src/main/patterns/structural` - Composite, Decorator, and Facade
- `src/test/tests` - automated Java tests

## Requirements

JDK 17 or newer and IntelliJ IDEA (or a terminal with `javac` and `java`). No external libraries are required.

## Run in IntelliJ IDEA

1. Open this project in IntelliJ IDEA.
2. Mark `src/main` as **Sources Root** and `src/test` as **Test Sources Root**.
3. Run the `main()` method in `src/main/app/Main.java`.
4. Run `src/test/tests/AllTests.java` to execute all six automated tests.

## Run from PowerShell (Windows)

From the project root, run the application:

```powershell
New-Item -ItemType Directory -Force -Path out\lab2 | Out-Null
$javaFiles = Get-ChildItem src\main -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out\lab2 $javaFiles
java -cp out\lab2 app.Main
```

Run all tests in one PowerShell invocation:

```powershell
New-Item -ItemType Directory -Force -Path out\lab2-test | Out-Null
$javaFiles = Get-ChildItem src\main,src\test -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out\lab2-test $javaFiles
java -cp out\lab2-test tests.AllTests
```

## Reports

- [Lab 0 report](lab0/README.md)
- [Lab 1 report](lab1/README.md)
- [Lab 2 report](lab2/REPORT.md)
