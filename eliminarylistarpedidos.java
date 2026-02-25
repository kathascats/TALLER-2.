import java.io.*;
import java.util.*;

public class Branch2 {

    private static final String RUTA_CLIENTES = "clientes.csv";
    private static final String RUTA_PEDIDOS = "pedidos.csv";

    /*OPCIÓN 3*/
    public static void opcion3EliminarCliente(Scanner sc) {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        File archivo = new File(RUTA_CLIENTES);
        if (!archivo.exists()) {
            System.out.println("No existe el archivo clientes.csv");
            return;
        }

        List<String> nuevasLineas = new ArrayList<>();
        boolean eliminado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {

                if (primera) { // encabezado
                    nuevasLineas.add(linea);
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");

                int id = Integer.parseInt(partes[0]);
                String activo = partes[4];

                if (id == idCliente && activo.equals("1")) {
                    partes[4] = "0"; // eliminación lógica
                    eliminado = true;
                    nuevasLineas.add(String.join(",", partes));
                } else {
                    nuevasLineas.add(linea);
                }
            }

        } catch (Exception e) {
            System.out.println("Error leyendo clientes.csv");
            return;
        }

        if (!eliminado) {
            System.out.println("Cliente no encontrado o ya estaba inactivo.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (String l : nuevasLineas) {
                pw.println(l);
            }
        } catch (Exception e) {
            System.out.println("Error escribiendo clientes.csv");
            return;
        }

        System.out.println("Cliente eliminado correctamente (activo = 0).");
    }


    /*OPCIÓN 5*/
    public static void opcion5ListarPedidosCliente(Scanner sc) {
        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        File archivo = new File(RUTA_PEDIDOS);
        if (!archivo.exists()) {
            System.out.println("No existe el archivo pedidos.csv");
            return;
        }

        boolean encontrados = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primera = true;

            System.out.println("\nPedidos del cliente ID " + idCliente + ":");
            System.out.println("--------------------------------------");

            while ((linea = br.readLine()) != null) {

                if (primera) { // saltar encabezado
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");

                int idPedido = Integer.parseInt(partes[0]);
                int idCli = Integer.parseInt(partes[1]);
                String producto = partes[2];
                String precio = partes[3];
                String cantidad = partes[4];
                String activo = partes[5];

                if (idCli == idCliente && activo.equals("1")) {
                    encontrados = true;
                    System.out.println(
                            "Pedido #" + idPedido +
                            " | Producto: " + producto +
                            " | Precio: " + (precio.isEmpty() ? "N/A" : precio) +
                            " | Cantidad: " + (cantidad.isEmpty() ? "N/A" : cantidad)
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error leyendo pedidos.csv");
            return;
        }

        if (!encontrados) {
            System.out.println("No hay pedidos activos para este cliente.");
        }

        System.out.println("--------------------------------------\n");
    }
}
