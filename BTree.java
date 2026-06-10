package ejercicio02;

public class BTree<E extends Comparable<E>> {

    BNode<E> root;

    @SuppressWarnings("unchecked")
    public BTree() {

        // Árbol de ejemplo

        root = new BNode<>(4, 1, false);

        root.keys[0] = (E) Integer.valueOf(30);
        root.count = 1;

        root.children[0] = new BNode<>(4, 2, true);
        root.children[1] = new BNode<>(4, 3, true);

        root.children[0].keys[0] = (E) Integer.valueOf(10);
        root.children[0].keys[1] = (E) Integer.valueOf(20);
        root.children[0].count = 2;

        root.children[1].keys[0] = (E) Integer.valueOf(40);
        root.children[1].keys[1] = (E) Integer.valueOf(50);
        root.children[1].keys[2] = (E) Integer.valueOf(60);
        root.children[1].count = 3;
    }

    // Búsqueda por rango
    public void searchRange(E min, E max) {

        if (min.compareTo(max) > 0) {
            System.out.println("Rango inválido");
            return;
        }

        searchRange(root, min, max);
        System.out.println();
    }

    private void searchRange(BNode<E> node, E min, E max) {

        int i;

        for (i = 0; i < node.count; i++) {

            // Explora el hijo izquierdo
            if (!node.leaf &&
                min.compareTo(node.keys[i]) < 0) {

                searchRange(node.children[i], min, max);
            }

            // Imprime si la clave pertenece al rango
            if (node.keys[i].compareTo(min) >= 0 &&
                node.keys[i].compareTo(max) <= 0) {

                System.out.print(node.keys[i] + " ");
            }

            // Si ya superó el máximo, termina
            if (node.keys[i].compareTo(max) > 0) {
                return;
            }
        }

        // Explora el último hijo
        if (!node.leaf) {
            searchRange(node.children[i], min, max);
        }
    }
}