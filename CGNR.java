package CGNR;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;


public class CGNR {

    public static DoubleMatrix getImage(DoubleMatrix H, DoubleMatrix g) {
        System.out.println("H is " + H.rows + " x " + H.columns + " and g is " + g.columns);
        DoubleMatrix f = DoubleMatrix.zeros(H.columns);
        System.out.println("Multiply H "+ H.rows +" x "+ H.columns +" and f " + f.rows +" x "+ f.columns );
        DoubleMatrix r = g.sub(H.mmul(f));
        System.out.println("Multiply H "+ H.rows +" x "+ H.columns +" and r " + r.rows +" x "+ r.columns );

        DoubleMatrix z = H.transpose().mmul(r);
        DoubleMatrix p = z;

        for(int i = 0; i <= 2; i++) {
            DoubleMatrix w = H.mmul(p); // mmul serve pras duas multiplicacao?
            Double z_squared = z.norm2() * z.norm2();
            Double w_squared = w.norm2() * w.norm2();

            Double alpha = z_squared / w_squared;

            DoubleMatrix newF = f.add(p.mul(alpha)); // originalmente é alpha * p
            DoubleMatrix newR = r.sub(w.mul(alpha)); // originalmente é alpha * w

            DoubleMatrix newZ = H.transpose().mmul(newR);

            Double newZ_squared = newZ.norm2() * newZ.norm2();

            Double beta = newZ_squared / z_squared;

            DoubleMatrix newP = newZ.add(p.mul(beta)); // originalmente é beta * p

            f = newF;
            r = newR;
            z = newZ;
            p = newP;
        }

        System.out.println("final f is " + f.rows +" x "+ f.columns);

        f.reshape(30, 30);

        return f;
    }
}
