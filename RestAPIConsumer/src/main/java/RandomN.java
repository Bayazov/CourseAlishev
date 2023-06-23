import java.util.Random;

public class RandomN {
    public static void main(String[] args) {
        Random random = new Random();
        double randomNumber = random.nextDouble() * 200 - 100;
        randomNumber = Math.round(randomNumber * 100.0) / 100.0; // Округляем до двух цифр после точки
        System.out.println(randomNumber);
    }
}