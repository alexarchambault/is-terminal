# is-terminal

Tiny Java library to know whether the current app runs in an actual terminal or not.

Checking `System.console() != null` used to be the standard way to do that. But since
[JDK 22](https://www.oracle.com/java/technologies/javase/22-relnote-issues.html#JDK-8308591),
`System.console()` uses JLine by default and can return a non-null `Console` even when
the app's input or output is redirected. JDK 22 added `Console.isTerminal()` to handle
that, but that method doesn't exist on older JVMs.

is-terminal does the right check on every JVM. It works on Java 8 and later, and
picks the right implementation at runtime. It doesn't use reflection: it ships as a
[multi-release JAR](https://openjdk.org/jeps/238), with one implementation for Java 8 to 21
and another for Java 22 and later.

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

The library exposes a single method, `IsTerminal.isTerminal()`. A typical use is to only
enable colors, progress bars or interactive prompts when a user is actually looking at
the output:

```java
import io.github.alexarchambault.isterminal.IsTerminal;

boolean useColors = IsTerminal.isTerminal() && System.getenv("NO_COLOR") == null;
```

From Scala:

```scala
import io.github.alexarchambault.isterminal.IsTerminal

val useColors = IsTerminal.isTerminal() && !sys.env.contains("NO_COLOR")
```
