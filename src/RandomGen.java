import java.util.Random;

public class RandomGen {
    private static Random random = new Random();
    public static String getRandomName(int size)
    {
        int[] chars = new int[size];

        for (int i = 0; i < size; i++) {
            chars[i] = random.nextInt(48, 122);

            if(chars[i]>=91&&chars[i]<=96)
            {
                chars[i] = random.nextInt(97, 122);
            }
            else if (chars[i]>=47&&chars[i]<=64)
            {
                chars[i] = random.nextInt(48, 57);
            }
        }

        char[] part = new char[size];

        for (int i = 0; i < size; i++) {
            part[i] = (char) chars[i];
        }

        return new String(part);
    }
}
