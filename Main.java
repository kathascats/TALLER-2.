import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int opcion;

            do {
                System.out.println("   SISTEMA CLIENTES Y PEDIDOS (CSV) ");
                System.out.println("1. Registrar un cliente");
                System.out.println("2. Listar clientes");
                System.out.println("3. Eliminar un cliente (lógico)");
                System.out.println("4. Registrar un pedido");
                System.out.println("5. Listar pedidos de un cliente");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    opcion = -1;
                }

                System.out.println();

                switch (opcion) {
                    case 1 : System.out.println("Opción 1 pendiente (la hace el branch 1).");
                    case 2 : System.out.println("Opción 2 pendiente (la hace el branch 1).");
                    case 3 : EliminarYListarPedidos.opcion3EliminarCliente(sc);
                    case 4 : System.out.println("Opción 4 pendiente (la hace el branch 3).");
                    case 5 : EliminarYListarPedidos.opcion5ListarPedidosCliente(sc);
                    case 6 : System.out.println("Saliendo del programa...");
                    default : System.out.println("Opción inválida. Intente nuevamente.");
                }

                System.out.println();

            } while (opcion != 6);
        }
    }
}
