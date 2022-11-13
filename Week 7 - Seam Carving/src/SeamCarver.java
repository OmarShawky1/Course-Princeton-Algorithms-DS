import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    // Global Variables
    private final Picture p;
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private double[][] energyMatrix;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix();
    }

    // current picture
    public Picture picture() {
        return p;
    }

    // width of current picture
    public int width() {
        return p.width();
    }

    // height of current picture
    public int height() {
        return p.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return energyMatrix[y][x];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return (new int[0]); //TODO
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return (new int[0]); //TODO
    }

    // calculate Energy Matrix
    private void energyMatrix() {
        double dx, dy, rR, rG, rB, cR, cG, cB;
        energyMatrix = new double[p.width()][p.height()];

        // width is column is x; height is row is y
        for (int row = 0; row < p.height(); row++) {
            energyMatrix[row][0] = 1000;
            energyMatrix[row][p.width() - 1] = 1000;
        }

        for (int col = 1; col < p.height() - 1; col++) {
            // most left pixel
            energyMatrix[0][col] = 1000;
            for (int row = 1; row < p.width() - 1; row++) {
                // compute energy of pixel
                Color rightPixel = p.get(col, row + 1);
                Color leftPixel = p.get(col, row - 1);

                rR = colorGrad(rightPixel, leftPixel, RED);
                rG = colorGrad(rightPixel, leftPixel, GREEN);
                rB = colorGrad(rightPixel, leftPixel, BLUE);
                dx = rR * rR + rG * rG + rB * rB;


                Color lowerPixel = p.get(col + 1, row);
                Color upperPixel = p.get(col - 1, row);
                cR = colorGrad(lowerPixel, upperPixel, RED);
                cG = colorGrad(lowerPixel, upperPixel, GREEN);
                cB = colorGrad(lowerPixel, upperPixel, BLUE);
                dy = cR*cR + cG*cG + cB*cB;

                energyMatrix[row][col] = Math.sqrt(dx + dy);
            }
            // most left pixel
            energyMatrix[p.width() - 1][col] = 1000;
        }
    }

    // Calculate color difference between two single pixels
    private static int colorGrad(Color highPixel, Color lowPixel, int option) {
        // High pixel is either right or down; Low pixel is either left or up

        // TODO: use this instead of creating Color object
        // https://stackoverflow.com/questions/2615522/java-bufferedimage-getting-red-green-and-blue-individually

        if (option == RED) return (highPixel.getRed() - lowPixel.getRed());
        if (option == GREEN) return highPixel.getGreen() - lowPixel.getGreen();
        return highPixel.getBlue() - lowPixel.getBlue();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        //TODO
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        //TODO
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}