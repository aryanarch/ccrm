package edu.ccrm.domain;

/**
 * Enum representing grades with associated grade points.
 */
public enum Grade {
    S(10),
    A(9),
    B(8),
    C(7),
    D(6),
    E(5),
    F(0);

    private final int gradePoint;

    Grade(int gradePoint) {
        this.gradePoint = gradePoint;
    }

    public int getGradePoint() {
        return gradePoint;
    }

    public static Grade fromMarks(int marks) {
        if (marks >= 90) return S;
        else if (marks >= 80) return A;
        else if (marks >= 70) return B;
        else if (marks >= 60) return C;
        else if (marks >= 50) return D;
        else if (marks >= 40) return E;
        else return F;
    }
}
