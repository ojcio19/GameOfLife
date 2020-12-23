package agh.cs.project.enums;

public enum MoveDirection {
    FORWARD(0), FORWARD_RIGHT(1),RIGHT(2), BACKWARD_RIGHT(3), BACKWARD(4), BACKWARD_LEFT(5), LEFT(6), FORWARD_LEFT(7);

    public final int fID;
    MoveDirection(int i) {
        this.fID = i;
    }
}
