package seamcarving.seamfinding;

import seamcarving.Picture;
import seamcarving.SeamCarver;
import seamcarving.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 *
 * Implemented by Min.
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code

        // built 2d array of energies of all pixels
        double[][] picEnergy = new double[picture.width()][picture.height()];
        for (int i = 0; i < picEnergy[0].length; i++) {
            picEnergy[0][i] = f.apply(picture, 0, i);
        }
        for (int i = 1; i < picEnergy.length; i++) {
            for (int j = 0; j < picEnergy[i].length; j++) {
                //find min predecessor
                //calculate energy
                double minEnergy;
                double leftup;
                double leftmid = picEnergy[i-1][j];
                double leftdown;
                if (j == 0) {
                    leftdown = picEnergy[i-1][j+1];
                    minEnergy = Math.min(leftdown, leftmid);
                } else if (j == picEnergy[i].length - 1) {
                    leftup = picEnergy[i-1][j-1];
                    minEnergy = Math.min(leftup, leftmid);
                } else {
                    leftdown = picEnergy[i-1][j+1];
                    leftup = picEnergy[i-1][j-1];
                    double temp = Math.min(leftdown, leftup);
                    minEnergy = Math.min(temp,leftmid);
                }
                picEnergy[i][j] = minEnergy + f.apply(picture, i, j);
            }
        }

        // list of which pixels to remove
        List<Integer> result = new ArrayList<Integer>();
        double min = picEnergy[picEnergy.length-1][0];
        int yVal = 0;
        for (int i = 1; i < picEnergy[0].length; i++) {
            if (picEnergy[picEnergy.length-1][i] < min) {
                min = picEnergy[picEnergy.length - 1][i];
                yVal = i;
            }
        }
        result.add(yVal);

        int currY = yVal;
        for (int i = picEnergy.length-1; i > 0; i--) {
            double minEnergy;
            double leftup;
            double leftmid = picEnergy[i-1][currY];
            double leftdown;
            List<Double> neighbor = new ArrayList<Double>(3);
            int tmp_Y = currY;
            if (currY == 0) {
                leftdown = picEnergy[i-1][currY+1];
                if (leftdown < leftmid) currY++;
                neighbor.add(-0.12345);
                neighbor.add(leftmid);
                neighbor.add(leftdown);


            } else if (currY == picEnergy[0].length - 1) {
                leftup = picEnergy[i-1][currY-1];
                if (leftup < leftmid) currY--;
                neighbor.add(leftup);
                neighbor.add(leftmid);
                neighbor.add(-0.12345);

            } else {
                leftdown = picEnergy[i-1][currY+1];
                leftup = picEnergy[i-1][currY-1];
                if (leftdown <= leftup && leftdown <= leftmid) currY++;
                else if (leftup < leftdown && leftup < leftmid) currY--;

                neighbor.add(leftup);
                neighbor.add(leftmid);
                neighbor.add(leftdown);


            }
            if(i>picEnergy.length-5){

                System.out.printf("i=%d, min_y=%d ",i, tmp_Y);
                System.out.println(Arrays.toString(neighbor.toArray()));
            }
            result.add(currY);
        }


        Collections.reverse(result);

        return result;
    }
}
