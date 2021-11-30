import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GameEngine {

    static Scanner input = new Scanner(System.in);
    static Random randomNumber = new Random();


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of Players:");
        boolean number = true;
        int totalPlayers = 0;
        while (number) {
            try {
                int numberOfPlayers = input.nextInt();
                if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                    System.out.println("Kindly enter the numbers between 2 and 4");
                    number = true;
                } else {
                    totalPlayers = numberOfPlayers;
                    number = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Kindly enter the integer !");
            }
        }
        createBoard();
        HashSet<Integer> specialFields = createSpecialFields();
        movePlayer(specialFields, createPlayers(totalPlayers));

    }

    public static List<Player> createPlayers(int numberOfPlayers) {
        List<Player> playerList = new ArrayList<>();
        switch (numberOfPlayers) {
            case 2: {
                Player player1 = new Player();
                Player player2 = new Player();
                playerList.add(player1);
                playerList.add(player2);
                System.out.println("2 Players Has been created");
                System.out.println("Enter Player 1 Name");
                player1.setName(input.next());
                System.out.println("Enter Player 2 Name");
                player2.setName(input.next());
                break;
            }
            case 3: {
                Player player1 = new Player();
                Player player2 = new Player();
                Player player3 = new Player();
                playerList.add(player1);
                playerList.add(player2);
                playerList.add(player3);
                System.out.println("3 Players Has been created");
                System.out.println("Enter Player 1 Name");
                player1.setName(input.next());
                System.out.println("Enter Player 2 Name");
                player2.setName(input.next());
                System.out.println("Enter Player 3 Name");
                player3.setName(input.next());
                break;
            }
            case 4: {
                Player player1 = new Player();
                Player player2 = new Player();
                Player player3 = new Player();
                Player player4 = new Player();

                playerList.add(player1);
                playerList.add(player2);
                playerList.add(player3);
                playerList.add(player4);

                System.out.println("4 Players Has been created");
                System.out.println("3 Players Has been created");
                System.out.println("Enter Player 1 Name");
                player1.setName(input.next());
                System.out.println("Enter Player 2 Name");
                player2.setName(input.next());
                System.out.println("Enter Player 3 Name");
                player3.setName(input.next());
                System.out.println("Enter Player 4 Name");
                player4.setName(input.next());
                break;
            }
        }
        return playerList;
    }

    public static List createBoard() {
        List<Integer> board = new ArrayList<>(30);
        System.out.println(board.size());
        for (int i = 0; i < board.size(); i++) {
            board.add(i + 1);
        }

        for (int i = 0; i < board.size(); i++) {
            System.out.println(board.indexOf(i));
        }
        return board;
    }

    public static HashSet createSpecialFields() {
        HashSet<Integer> specialFields = new HashSet<Integer>();
        for (int i = 0; i < 10; i++) {  //Create special fields but bonus and traps will generate on this hashset on runtime
            specialFields.add(ThreadLocalRandom.current().nextInt(1, 31));
        }
        System.out.println(specialFields.stream().collect(Collectors.toList()));
        return specialFields;
    }

    public static boolean playerWon(List<Player> playerList) {
        return playerList.stream().anyMatch(player -> player.getIndex() > 30);
    }

    public static void movePlayer(HashSet<Integer> specialField, List<Player> playerList) {

        List<Integer> specialFieldsArray = new ArrayList<>(specialField);
        while (!playerWon(playerList)) {
            for (int i = 0; i < playerList.size(); i++) {
                System.out.println("Its Player " + playerList.get(i).getName() + " turn.");
                int value = rollDice();
                System.out.println("Player " + playerList.get(i).getName() + " got: " + value);
                int oldIndexValue = playerList.get(i).getIndex(); //Old index
                playerList.get(i).setIndex(oldIndexValue + value);
                int updatedIndexValue = playerList.get(i).getIndex(); //new or updated index
                for (int j = 0; j < specialFieldsArray.size(); j++) {
                    if (updatedIndexValue == specialFieldsArray.get(j)) {
                        int randomNum = randomNumber.nextInt(6 - 0) + 6; //generate random number for trap or bonus
                        if (j == 1) {                                           //Trap Type 1: The player will move 2 fields backward
                            System.out.println("You are in Trap 1");
                            playerList.get(i).setIndex(updatedIndexValue - 2);
                        } else if (j == 2) {                                  //Trap type 2: All other players move 2 fields forward
                            System.out.println("You are in Trap 2");
                            String name = playerList.get(i).getName();
                            playerList.stream().filter(player -> {
                                if (playerList.contains(player.getName() == name)) {
                                } else player.setIndex(updatedIndexValue + 2);
                                return false;
                            }).collect(Collectors.toList());
                            playerList.get(i).setIndex(updatedIndexValue + 2);
                        } else if (j == 3) { // Trap type 3: Skip the round
                            System.out.println("You are in Trap 3");
                            break;
                        } else if (j == 4) {                                      //bonus Type 1: The player will move 2 fields forward
                            System.out.println("You got the bonus 1");
                            playerList.get(i).setIndex(updatedIndexValue + 2);

                        } else if (j == 5) {                                      //bonus Type 2: All other players will move backward
                            System.out.println("You got the bonus 2");
                            String name = playerList.get(i).getName();
                            playerList.stream().filter(player -> {
                                if (playerList.contains(player.getName() == name)) {
                                } else player.setIndex(updatedIndexValue - 2);
                                return false;
                            }).collect(Collectors.toList());
                        } else playerList.get(i).setJoker(true);
                    }
                }
                if (playerList.get(i).getIndex() > 30) {
                    System.out.println("Player " + playerList.get(i).getName() + " Won the game");
                    break;
                }

            }
        }
    }

    public static int rollDice() {
        int randomNum = randomNumber.nextInt(7 - 0);
        return randomNum;
    }
}