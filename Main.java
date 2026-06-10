package ejercicio01;

public class Main {

    public static void main(String[] args) {

        // Crear árbol B
        BTree<Integer> arbol = new BTree<>();

        // Buscar una clave existente en el hijo izquierdo
        System.out.println(arbol.search(10));
        System.out.println();

        // Buscar una clave existente en el hijo derecho
        System.out.println(arbol.search(60));
        System.out.println();

        // Buscar una clave que está en la raíz
        System.out.println(arbol.search(30));
        System.out.println();

        // Buscar una clave inexistente
        System.out.println(arbol.search(100));
    }
}