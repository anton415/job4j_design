package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Чат.
 */
public class ConsoleChat {
    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    /**
     * Запуск чата.
     */
    public void run() {
        List<String> log = new LinkedList<>();
        boolean isChatActive = true;
        boolean isBotActive = true;
        Scanner input = new Scanner(System.in);
        List<String> botPhrases = readPhrases();
        String userLine;
        String botLine;
        while (isChatActive) {
            userLine = input.nextLine();
            botLine = botPhrases.get(new Random().nextInt(botPhrases.size()));
            switch (userLine) {
                case OUT:
                    isChatActive = false;
                    break;
                case STOP:
                    isBotActive = false;
                    break;
                case CONTINUE:
                    isBotActive = true;
                    break;
                default:
                    log.add(userLine);
                    if (isBotActive) {
                        System.out.println(botLine);
                        log.add(botLine);
                    }
            }
        }
        saveLog(log);
    }

    /**
     * Чтение фраз бота.
     * @return список фраз.
     */
    private List<String> readPhrases() {
        List<String> phrases = Collections.emptyList();
        try (BufferedReader in = new BufferedReader(new FileReader(this.botAnswers))) {
            phrases = in.lines().toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phrases;
    }

    /**
     * Сохранение лога чата.
     * @param log лог.
     */
    private void saveLog(List<String> log) {
        try (PrintWriter out = new PrintWriter(new FileWriter(this.path, StandardCharsets.UTF_8, true))) {
            log.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/chat.log", "./data/botAnswers.txt");
        cc.run();
    }
}