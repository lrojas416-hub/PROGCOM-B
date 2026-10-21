import java.util.*;

public class FloristeriaHashMap {
    static Scanner sc = new Scanner(System.in);
    static String pago_opc = "";
    static int dinero_user;
    static int cuotas;
    static void mostrar_menu() {
        System.out.println("\nQué desea hacer?");
        System.out.println("1. Comprar flores");
        System.out.println("2. Pagar");
        System.out.println("3. Generar factura");
        System.out.println("4. Salir");
    }
    static void agregar_al_carrito(HashMap<String, ArrayList<Object>> flores,
                                   HashMap<String, ArrayList<Object>> compra_user) {
        if (flores.get("tipo").size() == 0) {
            System.out.println("Ya no queda stock, puede pagar lo que tiene en el carrito");
        } else {

            System.out.println("Flores disponibles:");
            for (int i = 0; i < flores.get("tipo").size(); i++) {
                String t = (String) flores.get("tipo").get(i);
                System.out.println("- " + t.substring(0, 1).toUpperCase() + t.substring(1) + ": "
                        + flores.get("cant").get(i) + " disponibles a $" + flores.get("precios").get(i) + " c/u");
            }

            System.out.print("Seleccione la flor que desea comprar: ");
            String flor_opc = sc.nextLine().toLowerCase().trim();
            if (flores.get("tipo").contains(flor_opc)) {
                int idx = flores.get("tipo").indexOf(flor_opc);
                System.out.print("Ingrese la cantidad que desea comprar: ");
                int flor_cant = Integer.parseInt(sc.nextLine());

                if (flor_cant <= (int) flores.get("cant").get(idx)) {

                    compra_user.get("tipo").add(flor_opc);
                    compra_user.get("cantidad").add(flor_cant);
                    compra_user.get("precio").add(flores.get("precios").get(idx));

                    flores.get("cant").set(idx, (int) flores.get("cant").get(idx) - flor_cant);
                    System.out.println("¡Se han añadido " + flor_cant + " " + flor_opc + "(s) al carrito!");
                    if ((int) flores.get("cant").get(idx) == 0) {
                        flores.get("tipo").remove(idx);
                        flores.get("cant").remove(idx);
                        flores.get("precios").remove(idx);
                    }

                } else {
                    System.out.println("Cantidad no disponible");
                }
            } else {
                System.out.println("La flor " + flor_opc + " no está disponible");
            }
        }
    }

    static void quitar_del_carrito(HashMap<String, ArrayList<Object>> flores,
                                   HashMap<String, ArrayList<Object>> compra_user) {
        if (compra_user.get("tipo").size() == 0) {
            System.out.println("El carrito de compras está vacío. ");
        } else {
            System.out.println("En su carrito tiene:");
            for (int i = 0; i < compra_user.get("tipo").size(); i++) {
                String t = (String) compra_user.get("tipo").get(i);
                System.out.println("- " + t.substring(0, 1).toUpperCase() + t.substring(1) + ": "
                        + compra_user.get("cantidad").get(i));
            }

            System.out.print("Seleccione la flor que desea quitar: ");
            String flor_quitar = sc.nextLine().toLowerCase().trim();
            if (compra_user.get("tipo").contains(flor_quitar)) {
                int j = compra_user.get("tipo").indexOf(flor_quitar);
                System.out.print("Ingrese la cantidad que desea quitar: ");
                int cant_quitar = Integer.parseInt(sc.nextLine());

                if (0 < cant_quitar && cant_quitar <= (int) compra_user.get("cantidad").get(j)) {
                    if (flores.get("tipo").contains(flor_quitar)) {
                        int idx = flores.get("tipo").indexOf(flor_quitar);
                        flores.get("cant").set(idx, (int) flores.get("cant").get(idx) + cant_quitar);
                    } else {
                        flores.get("tipo").add(flor_quitar);
                        flores.get("cant").add(cant_quitar);
                        flores.get("precios").add(compra_user.get("precio").get(j));
                    }

                    compra_user.get("cantidad").set(j, (int) compra_user.get("cantidad").get(j) - cant_quitar);
                    if ((int) compra_user.get("cantidad").get(j) == 0) {
                        compra_user.get("tipo").remove(j);
                        compra_user.get("cantidad").remove(j);
                        compra_user.get("precio").remove(j);
                    }
                    System.out.println("Se han quitado " + cant_quitar + " " + flor_quitar + "(s) del carrito");
                } else {
                    System.out.println("Cantidad inválida");
                }
            } else {
                System.out.println("La flor " + flor_quitar + " no esta en su carrito");
            }
        }
    }


    static void pagar(HashMap<String, ArrayList<Object>> compra_user) {
        if (compra_user.get("tipo").size() > 0) {

            int total_pagar = 0;
            for (int i = 0; i < compra_user.get("tipo").size(); i++) {
                int subtotal = (int) compra_user.get("cantidad").get(i) * (int) compra_user.get("precio").get(i);
                total_pagar += subtotal;
            }
            System.out.println("\nUsted compro:");
            System.out.printf("%-15s | %-10s | %-15s | %-15s%n", "Flores", "Cantidad", "Precio Unitario", "Subtotal");
            System.out.println("-".repeat(46));
            for (int i = 0; i < compra_user.get("tipo").size(); i++) {
                String tipo = (String) compra_user.get("tipo").get(i);
                int cantidad = (int) compra_user.get("cantidad").get(i);
                int precio = (int) compra_user.get("precio").get(i);
                System.out.printf("%-15s | %-10s | $%-14s | $%-14s%n",
                        tipo.substring(0, 1).toUpperCase() + tipo.substring(1),
                        cantidad, precio, precio * cantidad);
            }
            System.out.println("\nTotal a pagar: $" + total_pagar + "\n");

            System.out.println("Seleccione el medio de pago: \n");
            System.out.println("1. Efectivo");
            System.out.println("2. Tarjeta de crédito");
            System.out.println("3. Tarjeta de débito");
            System.out.println("4. Otro medio de pago");
            System.out.println("Seleccione su opción: ");
            pago_opc = sc.nextLine();

            System.out.println("\nUsted ha seleccionado la opción de pago " + pago_opc + ".");

            if (pago_opc.equals("1") || pago_opc.equals("3")) {
                System.out.print("¿Cuánto dinero tiene disponible? $");
                dinero_user = Integer.parseInt(sc.nextLine());
                if (dinero_user >= total_pagar) {
                    System.out.println("Pago exitoso. Le sobran: $" + (dinero_user - total_pagar));
                } else {
                    System.out.println("Dinero insuficiente, le faltan: $" + (total_pagar - dinero_user));
                }

            } else if (pago_opc.equals("2")) {
                System.out.print("¿En cuántas cuotas desea pagar?: ");
                cuotas = Integer.parseInt(sc.nextLine());
                if (cuotas > 0) {
                    System.out.println("Pagará " + cuotas + " cuota(s) de $" + Math.round((double) total_pagar / cuotas));
                } else {
                    System.out.println("Número de cuotas inválido");
                }

            } else {
                System.out.println("El total a pagar por su compra es: $" + total_pagar);
            }

        } else {
            System.out.println("El carrito de compras está vacío. ");
        }
    }

    static void generar_factura(HashMap<String, ArrayList<Object>> compra_user) {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("                  AVES PROGRAMADORAS");
        System.out.println("                    NIT: 9018086767");
        System.out.println("             ¡Somos evasores de impuestos!");
        System.out.println("=".repeat(65));
        System.out.println("                         FACTURA DE VENTA");
        System.out.println("=".repeat(65));

        System.out.printf("%-15s | %-10s | %-15s | %-15s%n", "Flores", "Cantidad", "Precio Unitario", "Subtotal");
        System.out.println("-".repeat(65));

        int total_pagar = 0;
        for (int i = 0; i < compra_user.get("tipo").size(); i++) {
            String tipo = (String) compra_user.get("tipo").get(i);
            int cantidad = (int) compra_user.get("cantidad").get(i);
            int precio = (int) compra_user.get("precio").get(i);
            int subtotal = cantidad * precio;
            total_pagar += subtotal;
            System.out.printf("%-15s | %-10s | $%-14s | $%-14s%n",
                    tipo.substring(0, 1).toUpperCase() + tipo.substring(1),
                    cantidad, precio, subtotal);
        }

        System.out.println("-".repeat(65));
        System.out.printf("%-44s $%s%n", "TOTAL A PAGAR:", total_pagar);
        System.out.println("=".repeat(65));

        if (compra_user.get("tipo").size() > 0) {
            System.out.println("                    INFORMACIÓN DEL PAGO");
            System.out.println("-".repeat(65));

            if (pago_opc.equals("1")) {
                System.out.println("Medio de pago: Efectivo");
                System.out.println("Total recibido: $" + dinero_user);
                System.out.println("Cambio: $" + (dinero_user - total_pagar));

            } else if (pago_opc.equals("2")) {
                System.out.println("Medio de pago: Tarjeta de crédito");
                System.out.println("Cuotas: " + cuotas);
                System.out.println("Valor aproximado por cuota: $" + Math.round((double) total_pagar / cuotas));
                System.out.println("Total recibido: Pago con tarjeta de crédito");
                System.out.println("Cambio: $0");

            } else if (pago_opc.equals("3")) {
                System.out.println("Medio de pago: Tarjeta de débito");
                System.out.println("Total recibido: Pago con tarjeta de débito");
                System.out.println("Cambio: $0");

            } else if (pago_opc.equals("4")) {
                System.out.println("Medio de pago: Otro medio de pago");
                System.out.println("Total recibido: Pago por otro medio");
                System.out.println("Cambio: $0");
            }

            System.out.println("-".repeat(65));
            System.out.println("        ¡GRACIAS POR COMPRAR EN AVES PROGRAMADORAS!");
            System.out.println("=".repeat(65));
        } else {
            System.out.println("No hay productos en la factura.");
        }
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Object>> flores = new HashMap<>();
        flores.put("tipo", new ArrayList<>(Arrays.asList("margarita", "azucena", "rosa")));
        flores.put("cant", new ArrayList<>(Arrays.asList(10, 20, 30)));
        flores.put("precios", new ArrayList<>(Arrays.asList(1200, 3000, 1200)));

        HashMap<String, ArrayList<Object>> compra_user = new HashMap<>();
        compra_user.put("tipo", new ArrayList<>());
        compra_user.put("cantidad", new ArrayList<>());
        compra_user.put("precio", new ArrayList<>());

        while (true) {
            mostrar_menu();

            System.out.println("Seleccione su opción: ");
            String opc = sc.nextLine();

            if (opc.equals("1")) {
                System.out.println("Ha seleccionado: Comprar flores\n");

                // aca si no hay flores sale del bucle porque si no hay nada pos ya q
                if (flores.get("tipo").size() == 0 && compra_user.get("tipo").size() == 0) {
                    System.out.println("Lo siento, estamos sin stock");
                    break;
                }

                System.out.println("1. Agregar al carrito");
                System.out.println("2. Quitar del carrito (me arrepentí)");
                System.out.println("Seleccione su opción: ");
                String sub_opc = sc.nextLine();

                if (sub_opc.equals("1")) {
                    agregar_al_carrito(flores, compra_user);


                } else if (sub_opc.equals("2")) {
                    quitar_del_carrito(flores, compra_user);
                } else {
                    System.out.println("Numero incorrecto");
                }

            } else if (opc.equals("2")) {
                pagar(compra_user);

            } else if (opc.equals("3")) {
                generar_factura(compra_user);
                if (compra_user.get("tipo").size() == 0) {
                    break;
                }

            } else if (opc.equals("4")) {
                System.out.println("Ha seleccionado: Salir\n");
                break;

            } else {
                System.out.println("Número incorrecto, por favor seleccione uno correcto");
            }
        }

        System.out.println("Gracias por emplear nuestros servicios. Feliz día");
    }
}