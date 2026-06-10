package ejercicio01;

public class BNode<E extends Comparable<E>> {

    int idNode;              // Identificador del nodo
    int count;               // Cantidad de claves almacenadas
    boolean leaf;            // Indica si es hoja

    E[] keys;                // Claves del nodo
    BNode<E>[] children;     // Hijos del nodo

    public BNode(int order, int idNode, boolean leaf) {
        this.idNode = idNode;
        this.leaf = leaf;
        this.count = 0;

        keys = (E[]) new Comparable[order - 1];
        children = (BNode<E>[]) new BNode[order];
    }
}