package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcolor.Attribute;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;
import java.util.Random;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;

    private static final Telemetry telemetry = new Telemetry();

    public static void main(String[] args) {
        telemetry.trackEvent("ApplicationStarted", "Technology", "Java");
        System.out.println(colorize("                                     |__", MAGENTA_TEXT()));
        System.out.println(colorize("                                     |\\/", MAGENTA_TEXT()));
        System.out.println(colorize("                                     ---", MAGENTA_TEXT()));
        System.out.println(colorize("                                     / | [", MAGENTA_TEXT()));
        System.out.println(colorize("                              !      | |||", MAGENTA_TEXT()));
        System.out.println(colorize("                            _/|     _/|-++'", MAGENTA_TEXT()));
        System.out.println(colorize("                        +  +--|    |--|--|_ |-", MAGENTA_TEXT()));
        System.out.println(colorize("                     { /|__|  |/\\__|  |--- |||__/", MAGENTA_TEXT()));
        System.out.println(colorize("                    +---------------___[}-_===_.'____                 /\\", MAGENTA_TEXT()));
        System.out.println(colorize("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _", MAGENTA_TEXT()));
        System.out.println(colorize(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7", MAGENTA_TEXT()));
        System.out.println(colorize("|                        Welcome to Battleship                         BB-61/", MAGENTA_TEXT()));
        System.out.println(colorize(" \\_________________________________________________________________________|", MAGENTA_TEXT()));
        System.out.println("");

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\033[2J\033[;H");
        System.out.println(colorize("                  __",BLUE_TEXT()));
        System.out.println(colorize("                 /  \\",BLUE_TEXT()));
        System.out.println(colorize("           .-.  |    |",BLUE_TEXT()));
        System.out.println(colorize("   *    _.-'  \\  \\__/",BLUE_TEXT()));
        System.out.println(colorize("    \\.-'       \\",BLUE_TEXT()));
        System.out.println(colorize("   /          _/",BLUE_TEXT()));
        System.out.println(colorize("  |      _  /\" \"",BLUE_TEXT()));
        System.out.println(colorize("  |     /_\'",BLUE_TEXT()));
        System.out.println(colorize("   \\    \\_/",BLUE_TEXT()));
        System.out.println(colorize("    \" \"\" \"\" \"\" \"",BLUE_TEXT()));

        do {
            System.out.println("");
            System.out.println("Player, it's your turn");
            System.out.println("Enter coordinates for your shot :");
            Position position = parsePosition(scanner.next());
            boolean isHit = GameController.checkIsHit(enemyFleet, position);

            if (isHit) {
                printHit(GREEN_TEXT());
            }
            System.out.println(isHit ? "Yeah ! Nice hit !" : "Miss");

            for (Ship ship : enemyFleet) {
                if (ship.checkSunk()) {
                    System.out.println(ship.getName()+" is sunk");
                } else {
                    System.out.println(ship.getName()+" is afloat");
                }
            }
           
            telemetry.trackEvent("Player_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());

            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);
            System.out.println("");
            System.out.println(String.format(colorize("Computer shoot in %s%s and %s",YELLOW_TEXT()), position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss"));
            telemetry.trackEvent("Computer_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());
            if (isHit) {
                printHit(RED_TEXT());
            }

            for (Ship ship : myFleet) {
                if (ship.checkSunk()) {
                    System.out.println(ship.getName()+" is sunk");
                }
            }

            for (Ship ship : myFleet) {
                if (! ship.checkSunk()) {
                    System.out.println(ship.getName()+" is afloat");
                }
            }

        } while (true);
    }

    private static void beep() {
        System.out.print("\007");
    }

    public static void printHit(Attribute color){
        beep();

        System.out.println(colorize("                \\         .  ./", color));
        System.out.println(colorize("              \\      .:\" \";'.:..\" \"   /", color));
        System.out.println(colorize("                  (M^^.^~~:.'\" \").", color));
        System.out.println(colorize("            -   (/  .    . . \\ \\)  -", color));
        System.out.println(colorize("               ((| :. ~ ^  :. .|))", color));
        System.out.println(colorize("            -   (\\- |  \\ /  |  /)  -", color));
        System.out.println(colorize("                 -\\  \\     /  /-", color));
        System.out.println(colorize("                   \\  \\   /  /", color));
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        System.out.println("Do you want to place ships manually? (Y/N)");
        String positionInput = scanner.next();

        while (!positionInput.equals("Y") && !positionInput.equals("N")){
            System.out.println("Please add correct input");
            positionInput = scanner.next();
        }

        if (positionInput.equals("Y")){
            manualShipPlacement(myFleet);
        } else {
            randomizeShipPlacement();
        }
    }

    private static void manualShipPlacement(List<Ship> myFleet){
        Scanner scanner = new Scanner(System.in);

        for (Ship ship : myFleet) {
            System.out.println("");
            System.out.println(String.format(colorize("Please enter the positions for the %s (size: %s)",BRIGHT_BLUE_TEXT()), ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                System.out.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
                telemetry.trackEvent("Player_PlaceShipPosition", "Position", positionInput, "Ship", ship.getName(), "PositionInShip", Integer.valueOf(i).toString());
            }
        }
    }

    private static void randomizeShipPlacement() {
        System.out.println("Automatically positioning your fleet (Game board has size from A to H and 1 to 8):");

        Random random = new Random();
        for (Ship ship : myFleet) {
            boolean placed = false;

            boolean isHorizontal = false;
            while (!placed) {
                // Randomly decide if the ship will be placed horizontally or vertically
                isHorizontal = random.nextBoolean();

                // Randomly select a starting point on the board
                char startColumn = (char) ('A' + random.nextInt(8));
                int startRow = 1 + random.nextInt(8);

                // Check if the ship can be placed without going out of bounds
                if (isHorizontal) {
                    if (startColumn + ship.getSize() - 1 <= 'H') {
                        // Place the ship horizontally
                        for (int i = 0; i < ship.getSize(); i++) {
                            String position = "" + (char) (startColumn + i) + startRow;
                            ship.addPosition(position);
                            telemetry.trackEvent("Player_PlaceShipPosition", "Position", position, "Ship", ship.getName(), "PositionInShip", Integer.valueOf(i + 1).toString());
                        }
                        placed = true;
                    }
                } else {
                    if (startRow + ship.getSize() - 1 <= 8) {
                        // Place the ship vertically
                        for (int i = 0; i < ship.getSize(); i++) {
                            String position = "" + startColumn + (startRow + i);
                            ship.addPosition(position);
                            telemetry.trackEvent("Player_PlaceShipPosition", "Position", position, "Ship", ship.getName(), "PositionInShip", Integer.valueOf(i + 1).toString());
                        }
                        placed = true;
                    }
                }
            }
            System.out.println(String.format("Placed the %s at starting position %s %s", ship.getName(), ship.getPositions().toString(), (isHorizontal ? "horizontally" : "vertically")));
        }
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
