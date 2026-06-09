package btree;

public class Main {

	public static void main(String[] args) {

	    BTree<Integer> bt = new BTree<>(3);

	    bt.insert(10);
	    bt.insert(20);
	    bt.insert(5);
	    bt.insert(6);
	    bt.insert(12);
	    bt.insert(30);
	    bt.insert(7);
	    bt.insert(17);

	    System.out.println(bt);

	    bt.remove(6);
	    bt.remove(17);

	    System.out.println("Después de eliminar:");
	    System.out.println(bt);
	}

}
