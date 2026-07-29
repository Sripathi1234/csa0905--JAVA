public class Arrlistbasic {

    int id;
    String name;
    int mark1, mark2, mark3;

    public Arrlistbasic(int id, String name, int mark1, int mark2, int mark3) {
        this.id = id;
        this.name = name;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public int calculateTotal() {
        return mark1 + mark2 + mark3;
    }

    public double calculateAverage() {
        return calculateTotal() / 3.0;
    }

    public void display() {
        System.out.println("ID : " + id);
        System.out.println("Name : " + name);
        System.out.println("Total : " + calculateTotal());
        System.out.println("Average : " + calculateAverage());
        System.out.println();
    }
}