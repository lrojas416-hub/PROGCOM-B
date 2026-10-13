import java.util.*;

public class Main {

    record Compra(String flor, int cantidad, double subtotal) {}

    static String capitalizar(String s) {
        s = s.trim();
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<String> tipo = new ArrayList<>(List.of("Rosa", "Tulipán", "Girasol", "Orquídea"));
        List<Integer> cantidad = new ArrayList<>(List.of(20, 15, 10, 5));
        List<Double> precio = new ArrayList<>(List.of(3000.0, 2500.0, 4000.0, 12000.0));

        boolean estado = true;
        String opcion = "";
        List<Compra> comp = new ArrayList<>();
        double pagar = 0;
        String rta = "";
        String metodo = "";

        System.out.println("Bienvenido a mi floristería.");
        while (estado) {
            System.out.println("***MENÚ***");
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Visualizar nuestras flores");
            System.out.println("2. Comprar");
            System.out.println("3. Pagar");
            System.out.println("4. Factura de compra");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\nEstas son nuestras flores con sus respectivas características:\n");
                    for (int f = 0; f < tipo.size(); f++) {
                        String t = tipo.get(f);
                        int c = cantidad.get(f);
                        double p = precio.get(f);
                        System.out.println(t + ": " + c + " disponible, valor por unidad: " + p + "\n");
                    }
                    break;

                case "2":
                    System.out.println("\n¿Qué flores desea comprar?");
                    System.out.print("Ingrese el nombre de la flor: ");
                    String florac = capitalizar(sc.nextLine());
                    if (tipo.contains(florac)) {
                        int n = tipo.indexOf(florac);
                        int stockDisponible = cantidad.get(n);

                        if (stockDisponible > 0) {
                            try {
                                System.out.print("¿Cuántas unidades de " + florac + " desea comprar? (Disponibles: " + stockDisponible + "): ");
                                int cantComprar = Integer.parseInt(sc.nextLine().trim());
                                if (cantComprar <= 0) {
                                    System.out.println("\nLa cantidad debe ser mayor a 0.\n");
                                } else if (cantComprar <= stockDisponible) {
                                    double costoTotal = cantComprar * precio.get(n);
                                    cantidad.set(n, stockDisponible - cantComprar);
                                    comp.add(new Compra(florac, cantComprar, costoTotal));
                                    pagar += costoTotal;

                                    System.out.println("\nSe han agregado " + cantComprar + " " + florac + "(s) a tu carrito\n");
                                } else {
                                    System.out.println("\nNo hay suficiente stock. Solo tenemos " + stockDisponible + " unidades.\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("\nPor favor, pida un valor válido.\n");
                            }
                        } else {
                            System.out.println("\nNo quedan unidades de " + florac + ".\n");
                        }
                    } else {
                        System.out.println("\nEsa flor no existe en nuestro catálogo.\n");
                    }
                    break;

                case "3":
                    System.out.print("¿Cuál será su método de pago? [Efectivo, Tarjeta Débito, Tarjeta Crédito, PSE]: ");
                    metodo = capitalizar(sc.nextLine());
                    System.out.println("Debe pagar " + (pagar + (pagar * 0.19)));
                    System.out.print("¿Pagar?");
                    rta = capitalizar(sc.nextLine());
                    if (rta.equals("Si")) {
                        System.out.println("\n¡Gracias por su compra!\n");
                    } else if (rta.equals("No")) {
                        System.out.println("\n¡PAGA!\n");
                    } else {
                        System.out.println("\nIngresa una respuesta válida.\n");
                    }
                    break;

                case "4":
                    if (rta.equals("Si")) {
                        System.out.println("***FACTURA DE COMPRA***");
                        System.out.println("Productos comprados");
                        for (Compra c : comp) {
                            System.out.println(c.flor());
                            System.out.println("Cantidad comprada: " + c.cantidad());
                            System.out.println("Subtotal: " + c.subtotal());
                        }
                        System.out.println("Total pagado (IVA INCLUIDO): " + (pagar + (pagar * 0.19)));
                        System.out.println("Método de pago: " + metodo);
                    } else {
                        System.out.println("\n¡PAGA!\n");
                    }
                    break;

                case "5":
                    System.out.println("\n¡Hasta pronto!\n");
                    estado = false;
                    break;

                default:
                    System.out.println("\nOpción no válida.\n");
            }
        }
        sc.close();
    }
}