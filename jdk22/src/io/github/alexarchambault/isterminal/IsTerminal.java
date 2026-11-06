package io.github.alexarchambault.isterminal;

/**
 * Tells whether the current JVM is attached to an interactive terminal.
 *
 * <p>This class ships in a multi-release JAR: on Java 8 to 21, the base
 * implementation is used, while on Java 22 and later, the JVM picks the
 * implementation under {@code META-INF/versions/22}, which accounts for
 * the {@link System#console()} changes of JDK 22.
 */
public final class IsTerminal {
  private IsTerminal() {}

  /**
   * Whether the current app is running in an actual terminal.
   *
   * <p>This returns {@code true} only if both standard input and standard
   * output are attached to a terminal. If either of them is redirected
   * (to or from a file, or a pipe), this returns {@code false}. The state
   * of standard error is not taken into account.
   *
   * <p>On Java 22 and later, {@link System#console()} can return a non-null
   * {@link java.io.Console} even when the streams are redirected, so this
   * also checks {@link java.io.Console#isTerminal()}.
   *
   * @return {@code true} if the current app runs in an interactive terminal,
   *         {@code false} otherwise
   */
  public static boolean isTerminal() {
    return System.console() != null && System.console().isTerminal();
  }
}
