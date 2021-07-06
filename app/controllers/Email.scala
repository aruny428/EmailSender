package controllers

import org.apache.commons.mail.{HtmlEmail, SimpleEmail, DefaultAuthenticator}
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.Inject



class Email @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  def send= Action{ implicit request =>

    val email = new SimpleEmail
    email.setAuthentication("arun952138@gmail.com","Arun1234")
    email.addTo("fatearun428@gmail.com")
    email.setFrom("arun952138@gmail.com")
    email.setSubject("demo mail")
    email.setHostName("smtp.gmail.com")
    email.setMsg("this is a test mail....")
    email.setSmtpPort(465)
    email.setStartTLSEnabled(true)
    email.send
    Ok("email sent")
  }



}