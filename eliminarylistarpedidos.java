import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final String CLIENTES_FILE = "clientes.csv";
    static final String PEDIDOS_FILE = "pedidos.csv";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Eliminar cliente");
        System.out.println("2. Listar pedidos de un cliente");
        System.out.print("Opcion: ");
        int op = Integer.parseInt(sc.nextLine());

        if (op == 1) eliminarCliente();
        else if (op == 2) listarPedidosCliente();
        else System.out.println("Opcion invalida");
    }

    static void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        List<String> nuevasLineas = new ArrayList<>();
        boolean encontrado = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(CLIENTES_FILE));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", -1);

                if (datos[0].equals(String.valueOf(id))) {
                    datos[4] = "0";
                    linea = String.join(",", datos);
                    encontrado = true;
                }

                nuevasLineas.add(linea);
            }

            br.close();

            if (!encontrado) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(CLIENTES_FILE));
            for (String l : nuevasLineas) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Cliente eliminado logicamente.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void listarPedidosCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        boolean encontrado = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(PEDIDOS_FILE));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", -1);

                if (datos[1].equals(String.valueOf(idCliente)) && datos[5].equals("1")) {
                    System.out.println("ID Pedido: " + datos[0] +
                            " | Producto: " + datos[2] +
                            " | Precio: " + datos[3] +
                            " | Cantidad: " + datos[4]);
                    encontrado = true;
                }
            }

            br.close();

            if (!encontrado) {
                System.out.println("No hay pedidos activos para este cliente.");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
