import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // INSTANTIATE A SCANNER
        Scanner input = new Scanner(System.in);

        // PROMPT THE USER FOR A NUMBER OF PLAYERS
        System.out.println("How many players?");
        int player_count = input.nextInt();

        // INSTANTIATE THE PLAYER ARRAY
        Player[] player = new Player[player_count];
        for (int i = 0; i < player_count; i++) {
            player[i] = new Player();
        }

        // SET THE NUMBER OF COMPLETED ROUNDS TO ZERO
        int rounds = 1;

        // RANDOMLY CHOOSE THE NEXT PLAYER (MINUS 1)
        int current_player = (int) (Math.random() * player_count) - 1;

        // START THE GAME WITH A LOOP THAT GOES UNTIL ALL THE ROUNDS ARE FINISHED
        while (rounds <= 5) {

            // RESET ALL THE KIDS SO THAT THEY HAVEN'T RUN AWAY
            for (int i = 0; i < player_count; i++) {
                player[i].ran_away = false;
            }

            // RESET THE POT TO ZERO CANDY
            int pot = 0;

            // RESET THE NUMBER OF GOBLINS ENCOUNTERED TO ZERO
            int goblin_count = 0;

            if (rounds == 1) {
                System.out.println("\nTHE NIGHT BEGINS...");
            } else {
                System.out.println("\nTHE PARTY REGROUPS AND CONTINUES ONTO ROUND #".toUpperCase() + rounds);
            }

            // A ROUND CONTINUES UNTIL TWO GOBLINS HAVE BEEN ENCOUNTERED
            // OR UNTIL THERE ARE NO KIDS LEFT
            while (goblin_count < 2 && RemainingPlayers(player, player_count) > 0) {

                // MOVE TO THE NEXT PLAYER
                current_player = GetNextPlayer(player, player_count, current_player);

                // KNOCK ON A DOOR...
                System.out.println("==========================================================");
                System.out.println("Knock, knock...");

                // DETERMINE IF THE HOUSE HAS CANDY OR A GOBLIN (1/6)
                int chance_goblin = (int) (Math.random() * 6);

                // IF IT'S A GOBLIN...
                if (chance_goblin == 0) {
                    System.out.println("----------------------------------------------------------");
                    System.out.println("Oh, no! It's a goblin...");
                    System.out.println("----------------------------------------------------------");

                    goblin_count++;
                } else {
                    int new_candy = (int) (Math.random() * 10 + 1);
                    pot += new_candy;

                    System.out.println("----------------------------------------------------------");
                    System.out.println("This house gave " + new_candy + " pieces of candy!");
                    System.out.println("The size of the pot is now " + pot + " pieces of candy.");
                    System.out.println("----------------------------------------------------------");
                }

                if (goblin_count < 2) {
                    System.out.println("Player #" + (current_player + 1) + ", do you want to steal all the candy in the pot? (true/false)");
                    boolean steal_candy = input.nextBoolean();

                    // IF SO...
                    if (steal_candy) {
                        // MOVE ALL THE CANDY TO THE CURRENT PLAYER
                        player[current_player].candy += pot;
                        pot = 0;

                        // THEY'RE DONE FOR THE ROUND
                        player[current_player].ran_away = true;

                        // LET THEM KNOW
                        System.out.println("~~~~You ran off with the candy!~~~~");

                    }


                }

            }
            // REPORT THE SCORES
            System.out.println("==========================================================");
            System.out.println();

            System.out.println("~~~~~~~~~~~~~~~~~~~~ROUND " + rounds + " HAS ENDED!~~~~~~~~~~~~~~~~~~~~~");

            for (int i = 0; i < player_count; i++) {
                System.out.println("Player #" + (i + 1) + ": " + player[i].candy + " pieces of candy.");
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            rounds++;
        }

        System.out.println("Game Over!".toUpperCase());
    }

    public static int RemainingPlayers(Player[] player, int player_count) {
        int result = 0;

        for (int i = 0; i < player_count; i++) {

            if (!player[i].ran_away) {
                result++;
            }

        }
        return result;
    }

    public static int GetNextPlayer(Player[] player, int player_count, int current_player) {

        boolean found_one = false;

        do {

            current_player++;

            if (current_player >= player_count) {
                current_player = 0;
            }

            if (!player[current_player].ran_away) {
                found_one = true;
            }


        } while (!found_one);

        return current_player;
    }

}