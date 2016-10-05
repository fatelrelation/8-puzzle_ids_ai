package com.ai;
public class EightQueen {
    private final static int MAXTEMP = 1000000; //for Schedule
    public static void main(String args[]){
        int table[][] = initTable();
        printTable(table);
        System.out.println("no. Q Attack " + checkNumQAtk(table));
        printTable(simulatedAnnealing(table));
    }
    //Simulated Annealing Search
    public static int[][] simulatedAnnealing(int table[][]){
        int current[][];
        int next[][];
        double temp;
        int deltaE;
        current = copyArray(table);
        for(double t = 1.0 ; t <Integer.MAX_VALUE ; t *= 1.001){ //t(time) will slowly increasing cause temp will slowly decreasing in next line.
            temp = MAXTEMP / t; //Schedule
            if(temp < 0.001) //check temp for return current.
                break;
            if(checkNumQAtk(current) == 0) //if find the goal will return current table.
                break;
            next = findRandomSuccessor(current);
            deltaE = checkNumQAtk(current) - checkNumQAtk(next); //Q attack another Q less mean it higher than.
            if(deltaE > 0) //that mean Next is higher than current. (number of Q attack another Q is value to check)
                current = copyArray(next);
            else{
                if(Math.random() < Math.pow(Math.E,deltaE/temp)) //go with probability e^dE/temp.
                    current = copyArray(next);
            }
            System.out.println("no. Q Attack " + checkNumQAtk(current));
        }
        return current;
    }
    //find successor of table (problem) randomly
    public static int[][] findRandomSuccessor(int[][] table){
        int[][] successor;
        successor = copyArray(table);
        int randomQueen = 1 + (int) (Math.random() * 8);
        int count = 0;
        int x = 0;
        int y = 0;
        for(int i = 0 ; i < 8 ; i++){ //find index of Q that randomly choose.
            for(int j = 0 ; j < 8 ; j++){
                if(successor[i][j] == 1)
                    count++;
                if(count == randomQueen) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if(count == randomQueen)
                break;
        }
        int randomSuccessor;
        int modrandomSuccessor;
        while(true) { ///find new location for Q.
            randomSuccessor = (int) (Math.random() * 64);
            modrandomSuccessor = (randomSuccessor % 8);
            randomSuccessor = (randomSuccessor / 8);
            if(successor[randomSuccessor][modrandomSuccessor] == 0) {
                successor[y][x] = 0;
                successor[randomSuccessor][modrandomSuccessor] = 1;
                break;
            }
        }
        //printTable(successor);
        return successor;
    }

    //initial table (problem) 8-Queen
    public static int[][] initTable(){
        int table[][] = new int[8][8];
        int random;
        int modrandom;
        int count = 0;
        while(count < 8){
            random = (int)(Math.random() * 64);
            modrandom = (random % 8);
            random = (random / 8);
            if(table[random][modrandom] == 0) {
                table[random][modrandom] = 1;
                count++;
            }
        }
        return table;
    }

    //find number of Q that attack another Q
    public static int checkNumQAtk(int table[][]){
        int count = 0;
        int x;
        int y;
        boolean counted = false;
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0 ; j < 8 ; j++)
                if(table[i][j] == 1){
                    y = i - 1;
                    x = j - 1;
                    while(y > -1 && y < 8 && x > -1 && x < 8 && !counted){ //y - 1 , x - 1   (north-west)
                        if(table[y][x] == 1){
                            count++;
                            counted = true;
                        }
                        y--;x--;
                    }
                    y = i - 1;
                    x = j + 1;
                    while(y > -1 && y < 8 && x > -1 && x < 8 && !counted){ //y - 1 , x + 1   (north-east)
                        if(table[y][x] == 1){
                            count++;
                            counted = true;
                        }
                        y--;x++;
                    }
                    y = i + 1;
                    x = j - 1;
                    while(y > -1 && y < 8 && x > -1 && x < 8 && !counted){ //y + 1 , x - 1   (south-west)
                        if(table[y][x] == 1){
                            count++;
                            counted = true;
                        }
                        y++;x--;
                    }
                    y = i + 1;
                    x = j + 1;
                    while(y > -1 && y < 8 && x > -1 && x < 8 && !counted){ //y + 1 , x + 1   (south-east)
                        if(table[y][x] == 1){
                            count++;
                            counted = true;
                        }
                        y++;x++;
                    }
                    y = 0;
                    x = j;
                    while(y < 8){                                                            //(north-south)
                        if(table[y][x] == 1 && y != i && !counted){
                            count++;
                            counted = true;
                        }
                        y++;
                    }
                    y = i;
                    x = 0;
                    while(x < 8){                                                            //(west-east)
                        if(table[y][x] == 1 && x != j && !counted){
                            counted = true;
                            count++;
                        }
                        x++;
                    }
                    counted = false;
                }
        return count;
    }
    //print table (problem) 8-Queen
    public static void printTable(int[][] table){
        for(int i = 0 ; i < 8 ; i++) {
            System.out.println("  -   -   -   -   -   -   -   -");
            System.out.print("|");
            for (int j = 0; j < 8; j++)
                if(table[i][j] == 1)
                    System.out.print(" Q |");
                else
                    System.out.print("   |");
            System.out.println();
        }
        System.out.println("  -   -   -   -   -   -   -   -");
    }
    //copy two-dimentional array of int
    public static int[][] copyArray(int[][] src){
        int des[][] = new int[src.length][src[0].length];
        for(int i = 0 ; i < src.length ; i++)
            System.arraycopy(src[i],0,des[i],0,src[i].length);
        return des;
    }
}
