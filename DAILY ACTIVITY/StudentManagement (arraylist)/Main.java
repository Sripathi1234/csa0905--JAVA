import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        ArrayList<Arrlistbasic> students = new ArrayList<>();

        students.add(new Arrlistbasic(101, "Sripathi", 85, 90, 88));
        students.add(new Arrlistbasic(102, "Maram", 95, 92, 98));
        students.add(new Arrlistbasic(103, "Sravan", 75, 80, 70));

        Collections.sort(students, Comparator.comparing(s -> s.name));

        System.out.println("Student Details\n");

        for (Arrlistbasic s : students) {
            s.display();
        }
    }
}