package btree;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> {

    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;

    // Identificador automático
    private static int nextId = 1;
    protected int idNode;

    public BNode(int n) {

        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n);
        this.count = 0;

        this.idNode = nextId++;

        for(int i = 0; i < n; i++) {
            this.keys.add(null);
            this.childs.add(null);
        }
    }

    // Verifica si el nodo está lleno
    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }

    // Verifica si el nodo está vacío
    public boolean nodeEmpty() {
        return count == 0;
    }

    /*
     * Busca una clave dentro del nodo.
     * Si la encuentra devuelve true y pos[0]
     * Si no la encuentra devuelve false y pos[0]
     */
    public boolean searchNode(E cl, int pos[]) {

        pos[0] = 0;

        while(pos[0] < count &&
                cl.compareTo(keys.get(pos[0])) > 0) {

            pos[0]++;
        }

        if(pos[0] < count &&
                cl.compareTo(keys.get(pos[0])) == 0) {

            return true;
        }

        return false;
    }

    public int getIdNode() {
        return idNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(");

        for(int i = 0; i < count; i++) {
            sb.append(keys.get(i));

            if(i < count - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }
}