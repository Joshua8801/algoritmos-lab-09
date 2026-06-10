package ejercicio01;

public class BTree<E extends Comparable<E>> {

    // Nodo raíz del árbol
    BNode<E> root;

    public BTree() {

        // Se construye manualmente un árbol para pruebas

        root = new BNode<>(4, 1, false);

        // Clave de la raíz
        root.keys[0] = (E) Integer.valueOf(30);
        root.count = 1;

        // Creación de los hijos
        root.children[0] = new BNode<>(4, 2, true);
        root.children[1] = new BNode<>(4, 3, true);

        // Claves del hijo izquierdo
        root.children[0].keys[0] = (E) Integer.valueOf(10);
        root.children[0].keys[1] = (E) Integer.valueOf(20);
        root.children[0].count = 2;

        // Claves del hijo derecho
        root.children[1].keys[0] = (E) Integer.valueOf(40);
        root.children[1].keys[1] = (E) Integer.valueOf(50);
        root.children[1].keys[2] = (E) Integer.valueOf(60);
        root.children[1].count = 3;
    }

    // Método público de búsqueda
    public boolean search(E cl) {

        // Si el árbol está vacío no hay nada que buscar
        if (root == null)
            return false;

        // Inicia la búsqueda desde la raíz
        return search(root, cl);
    }

    // Método recursivo de búsqueda
    private boolean search(BNode<E> node, E cl) {

        // Índice para recorrer las claves del nodo
        int i = 0;

        // Busca la posición donde debería estar la clave
        while (i < node.count &&
                cl.compareTo(node.keys[i]) > 0) {

            i++;
        }

        // Verifica si la clave fue encontrada
        if (i < node.count &&
                cl.compareTo(node.keys[i]) == 0) {

            System.out.println(
                    cl + " se encuentra en el nodo "
                            + node.idNode
                            + " en la posición "
                            + i);

            return true;
        }

        // Si es hoja y no se encontró la clave
        // significa que no existe en el árbol
        if (node.leaf)
            return false;

        // Continúa la búsqueda en el hijo correspondiente
        return search(node.children[i], cl);
    }
}