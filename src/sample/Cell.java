package sample;

public class Cell {

    int value;
    int cost;
    int finalCost;
    int x, y;

    public Cell(int x, int y, int cost) {
        this.finalCost = cost;
        this.value = 0;
        this.cost = cost;
        this.x = x;
        this.y = y;
    }

    public void displayCell() {
        System.out.println("(" + this.x + ", " + this.y + ") -> cost: " + this.finalCost + ", " + this.cost + " - value: " + this.value);
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
