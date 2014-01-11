package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueIdentifier {

	private static int id = 0;
	private static final int MAX_ID = 10;

	private UniqueIdentifier() {}

	public static int getIdentifier() {
		if (!(id == MAX_ID)) {
			return id++;
		} else {
			return -1;
		}
	}

}
