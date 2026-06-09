package btree;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> {

    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;

    private static int counter = 0;
    protected int idNode;

    public BNode(int n) {

        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n + 1);

        this.count = 0;
        this.idNode = counter++;

        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }

        for (int i = 0; i < n + 1; i++) {
            this.childs.add(null);
        }
    }

    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }

    public boolean nodeEmpty() {
        return count == 0;
    }

    public boolean searchNode(E key, int pos[]) {
        int i = 0;

        while (z && key.compareTo(keys.get(i)) > 0){
            i++;
        }

        pos[0] = i;

        if (i < count && key.equals(keys.get(i))) {
            return true; // clave encontrada
        }

        return false; // no encontrada
    }

    @Override
    public String toString() {
        String s = "[Nodo " + idNode + ": ";

        for (int i = 0; i < count; i++) {
            s += keys.get(i) + " ";
        }

        s += "]";
        return s;
    }
}
