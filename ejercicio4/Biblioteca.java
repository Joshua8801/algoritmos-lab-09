package ejercicio4;

import java.io.BufferedReader;
import java.io.FileReader;

import btree.BTree;

public class Biblioteca {

    private BTree<Libro> arbol;
    private int totalLibros;

    public Biblioteca(int orden) {
        arbol = new BTree<>(orden);
        totalLibros = 0;
    }
    
    public void agregarLibro(Libro libro) {

        arbol.insert(libro);
        totalLibros++;
    }
    
    public void buscarLibro(String isbn) {

        Libro libro =
            new Libro(isbn, "", "", 0);

        if (!arbol.searchPath(libro))
            System.out.println("Libro no encontrado");
    }
    
    
    public void eliminarLibro(String isbn) {

        Libro libro = new Libro(isbn, "", "", 0);

        arbol.remove(libro);
        totalLibros--;
    }
    
    public void mostrarArbol() {
        System.out.println(arbol);
    }
    
    public void mostrarLibros() {

        arbol.inOrder();
    }
    
    public static Biblioteca cargarArchivo(String nombreArchivo)
            throws Exception {

        BufferedReader br = new BufferedReader( new FileReader(nombreArchivo));

        int orden = Integer.parseInt(br.readLine());

        Biblioteca biblioteca = new Biblioteca(orden);

        String linea;

        while ((linea = br.readLine()) != null) {

            String[] datos = linea.split(",");

            String isbn = datos[0];
            String titulo = datos[1];
            String autor = datos[2];
            int anio = Integer.parseInt(datos[3]);

            Libro libro = new Libro(isbn,titulo,autor,anio);

            biblioteca.agregarLibro(libro);
        }

        br.close();

        return biblioteca;
    }
    
    public int obtenerAltura() {
        return arbol.height();
    }
    
    public int cantidadLibros() {
        return totalLibros;
    }
}