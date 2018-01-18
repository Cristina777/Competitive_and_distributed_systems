package siteparsing;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class ParseSite implements Runnable {
	private final ReentrantLock filter;
	private final HeadphonesFile headphones;
	private final String cellUrl;
	private final String emagUrl;

	public ParseSite(HeadphonesFile headphones, String cellUrl, String emagUrl, ReentrantLock theFilter) {
		super();
		this.filter = theFilter;
		this.headphones = headphones;
		this.cellUrl = cellUrl;
		this.emagUrl = emagUrl;

	}

	@Override
	public void run() {

		if (!Application.cellNoMoreWebPage) {
                    try {
                        parseCel(cellUrl);
                    } catch (IOException ex) {
                        Logger.getLogger(ParseSite.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		if (!Application.emagNoMorePage) {
                    try {
                        parseEmag(emagUrl);
                    } catch (IOException ex) {
                        Logger.getLogger(ParseSite.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}

	}

	public void parseCel(String theUrl) throws IOException {

		System.out.println(" Cell url" + theUrl);
		if (theUrl.equals("http://www.cel.ro/casti/0a-25")) {
			Application.cellNoMoreWebPage = true;
		}
		Document doc = null;
                doc = Jsoup.connect(theUrl).get();
                if (doc == null) {
                    return;
                }
                org.jsoup.select.Elements prices = doc.select("b[itemprop = price]");
                org.jsoup.select.Elements ids = doc.select("div.stoc_list").select("span[id]");
                org.jsoup.select.Elements urls = doc.select("h4.productTitle").select("a[href]");
                org.jsoup.select.Elements titles = doc.select("h4.productTitle").select("span[itemprop]");
                Float[] allPrice = new Float[prices.size()];
                String[] allId = new String[ids.size()];
                String[] allURL = new String[urls.size()];
                String[] allTitle = new String[titles.size()];
                int counter = 0;
                for (Element price : prices) {
                    allPrice[counter] = Float.parseFloat(price.attr("content"));
                    counter++;
                    
                }
                counter = 0;
                for (Element id : ids) {
                    String i = substringForCellToValidateId(id.attr("id"), id.attr("id").length());
                    allId[counter] = i;
                    counter++;
                }
                counter = 0;
                for (Element url : urls) {
                    allURL[counter] = url.attr("abs:href");
                    counter++;
                }
                counter = 0;
                for (Element title : titles) {
                    allTitle[counter] = title.text();
                    counter++;
                }
                System.out.println("Number of products :" + counter);
                filter.lock();
                try {
                for (int i = 0; i < counter; i++) {                 
                        headphones.addHeadphones(allTitle[i], allId[i], allPrice[i], allURL[i]);
                    }
                   } finally {
                        filter.unlock();
                    }
	}

	public void parseEmag(String theUrl) throws IOException {
		System.out.println("Emag url" + theUrl);
		if (theUrl.equals("https://www.emag.ro/casti-pc/p11/c")) {
			Application.emagNoMorePage = true;

		}
		Document doc = null;
                doc = Jsoup.connect(theUrl).get();
                org.jsoup.select.Elements prices = doc.select("p.product-new-price");
                org.jsoup.select.Elements ids = doc.select("input[type = hidden]").select("[name = product[]]");
                org.jsoup.select.Elements urls = doc.select("h2.card-body.product-title-zone").select("a[href]");
                org.jsoup.select.Elements titles = doc.select("h2.card-body.product-title-zone");
                Float[] allPrice = new Float[prices.size()];
                String[] allId = new String[prices.size()];
                String[] allURL = new String[urls.size()];
                String[] allTitle = new String[titles.size()];
                int counter = 0;
                for (Element price : prices) {
                    if (!(price.text().equals(""))) {
                        String p = substringForEmagToFoundPrice(price.text(), price.text().length() - 4);
                        if (p.contains("de la")) {
                            p = substringForEmagToFoundPriceException(p, p.length());
                        }
                        Float pricefloat = 0.0f;
                        try {
                            pricefloat = Float.parseFloat(p);
                            pricefloat /= (float) 100;
                            
                            allPrice[counter++] = pricefloat;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    
                }
                counter = 0;
                for (Element id : ids) {
                    allId[counter++] = id.attr("value");
                }
                counter = 0;
                for (Element url : urls) {
                    allURL[counter++] = url.select("a").attr("abs:href");
                }
                counter = 0;
                for (Element title : titles) {
                    
                    allTitle[counter++] = title.text();
                }
                System.out.println("Number of products :" + counter);
                filter.lock();
                try {
                for (int i = 0; i < counter; i++) {       
                   
                        headphones.addHeadphones(allTitle[i], allId[i], allPrice[i], allURL[i]);
                   
                }
                } finally {
                       filter.unlock();
                }
	}

	private String substringForEmagToFoundPrice(String s, int length) {
		String newString = "";
		for (int i = 0; i < length; i++) {
			if (s.charAt(i) != '.') {
				newString = newString + s.charAt(i);
			}
		}
		return newString;
	}

	private String substringForEmagToFoundPriceException(String s, int length) {
		String newString = "";
		for (int i = 5; i < length; i++) {
			newString = newString + s.charAt(i);
		}
		return newString;
	}

	private String substringForCellToValidateId(String s, int length) {
		String newString = "";
		for (int i = 0; i < length; i++) {
			if ((s.charAt(i) != 's')) {
				newString = newString + s.charAt(i);
			}
		}
		return newString;
	}

}


