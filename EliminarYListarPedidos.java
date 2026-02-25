import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EliminarYListarPedidos {
    private static final String RUTA_CLIENTES = "clientes.csv";
    private static final String RUTA_PEDIDOS = "pedidos.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Menú de opciones");
            System.out.println("3 - Eliminar cliente");
            System.out.println("5 - Listar pedidos por cliente");
            System.out.println("0 - Salir");
            System.out.print("Elija una opción: ");

            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "3":
                    opcion3EliminarCliente(sc);
                    break;
                case "5":
                    opcion5ListarPedidosCliente(sc);
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
            System.out.println();
        }
    }

    /* OPCIÓN 3 */
    public static void opcion3EliminarCliente(Scanner sc) {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        int idCliente;
        try {
            idCliente = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        List<String> nuevasLineas = new ArrayList<>();
        boolean primera = true;
        boolean eliminado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CLIENTES))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    nuevasLineas.add(linea);
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length < 5) {
                    nuevasLineas.add(linea);
                    continue;
                }

                int id;
                try {
                    id = Integer.parseInt(partes[0]);
                } catch (NumberFormatException ex) {
                    nuevasLineas.add(linea);
                    continue;
                }
                String activo = partes[4];

                if (id == idCliente && activo.equals("1")) {
                    partes[4] = "0";
                    eliminado = true;
                    nuevasLineas.add(String.join(",", partes));
                } else {
                    nuevasLineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo clientes.csv: " + e.getMessage());
            return;
        }

        if (!eliminado) {
            System.out.println("Cliente no encontrado o ya inactivo.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CLIENTES))) {
            for (String l : nuevasLineas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo clientes.csv: " + e.getMessage());
            return;
        }

        System.out.println("Cliente eliminado correctamente (activo = 0).\n");
    }

    /* OPCIÓN 5 */
    public static void opcion5ListarPedidosCliente(Scanner sc) {
        System.out.print("Ingrese el ID del cliente: ");
        int idCliente;
        try {
            idCliente = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        boolean primera = true;
        boolean encontrados = false;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PEDIDOS))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length < 6) continue;

                int idPedido;
                int idCli;
                try {
                    idPedido = Integer.parseInt(partes[0]);
                    idCli = Integer.parseInt(partes[1]);
                } catch (NumberFormatException ex) {
                    continue;
                }
                String producto = partes[2];
                String precio = partes[3];
                String cantidad = partes[4];
                String activo = partes[5];

                if (idCli == idCliente && activo.equals("1")) {
                    encontrados = true;
                    System.out.println("Pedido #" + idPedido +
                            " | Producto: " + producto +
                            " | Precio: " + (precio.isEmpty() ? "N/A" : precio) +
                            " | Cantidad: " + (cantidad.isEmpty() ? "N/A" : cantidad));
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo pedidos.csv: " + e.getMessage());
            return;
        }

        if (!encontrados) {
            System.out.println("No hay pedidos activos para este cliente.");
        }
    }
}
