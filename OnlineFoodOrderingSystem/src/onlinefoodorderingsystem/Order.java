package onlinefoodorderingsystem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Order {
	private int Order_Id;
	private Restaurant Order_Rest = new Restaurant();
	private ArrayList<Order_Item> Ordered_Items;
	private String Order_Date;
	private Coupon Order_Promo;
	private double Order_Price;
	private String Orderstatus;
	public Order_Item m_Order_Item;
	public Payment_Method m_Payment_Method;
        DB_Connection_Seif db = new DB_Connection_Seif();
        
	public Order(){
            Order_Price = 0;
            m_Payment_Method = null;
	}

    public Order(int Order_Id, Restaurant Order_Rest, ArrayList<Order_Item> Ordered_Items, String Order_Date, Coupon Order_Promo, double Order_Price, String Order_status, Order_Item m_Order_Item, Payment_Method m_Payment_Method) {
        this.Order_Id = Order_Id;
        this.Order_Rest = Order_Rest;
        this.Ordered_Items = Ordered_Items;
        this.Order_Date = Order_Date;
        this.Order_Promo = Order_Promo;
        this.Order_Price = Order_Price;
        this.Orderstatus = Order_status;
        this.m_Order_Item = m_Order_Item;
        this.m_Payment_Method = m_Payment_Method;
    }

    public Order(int Order_Id, Restaurant Order_Rest, String Order_Date, double Order_Price, String Orderstatus) {
        this.Order_Id = Order_Id;
        this.Order_Rest = Order_Rest;
        this.Order_Date = Order_Date;
        this.Order_Price = Order_Price;
        this.Orderstatus = Orderstatus;
    }

    public Order(int Order_Id, String Order_Date, double Order_Price, String Orderstatus) {
        this.Order_Id = Order_Id;
        this.Order_Date = Order_Date;
        this.Order_Price = Order_Price;
        this.Orderstatus = Orderstatus;
    }
        
        

	public void finalize() throws Throwable {

	}
        
	public void Add_To_Cart(Order_Item item, Order order){
            db.Add_To_Cart(item, order);
	}

	/**
	 * 
	 * @param item
	 */
	public boolean Remove_From_Cart(int id){
           db.Remove_From_Cart(id);
		return false;
	}

	/**
	 * 
	 * @param item
	 * @param new_quant
	 */
	public void Modify_Cart_Item(int id, int amount, int sum){
            db.Modify_Cart_Item(id, amount, sum);
	}
        
        

	/**
	 * 
	 * @param c
	 */
        
	public Coupon Redeem_Coupon(Customer c, int code){
            return db.Redeem_Coupon(c, code);
	}

	public void Checkout(Order o){
            db.Checkout(o);
	}

        public void setOrder_Id(int Order_Id) {
            this.Order_Id = Order_Id;
        }

        public void setOrder_Rest(Restaurant Order_Rest) {
            this.Order_Rest = Order_Rest;
        }

        public void setOrdered_Items(ArrayList<Order_Item> Ordered_Items) {
            this.Ordered_Items = Ordered_Items;
        }

        public void setOrder_Date(String Order_Date) {
            this.Order_Date = Order_Date;
        }

        public void setOrder_Promo(Coupon Order_Promo) {
            this.Order_Promo = Order_Promo;
        }

        public void setOrder_Price(double Order_Price) {
            this.Order_Price = Order_Price;
        }

        public void setOrderstatus(String Orderstatus) {
            this.Orderstatus = Orderstatus;
        }

        public void setM_Order_Item(Order_Item m_Order_Item) {
            this.m_Order_Item = m_Order_Item;
        }

        public void setM_Payment_Method(Payment_Method m_Payment_Method) {
            this.m_Payment_Method = m_Payment_Method;
        }

        public int getOrder_Id() {
            return Order_Id;
        }

        public Restaurant getOrder_Rest() {
            return Order_Rest;
        }

        public ArrayList<Order_Item> getOrdered_Items() {
            return Ordered_Items;
        }

        public String getOrder_Date() {
            return Order_Date;
        }

        public Coupon getOrder_Promo() {
            return Order_Promo;
        }

        public double getOrder_Price() {
            return Order_Price;
        }

        public String getOrderstatus() {
            return Orderstatus;
        }

        public Order_Item getM_Order_Item() {
            return m_Order_Item;
        }

        public Payment_Method getM_Payment_Method() {
            return m_Payment_Method;
        }
 
}//end Order