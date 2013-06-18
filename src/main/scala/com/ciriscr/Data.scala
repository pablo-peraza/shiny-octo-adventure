package com.ciriscr

import reactivemongo.bson.{BSONValue, BSONReader, BSONDocument, BSONDocumentReader}
import scala.concurrent.Future
import akka.actor.{Props, ActorSystem, Actor}
import com.ciriscr.DataDAO.Results

/**
 * Creado por<br/>
 * Compañía:  Ciris Informatic Solutions<br/>
 * Web:       www.ciriscr.com<br/>
 * Usuario:   piousp<br/>
 * Fecha:     17/06/13<br/>
 * Hora:      06:43 PM<br/>
 * -----------------------------------------------
 *
 */
case class Data(name: String, tags: List[String], number: Int)

object Data{
  implicit object reader extends DataBSONReader
}

trait DataBSONReader extends BSONDocumentReader[Data]{

  def read(bson: BSONDocument): Data = {

    def extractor[A](field: String)(implicit reader: BSONReader[_ <: BSONValue, A]) = extract[A](bson)(field)
    val name = extractor[String]("name")
    val tags = extractor[List[String]]("tags")
    val number = extractor[Int]("number")
    Data(name, tags, number)
  } //def
} //trait

trait DataDAO{
  import scala.concurrent.ExecutionContext.Implicits.global
  private lazy val collection = MongoLab("pulpos")

  def find(filters: BSONDocument): Future[List[Data]] = {
    collection.find(filters).cursor[Data].toList()
  } //def

  def byNumber(number: Int): Future[List[Data]] = this.find(BSONDocument("number" -> number))
} //trait

object DataDAO{
  def apply(implicit system: ActorSystem) = system.actorOf(Props[DataDAOActor])
  case class Results(data: Future[List[Data]])
} //object

private class DataDAOActor extends Actor{
  import context._
  val dao = new DataDAO{ }
  def receive = {
    case number: Int =>
      sender ! Results(dao.byNumber(number))
      stop(self)
  } //def
} //class