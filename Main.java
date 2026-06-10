package ejercicio02;

public class Main {

    public static void main(String[] args) {

        BTree<Integer> arbol = new BTree<>();

        System.out.println("Rango [10,40]");
        arbol.searchRange(10, 40);

        System.out.println("Rango [20,60]");
        arbol.searchRange(20, 60);

        System.out.println("Rango [15,35]");
        arbol.searchRange(15, 35);

        System.out.println("Rango [100,200]");
        arbol.searchRange(100, 200);

        System.out.println("Rango [50,20]");
        arbol.searchRange(50, 20);
    }
}