package com

import scala.annotation.tailrec
import reactivemongo.bson._

package object ciriscr{
  @tailrec
  final def extract[A](bson: BSONDocument)(field: String)(implicit reader: BSONReader[_ <: BSONValue, A]): A = {
    def extractor[B](doc: BSONDocument, name: String)
                    (implicit reader: BSONReader[_ <: BSONValue, B]) = {
      doc.getAsTry[B](name).getOrElse(throw new MappingException(field, bson))
    } //def
    field.split("""\.""").toList match{
      case a :: Nil => extractor[A](bson, a)
      case a :: b => extract[A](extractor[BSONDocument](bson, a))(b.mkString("."))
      case Nil => throw new MappingException(field.mkString("."), bson)
    } //fields

  } //def

  class MappingException(field: String, document: BSONDocument)
    extends Throwable(s"The field '$field' was not found on this document: \n${ BSONDocument.pretty(document)}")
}