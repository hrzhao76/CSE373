package seamcarving.seamfinding;

import seamcarving.Picture;
import seamcarving.SeamCarver;
import seamcarving.energy.EnergyFunction;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder_Haoran implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        List<Integer> result = new ArrayList<>();
        double[][] EnergyCost = new double[picture.width()][picture.height()];

        for (int y=0; y<picture.height(); y++) {
            EnergyCost[0][y] = f.apply(picture, 0, y);
        }

        for (int x=1; x< picture.width(); x++){
            for (int y=0; y<picture.height(); y++) {
                EnergyCost[x][y] = LowestPredecessor(EnergyCost[x-1], y) + f.apply(picture, x, y);
            }
        }

        int min_y = min_idx(EnergyCost[picture.width()-1]);
        result.add(min_y);
        for(int x= picture.width()-1; x>0; x-- ){
            min_y = LowestPredecessorIndex(EnergyCost[x-1], min_y);
            result.add(min_y);
        }

        Collections.reverse(result);
        return result;
    }

    public double LowestPredecessor(double[] EnergyColumn, int y){
        if(y==0)
            return Math.min(EnergyColumn[y], EnergyColumn[y + 1]);
        else if(y== EnergyColumn.length - 1)
            return Math.min(EnergyColumn[y], EnergyColumn[y - 1]);
        else{
            double tmp_min = Math.min(EnergyColumn[y], EnergyColumn[y - 1]);
            return Math.min(tmp_min, EnergyColumn[y + 1]);
        }
    }

    public int LowestPredecessorIndex(double[] EnergyColumn, int y){
        if(y==0)
            return EnergyColumn[y] < EnergyColumn[y + 1]? y: y+1;
        else if(y== EnergyColumn.length - 1)
            return EnergyColumn[y] < EnergyColumn[y - 1]? y: y-1;
        else{
            int tmp_min_idx = EnergyColumn[y] < EnergyColumn[y - 1]? y: y-1;
            return  EnergyColumn[tmp_min_idx] < EnergyColumn[y + 1]? tmp_min_idx: y+1;
        }
    }

    public int min_idx(double [] array) {
        double min = array[0];
        int idx = 0;

        for(int i=0; i<array.length; i++ ) {
            if(array[i]<min) {
                min = array[i];
                idx = i;
            }
        }
        return idx;
    }
}
