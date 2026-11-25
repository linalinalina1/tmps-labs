# Travel Planner

**TMPS Java project:** Lab 0 (SOLID Principles), Lab 1 (Creational Design Patterns), Lab 2 (Structural Design Patterns), and Lab 3 (Behavioral Design Patterns).

The console application manages destinations and estimates trip costs with sightseeing and dining activities, travel insurance, and airport transfers. It also supports selecting a booking pricing strategy, enabling or disabling booking notifications, and undoing the latest trip creation. All information is stored in memory while the application runs. The application does not make real travel reservations.

## Source Structure

- `src/main/app` - application entry point (`app.Main`)
- `src/main/model` - destinations, trips, activities, and trip bookings
- `src/main/solid` - repository, destination, and cost-calculation services
- `src/main/patterns/creational` - Builder, Factory Method, and Singleton
- `src/main/patterns/structural` - Composite, Decorator, and Facade
- `src/main/patterns/behavioral` - Strategy, Observer, and Command
- `src/test/tests` - automated Java tests for the structural and behavioral patterns
- `lab0`, `lab1`, `lab2`, `lab3` - laboratory reports and supporting material

## Requirements

JDK 17 or newer and IntelliJ IDEA (or a terminal with `javac` and `java`). No external Java libraries are required.

## Run in IntelliJ IDEA

1. Open this project in IntelliJ IDEA.
2. Mark `src/main` as **Sources Root** and `src/test` as **Test Sources Root**.
3. Run the `main()` method in `src/main/app/Main.java`.
4. Run `src/test/tests/AllTests.java` to execute all **12 automated tests**.

## Run from PowerShell (Windows)

From the project root, compile and run the application:

```powershell
New-Item -ItemType Directory -Force -Path out/app | Out-Null
$javaFiles = @(Get-ChildItem src/main -Recurse -Filter *.java -File | Select-Object -ExpandProperty FullName)
javac -encoding UTF-8 -d out/app $javaFiles
if ($LASTEXITCODE -eq 0) { java -cp out/app app.Main }
```

To **compile and run all 12 tests with one command**, use the included `test.bat` from the project root:

```powershell
.\test.bat
```

The script compiles the sources in `src/main` and `src/test`, runs `tests.AllTests`, and returns a nonzero exit code if compilation or tests fail. It supports Windows project paths containing spaces.

## Reports

- [Lab 0 report](lab0/README.md)
- [Lab 1 report](lab1/README.md)
- [Lab 2 report](lab2/REPORT.md)
- [Lab 3 report](lab3/REPORT.md)

Lab 3 diagrams, execution screenshots, and test results are stored under `lab3/` and referenced from its report.
