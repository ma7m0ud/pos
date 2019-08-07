package com.pos.mahmoud.pos.Helpers;

import android.util.SparseArray;

import com.pos.mahmoud.pos.R;

public class ErrorID {



    public static int getError(int num){
         SparseArray<Integer> errors=new SparseArray<>();

         errors.put( 0 , R.string._0);
        errors.put(103, R.string._103);
        errors.put( 130, R.string._130);
        errors.put( 178, R.string._178);
        errors.put( 158, R.string._158);
        errors.put( 161, R.string._161);
        errors.put(  191, R.string._191);
        errors.put(  194, R.string._194);
        errors.put(  196, R.string._196);
        errors.put(  201, R.string._201);
        errors.put(  205, R.string._205);
        errors.put(  251, R.string._251);
        errors.put(  281, R.string._281);
        errors.put(  338, R.string._338);
        errors.put( 355, R.string._355);
        errors.put(375, R.string._375);
        errors.put(362, R.string._362);
        errors.put(389, R.string._389);
        errors.put(412, R.string._412);
        errors.put(413, R.string._413);
        errors.put(467, R.string._467);
        errors.put(514, R.string._514);
        errors.put(536, R.string._536);
        errors.put(541, R.string._541);
        errors.put(543, R.string._543);
        errors.put(550, R.string._550);
        errors.put(552, R.string._552);
        errors.put(554, R.string._554);
        errors.put(600, R.string._600);
        errors.put(601, R.string._601);
        errors.put(602, R.string._602);
        errors.put(603, R.string._603);
        errors.put(604, R.string._604);
        errors.put(605, R.string._605);
        errors.put(606, R.string._606);
        errors.put(607, R.string._607);
        errors.put(608, R.string._608);
        errors.put(609, R.string._609);
        errors.put(610, R.string._610);
        errors.put(611, R.string._611);
        errors.put(615, R.string._615);
        errors.put(616, R.string._616);
        errors.put(617, R.string._617);
        errors.put(618, R.string._618);
        errors.put(619, R.string._619);
        errors.put(620, R.string._620);
        errors.put(621, R.string._621);
        errors.put(622, R.string._622);
        errors.put(696, R.string._696);
        errors.put(632, R.string._632);

        errors.put(999, R.string._196);
        try {
            return errors.get(num);
        }catch (Exception e){
            return R.string._196;
        }
    }

}
