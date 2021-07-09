package controllers

import com.datastax.driver.core.Row
import org.apache.commons.mail.{DefaultAuthenticator, HtmlEmail, SimpleEmail}
import play.api.Configuration
import play.api.mvc.{BaseController, ControllerComponents}
import utils.CassandraClient

import javax.inject.Inject
import scala.jdk.CollectionConverters.IterableHasAsScala

class Email @Inject() (val controllerComponents: ControllerComponents,cassandraClient: CassandraClient,config : Configuration) extends BaseController {

  val session = cassandraClient.session
  private val emailId= config.get[String]("email")
  private val password = config.get[String]("password")
  val templateType= "subscribe"
  val keyspace = "arun"
  val emailList = "emaillist"
  val template = "templates"
  val getEmailQ = session.prepare(s"select * from $keyspace.$emailList")
  val getTemplateQ = session.prepare(s"select content, subject from $keyspace.$template where type = ? ")

  def send= Action{ implicit request =>

    val email = new SimpleEmail

    //val emails= Seq("fatearun428@gmail.com","aruny428@gmail.com")
    val emails = getEmail()
    email.setAuthentication(emailId,password)
    emails.foreach(em => email.addTo(em))
    email.setFrom(emailId,"Arun")
    email.setSubject(getTemplate(templateType).get.Subject)
    email.setHostName("smtp.gmail.com")
    email.setMsg(getTemplate(templateType).get.body)
    email.setSmtpPort(465)
    email.setSSL(true)
    //email.setStartTLSEnabled(true)
    email.send
    Ok("email sent")
  }

  def getEmail()={
    session.execute(getEmailQ.bind()).asScala.map(r => r.getString("email")).toSeq
  }

  def getTemplate(templateType : String ) = {
    session.execute(getTemplateQ.bind(templateType)).asScala.map(toTemplate).headOption
  }

  def toTemplate(r :Row)={
    Template(r.getString("content"),r.getString("subject"))
  }

  case class Template(
                     body : String,
                     Subject : String
                     )

}