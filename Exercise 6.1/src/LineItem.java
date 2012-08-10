import java.text.NumberFormat;

public class LineItem
{
    private Product product;
    private int quantity;
    private double total;
    private static int INSTANCES = 0;

    public LineItem()
    {
    	this(new Product(), 0);
        this.total = 0;
    }

    public LineItem(Product product, int quantity)
    {
    	++INSTANCES;
        this.product = product;
        this.quantity = quantity;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public double getTotal()
    {
        this.calculateTotal();
        return total;
    }

    private void calculateTotal()
    {
        total = quantity * product.getPrice();
    }

    public String getFormattedTotal()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
    
    public static int getObjectCount() {
    	return INSTANCES;
    }
}