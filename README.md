# is-terminal

Tiny Java library to know whether the current app runs in an actual terminal or not

Aware of [JDK 22's `System.console()` changes](https://www.oracle.com/java/technologies/javase/22-relnote-issues.html#JDK-8308591)

JVM version agnostic

Doesn't use reflection, but relies on a multi-release JAR instead

## Usage

### Add dependency

#### Scala CLI

```scala
//> using dep io.github.alexarchambault:is-terminal:0.1.2
```

#### Mill

```scala
def mvnDeps = Seq(
  mvn"io.github.alexarchambault:is-terminal:0.1.2"
)
```

#### Maven

```xml
<dependency>
  <groupId>io.github.alexarchambault</groupId>
  <artifactId>is-terminal</artifactId>
  <version>0.1.2</version>
</dependency>
```

#### Gradle

```kotlin
implementation("io.github.alexarchambault:is-terminal:0.1.2")
```

#### sbt

```scala
libraryDependencies += "io.github.alexarchambault" % "is-terminal" % "0.1.2"
```

### API

```java
if (io.github.alexarchambault.isterminal.IsTerminal.isTerminal()) {
  // We're running with an actual terminal
}
```
