package ejercicio4;

public class TestBiblioteca {

    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca(4);

        biblioteca.agregarLibro(
                new Libro("9780132350884",
                        "One",
                        "Roberto Martinez",
                        2008));

        biblioteca.agregarLibro(
                new Libro("9780134494166",
                        "Two",
                        "Roberto Martinez",
                        2017));

        biblioteca.agregarLibro(
                new Libro("9780201633610",
                        "Four",
                        "Vizcarra",
                        1994));

        biblioteca.agregarLibro(
                new Libro("9780596009205",
                        "Five",
                        "Kathy Mamani",
                        2005));

        biblioteca.agregarLibro(
                new Libro("9780134685991",
                        "Version 2 Java",
                        "Joshua Huillca",
                        2018));

        System.out.println("=== ARBOL B ===");
        biblioteca.mostrarArbol();

        System.out.println("\n=== LIBROS ORDENADOS ===");
        biblioteca.mostrarLibros();

        System.out.println("\n=== BUSQUEDA ===");
        biblioteca.buscarLibro("9780132350884");

        System.out.println("\n=== ALTURA ===");
        System.out.println(
                biblioteca.obtenerAltura());

        System.out.println("\n=== TOTAL LIBROS ===");
        System.out.println(
                biblioteca.cantidadLibros());

        System.out.println("\n=== ELIMINAR ===");
        biblioteca.eliminarLibro(
                "9780201633610");

        biblioteca.mostrarArbol();

        System.out.println(
                "\nCantidad actual: "
                + biblioteca.cantidadLibros());
    }
}
