import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
            } catch (Exception e) {
                opcion = -1;
            }

            System.out.println();

            switch (opcion) {
                case 1:
                    System.out.println("Opción 1 pendiente (la hace el branch 1).");
                    break;

                case 2:
                    System.out.println("Opción 2 pendiente (la hace el branch 1).");
                    break;

                case 3:
                    Branch2.opcion3EliminarCliente(sc);
                    break;

                case 4:
                    System.out.println("Opción 4 pendiente (la hace el branch 3).");
                    break;

                case 5:
                    Branch2.opcion5ListarPedidosCliente(sc);
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println();

        } while (opcion != 6);

        sc.close();
    }
}
