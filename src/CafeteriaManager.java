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
        System.out.println("  HELP        - show this help");
        System.out.println("  ARRIVE имя  - join the queue");
        System.out.println("  VIP_ARRIVE  - join at the front");
        System.out.println("  SERVE       - serve next person");
        System.out.println("  LEAVE имя   - leave the queue");
        System.out.println("  PEEK        - see who's next");
        System.out.println("  SIZE        - queue size");
        System.out.println("  TICK минуты - advance time");
        System.out.println("  STATS       - show statistics");
        System.out.println("  PRINT       - show entire queue");
        System.out.println("  TEST        - performance test");
        System.out.println("  EXIT        - exit program");
    }

    static void handleArrive(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: specify name. Example: ARRIVE Aliya");
            return;
        }

        String name = args[0];

        if (name.isEmpty() || name.contains(" ")) {
            System.out.println("Error: name cannot be empty or contain spaces");
            return;
        }

        if (arrivalTime.containsKey(name)) {
            System.out.println("Error: " + name + " already in system");
            return;
        }

        queue.addLast(name);
        arrivalTime.put(name, currentTime);

        System.out.println(name + " arrived at time " + currentTime +
                ". Queue size = " + queue.size());
    }

    static void handleVipArrive(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: specify name. Example: VIP_ARRIVE Amir");
            return;
        }

        String name = args[0];

        if (name.isEmpty() || name.contains(" ")) {
            System.out.println("Error: name cannot be empty or contain spaces");
            return;
        }

        if (arrivalTime.containsKey(name)) {
            System.out.println("Error: person with name " + name + " already in system");
            return;
        }

        queue.addFirst(name);
        arrivalTime.put(name, currentTime);

        System.out.println(" VIP " + name + " arrived at time " + currentTime +
                " (to the front). Queue size = " + queue.size());
    }

    static void handleServe() {
        if (queue.isEmpty()) {
            System.out.println(" No one to serve — queue is empty");
            return;
        }

        String name = queue.removeFirst();
        int waitTime = currentTime - arrivalTime.get(name);

        servedCount++;
        totalWaitTime += waitTime;
        arrivalTime.remove(name);

        System.out.println(" Served: " + name + " (waited " + waitTime + " min).");
    }

    static void handleLeave(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: specify name. Example: LEAVE Aliya");
            return;
        }

        String name = args[0];

        if (!queue.contains(name)) {
            System.out.println(" Not found: " + name + " is not in queue");
            return;
        }

        queue.removeFirstOccurrence(name);
        arrivalTime.remove(name);

        System.out.println(" " + name + " left the queue. Queue size = " + queue.size());
    }

    static void handlePeek() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            System.out.println(" Next: " + queue.peekFirst());
        }
    }

    static void handleSize() {
        System.out.println(" Queue size: " + queue.size());
    }

    static void handlePrint() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            System.out.print(" Queue (front -> back): [");
            boolean first = true;
            for (String name : queue) {
                if (!first) {
                    System.out.print(", ");
                }
                System.out.print(name);
                first = false;
            }
            System.out.println("]");
        }
    }

    static void handleTick(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: specify minutes. Example: TICK 5");
            return;
        }

        try {
            int minutes = Integer.parseInt(args[0]);

            if (minutes < 0) {
                System.out.println("Error: minutes cannot be negative");
                return;
            }

            currentTime += minutes;
            System.out.println("  Time advanced by " + minutes + " min. Current time = " + currentTime);

        } catch (NumberFormatException e) {
            System.out.println("Error: enter an integer number of minutes");
        }
    }

    static void handleStats() {
        System.out.println(" STATISTICS:");
        System.out.println("   People served: " + servedCount);

        if (servedCount == 0) {
            System.out.println("   Average wait time: 0.00 min.");
        } else {
            double avgWait = (double) totalWaitTime / servedCount;
            System.out.printf("   Average wait time: %.2f min.%n", avgWait);
        }
    }

    //замеры производительности
    static void runPerformanceTest() {
        System.out.println("\n🔬 PERFORMANCE MEASUREMENT");
        System.out.println("═".repeat(50));

        int[] sizes = {1000, 5000, 10000, 20000, 50000};

        for (int N : sizes) {
            //пропускаем тест, если N слишком большой для слабых компьютеров
            if (N > 30000 && Runtime.getRuntime().totalMemory() < 500_000_000) {
                System.out.println("Skipping N=" + N + " (low memory)");
                continue;
            }

            //тест ARRIVE (добавление в конец)
            queue.clear();
            arrivalTime.clear();

            //прогрев JVM
            for (int i = 0; i < 1000; i++) {
                queue.addLast("warmup" + i);
            }
            queue.clear();

            //настоящий замер
            long startArrive = System.nanoTime();
            for (int i = 0; i < N; i++) {
                String name = "Person" + i;
                queue.addLast(name);
                arrivalTime.put(name, 0);
            }
            long endArrive = System.nanoTime();
            long timeArrive = endArrive - startArrive;

            //тест LEAVE (поиск и удаление)
            long startLeave = System.nanoTime();
            for (int i = 0; i < N; i++) {
                queue.removeFirstOccurrence("Person" + i);
            }
            long endLeave = System.nanoTime();
            long timeLeave = endLeave - startLeave;

            //результаты
            System.out.println("\n N = " + N + " operations:");
            System.out.printf("   ARRIVE (O(1) each): %8d ms total, %6d ns per operation%n",
                    timeArrive / 1_000_000, timeArrive / N);
            System.out.printf("   LEAVE  (O(n) each): %8d ms total, %6d ns per operation%n",
                    timeLeave / 1_000_000, timeLeave / N);
            System.out.printf("    LEAVE/ARRIVE ratio: %.2fx%n",
                    (double) timeLeave / timeArrive);
        }

        System.out.println("\n" + "═".repeat(50));
        System.out.println("CONCLUSION: LEAVE is slower than ARRIVE because:");
        System.out.println("  - ARRIVE: O(1) — just add to the end");
        System.out.println("  - LEAVE: O(n) — need to find element in the queue");
        System.out.println("  - The larger the queue, the more noticeable the difference\n");

        //очищаем после теста
        queue.clear();
        arrivalTime.clear();
        currentTime = 0;
        totalWaitTime = 0;
        servedCount = 0;
    }
}

