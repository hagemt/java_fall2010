public class ProductDB
{
    public static Product getProduct(String productCode) throws ProductNotFoundException
    {
        // In a more realistic application, this code would
        // get the data for the product from a file or database
        // For now, this code just uses if/else statements
        // to return the correct product data

        // create the Product object
        Product product = new Product();

        // fill the Product object with data
        product.setCode(productCode);
        if (productCode.equalsIgnoreCase("java"))
        {
            product.setDescription("Murach's Beginning Java 2");
            product.setPrice(49.50);
        }
        else if (productCode.equalsIgnoreCase("jsps"))
        {
            product.setDescription("Murach's Java Servlets and JSP");
            product.setPrice(49.50);
        }
        else if (productCode.equalsIgnoreCase("mcb2"))
        {
            product.setDescription("Murach's Mainframe COBOL");
            product.setPrice(59.50);
        }
        else if (productCode.equalsIgnoreCase("txtp"))
        {
            product.setDescription("TextPad");
            product.setPrice(20.00);
        }
        else if (productCode.equalsIgnoreCase("magic")) {
        	product.setDescription("Magic Cards");
        	product.setPrice(3.50);
        }
        else {
            product.setDescription("Unknown");
        	throw new ProductNotFoundException("Error! \"" + productCode + "\" is not in the database.");
        }
        return product;
    }
}