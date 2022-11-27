import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BaseballElimination {

    // Global Variables
    private final int[][] gamesLeft; // games left between team i and team j
    private final int[] wins, loses;
    private final int[] remaining; // games left for team x in general
    private final String[] teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        //TODO

        //Open file
        In in = new In(filename);

        int size = in.readInt(); // read number of teams

        gamesLeft = new int[size][size];
        wins = new int[size];
        loses = new int[size];
        remaining = new int[size];
        teams = new String[size];

        // register each teams name, wins, loses, remaining
        for (int i = 0; !in.isEmpty(); i++) {
            teams[i] = in.readString();
            wins[i] = in.readInt();
            loses[i] = in.readInt();
            remaining[i] = in.readInt();

            // register games left for team i against every other team
            for (int j = 0; j < size; j++) gamesLeft[i][j] = in.readInt();
        }
        in.close();
    }

    // number of teams
    public int numberOfTeams() {
        return 0; //TODO
    }


    // all teams
    public Iterable<String> teams() {
        return new LinkedList<>(); //TODO
    }


    // number of wins for given team
    public int wins(String team) {
        return 0; //TODO
    }

    // number of losses for given team
    public int losses(String team) {
        return 0; //TODO
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return 0; //TODO
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return 0; //TODO
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false; //TODO
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return new LinkedList<>(); //TODO
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
