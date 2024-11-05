ThisBuild / scalaVersion := "3.5.2"
ThisBuild / Test / fork  := true

lazy val root = (project in file("."))
  .aggregate(core, backend)

lazy val core = (project in file("teco-core"))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio"       %% "zio"                % "2.1.11",
      "dev.zio"       %% "zio-json"           % "0.7.3",
      "dev.zio"       %% "zio-http"           % "3.0.1",
      "dev.zio"       %% "zio-logging"        % "2.3.2",
      "dev.zio"       %% "zio-logging-slf4j2" % "2.3.2",
      "ch.qos.logback" % "logback-classic"    % "1.5.12",
      "io.getquill"   %% "quill-zio"          % "4.8.6",
      "io.getquill"   %% "quill-jdbc-zio"     % "4.8.6",
      "com.h2database" % "h2"                 % "2.3.232",
      "dev.zio"       %% "zio-test"           % "2.1.11" % Test,
      "dev.zio"       %% "zio-http-testkit"   % "3.0.1"  % Test,
      "dev.zio"       %% "zio-test-sbt"       % "2.1.11" % Test,
    ),
    resolvers ++= Resolver.sonatypeOssRepos("snapshots"),
  )

lazy val backend = (project in file("teco-backend"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(
    //
  )
