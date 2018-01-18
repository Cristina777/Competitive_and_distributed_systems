package siteparsing;

public class Headphones {
	public String title;
	public String id;
	public float price;
	public String url;

	public Headphones(String title, String id, Float price, String url) {
		super();
		this.title = title;
		this.id = id;
		this.price = price;
		this.url = url;

	}
	public String getTitle() {

		return title;
	}

	public String getId() {

		return id;
	}

	public float getPrice() {
		return price;

	}

	public String getUrl() {
		return url;
	}
}
