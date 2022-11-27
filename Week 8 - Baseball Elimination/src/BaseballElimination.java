import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BaseballElimination {

    // Global Variables
    private final int[][] against; // games left between team i and team j
    private final int[] wins, losses;
    private final int[] remaining; // games left for team x in general
    private final String[] teams;
    private final int numberOfTeams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        //Open file
        In in = new In(filename);

        numberOfTeams = in.readInt(); // read number of teams

        against = new int[numberOfTeams][numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        teams = new String[numberOfTeams];

        // register each teams name, wins, loses, remaining
        for (int i = 0; !in.isEmpty(); i++) {
            teams[i] = in.readString();
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            // register games left for team i against every other team
            for (int j = 0; j < numberOfTeams; j++) against[i][j] = in.readInt();
        }
        
        in.close();

        //TODO: Construct Flow Network graph
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }


    // all teams
    public Iterable<String> teams() {
        return new LinkedList<>(); //TODO
    }


    // number of wins for given team
    public int wins(String team) {
        return wins[indexOf(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return losses[indexOf(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[indexOf(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return against[indexOf(team1)][indexOf(team2)];
    }

    private int indexOf(String team1) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (team1.equals(teams[i])) return i;
        }
        return -1; // team not found
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
