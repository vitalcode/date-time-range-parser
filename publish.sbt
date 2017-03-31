import scala.util.matching.Regex

lazy val `date-time-range-parser` = (project in file(".")).enablePlugins(GitVersioning, GitBranchPrompt)

// sbt-git
git.useGitDescribe := true

git.baseVersion := "0.0.0"

val versionRegex: Regex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r

git.gitTagToVersionNumber := {
  case versionRegex(v, "") => Some(v)
  case versionRegex(v, "SNAPSHOT") => Some(s"$v-SNAPSHOT")
  case versionRegex(v, s) => Some(s"$v-$s-SNAPSHOT")
  case _ => None
}
publishMavenStyle := true

pomIncludeRepository := { _ => false }

publishArtifact in Test := false

// sbt-sonatype
credentials ++= (
  for {
    username <- sys.env.get("SONATYPE_USERNAME")
    password <- sys.env.get("SONATYPE_PASSWORD")
  } yield Seq(Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password))
  ).getOrElse(Seq())

pomExtra := <url>https://github.com/vitalcode/date-time-range-parser</url>
  <licenses>
    <license>
      <name>MIT</name>
      <url>https://opensource.org/licenses/MIT</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:vitalcode/date-time-range-parser.git</url>
    <connection>scm:git:git@github.com:vitalcode/date-time-range-parser.git</connection>
  </scm>
  <developers>
    <developer>
      <id>vitaliy</id>
      <name>Vitaliy Kuznetsov</name>
      <email>vitaliy.kuznetsov@yahoo.co.uk</email>
      <organization>Vital Code Ltd</organization>
      <url>http://vitalcode.uk/</url>
    </developer>
  </developers>

// sbt-pgp
pgpSecretRing := file("secring.gpg")

pgpPublicRing := file("pubring.gpg")

pgpPassphrase := sys.env.get("PASS_PHRASE").map(_.toArray)

