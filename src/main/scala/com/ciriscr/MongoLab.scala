package com.ciriscr

/**
 * Creado por<br/>
 * Compañía:  Ciris Informatic Solutions<br/>
 * Web:       www.ciriscr.com<br/>
 * Usuario:   piousp<br/>
 * Fecha:     17/06/13<br/>
 * Hora:      06:36 PM<br/>
 * -----------------------------------------------
 *
 */
object MongoLab{
  import reactivemongo.api._
  import scala.concurrent.ExecutionContext.Implicits.global
  import reactivemongo.api.collections.default.BSONCollection
  import scala.concurrent.Await
  import scala.concurrent.duration._

  private lazy val host = "ds031278.mongolab.com"
  private lazy val port = "31278"
  private lazy val dbName = "shiny-octo-adventure"
  private lazy val user = "admin"
  private lazy val pass = "mypasswd"


  private lazy val driver = new MongoDriver
  private lazy val connection = driver.connection(List(s"$host:$port"))
  private lazy val db = connection(dbName)
  implicit val timeout = 5 seconds
  val auth = Await.result(db.authenticate(user, pass), timeout)


  def apply(collection: String): BSONCollection = {
    db(collection)
  }

  def collectionNames = db.collectionNames
} //object