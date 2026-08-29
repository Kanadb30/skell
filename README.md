# Java Shell Implementation

A POSIX-compliant shell implementation in Java with support for builtin commands (cd, pwd, echo, history, declare) and external program execution.

## Getting Started

### Prerequisites
- Java 26+
- Maven

### Building

```sh
mvn clean package
```

### Running

```sh
java -jar target/shell.jar
```

## Project Structure

- `src/main/java/Main.java` - Entry point
- `src/main/java/builtin/` - Builtin command implementations (cd, pwd, echo, history, declare)
- `src/main/java/custom/` - Custom utilities (cmd, declarePair)
- `src/main/java/parser/` - Command parsing logic
