ThisBuild / organization := "com.guidewire"
ThisBuild / scalaVersion := "3.3.1"

lazy val multiconf = ( project in file( "." ) )
  .settings(
    name := "multiconf",
    version := "0.1.0-SNAPSHOT",
    description := "A tool to master configuration settings for multiple profiles.",
    libraryDependencies ++= Seq( javaFx, scalaTest ),
    scalacOptions ++= Seq(
      "-deprecation", // Explain deprecation warnings.
      //"-explain",     // Explain errors in detail.
      "-indent",      // Allow Scala 3 significant indentation.
      "-new-syntax",  // Use "then" and "do" in control structures, rather than parens.
      "-print-lines" // Show source code line numbers.
      //"-Xmigration"   // Warn me when a new version changes things.
    ),
    // Fork a new JVM for "run" and "test:run", to avoid JFX double instantiation problems.
    fork := true
  )

// Library dependencies:
lazy val javaFx = "org.openjfx" % "javafx-controls" % "21.0.1" classifier osType
lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.17" % Test

// Determine OS verion for JavaFx binaries:
lazy val osType = {
  
  val osName = System.getProperty( "os.name" )
  
  if ( osName.startsWith( "Linux" ) ) "linux"
  else if ( osName.startsWith( "Mac" ) ) {
  
    val os = "mac"
    val osArch = System.getProperty( "os.arch" )

    if ( osArch == "aarch64" ) s"$os-$osArch"
    else os
  }
  else if ( osName.startsWith( "Windows" ) ) "win"
  else ""
}

