import java.util.LinkedList;

public class Main1 {

    public static void main(String[] args) {

        Book b1 = new Book(101, "OnePiece", "Maram");

        LinkedList<Book> books = new LinkedList<>();

        books.add(b1);
        books.add(new Book(102, "Marvel", "Sripathi"));
        books.add(new Book(103, "Book", "Sravan"));

        System.out.println("Book Details:\n");

        for (Book b : books) {
            b.display();
        }

        books.removeFirst();

        System.out.println("Book Details:\n");

        for (Book b : books) {
            b.display();
        }

    }
}