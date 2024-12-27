package io.github.alexarchambault.isterminal;

public final class IsTerminal {
  private IsTerminal() {}

  /**
   * Whether the current app is running with an actual terminal or not
   */
  public static boolean isTerminal() {
    return System.console() != null && System.console().isTerminal();
  }
}
