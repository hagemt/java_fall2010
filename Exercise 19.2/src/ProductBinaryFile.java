import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductBinaryFile implements ProductDAO
{
	private ArrayList<Product> products = null;
	private File productsFile = null;

	public ProductBinaryFile()
	{
		productsFile = new File("products.dat");
		products = this.getProducts();
	}

	private void checkFile() throws IOException
	{
		// if the file doesn't exist, create it
		if (!productsFile.exists())
			productsFile.createNewFile();
	}

	private boolean saveProducts()
	{
		DataOutputStream out = null;
		try
		{
			this.checkFile();

			// open output stream for overwriting
			out = new DataOutputStream(
				  new BufferedOutputStream(
				  new FileOutputStream(productsFile)));
			
			// write all products in the array list
			// to the file
			for (Product p : products)
			{
				out.writeUTF(p.getCode());
				out.writeUTF(p.getDescription());
				out.writeDouble(p.getPrice());
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		finally
		{
			this.close(out);
		}
		return true;
	}

	private void close(Closeable stream)
	{
		try
		{
			if (stream != null)
				stream.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public ArrayList<Product> getProducts()
	{
		// if the products file has been read, don't read it again
		if (products != null)
			return products;

		DataInputStream in = null;
		try
		{
			this.checkFile();

			in = new DataInputStream(
				 new BufferedInputStream(
				 new FileInputStream(productsFile)));

			products = new ArrayList<Product>();

			// read all products stored in the file
			// into the array list
			while(true)
			{
				try {
					products.add(new Product(in.readUTF(), in.readUTF(), in.readDouble()));
				} catch (EOFException eofe) {
					break;
				}
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
		finally
		{
			this.close(in);
		}
		return products;
	}

	public Product getProduct(String code)
	{
		for (Product p : products)
		{
			if (p.getCode().equals(code))
				return p;
		}
		return null;
	}

	public boolean addProduct(Product p)
	{
		products.add(p);
		return this.saveProducts();
	}

	public boolean deleteProduct(Product p)
	{
		products.remove(p);
		return this.saveProducts();
	}

	public boolean updateProduct(Product newProduct)
	{
		// get the old product and remove it
		Product oldProduct = this.getProduct(newProduct.getCode());
		int i = products.indexOf(oldProduct);
		products.remove(i);

		// add the updated product
		products.add(i, newProduct);

		return this.saveProducts();
	}

	// another possible public method that might cause problems
	public boolean saveProducts(ArrayList<Product> products)
	{
		this.products = products;
		return this.saveProducts();
	}
}