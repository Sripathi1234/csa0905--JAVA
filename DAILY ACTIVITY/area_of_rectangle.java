import java.util.Scanner;

public class area_of_rectangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int length = sc.nextInt();
        int breadth = sc.nextInt();

        int area = length * breadth;

        System.out.println("Area of rectangle: " + area);

        sc.close();
    }
}