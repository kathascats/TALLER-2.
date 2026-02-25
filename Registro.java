import java.io.*;
import java.util.*;

public class RegistroPedido {
    private static final String PEDIDOS_FILE = "pedidos.csv";
    private static final String CLIENTES_FILE = "clientes.csv";

    public static void registrarPedido(Scanner scanner) {
        try {
            System.out.print("ingrese ID del cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine();

            // validar que el cliente y us este exite o esta activo
            
            if (!clienteExisteYActivo(idCliente)) {
                System.out.println("el cliente no existe o está inactivo.");
                return;
            }

            System.out.print("ingrese nombre del producto: ");
            String producto = scanner.nextLine().trim();

            System.out.print("ingrese precio (esto es opcional, si quiere presione enter para omitir): ");
            String precioInput = scanner.nextLine().trim();
            double precio = precioInput.isEmpty() ? 0.0 : Double.parseDouble(precioInput);

            System.out.print("ingrese cantidad (esto es opcional, si quiere presione enter para omitir");
            String cantidadInput = scanner.nextLine().trim();
            int cantidad = cantidadInput.isEmpty() ? 0 : Integer.parseInt(cantidadInput);

            // obt siguiente ID de pedido
            int idPedido = obtenerSiguienteIdPedido();

            // escribir en archivo CSV
            escribirPedido(idPedido, idCliente, producto, precio, cantidad);

            System.out.println("pedido registrado exitosamente con ID: " + idPedido);

        } catch (NumberFormatException e) {
            System.out.println("error: ingrese valores numéricos válidos.");
        } catch (IOException e) {
            System.out.println("error al acceder al archivo: " + e.getMessage());
        }
    }

    private static boolean clienteExisteYActivo(int idCliente) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5 && Integer.parseInt(datos[0]) == idCliente) {
                    return Integer.parseInt(datos[4]) == 1;
                }
            }
        }
        return false;
    }

    private static int obtenerSiguienteIdPedido() throws IOException {
        int maxId = 0;
        File file = new File(PEDIDOS_FILE);
        
        if (!file.exists()) {
            return 1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length > 0 && !datos[0].equals("id_pedido")) {
                    int id = Integer.parseInt(datos[0]);
                    maxId = Math.max(maxId, id);
                }
            }
        }
        return maxId + 1;
    }

    private static void escribirPedido(int idPedido, int idCliente, String producto, 
                                       double precio, int cantidad) throws IOException {
        File file = new File(PEDIDOS_FILE);
        boolean esNuevoArchivo = !file.exists();

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (esNuevoArchivo) {
                bw.write("id_pedido,id_cliente,producto,precio,cantidad,activo\n");
            }

            String linea = String.format("%d,%d,%s,%.2f,%d,1\n", 
                idPedido, idCliente, producto, precio, cantidad);
            bw.write(linea);
        }
    }
}