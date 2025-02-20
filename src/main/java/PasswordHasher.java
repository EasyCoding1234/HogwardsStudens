import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

//Хеширование пароля
public class PasswordHasher {
    private static final int SALT_LENGTH = 16;

    // Метод для хеширования пароля с солью
    public static String hashPassword(String password) {
        byte[] salt = generateSalt(); // Генерируем случайную соль
        byte[] hash = hashWithSalt(password, salt); // Хешируем пароль с солью

        // Кодируем соль и хеш в строку
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    // Проверка пароля
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            return false; // Неверный формат
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = hashWithSalt(inputPassword, salt);

        return Base64.getEncoder().encodeToString(hash).equals(parts[1]);
    }

    // Метод для генерации случайной соли
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    // Метод хеширования пароля с солью (SHA-256)
    private static byte[] hashWithSalt(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); // Добавляем соль
            return md.digest(password.getBytes()); // Хешируем пароль
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }
}
