package DB;

import entities.Orderer;
import java.util.ArrayList;
import java.util.List;

public class DB {
	public static List<Orderer> orderers = new ArrayList<>();
	
	static {
		Orderer orderer1 = new Orderer();
		
		orderer1.setAddress("Borska 36a");
		orderer1.setFirstName("Snezana");
		orderer1.setLastName("Tanic");
		orderer1.setUsername("s_tanic");
		orderer1.setPassword("sifra123");
		orderer1.setPhoneNumber("+381655777396");
		
		orderers.add(orderer1);
	}
	
}
