package com.ciriscr

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers
import akka.pattern.ask
import akka.actor.ActorSystem
import scala.concurrent.Await

/**
 * Creado por<br/>
 * Compañía:  Ciris Informatic Solutions<br/>
 * Web:       www.ciriscr.com<br/>
 * Usuario:   piousp<br/>
 * Fecha:     17/06/13<br/>
 * Hora:      06:57 PM<br/>
 * -----------------------------------------------
 *
 */
class DataTest extends FunSpec with MustMatchers{

  import scala.concurrent.duration._
  implicit val system = ActorSystem("Test")
  implicit val timeout = 5 seconds
  implicit val akkatimeout = new akka.util.Timeout(timeout)

  private def bringData: List[Data] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val future = DataDAO(system) ? 1
    Await.result(future.mapTo[DataDAO.Results].flatMap(_.data), timeout)
  }

  describe("The reactivemongo connection"){
    it("should be able to query and bring data from the db: 1"){
      val data = bringData
      data must not equal(Nil)
    }
    it("should be able to query and bring data from the db: 2"){
      val data = bringData
      data must not equal(Nil)
    }
    it("should be able to query and bring data from the db: 3"){
      val data = bringData
      data must not equal(Nil)
    }
    it("should be able to query and bring data from the db: 4"){
      val data = bringData
      data must not equal(Nil)
    }
    it("should be able to query and bring data from the db: 5"){
      val data = bringData
      data must not equal(Nil)
    }

  } //describe

} //class