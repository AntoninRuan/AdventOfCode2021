package fr.antoninruan.advendofcode.util;

public class BoundingBox {

    private final Point2D p0;
    private final Point2D p1;

    public BoundingBox(Point2D bottomLeft, Point2D topRight) {
        this.p0 = bottomLeft;
        this.p1 = topRight;
    }

    public int getMinX() {
        return Math.min(p0.getX(), p1.getX());
    }

    public int getMaxX() {
        return Math.max(p0.getX(), p1.getX());
    }

    public int getMinY() {
        return Math.min(p0.getY(), p1.getY());
    }

    public int getMaxY() {
        return Math.max(p0.getY(), p1.getY());
    }

    public boolean isIn(Point2D p) {
        return this.getMinX() <= p.getX() && p.getX() <= this.getMaxX() && this.getMinY() <= p.getY() && p.getY() <= this.getMaxY();
    }

    public Point2D compare(Point2D p) {
        int x, y;
        if (this.getMaxX() < p.getX())
            x = 1;
        else if (p.getX() < this.getMinX())
            x = -1;
        else
            x = 0;

        if(this.getMaxY() < p.getY())
            y = 1;
        else if (p.getY() < this.getMinY())
            y = -1;
        else
            y = 0;

        return new Point2D(x, y);
    }

}
