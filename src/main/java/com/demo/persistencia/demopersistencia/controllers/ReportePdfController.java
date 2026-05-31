package com.demo.persistencia.demopersistencia.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.services.ReportePdfServicio;
import com.demo.persistencia.demopersistencia.entidades.Cita;
import com.demo.persistencia.demopersistencia.repositorio.CitaRepositorio;
@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportePdfController {

    @Autowired
    private ReportePdfServicio reportePdfServicio;

    @GetMapping("/citas/pdf")
    public ResponseEntity<InputStreamResource> generarPdfCitas() {

        ByteArrayInputStream pdf =
                reportePdfServicio.generarReporteCitas();

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "attachment; filename=reporte_citas.pdf"
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

@GetMapping("/historial/pdf")
public ResponseEntity<InputStreamResource> generarPdfHistorial() {

    ByteArrayInputStream pdf =
            reportePdfServicio.generarReporteHistorial();

    HttpHeaders headers = new HttpHeaders();

    headers.add(
            "Content-Disposition",
            "attachment; filename=reporte_historial_medico.pdf"
    );

    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(pdf));
}

}