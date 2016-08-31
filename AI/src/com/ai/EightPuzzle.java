package com.ai;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.Stack;

public class EightPuzzle {
    private static final int GOAL[] = {1,2,3,4,5,6,7,8,0};
    private static boolean check = false; //check that solution meet goal?
    private static Stack<int[]> stack = new Stack<>(); //keep solution from root to goal.
    private static String flag = "unknown"; //flag check that leaves is duplicate any parent.
    private static int count; //count moves until goal.

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
        long tStart = System.currentTimeMillis(); //for check time.
        ids(puzzle);
        long tEnd = System.currentTimeMillis(); //for check time.
        long tDelta = tEnd - tStart; //for check time.
        double elapsedSeconds = tDelta / 1000.0; //for check time.
        while (!stack.empty()) //print solution.
            print(stack.pop());
        System.out.println(elapsedSeconds + " sec."); //for check time.
    }

    //Iterative Deepening Search
    public static void ids(int[] puzzle){
        int depth = 0;
        int level = 0;
        while(!check){
            dls(depth,level,puzzle);
            depth++;
        }
    }

    //Depth Limited Search
    public static void dls(int depth,int level,int[] puzzle){
        if(check) { //if puzzle meet goal push that state in stack.
            stack.push(puzzle);
            return;
        }
        if(isGoal(puzzle)) {//if puzzle meet goal change check to true and push that state in stack.
            check = true;
            stack.push(puzzle);
            return;
        }
        if(depth <= level){ //if level >= depth exit function go out to + max depth
            return;
        }
        int index = -1; //initial index of " "
        String tempFlag = flag; //set flag
        for(int i = 0 ; i < puzzle.length ; i++) //find index of " "
            if(puzzle[i] == 0) {
                index = i;
                break;
            }
        if(canUp(index) && !flag.equals("up")) { //check that " " can up and flag isn't equals "up".
            flag = "down"; //change flag to flag for next level.
            dls(depth, level + 1, up(index, puzzle));
        }
        if(check){ //if puzzle meet goal push that state in stack.
            stack.push(puzzle);
            return;
        }
        flag = tempFlag; //set flag to flag for this level depth
        if(canDown(index,puzzle) && !flag.equals("down")) { //check that " " can up and flag isn't equals "down".
            flag = "up"; //change flag to flag for next level.
            dls(depth, level + 1, down(index, puzzle));
        }
        if(check){ //if puzzle meet goal push that state in stack.
            stack.push(puzzle);
            return;
        }
        flag = tempFlag; //set flag to flag for this level depth
        if(canLeft(index) && !flag.equals("left")) { //check that " " can up and flag isn't equals "left".
            flag = "right"; //change flag to flag for next level.
            dls(depth, level + 1, left(index, puzzle));
        }
        if(check){ //if puzzle meet goal push that state in stack.
            stack.push(puzzle);
            return;
        }
        flag = tempFlag; //set flag to flag for this level depth
        if(canRight(index) && !flag.equals("right")) { //check that " " can up and flag isn't equals "right".
            flag = "left"; //change flag to flag for next level.
            dls(depth, level + 1, right(index, puzzle));
        }
        if(check){ //if puzzle meet goal push that state in stack.
            stack.push(puzzle);
            return;
        }
        flag = tempFlag; //set flag to flag for this level depth
    }

    //check puzzle meet goal?
    public static boolean isGoal(int[] puzzle){
        for(int i = 0 ; i < GOAL.length ; i++){
            if(puzzle[i] != GOAL[i])
                return false;
        }
        return true;
    }
    //check " " direction which can go?
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

    //move " " in puzzle go left.
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
    //move " " in puzzle go right.
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
    //move " " in puzzle up.
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
    //move " "  in puzzle down.
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

    //random puzzle mode.
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

    //insert puzzle mode.
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

    //convert interger[] to string[] and replace 0 with " "
    public static String[] itos(int[] array){
        String result[] = new String[array.length];
        for(int i = 0 ; i < array.length ; i++)
            if(array[i] == 0)
                result[i] = " ";
            else
                result[i] = Integer.toString(array[i]);
        return result;
    }

    //print puzzle at current state.
    public static void print(int[] puzzle){
        //enable this to press enter to show step to step.
//        try {
//            System.out.println("Please enter to continue...");
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            Robot robot = new Robot();
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_SEMICOLON);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//            robot.keyRelease(KeyEvent.VK_SEMICOLON);
//            robot.delay(100);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
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
