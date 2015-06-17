import java.io.Serializable;

/**
 * Created by william on 4/30/15.
 */
public class State implements Serializable {
    public String name;
    public double PointX;
    public double PointY;
    public State(String name) {
        this.name = name;
    }

    public State(String name,  double pointX, double pointY) {
        this.name = name;
        PointX = pointX;
        PointY = pointY;
    }

    public void setPoint(double pointX,double pointY){

        this.PointX = pointX;
        this.PointY = pointY;
    }






}
