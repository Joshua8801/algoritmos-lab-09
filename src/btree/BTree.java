package btree;

public class BTree<E extends Comparable<E>> {

    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(E cl) {
        up = false;

        E mediana = push(this.root, cl);

        if (up) {
            BNode<E> pnew = new BNode<E>(this.orden);

            pnew.count = 1;
            pnew.keys.set(0, mediana);

            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);

            this.root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) {
        int pos[] = new int[1];

        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        }

        boolean fl = current.searchNode(cl, pos);

        if (fl) {
            System.out.println("Item duplicado");
            up = false;
            return null;
        }

        E mediana = push(current.childs.get(pos[0]), cl);

        if (up) {
            if (current.nodeFull(this.orden - 1)) {
                mediana = dividedNode(current, mediana, pos[0]);
            } else {
                up = false;
                putNode(current, mediana, nDes, pos[0]);
            }
        }

        return mediana;
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        int i;

        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }

        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;

        int i;
        int posMdna;

        posMdna = (k <= this.orden / 2)
                ? this.orden / 2
                : this.orden / 2 + 1;

        nDes = new BNode<E>(this.orden);

        for (i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;

        if (k <= this.orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }

        E median = current.keys.get(current.count - 1);

        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;

        return median;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "BTree is empty...";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Id.Nodo\tClaves Nodo\tId.Padre\tId.Hijos\n");
        writeTree(this.root, null, sb);

        return sb.toString();
    }

    private void writeTree(BNode<E> current, BNode<E> parent, StringBuilder sb) {
        if (current == null) {
            return;
        }

        sb.append(current.idNode).append("\t");

        sb.append("(");
        for (int i = 0; i < current.count; i++) {
            sb.append(current.keys.get(i));

            if (i < current.count - 1) {
                sb.append(", ");
            }
        }
        sb.append(")\t\t");

        if (parent == null) {
            sb.append("--\t\t");
        } else {
            sb.append("[").append(parent.idNode).append("]\t\t");
        }

        boolean tieneHijos = false;
        sb.append("[");

        for (int i = 0; i <= current.count; i++) {
            BNode<E> hijo = current.childs.get(i);

            if (hijo != null) {
                if (tieneHijos) {
                    sb.append(", ");
                }

                sb.append(hijo.idNode);
                tieneHijos = true;
            }
        }

        if (!tieneHijos) {
            sb.append("--");
        }

        sb.append("]\n");

        for (int i = 0; i <= current.count; i++) {
            writeTree(current.childs.get(i), current, sb);
        }
    }
}