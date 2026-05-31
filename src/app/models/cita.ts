export interface Cita {

  idCita?: number;
  paciente: {
    idPaciente: number;
    nombre?: string;

  };

  doctor: {
    idDoctor: number;
    nombreDoctor?: string;
  };

  fechaCita: string;
  horaCita: string;
  estado: string;
  observacion: string;

}