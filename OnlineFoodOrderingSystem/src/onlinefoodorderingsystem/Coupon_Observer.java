package onlinefoodorderingsystem;
import java.util.ArrayList;


public interface Coupon_Observer {

    
        ArrayList<String> Coupon_Notifs = new ArrayList<String>();

        
	public void Update(String msg);

	public ArrayList<String> getCoupon_Notifs();

}