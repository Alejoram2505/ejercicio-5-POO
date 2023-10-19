import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para representar a un jugador de voleibol.
 */
class Jugador {
    private String nombre;
    private String país;
    private int errores;
    private int aces;
    private int totalServicios;

    /**
     * Constructor de la clase Jugador.
     *
     * @param nombre Nombre del jugador.
     * @param país País del jugador.
     * @param errores Cantidad de errores del jugador.
     * @param aces Cantidad de aces del jugador.
     * @param totalServicios Cantidad total de servicios del jugador.
     */
    public Jugador(String nombre, String país, int errores, int aces, int totalServicios) {
        this.nombre = nombre;
        this.país = país;
        this.errores = errores;
        this.aces = aces;
        this.totalServicios = totalServicios;
    }

    /**
     * Calcula la efectividad del jugador según la fórmula dada.
     *
     * @return Efectividad del jugador en porcentaje.
     */
    public float calcularEfectividad() {
        return (((float) (totalServicios - errores) / (totalServicios + errores)) + ((float) aces / totalServicios)) * 100;
    }

    /**
     * Representación en cadena del jugador.
     *
     * @return Cadena con el nombre, país y efectividad del jugador.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", País: " + país + ", Efectividad: " + calcularEfectividad();
    }

    /**
     * Convierte los atributos del jugador en una cadena en formato CSV.
     *
     * @return Cadena en formato CSV con los atributos del jugador.
     */
    public String toCSV() {
        return nombre + "," + país + "," + errores + "," + aces + "," + totalServicios;
    }
}

/**
 * Clase para gestionar el sistema de registro de jugadores de voleibol.
 */
class SistemaRegistroVoleibol {
    private List<Jugador> listaDeJugadores;

    /**
     * Constructor de la clase SistemaRegistroVoleibol.
     */
    public SistemaRegistroVoleibol() {
        listaDeJugadores = new ArrayList<>();
    }

    /**
     * Agrega un jugador a la lista de jugadores.
     *
     * @param jugador Objeto de la clase Jugador.
     */
    public void agregarJugador(Jugador jugador) {
        listaDeJugadores.add(jugador);
    }

    /**
     * Muestra la lista de jugadores en la consola.
     */
    public void mostrarJugadores() {
        for (Jugador jugador : listaDeJugadores) {
            System.out.println(jugador);
        }
    }

    /**
     * Encuentra y devuelve una lista de jugadores con una efectividad mayor o igual a la efectividad mínima especificada.
     *
     * @param efectividadMinima Valor mínimo de efectividad para buscar jugadores efectivos.
     * @return Lista de jugadores efectivos.
     */
    public List<Jugador> encontrarJugadoresEfectivos(float efectividadMinima) {
        List<Jugador> jugadoresEfectivos = new ArrayList<>();
        for (Jugador jugador : listaDeJugadores) {
            if (jugador.calcularEfectividad() >= efectividadMinima) {
                jugadoresEfectivos.add(jugador);
            }
        }
        return jugadoresEfectivos;
    }

    /**
     * Guarda la lista de jugadores en un archivo CSV.
     *
     * @param archivo Nombre del archivo CSV.
     */
    public void guardarCatalogoCSV(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Jugador jugador : listaDeJugadores) {
                writer.write(jugador.toCSV());
                writer.newLine(); // Agregar nueva línea
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga la lista de jugadores desde un archivo CSV.
     *
     * @param archivo Nombre del archivo CSV.
     */
    public void cargarCatalogoCSV(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String nombre = datos[0];
                    String país = datos[1];
                    int errores = Integer.parseInt(datos[2]);
                    int aces = Integer.parseInt(datos[3]);
                    int totalServicios = Integer.parseInt(datos[4]);
                    Jugador jugador = new Jugador(nombre, país, errores, aces, totalServicios);
                    agregarJugador(jugador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SistemaRegistroVoleibol sistema = new SistemaRegistroVoleibol();

        Jugador jugador1 = new Jugador("JP", "francia", 5, 3, 20);
        Jugador jugador2 = new Jugador("Ram", "guam", 8, 2, 25);

        sistema.agregarJugador(jugador1);
        sistema.agregarJugador(jugador2);

        sistema.mostrarJugadores();

        List<Jugador> jugadoresEfectivos = sistema.encontrarJugadoresEfectivos(80.0f);
        System.out.println("\nJugadores efectivos con efectividad mayor o igual al 80%:");
        for (Jugador jugador : jugadoresEfectivos) {
            System.out.println(jugador);
        }

        sistema.guardarCatalogoCSV("jugadores.csv");

        SistemaRegistroVoleibol sistemaCargado = new SistemaRegistroVoleibol();
        sistemaCargado.cargarCatalogoCSV("jugadores.csv");

        System.out.println("\nJugadores cargados desde el archivo CSV:");
        sistemaCargado.mostrarJugadores();
    }
}
