package book_store;
import java.util.*;

public class main {
	public static void main(String []args) {
		Customer ob = new Customer();
		LogIn ob2 = new LogIn();
		int log = ob2.check("zozo","11");
		List<String> aaa = new ArrayList<>();
		long res=0;
		int price =0;
		try {
			 aaa =ob.search_for_book("Title", "proceed");
			 ob.add_item("984",2);
			 ob.add_item("33",1);
			// res= ob .total_price();
			// price = ob.individual_price("33");
			 ob.checkout();
			
			
		} catch (Exception e) {
			System.out.println("Error!!");
		}
		
		/*for(int i=0;i<aaa.size() ; i++) {
			System.out.println(aaa.get(i));
		}*/
		System.out.println(log);
		
		
		
	}
}
