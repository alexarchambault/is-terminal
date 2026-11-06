# is-terminal

[![Maven Central](https://img.shields.io/maven-central/v/io.github.alexarchambault/is-terminal.svg)](https://central.sonatype.com/artifact/io.github.alexarchambault/is-terminal)
[![javadoc](https://javadoc.io/badge2/io.github.alexarchambault/is-terminal/javadoc.svg)](https://javadoc.io/doc/io.github.alexarchambault/is-terminal)
[![CI](https://github.com/alexarchambault/is-terminal/actions/workflows/ci.yml/badge.svg)](https://github.com/alexarchambault/is-terminal/actions/workflows/ci.yml)

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

### What counts as a terminal

`IsTerminal.isTerminal()` returns `true` only if both standard input and standard output
are attached to a terminal:

| Command | Result |
| --- | --- |
| `app` | `true` |
| `app > out.txt` | `false` |
| `app \| less` | `false` |
| `app < in.txt` | `false` |
| `app 2> err.txt` | `true` |

Standard error isn't taken into account. So if you write progress output to stderr,
`isTerminal()` can't tell whether stderr itself is redirected.

Under the hood, on Java 8 to 21 it checks `System.console() != null`. On Java 22 and later,
it also checks `System.console().isTerminal()`.

### Fat JARs and shading

The Java 22 implementation lives under `META-INF/versions/22` in the JAR, and is only picked
up if the JAR's manifest has `Multi-Release: true`. If you repackage is-terminal in a fat JAR
(assembly, shading, etc.), make sure that the resulting JAR keeps both the
`META-INF/versions/22` entries and the `Multi-Release: true` manifest attribute. Otherwise, the
Java 8 implementation is used on all JVMs, and on Java 22 and later `isTerminal()` can return
`true` even when the output is redirected.

## Building

is-terminal is built with [Mill](https://mill-build.org), using the `./mill` launcher at the
root of the repository. Building it requires JDK 22 or later, as the Java 22 implementation
is compiled with `--release 22` (the main implementation is compiled with `--release 8`).

Compile everything, and publish it locally:

```text
$ ./mill __.publishLocal
```

Releases are published to Maven Central by CI when a tag is pushed.

## License

Licensed under the [Apache License, Version 2.0](LICENSE).
