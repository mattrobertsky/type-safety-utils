package com.mttrbrtsky.types

import cats.implicits.{catsSyntaxOptionId, none}
import shapeless.tag.@@

class ValidatedTypeSpec extends munit.FunSuite {

  type TestValidatedString = String @@ TestValidatedString.Tag

  object TestValidatedString
    extends RegexValidatedString(
      regex = """.{0,3}"""
    )

  type TestValidatedInteger = Integer @@ TestValidatedInteger.Tag

  object TestValidatedInteger extends ValidatedType[Integer] {
    override def validateAndTransform(in: Integer): Option[Integer] =
      if (in > 10 && in < 100) in.some
      else none
  }

  type TestPositiveMonetaryType = BigDecimal @@ TestPositiveMonetaryType.Tag

  object TestPositiveMonetaryType extends ValidatedType[BigDecimal] {
    override def validateAndTransform(in: BigDecimal): Option[BigDecimal] =
      if (in >= 0 && in.scale <= 2) in.some
      else none
  }

  test("TestPositiveMonetaryType with valid BaseType arg is fine otherwise IllegalArgumentException thrown") {
    val test: TestPositiveMonetaryType = TestPositiveMonetaryType(9.99)
    intercept[IllegalArgumentException] {
      val neg: BigDecimal = TestPositiveMonetaryType(-1)
    }
    intercept[IllegalArgumentException] {
      val badScale: BigDecimal = TestPositiveMonetaryType(1.555)
    }
  }
  test("TestValidatedInteger with valid BaseType arg is fine otherwise IllegalArgumentException thrown") {
    val test: TestValidatedInteger = TestValidatedInteger(23)
    intercept[IllegalArgumentException] {
      TestValidatedInteger.apply(123)
    }
  }

  test("TestValidatedString with valid BaseType arg is fine otherwise IllegalArgumentException thrown") {
    val test: TestValidatedString = TestValidatedString("abc")
    intercept[IllegalArgumentException] {
      TestValidatedString.apply("abcd")
    }
  }
}

