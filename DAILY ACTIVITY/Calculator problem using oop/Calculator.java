import java.util.Scanner;

class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int subtract(int a, int b) {
        return a - b;
    }

    int multiply(int a, int b) {
        return a * b;
    }

    double divide(int a, int b) {
        return (double) a / b;
    }
}

class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator obj = new Calculator();

        System.out.print("Enter first number: ");
        int a = sc.nextInt();

        System.out.print("Enter second number: ");
        int b = sc.nextInt();

        System.out.print("Enter operator (+,-,*,/): ");
        char op = sc.next().charAt(0);

        switch (op) {
            case '+':
                System.out.println("Result: " + obj.add(a, b));
                break;
            case '-':
                System.out.println("Result: " + obj.subtract(a, b));
                break;
            case '*':
                System.out.println("Result: " + obj.multiply(a, b));
                break;
            case '/':
                if (b != 0)
                    System.out.println("Result: " + obj.divide(a, b));
                else
                    System.out.println("Cannot divide by zero");
                break;
            default:
                System.out.println("Invalid operator");
        }
        sc.close();
    }
}