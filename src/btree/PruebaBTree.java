package btree;

public class PruebaBTree {

    public static void main(String[] args) {

        BTree<Integer> arbol = new BTree<>(4);

        arbol.insert(50);
        arbol.insert(20);
        arbol.insert(70);
        arbol.insert(10);
        arbol.insert(30);
        arbol.insert(60);
        arbol.insert(80);
        arbol.insert(25);
        arbol.insert(27);
        arbol.insert(26);
        arbol.insert(65);
        arbol.insert(75);
        arbol.insert(85);
        arbol.insert(5);

        System.out.println(arbol);
    }
}