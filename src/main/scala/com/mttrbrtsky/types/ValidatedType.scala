package com.mttrbrtsky.types

import cats.implicits._
import shapeless._
import shapeless.tag._

import scala.util.matching.Regex

trait ValidatedType[BaseType] {

  private lazy val className: String = this.getClass.getSimpleName

  def validateAndTransform(in: BaseType): Option[BaseType]

  def apply(in: BaseType): BaseType @@ Tag =
    of(in).getOrElse {
      throw new IllegalArgumentException(
        s""""$in" is not a valid ${className.init}"""
      )
    }

  def of(in: BaseType): Option[BaseType @@ Tag] =
    validateAndTransform(in) map { x => tag[Tag][BaseType](x) }

  trait Tag
}

class RegexValidatedString(
    val regex: String,
    transform: String => String = identity
) extends ValidatedType[String] {

  private val regexCompiled: Regex = regex.r

  def validateAndTransform(in: String): Option[String] =
    if (regexCompiled.matches(transform(in)))
      in.some
    else
      none
}
