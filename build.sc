import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.4.1`

import de.tobiasroeser.mill.vcs.version.VcsVersion
import mill._
import mill.scalalib._
import mill.scalalib.publish._

object jdk22 extends JavaModule

object `is-terminal` extends JavaModule with PublishModule {
  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "io.github.alexarchambault",
    url = "https://github.com/alexarchambault/is-terminal",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("alexarchambault", "is-terminal"),
    developers = Seq(
      Developer("alexarchambault", "Alex Archambault", "https://github.com/alexarchambault")
    )
  )
  def publishVersion = VcsVersion.vcsState().format()

  def javacOptions = super.javacOptions() ++ Seq(
    "--release", "8"
  )

  def jdk22ClassesResources = T {
    val destDir = T.dest / "META-INF/versions/22"
    os.makeDir.all(destDir)
    for (elem <- os.list(jdk22.compile().classes.path))
      os.copy(elem, destDir / elem.last)
    PathRef(T.dest)
  }

  def resources = T {
    T.sources(super.resources() ++ Seq(jdk22ClassesResources()))
  }
  def manifest = T {
    super.manifest().add("Multi-Release" -> "true")
  }
}
