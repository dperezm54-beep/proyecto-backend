package com.demo.persistencia.demopersistencia.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Cita;
import com.demo.persistencia.demopersistencia.repositorio.CitaRepositorio;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.demo.persistencia.demopersistencia.entidades.HistorialMedico;
import com.demo.persistencia.demopersistencia.repositorio.HistorialMedicoRepositorio;
@Service
public class ReportePdfServicio {

    @Autowired
    private CitaRepositorio citaRepositorio;

    @Autowired
    private HistorialMedicoRepositorio historialRepositorio;

    public ByteArrayInputStream generarReporteCitas() {

        Document document = new Document();

        try {

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(
                    new Paragraph("HOSPITAL LA BENDICION")
            );

            document.add(
                    new Paragraph("Reporte General de Citas")
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable tabla =
                    new PdfPTable(5);

            tabla.addCell(
                    new Phrase("Paciente")
            );

            tabla.addCell(
                    new Phrase("Doctor")
            );

            tabla.addCell(
                    new Phrase("Fecha")
            );

            tabla.addCell(
                    new Phrase("Hora")
            );

            tabla.addCell(
                    new Phrase("Estado")
            );

            List<Cita> citas =
                    (List<Cita>) citaRepositorio.findAll();

            for (Cita cita : citas) {

                tabla.addCell(
                        cita.getPaciente() != null
                                ? cita.getPaciente().getNombre()
                                : "Sin paciente"
                );

                tabla.addCell(
                        cita.getDoctor() != null
                                ? cita.getDoctor().getNombreDoctor()
                                : "Sin doctor"
                );

                tabla.addCell(
                        cita.getFechaCita() != null
                                ? cita.getFechaCita().toString()
                                : ""
                );

                tabla.addCell(
                        cita.getHoraCita() != null
                                ? cita.getHoraCita()
                                : ""
                );

                tabla.addCell(
                        cita.getEstado() != null
                                ? cita.getEstado()
                                : ""
                );
            }

            document.add(tabla);

            document.close();

            return new ByteArrayInputStream(
                    out.toByteArray()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new ByteArrayInputStream(
                    new byte[0]
            );
        }
    }


    public ByteArrayInputStream generarReporteHistorial() {

    Document document = new Document();

    try {

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);

        document.open();

        document.add(
                new Paragraph("HOSPITAL LA BENDICION")
        );

        document.add(
                new Paragraph("Reporte de Historial Medico")
        );

        document.add(
                new Paragraph(" ")
        );

        PdfPTable tabla =
                new PdfPTable(6);

        tabla.addCell(new Phrase("Paciente"));
        tabla.addCell(new Phrase("Doctor"));
        tabla.addCell(new Phrase("Fecha"));
        tabla.addCell(new Phrase("Diagnostico"));
        tabla.addCell(new Phrase("Tratamiento"));
        tabla.addCell(new Phrase("Medicamentos"));

        List<HistorialMedico> historiales =
                (List<HistorialMedico>) historialRepositorio.findAll();

        for (HistorialMedico h : historiales) {

            tabla.addCell(
                    h.getPaciente() != null
                            ? h.getPaciente().getNombre()
                            : "Sin paciente"
            );

            tabla.addCell(
                    h.getDoctor() != null
                            ? h.getDoctor().getNombreDoctor()
                            : "Sin doctor"
            );

            tabla.addCell(
                    h.getFechaRegistro() != null
                            ? h.getFechaRegistro().toString()
                            : ""
            );

            tabla.addCell(
                    h.getDiagnostico() != null
                            ? h.getDiagnostico()
                            : ""
            );

            tabla.addCell(
                    h.getTratamiento() != null
                            ? h.getTratamiento()
                            : ""
            );

            tabla.addCell(
                    h.getMedicamentos() != null
                            ? h.getMedicamentos()
                            : ""
            );
        }

        document.add(tabla);

        document.close();

        return new ByteArrayInputStream(
                out.toByteArray()
        );

    } catch (Exception e) {

        e.printStackTrace();

        return new ByteArrayInputStream(
                new byte[0]
        );
    }
}
}