import { Paciente } from './paciente';
import { Doctor } from './doctor';
import { Cita } from './cita';

export interface Historial {

  idHistorial?: number;
  paciente: Paciente;
  doctor: Doctor;
  cita?: Cita;
  diagnostico: string;
  tratamiento: string;
  medicamentos: string;
  observaciones: string;
  fechaRegistro: string;

}