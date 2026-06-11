package btree;

public class BTree<E extends Comparable<E>> {

    // Raíz del árbol B
    private BNode<E> root;

    // Orden del árbol
    private int orden;

    // Indica si hubo división al insertar
    private boolean up;

    // Nodo derecho generado en una división
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    // Verifica si el árbol está vacío
    public boolean isEmpty() {
        return this.root == null;
    }

    // Inserta una clave en el árbol
    public void insert(E cl) {
        up = false;
        E mediana;

        mediana = push(this.root, cl);

        // Si la raíz se dividió, se crea una nueva raíz
        if (up) {
            BNode<E> pnew = new BNode<E>(orden);

            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, root);
            pnew.childs.set(1, nDes);

            root = pnew;
        }
    }

    // Inserción recursiva
    private E push(BNode<E> current, E cl) {

        int pos[] = new int[1];
        E mediana;

        // Se llegó a una hoja
        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        }

        boolean found = current.searchNode(cl, pos);

        // No se permiten duplicados
        if (found) {
            System.out.println("Elemento duplicado");
            up = false;
            return null;
        }

        mediana = push(current.childs.get(pos[0]), cl);

        if (up) {

            // Si el nodo está lleno se divide
            if (current.nodeFull(orden - 1)) {
                mediana = dividedNode(current, mediana, pos[0]);
            } else {
                putNode(current, mediana, nDes, pos[0]);
                up = false;
            }
        }

        return mediana;
    }

    // Inserta una clave dentro de un nodo
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {

        for (int i = current.count - 1; i >= k; i--) {

            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }

        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);

        current.count++;
    }

    // Divide un nodo lleno
    private E dividedNode(BNode<E> current, E cl, int k) {

        BNode<E> rd = nDes;

        int posMdna = orden / 2;

        nDes = new BNode<E>(orden);

        // Copiar mitad derecha al nuevo nodo
        for (int i = posMdna; i < orden - 1; i++) {

            nDes.keys.set(i - posMdna,
                    current.keys.get(i));

            nDes.childs.set(i - posMdna + 1,
                    current.childs.get(i + 1));
        }

        nDes.count = (orden - 1) - posMdna;
        current.count = posMdna;

        // Insertar donde corresponda
        if (k <= posMdna) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }

        // Obtener la mediana
        E median = current.keys.get(current.count - 1);

        nDes.childs.set(0,
                current.childs.get(current.count));

        current.count--;

        return median;
    }

    @Override
    public String toString() {

        if (isEmpty()) {
            return "Árbol vacío";
        }

        return writeTree(root, 0);
    }

    // Muestra el árbol por niveles
    private String writeTree(BNode<E> node, int nivel) {

        String result = "";

        if (node != null) {

            result += "Nivel " + nivel + " "
                    + node.toString() + "\n";

            for (int i = 0; i <= node.count; i++) {
                result += writeTree(
                        node.childs.get(i),
                        nivel + 1
                );
            }
        }

        return result;
    }


    // Busca una clave
    public boolean search(E cl) {
        return searchRec(root, cl);
    }

    private boolean searchRec(BNode<E> current, E cl) {

        if (current == null) {
            return false;
        }

        int i = 0;

        while (i < current.count &&
                cl.compareTo(current.keys.get(i)) > 0) {
            i++;
        }

        if (i < current.count &&
                cl.compareTo(current.keys.get(i)) == 0) {

            System.out.println(
                    cl + " se encuentra en el nodo "
                    + current.idNode
                    + " en la posición "
                    + i
            );

            return true;
        }

        return searchRec(current.childs.get(i), cl);
    }
    


    // Busca elementos dentro de un rango
    public void searchRange(E min, E max) {

        if (min.compareTo(max) > 0) {
            System.out.println("Rango inválido");
            return;
        }

        searchRangeRec(root, min, max);

        System.out.println();
    }

    private void searchRangeRec(
            BNode<E> node,
            E min,
            E max) {

        if (node == null)
            return;

        int i;

        for (i = 0; i < node.count; i++) {

            // Explorar subárbol izquierdo
            if (node.childs.get(i) != null &&
                    min.compareTo(node.keys.get(i)) < 0) {

                searchRangeRec(
                        node.childs.get(i),
                        min,
                        max
                );
            }

            // Mostrar si está dentro del rango
            if (node.keys.get(i).compareTo(min) >= 0 &&
                    node.keys.get(i).compareTo(max) <= 0) {

                System.out.print(
                        node.keys.get(i) + " "
                );
            }

            // Ya se superó el máximo
            if (node.keys.get(i).compareTo(max) > 0) {
                return;
            }
        }

        // Explorar último hijo
        if (node.childs.get(i) != null) {
            searchRangeRec(
                    node.childs.get(i),
                    min,
                    max
            );
        }
    }

    // =========================
    // EJERCICIO 3
    // =========================

    public void remove(E key) {

        if (root == null) {
            System.out.println("Árbol vacío");
            return;
        }

        removeRec(root, key);

        // Ajustar raíz si quedó vacía
        if (root.count == 0) {

            if (root.childs.get(0) == null)
                root = null;
            else
                root = root.childs.get(0);
        }
    }

    private void removeRec(BNode<E> node, E key) {

        int pos = 0;

        while (pos < node.count &&
                key.compareTo(node.keys.get(pos)) > 0) {
            pos++;
        }

        // Clave encontrada
        if (pos < node.count &&
                key.compareTo(node.keys.get(pos)) == 0) {

            if (isLeaf(node)) {

                removeFromLeaf(node, pos);

            } else {

                E succ = getSuccessor(node, pos);

                node.keys.set(pos, succ);

                removeRec(
                        node.childs.get(pos + 1),
                        succ
                );

                restore(node, pos + 1);
            }

        } else {

            if (isLeaf(node)) {
                System.out.println("Clave no encontrada");
                return;
            }

            removeRec(
                    node.childs.get(pos),
                    key
            );

            restore(node, pos);
        }
    }

    // Cantidad mínima de claves permitidas
    private int minKeys() {
        return (int) Math.ceil(orden / 2.0) - 1;
    }

    // Verifica si es hoja
    private boolean isLeaf(BNode<E> node) {
        return node.childs.get(0) == null;
    }

    // Elimina una clave de una hoja
    private void removeFromLeaf(
            BNode<E> node,
            int pos) {

        for (int i = pos;
                i < node.count - 1;
                i++) {

            node.keys.set(i,
                    node.keys.get(i + 1));
        }

        node.keys.set(node.count - 1, null);

        node.count--;
    }

    // Obtiene el sucesor de una clave
    private E getSuccessor(
            BNode<E> node,
            int pos) {

        BNode<E> current =
                node.childs.get(pos + 1);

        while (current.childs.get(0) != null) {
            current = current.childs.get(0);
        }

        return current.keys.get(0);
    }

    // Restaura propiedades del árbol B
    private void restore(
            BNode<E> parent,
            int pos) {

        BNode<E> child =
                parent.childs.get(pos);

        if (child == null)
            return;

        if (child.count >= minKeys())
            return;

        if (pos > 0 &&
                parent.childs.get(pos - 1).count > minKeys()) {

            borrowFromLeft(parent, pos);

        } else if (pos < parent.count &&
                parent.childs.get(pos + 1).count > minKeys()) {

            borrowFromRight(parent, pos);

        } else {

            if (pos > 0)
                merge(parent, pos - 1);
            else
                merge(parent, pos);
        }
    }

    // Pide prestada una clave del hermano izquierdo
    private void borrowFromLeft(
            BNode<E> parent,
            int pos) {

        BNode<E> child =
                parent.childs.get(pos);

        BNode<E> left =
                parent.childs.get(pos - 1);

        for (int i = child.count; i > 0; i--) {
            child.keys.set(i,
                    child.keys.get(i - 1));
        }

        for (int i = child.count + 1; i > 0; i--) {
            child.childs.set(i,
                    child.childs.get(i - 1));
        }

        child.keys.set(
                0,
                parent.keys.get(pos - 1)
        );

        child.childs.set(
                0,
                left.childs.get(left.count)
        );

        child.count++;

        parent.keys.set(
                pos - 1,
                left.keys.get(left.count - 1)
        );

        left.keys.set(left.count - 1, null);
        left.childs.set(left.count, null);

        left.count--;
    }

    // Pide prestada una clave del hermano derecho
    private void borrowFromRight(
            BNode<E> parent,
            int pos) {

        BNode<E> child =
                parent.childs.get(pos);

        BNode<E> right =
                parent.childs.get(pos + 1);

        child.keys.set(
                child.count,
                parent.keys.get(pos)
        );

        child.childs.set(
                child.count + 1,
                right.childs.get(0)
        );

        child.count++;

        parent.keys.set(
                pos,
                right.keys.get(0)
        );

        for (int i = 0;
                i < right.count - 1;
                i++) {

            right.keys.set(i,
                    right.keys.get(i + 1));
        }

        for (int i = 0;
                i < right.count;
                i++) {

            right.childs.set(i,
                    right.childs.get(i + 1));
        }

        right.keys.set(right.count - 1, null);
        right.childs.set(right.count, null);

        right.count--;
    }

    // Une dos nodos hermanos
    private void merge(
            BNode<E> parent,
            int pos) {

        BNode<E> left =
                parent.childs.get(pos);

        BNode<E> right =
                parent.childs.get(pos + 1);

        left.keys.set(
                left.count,
                parent.keys.get(pos)
        );

        left.count++;

        for (int i = 0; i < right.count; i++) {

            left.keys.set(
                    left.count,
                    right.keys.get(i)
            );

            left.childs.set(
                    left.count,
                    right.childs.get(i)
            );

            left.count++;
        }

        left.childs.set(
                left.count,
                right.childs.get(right.count)
        );

        for (int i = pos;
                i < parent.count - 1;
                i++) {

            parent.keys.set(i,
                    parent.keys.get(i + 1));

            parent.childs.set(
                    i + 1,
                    parent.childs.get(i + 2)
            );
        }

        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);

        parent.count--;
    }

    // =========================
    // EJERCICIO 4
    // =========================

    // Recorrido InOrder
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(BNode<E> node) {

        if (node == null)
            return;

        int i;

        for (i = 0; i < node.count; i++) {

            inOrder(node.childs.get(i));

            System.out.println(
                    node.keys.get(i)
            );
        }

        inOrder(node.childs.get(i));
    }

    // Altura del árbol
    public int height() {
        return height(root);
    }

    private int height(BNode<E> node) {

        if (node == null)
            return 0;

        return 1 + height(node.childs.get(0));
    }

    // Búsqueda mostrando el camino recorrido
    public boolean searchPath(E key) {
        return searchPath(root, key);
    }

    private boolean searchPath(
            BNode<E> node,
            E key) {

        if (node == null)
            return false;

        System.out.println(node);

        int i = 0;

        while (i < node.count &&
                key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.count &&
                key.compareTo(node.keys.get(i)) == 0) {

            return true;
        }

        return searchPath(
                node.childs.get(i),
                key
        );
    }
}
