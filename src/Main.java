import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        char ch_wall = '#';
        char ch_road = '.';

        int gridSize = 15;
        Stack<Point> stack = new Stack<>();
        Random random = new Random();

        int visitCount = 1;
        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
        for (int y = 0; y < gridSize; y++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int x = 0; x < gridSize; x++) {
                line.add(new Cell(new Point(x, y), ch_road));
                line.add(new Cell(new Point(x, y), ch_wall));
            }
            line.remove(line.size() - 1);
            grid.add(line);

            ArrayList<Cell> wall = new ArrayList<>();
            for (int x = 0; x < gridSize; x++) {
                wall.add(new Cell(new Point(x, y), ch_wall));
                wall.add(new Cell(new Point(x, y), ch_wall));
            }
            wall.remove(wall.size() - 1);
            grid.add(wall);
        }
        grid.remove(grid.size() - 1);


        Explorer explorer = new Explorer(new Point(0, 0), ch_road);
        explorer.visited = true;
        grid.get(0).set(0, explorer);


        while (visitCount < gridSize * gridSize) {
            ArrayList<Point> possibleMoves = new ArrayList<>();
            int explY = explorer.getPoint().y;
            int explX = explorer.getPoint().x;
            //up
            if (explY - 2 >= 0) {
                if (!grid.get(explY - 2).get(explX).visited) {
                    possibleMoves.add(new Point(explX, explY - 2));
                }
            }

            //down
            if (explY + 2 < gridSize + gridSize - 1) {
                if (!grid.get(explY + 2).get(explX).visited) {
                    possibleMoves.add(new Point(explX, explY + 2));
                }
            }
            //left
            if (explX - 2 >= 0) {
                if (!grid.get(explY).get(explX - 2).visited) {
                    possibleMoves.add(new Point(explX - 2, explY));
                }
            }
            //right
            if (explX + 2 < gridSize + gridSize - 1) {
                if (!grid.get(explY).get(explX + 2).visited) {
                    possibleMoves.add(new Point(explX + 2, explY));
                }
            }

            if (possibleMoves.size() > 0) {
                int move = random.nextInt(possibleMoves.size());
                Point moveP = possibleMoves.get(move);
                if (explX < moveP.x) {
                    grid.get(explY).get(explX + 1).ch = ch_road;
                } else if (explX > moveP.x) {
                    grid.get(explY).get(explX - 1).ch = ch_road;
                } else if (explY < moveP.y) {
                    grid.get(explY + 1).get(explX).ch = ch_road;
                } else if (explY > moveP.y) {
                    grid.get(explY - 1).get(explX).ch = ch_road;
                }


                explorer.move(moveP);
                grid.get(moveP.y).get(moveP.x).visited = true;
                stack.push(moveP);
                visitCount++;
            } else {
                Point point = stack.pop();
                explorer.move(point);
            }
        }

        render(grid, ch_road);
        try {
            createTextFile(grid, ch_road);
        }catch (IOException e){

        }

    }

    public static void render(ArrayList<ArrayList<Cell>> grid, char road) {
        System.out.print("# . ");
        for (int x = 0; x < grid.get(0).size(); x++) {
            System.out.print("# ");
        }
        System.out.println();
        for (int y = 0; y < grid.size(); y++) {
            System.out.print("# ");
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (!grid.get(y).get(x).visited) {
                    System.out.print(grid.get(y).get(x).ch + " ");
                } else {
                    System.out.print(road + " ");
                }
            }
            System.out.println("#");
        }
        for (int x = 0; x < grid.get(0).size(); x++) {
            System.out.print("# ");
        }
        System.out.print(". #");
        System.out.println();
    }

    public static void createTextFile(ArrayList<ArrayList<Cell>> grid, char road) throws IOException {
        PrintWriter writer = new PrintWriter("maze.txt", "UTF-8");
        writer.print("# . ");
        for (int x = 0; x < grid.get(0).size(); x++) {
            writer.print("# ");
        }
        writer.println();
        for (int y = 0; y < grid.size(); y++) {
            writer.print("# ");
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (!grid.get(y).get(x).visited) {
                    writer.print(grid.get(y).get(x).ch + " ");
                } else {
                    writer.print(road + " ");
                }
            }
            writer.println("#");
        }
        for (int x = 0; x < grid.get(0).size(); x++) {
            writer.print("# ");
        }
        writer.print(". #");
        writer.println();

        writer.close();
    }
}
