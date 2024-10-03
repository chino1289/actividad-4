package agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author juan_carlos
 */
   
public class AddressBook {

    public static void main(String[] args) {
         AddressBook agenda = new AddressBook();
        String archivo = "contactos.csv";

        try {
            agenda.load(archivo);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado o error al cargar.");
        }

        agenda.menu(archivo);
    }
    
    
    // Atributo para almacenar los contactos
    private HashMap<String, String> contactos;

    public AddressBook() {
        contactos = new HashMap<>();
    }

    // Método para cargar contactos desde el archivo CSV
    public void load(String archivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] datos = linea.split(","); 
            if (datos.length == 2) {
                contactos.put(datos[0], datos[1]);
            }
        }
        lector.close();
    }

    // Método para guardar contactos en el archivo CSV
    public void save(String archivo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
        for (Map.Entry<String, String> entry : contactos.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
        writer.close();
    }

    // Método para listar los contactos
    public void list() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos en la agenda");
        } else {
            System.out.println("Contactos:");
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Método para crear un nuevo contacto
    public void create(String numero, String nombre) {
        if (!contactos.containsKey(numero)) {
            contactos.put(numero, nombre);
            System.out.println("Contacto agregado.");
        } else {
            System.out.println("El contacto ya existe.");
        }
    }

    // Método para borrar un contacto
    public void delete(String numero) {
        if (contactos.containsKey(numero)) {
            contactos.remove(numero);
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Menú interactivo
    public void menu(String archivo) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear nuevo contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    list();
                    break;
                case 2:
                    System.out.print("Introduce el número: ");
                    String numero = scanner.nextLine();
                    System.out.print("Introduce el nombre: ");
                    String nombre = scanner.nextLine();
                    create(numero, nombre);
                    break;
                case 3:
                    System.out.print("Introduce el número del contacto a eliminar: ");
                    String numeroBorrar = scanner.nextLine();
                    delete(numeroBorrar);
                    break;
                case 4:
                    try {
                        save(archivo);
                        System.out.println("Contactos guardados. Saliendo...");
                    } catch (IOException e) {
                        System.out.println("Error al guardar los contactos.");
                    }
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
    }
