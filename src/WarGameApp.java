import java.util.Scanner;

public class WarGameApp {

    // Scanner для получения данных из консоли.
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        battle();
    }

    // Два игрока: пользователь и компьютерный бот.
    private static Player user = new Player("User", 10, 2);
    private static Player bot = new Player("Bot", 10, 2);

    // Класс игрока и поля.
    static class Player {
        String name;
        int health;
        int maxHealth;
        int shot;
        boolean block;

        public Player(String name, int maxHealth, int shot) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.health = this.maxHealth;
            this.shot = shot;
            this.block = false;
        }

        // Метод выстрела.
        public void shotAction(Player target) {
            int damage = this.shot;

            /* При режиме блокировки добавляется вероятность 75 %,
            что выстрел противника не пройдет и 25 % что пройдет с удвоенным уроном. */
            if (target.block) {
                if (Math.random() < 0.7) {
                    System.out.println(target.name + " shot failed ");
                    return;
                } else {
                    damage *= 2;
                    System.out.println(target.name + " double shot ");
                }
            }
            target.health -= damage;
            System.out.println(target.name + " health left " + target.health + " / " + target.maxHealth);
        }

        /* Метод блокировки с добавлением здоровья для игрока и
        проверка на превышение макс. значения здоровья */
        public void blockAction() {
            health++;
            if (health > maxHealth) {
                health = maxHealth;
            }
            block = true;
            System.out.println(this.name + " block health +1 ");
        }

        // Метод сброса блокировки игрока.
        public void resetAction() {
            block = false;
        }
    }

    // Метод боя пользователя и бота.
    public static void battle() {

        System.out.println("Game started!");
        while (true) {

            // Объявление хода пользователя.
            System.out.println(user.name + " move ");

            // Сброс блокировки пользователя при следующем его ходе.
            user.resetAction();

            String input = scan.next();

            // Команда выстрела пользователя по боту.
            if (input.equals("shot")) {
                user.shotAction(bot);

                // Проверка на здоровье бота.
                if (bot.health <= 0) {
                    System.out.println(user.name + " won " + bot.name);
                    break;
                }
            }
            // Команда блокировки пользователя.
            if (input.equals("block")) {
                user.blockAction();
            }

            // Объявление хода бота.
            System.out.println(bot.name + " move ");

            // Сброс блокировки бота при следующем его ходе.
            bot.resetAction();

            // При вероятности 50 % бот может нанести выстрел или поставить блок.
            if (Math.random() < 0.5) {
                bot.shotAction(user);

                // Проверка на здоровье пользователя.
                if (user.health <= 0) {
                    System.out.println(bot.name + " won " + user.name);
                    break;
                }
            } else {
                bot.blockAction();
            }
        }
    }
}


