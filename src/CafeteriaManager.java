import java.util.*;

public class CafeteriaManager {
    //структуры данных
    static ArrayDeque<String> queue = new ArrayDeque<>();
    static HashMap<String, Integer> arrivalTime = new HashMap<>();
    static int currentTime = 0;
    static long totalWaitTime = 0;
    static int servedCount = 0;

    //главный метод
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0].toUpperCase();
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            switch (command) {
                case "HELP":
                    printHelp();
                    break;
                case "ARRIVE":
                    handleArrive(arguments);
                    break;
                case "VIP_ARRIVE":
                    handleVipArrive(arguments);
                    break;
                case "SERVE":
                    handleServe();
                    break;
                case "LEAVE":
                    handleLeave(arguments);
                    break;
                case "PEEK":
                    handlePeek();
                    break;
                case "SIZE":
                    handleSize();
                    break;
                case "PRINT":
                    handlePrint();
                    break;
                case "TICK":
                    handleTick(arguments);
                    break;
                case "STATS":
                    handleStats();
                    break;
                case "TEST":
                    runPerformanceTest();
                    break;
                case "EXIT":
                    System.out.println("До свидания!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неизвестная команда. Введите HELP");
            }
        }
    }

    //методы для команд
    static void printHelp() {

        System.out.println("МЕНЕДЖЕР ОЧЕРЕДИ В СТОЛОВОЙ");
        System.out.println(" Команды:");
        System.out.println("  HELP        - показать помощь");
        System.out.println("  ARRIVE имя  - встать в очередь");
        System.out.println("  VIP_ARRIVE  - встать в начало");
        System.out.println("  SERVE       - обслужить следующего");
        System.out.println("  LEAVE имя   - уйти из очереди");
        System.out.println("  PEEK        - кто следующий");
        System.out.println("  SIZE        - размер очереди");
        System.out.println("  TICK минуты - увеличить время");
        System.out.println("  STATS       - статистика");
        System.out.println("  PRINT       - показать очередь");
        System.out.println("  TEST        - замер производительности");
        System.out.println("  EXIT        - выход");
    }
}