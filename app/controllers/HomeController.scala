package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import javax.mail.{Message, Session}
import javax.mail.internet.{InternetAddress, MimeMessage}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def check() = Action { implicit request: Request[AnyContent] =>
    Ok("4th attempt !!")
  }

  // not in use right now (not working)
  def send()= Action {implicit request =>

    val props = System.getProperties

    props.put("mail.transport.protocol", "smtp")
    props.put("mail.smtp.port", "465")
    props.put("mail.smtp.auth", "true")
    //props.put("email.smtp.ssl",true)
    //props.put("mail.smtp.starttls.enable", true)
    //props.put("mail.smtp.starttls.required", true)

    val session = Session.getDefaultInstance(props)

    val msg = new MimeMessage(session)

    msg.setFrom(new InternetAddress("arun952138@gmail.com", "Arun"))

    msg.setRecipient(Message.RecipientType.TO, new InternetAddress("fatearun428@gmail.com"))
    msg.setSubject("demo mail")
    msg.setText("This is a sample mail......")

    val transport = session.getTransport
    println("sending mail begins ")

    transport.connect("smtp.gmail.com", 465, "arun952138@gmail.com", "Arun1234@")
    transport.sendMessage(msg, msg.getAllRecipients)

    Ok("mail sent !!")
  }


}
