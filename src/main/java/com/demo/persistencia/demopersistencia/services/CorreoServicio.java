package com.demo.persistencia.demopersistencia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoServicio {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(
            String destinatario,
            String asunto,
            String mensaje
    ) {

        try {

            SimpleMailMessage correo =
                    new SimpleMailMessage();

            correo.setTo(destinatario);
            correo.setSubject(asunto);
            correo.setText(mensaje);

            mailSender.send(correo);

            System.out.println(
                    "Correo enviado a: " + destinatario
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al enviar correo: " + e.getMessage()
            );

        }
    }
}