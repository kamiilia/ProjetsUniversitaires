package androhack.serpent.classes;

/**
 * Created by yazid on 30/05/17.
 */

public class Coordonee {

    private int x;
    private int y;

    public Coordonee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordonee cord = (Coordonee) o;

        if (getX() != cord.getX()) return false;
        return getY() == cord.getY();

    }


}
