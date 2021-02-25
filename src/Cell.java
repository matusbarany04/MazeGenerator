public class Cell {
    public Cell before;
    public Cell after;
    public boolean visited;
    public char ch;
    public Point point;

    Cell(Point point,char ch){
        this.ch = ch;
        this.point = point;
        this.before = before;
        visited = false;
    }
}
