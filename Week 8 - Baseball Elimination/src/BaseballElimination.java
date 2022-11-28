import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BaseballElimination {

    // Global Variables
    private final int numberOfTeams;
    private final Team[] teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        //Open file
        In in = new In(filename);
        teams = new Team[numberOfTeams = in.readInt()];

        // register each teams name, wins, loses, remaining
        for (int i = 0; !in.isEmpty(); i++) {
            teams[i] = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt(), numberOfTeams);

            // register games left for team i against every other team
            for (int j = 0; j < numberOfTeams; j++) teams[i].setAgainst(j, in.readInt());
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
        return teams[indexOf(team)].wins;
    }

    // number of losses for given team
    public int losses(String team) {
        return teams[indexOf(team)].losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return teams[indexOf(team)].remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return teams[indexOf(team1)].against[indexOf(team2)];
    }

    // retrieve index i of team with string s
    private int indexOf(String team1) {
        for (int i = 0; i < numberOfTeams; i++) if (team1.equals(teams[i].teamName)) return i;
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

    // encapsulate each team with its attributes: name, wins, losses, games remaining, games remaining against
    private class Team {
        private final String teamName;
        private final int numberOfTeams;
        private int wins, losses, remaining;
        private int[] against;

        public Team(String teamName, int wins, int losses, int remaining, int numberOfTeams) {
            this.teamName = teamName;
            this.wins = wins;
            this.losses = losses;
            this.remaining = remaining;
            this.numberOfTeams = numberOfTeams;
            against = new int[numberOfTeams];
        }

        public void setAgainst(int i, int gamesRemaining) {
            against[i] = gamesRemaining;
        }
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