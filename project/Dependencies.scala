import sbt._

object Dependencies {
  lazy val compile  = Seq(
    "org.typelevel" %% "cats-core" % "2.10.0",
    "com.chuusai" %% "shapeless" % "2.3.10",
  )
  lazy val munit = Seq("org.scalameta" %% "munit" % "0.7.29" % Test)
}
