import java.io.*;
import java.util.Scanner;

public class Clientes {

    static String archivoClientes = "clientes.csv";

    public static void registrarCliente() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        int nuevoId = obtenerUltimoId() + 1;

        try (FileWriter fw = new FileWriter(archivoClientes, true)) {
            fw.write(nuevoId + "," + nombre + "," + apellido + "," + telefono + ",1\n");
            System.out.println("cliente registrado con éxito. ID: " + nuevoId);
        } catch (Exception e) {
            System.out.println("error al registrar cliente.");
        }
    }
    public static int obtenerUltimoId() {
        int ultimoId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoClientes))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().length() == 0) continue;

                String[] datos = linea.split(",");
                ultimoId = Integer.parseInt(datos[0]);
            }
        } catch (Exception e) {
        }

        return ultimoId;
    }
    public static void listarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoClientes))) {

            String linea;
            System.out.println("\nLISTA DE CLIENTES");

            while ((linea = br.readLine()) != null) {
                if (linea.trim().length() == 0) continue;

                String[] datos = linea.split(",");

                String estado = datos[4].equals("1") ? "ACTIVO" : "INACTIVO";

                System.out.println("ID: " + datos[0] +
                        " | Nombre: " + datos[1] +
                        " | Apellido: " + datos[2] +
                        " | Teléfono: " + datos[3] +
                        " | Estado: " + estado);
            }

            System.out.println("-------------------------\n");

        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo de clientes.");
        }
    }
}