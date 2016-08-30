package com.ai;

import java.util.Scanner;
import java.util.Stack;

public class EightPuzzle {
    //private static final int ROUND_RAND = 10000;
    private static final int GOAL[] = {1,2,3,4,5,6,7,8,0};
    private static boolean check = false;
    private static Stack<int[]> stack = new Stack<>();
    private static String flag = "unknown";
    static int count;

    public static void main(String[] args) {
        int puzzle[] ;
        int roundRand;
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter 0 to insert puzzle or 1 to random.");
        if(Sc.nextInt() == 0)
            puzzle = insertPuzzle();
        else {
            System.out.println("Enter number of times to random.");
            roundRand = Sc.nextInt();
            puzzle = randomPuzzle(GOAL,roundRand);
        }
        //puzzle = new int[]{1, 0, 2, 4, 5, 3, 7, 8, 6};
        System.out.println(puzzle[0]+" "+puzzle[1]+" "+puzzle[2]+" "+puzzle[3]+" "+puzzle[4]+" "+puzzle[5]+" "+puzzle[6]+" "+puzzle[7]+" "+puzzle[8]);
        long tStart = System.currentTimeMillis();
        ids(puzzle);
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        while (!stack.empty())
            print(stack.pop());
        System.out.println(elapsedSeconds);
    }

    public static void ids(int[] puzzle){
        int depth = 0;
        int level = 0;
        while(!check){
            System.out.println("Depth = "+depth);
            dls(depth,level,puzzle);
            depth++;
        }
    }

    public static void dls(int depth,int level,int[] puzzle){
        if(check) {
            stack.push(puzzle);
            return;
        }
        //System.out.println("Depth = "+level);
        if(isGoal(puzzle)) {
            check = true;
            stack.push(puzzle);
            return;
        }
        if(depth <= level){
            return;
        }
        int index = -1;
        String tempFlag = flag;
        for(int i = 0 ; i < puzzle.length ; i++)
            if(puzzle[i] == 0) {
                index = i;
                break;
            }
        if(canUp(index) && !flag.equals("up")) {
            flag = "down";
            dls(depth, level + 1, up(index, puzzle));
        }
        if(check){
            stack.push(puzzle);
            return;
        }
        flag = tempFlag;
        if(canDown(index,puzzle) && !flag.equals("down")) {
            flag = "up";
            dls(depth, level + 1, down(index, puzzle));
        }
        if(check){
            stack.push(puzzle);
            return;
        }
        flag = tempFlag;
        if(canLeft(index) && !flag.equals("left")) {
            flag = "right";
            dls(depth, level + 1, left(index, puzzle));
        }
        if(check){
            stack.push(puzzle);
            return;
        }
        flag = tempFlag;
        if(canRight(index) && !flag.equals("right")) {
            flag = "left";
            dls(depth, level + 1, right(index, puzzle));
        }
        if(check){
            stack.push(puzzle);
            return;
        }
        flag = tempFlag;
    }

    public static boolean isGoal(int[] puzzle){
        //print(puzzle);
        for(int i = 0 ; i < GOAL.length ; i++){
            if(puzzle[i] != GOAL[i])
                return false;
        }
        return true;
    }

    public static  boolean canLeft(int index){
        return !(index ==0 || index == 3 || index ==6);
    }
    public static  boolean canRight(int index){
        return !(index == 2 || index == 5 || index == 8);
    }
    public static  boolean canUp(int index){
        return index - 3 >= 0;
    }
    public static  boolean canDown(int index,int[] puzzle){
        return index + 3 < puzzle.length;
    }

    public static int[] left(int indexOfEmpty,int[] puzzle){
        if(indexOfEmpty - 1 < 0)
            return puzzle;
        int newPuzzle[] = new int[puzzle.length];
        System.arraycopy(puzzle,0,newPuzzle,0,puzzle.length);
        int temp;
        temp = newPuzzle[indexOfEmpty];
        newPuzzle[indexOfEmpty] = newPuzzle[indexOfEmpty - 1];
        newPuzzle[indexOfEmpty - 1] = temp;
        return newPuzzle;
    }
    public static int[] right(int indexOfEmpty,int[] puzzle){
        if(indexOfEmpty + 1 >= puzzle.length)
            return puzzle;
        int newPuzzle[] = new int[puzzle.length];
        System.arraycopy(puzzle,0,newPuzzle,0,puzzle.length);
        int temp;
        temp = newPuzzle[indexOfEmpty];
        newPuzzle[indexOfEmpty] = newPuzzle[indexOfEmpty + 1];
        newPuzzle[indexOfEmpty + 1] = temp;
        return newPuzzle;
    }
    public static int[] up(int indexOfEmpty,int[] puzzle){
        if(indexOfEmpty - 3 < 0)
            return puzzle;
        int newPuzzle[] = new int[puzzle.length];
        System.arraycopy(puzzle,0,newPuzzle,0,puzzle.length);
        int temp;
        temp = newPuzzle[indexOfEmpty];
        newPuzzle[indexOfEmpty] = newPuzzle[indexOfEmpty - 3];
        newPuzzle[indexOfEmpty - 3] = temp;
        return newPuzzle;
    }
    public static int[] down(int indexOfEmpty,int[] puzzle){
        if(indexOfEmpty + 3 >= puzzle.length)
            return puzzle;
        int newPuzzle[] = new int[puzzle.length];
        System.arraycopy(puzzle,0,newPuzzle,0,puzzle.length);
        int temp;
        temp = newPuzzle[indexOfEmpty];
        newPuzzle[indexOfEmpty] = newPuzzle[indexOfEmpty + 3];
        newPuzzle[indexOfEmpty + 3] = temp;
        return newPuzzle;
    }

    public static int[] randomPuzzle(int[] goal,int roundRand){
        int random;
        int puzzle[] = new int[9];
        for(int i = 0 ; i < puzzle.length ; i++)
            puzzle[i] = goal[i];
        for(int j = 0 ; j < roundRand ; j++)
            for(int i = 0 ; i < puzzle.length ; i++){
                if(puzzle[i] == 0){
                    if(i==0){
                        random = 1+ (int)(Math.random()*2);
                        if(random == 1)
                            puzzle = right(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==1){
                        random = 1+ (int)(Math.random()*3);
                        if(random == 1)
                            puzzle = left(i,puzzle);
                        else if(random==2)
                            puzzle = right(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==2){
                        random = 1+ (int)(Math.random()*2);
                        if(random == 1)
                            puzzle = left(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==3){
                        random = 1+ (int)(Math.random()*3);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else if(random==2)
                            puzzle = right(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==4){
                        random = 1+ (int)(Math.random()*4);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else if(random==2)
                            puzzle = left(i,puzzle);
                        else if(random==3)
                            puzzle = right(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==5){
                        random = 1+ (int)(Math.random()*3);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else if(random==2)
                            puzzle = left(i,puzzle);
                        else
                            puzzle = down(i,puzzle);
                    }else if(i==6){
                        random = 1+ (int)(Math.random()*2);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else
                            puzzle = right(i,puzzle);
                    }else if(i==7){
                        random = 1+ (int)(Math.random()*3);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else if(random==2)
                            puzzle = left(i,puzzle);
                        else
                            puzzle = right(i,puzzle);
                    }else{
                        random = 1+ (int)(Math.random()*2);
                        if(random == 1)
                            puzzle = up(i,puzzle);
                        else
                            puzzle = right(i,puzzle);
                    }
                }
            }
        return puzzle;
    }

    public static int[] insertPuzzle(){
        int puzzle[] = new int[GOAL.length];
        System.out.println("Please enter puzzle 9 numbers(0-8) (place empty with 0)");
        System.out.println("example : 123456780");
        Scanner sc = new Scanner(System.in);
        String puzzleSt = sc.next();
        System.out.println();
        char[] charArray = puzzleSt.toCharArray();
        for(int i = 0 ; i < puzzle.length ; i++)
            puzzle[i] = Character.getNumericValue(charArray[i]);
        return puzzle;
    }

    public static String[] itos(int[] array){
        String result[] = new String[array.length];
        for(int i = 0 ; i < array.length ; i++)
            if(array[i] == 0)
                result[i] = " ";
            else
                result[i] = Integer.toString(array[i]);
        return result;
    }

    public static void print(int[] puzzle){
//        System.out.println("Please enter to continue...");
//        Scanner sc = new Scanner(System.in);
//        sc.nextLine();
        String puzzleString[] = itos(puzzle);
        if(count!=0)
            System.out.println("Count = "+count);
        System.out.println("  -   -   -  ");
        System.out.println("| " + puzzleString[0] + " | " + puzzleString[1] + " | " + puzzleString[2] + " |");
        System.out.println("| -   -   - |");
        System.out.println("| " + puzzleString[3] + " | " + puzzleString[4] + " | " + puzzleString[5] + " |");
        System.out.println("| -   -   - |");
        System.out.println("| " + puzzleString[6] + " | " + puzzleString[7] + " | " + puzzleString[8] + " |");
        System.out.println("  -   -   -  \n");
        count++;
    }
}
