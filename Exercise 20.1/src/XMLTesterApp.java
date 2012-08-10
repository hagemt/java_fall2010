import java.util.ArrayList;
import java.io.*;
import javax.xml.stream.*; // StAX API

public class XMLTesterApp {
	private static String productsFilename = "products.xml";

	public static void main(String[] args) {
		System.out.println("Products list:");
		ArrayList<Product> products = readProducts();
		printProducts(products);

		products.add(new Product("test", "XML Tester", 77.77));
		writeProducts(products);
		System.out.println("XML Tester has been added to the XML document.\n");

		System.out.println("Products list:");
		products = readProducts();
		printProducts(products);

		products.remove(2);
		writeProducts(products);
		System.out.println("XML Tester has been deleted from the XML document.\n");

		System.out.println("Products list:");
		products = readProducts();
		printProducts(products);

	}

	private static ArrayList<Product> readProducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		String code = null, desc = null, price = null;
		XMLInputFactory inputFactory = XMLInputFactory.newFactory();
		try {
			XMLStreamReader reader = inputFactory.createXMLStreamReader(new FileReader(new File(productsFilename)));
			while (reader.hasNext()) {
				switch (reader.getEventType()) {
				case XMLStreamReader.START_ELEMENT:
					String tag = reader.getLocalName();
					if (tag.equals("Product")) {
						code = desc = price = null;
						code = reader.getAttributeValue(0);
					} else if (tag.equals("Description")) {
						desc = reader.getElementText();
					} else if (tag.equals("Price")) {
						price = reader.getElementText();
					}
					break;
				case XMLStreamReader.END_ELEMENT:
					if (reader.getLocalName().equals("Product")) {
						products.add(new Product(code, desc, Double.parseDouble(price)));
					}
					break;
				default:
					break;
				}
				reader.next();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return products;
	}

	private static void writeProducts(ArrayList<Product> products) {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		try {
			XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(new File(productsFilename)));
			writer.writeStartDocument();
			writer.writeComment("Product data");
			writer.writeStartElement("Products");
			for (Product p : products) {
				writer.writeStartElement("Product");
				writer.writeAttribute("code", p.getCode());

				writer.writeStartElement("Description");
				writer.writeCharacters(p.getDescription());
				writer.writeEndElement();

				writer.writeStartElement("Price");
				writer.writeCharacters(Double.toString(p.getPrice()));
				writer.writeEndElement();

				writer.writeEndElement();
			}
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printProducts(ArrayList<Product> products) {
		for (Product p : products) {
			printProduct(p);
		}
		System.out.println();
	}

	private static void printProduct(Product p) {
		String productString = StringUtils.padWithSpaces(p.getCode(), 8)
				+ StringUtils.padWithSpaces(p.getDescription(), 44)
				+ p.getFormattedPrice();

		System.out.println(productString);
	}
}
