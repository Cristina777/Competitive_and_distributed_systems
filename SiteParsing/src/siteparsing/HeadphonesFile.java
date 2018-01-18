package siteparsing;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class HeadphonesFile {
	private List<Headphones> headphones;

	public HeadphonesFile() {
		super();
		headphones = new ArrayList<>();

	}

	public void addHeadphones(String title, String id, Float price, String url) {
		headphones.add(new Headphones(title, id, price, url));
	}

	public void getHeadsphones() {
		
		for (Headphones headphone : headphones) {
			System.out.println("Titlul :" + headphone.getTitle());
			System.out.println("Id - ul :" + headphone.getId());
			System.out.println("Pretul :" + headphone.getPrice());
			System.out.println("Url - ul:" + headphone.getUrl());
		}

	}

	public void writeInFile() {
		FileOutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream("text.txt");
			PrintWriter writer = new PrintWriter(outputStream);
			for (Headphones headphone : headphones) {
				writer.println(headphone.getTitle());
				writer.println(headphone.getId());
				writer.println(headphone.getPrice());
				writer.println(headphone.getUrl());
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Eroare la deschiderea fisierului " + e.getMessage());
		} catch (NoSuchElementException e) {
			System.out.println("Eroare la procesarea fisierului " + e.getMessage());

		} catch (Exception e) {
			System.out.println("Eroare la procesarea fisierului " + e.getMessage());

		}

		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				System.out.println("Fisierul nu este deschis." + e.getMessage());
			}
		}
	}

}



