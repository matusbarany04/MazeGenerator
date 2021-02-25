public class Explorer extends Cell {


    Explorer(Point point, char ch) {
        super(point,ch);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void move(Point newPos){
        this.point = newPos;
    }

}
