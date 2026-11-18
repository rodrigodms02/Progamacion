import java.util.Scanner;

public class Proyecto {

    public static void main(String[] args) {
        String opcion;
        Scanner sc = new Scanner(System.in);
        String[][] citas = new String[200][4];
        
        do {
            opcion = mostrarMenuPrincipal(sc);
            menuOpciones(opcion, citas, sc);
        } while (!opcion.equals("0"));
    }

    public static String mostrarMenuPrincipal(Scanner sc) {
        System.out.println("--- BIENVENIDO AL SISTEMA DE GESTIÓN DE FISIOTERAPIA ---");
        System.out.println("1.- Registrar cita (Nombre, Profesional, Horario, Pago)");
        System.out.println("2.- Mostrar citas registradas");
        System.out.println("3.- Anular cita");
        System.out.println("0.- Salir");
        System.out.print("Seleccione una opción: ");
        return sc.nextLine();
    }

    public static void menuOpciones(String opcion, String[][] citas, Scanner sc) {
        switch (opcion) {
            case "1":
                registrarCita(citas, sc);
                break;
            case "2":
                mostrarCitas(citas);
                break;
            case "3":
                anularCita(citas, sc);
                break;
            case "0":
                System.out.println("Saliendo del sistema... Hasta luego.");
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    public static void registrarCita(String[][] citas, Scanner sc) {
        if (ingresarCita(citas, sc)) {
            System.out.println("Cita registrada exitosamente.");
        } else {
            System.out.println("No se pudo registrar la cita. El sistema está lleno.");
        }
    }

    public static boolean ingresarCita(String[][] citas, Scanner sc) {
        for (int i = 0; i < citas.length; i++) {
            if (citas[i][0] == null) {
                System.out.print("Ingrese el nombre del paciente: ");
                citas[i][0] = sc.nextLine();
                
                System.out.print("Ingrese el nombre del profesional (Fisioterapeuta): ");
                citas[i][1] = sc.nextLine();
                
                
                String horario = "";
                boolean horarioValido = false;
                do {
                    System.out.print("Ingrese el horario (ej. 10:00 - 11:00): ");
                    horario = sc.nextLine().trim();
                    
                    if (horario.isEmpty()) {
                        System.out.println("El horario no puede estar vacío.");
                    } else if (horarioOcupado(citas, horario)) {
                        System.out.println("Ese horario ya está ocupado. Por favor, elija otro.");
                    } else {
                        horarioValido = true;
                    }
                } while (!horarioValido);
                
                citas[i][2] = horario;
                
                String pago = "";
                boolean pagoValido = false;
                do {
                    System.out.print("Seleccione método de pago (1. Tarjeta / 2. Efectivo): ");
                    String opcionPago = sc.nextLine();
                    if (opcionPago.equals("1")) {
                        pago = "Tarjeta";
                        pagoValido = true;
                    } else if (opcionPago.equals("2")) {
                        pago = "Efectivo";
                        pagoValido = true;
                    } else {
                        System.out.println("Opción de pago no válida. Ingrese 1 o 2.");
                    }
                } while (!pagoValido);
                
                citas[i][3] = pago;
                
                return true; 
            }
        }
        return false; 
    }

    public static void mostrarCitas(String[][] citas) {
        System.out.println("--- LISTA DE CITAS REGISTRADAS ---");
        boolean hayCitas = false;
        
        for (int i = 0; i < citas.length; i++) {
            if (citas[i][0] != null && !citas[i][0].isEmpty()) {
                System.out.println("Cita #" + (i + 1));
                System.out.println("  Paciente    : " + citas[i][0]);
                System.out.println("  Profesional : " + citas[i][1]);
                System.out.println("  Horario     : " + citas[i][2]);
                System.out.println("  Pago        : " + citas[i][3]);
                System.out.println("-----------------------------------");
                hayCitas = true;
            }
        }
        
        if (!hayCitas) {
            System.out.println("No hay citas registradas en el sistema.");
        }
    }

    public static void anularCita(String[][] citas, Scanner sc) {
        System.out.println("--- ANULAR CITA ---");
        System.out.print("Ingrese el nombre del paciente cuya cita desea anular: ");
        String nombreBuscar = sc.nextLine();
        boolean encontrada = false;

        for (int i = 0; i < citas.length; i++) {
            if (citas[i][0] != null && citas[i][0].equalsIgnoreCase(nombreBuscar)) {
                citas[i][0] = null;
                citas[i][1] = null;
                citas[i][2] = null;
                citas[i][3] = null; 
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            System.out.println("La cita de " + nombreBuscar + " ha sido anulada correctamente.");
        } else {
            System.out.println("No se encontró ninguna cita registrada a nombre de " + nombreBuscar + ".");
        }
    }

    public static boolean horarioOcupado(String[][] citas, String horario) {
        for (int i = 0; i < citas.length; i++) {
            if (citas[i][2] != null && citas[i][2].equalsIgnoreCase(horario)) {
                return true;
            }
        }
        return false;
    }
}