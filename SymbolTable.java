package lab1;

public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;

	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val) {

		for (int i = 0; i < keys.length; i++) {

			// if the key already exists, remove it

			if (keys[i] != null && keys[i].equals(key)) {

				if (val == null) {
					delete(key);

					return;

				}

				vals[i] = null;
				keys[i] = null;
				N--;

			}
		}

		// if there is space in the correct spot, add it

		if (keys[hash(key)] == null) {

			keys[hash(key)] = key;
			vals[hash(key)] = val;

		}

		// otherwise go to the next empty spot in the array
		else {
			for (int i = hash(key); i < keys.length; i++) {

				if (keys[i] == null) {
					keys[i] = key;
					vals[i] = val;
					N++;
					return;
				}

				// if we are at the end of the array, go back to the start
				if (i == keys.length - 1) {
					i = -1;
				}

			}

		}

		N++;
		return;
	}

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {

		for (int i = hash(key); i < keys.length; i++) {

			if (keys[i + 1] == null)
				break;

			if (i == 0 && keys[i] != null && keys[i].equals(key))
				return vals[i];

			if (keys[i + 1] != null && keys[i + 1].equals(key))
				return vals[i + 1];

			if (i == keys.length - 1) {
				i = 0;
			}

		}

		return null;
	}

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {

		boolean deleted = false;
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null && keys[i].equals(key)) {
				keys[i] = null;
				vals[i] = null;
				deleted = true;
				N--;
			}
			if (deleted) {

				if (i == keys.length - 1)
					i = -1;
				if (keys[i + 1] != null && hash(keys[i + 1]) != i + 1) {

					String temp = keys[i + 1];
					char temp2 = get(keys[i + 1]);

					keys[i + 1] = null;
					vals[i + 1] = null;

					put(temp, temp2);
					N--;
				}

				else if (keys[i + 1] == null)
					break;
			}
		}

		return;
	}

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}
