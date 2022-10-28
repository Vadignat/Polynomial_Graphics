package ru.vadignat.math;

public class Polynomial {
    protected double [] coeff;
    public final double EPS = 1e-50;
    public Polynomial(){
        coeff = new double[1];
        coeff[0] = 0.0;
    }

    public Polynomial(double [] coeff){
        this.coeff = coeff.clone();
        correctPower();
    }

    public Polynomial(Polynomial polynomial)
    {
        coeff = polynomial.coeff.clone();
        correctPower();
    }

    private void correctPower(){
        int j = coeff.length;
        while(j > 1 && Math.abs(coeff[j-1]) < 1e-50 ){
            j--;
        }
        if(j < coeff.length) {
            var ncoeff = new double[j];
            System.arraycopy(coeff,0, ncoeff, 0, j);
            coeff = ncoeff;
        }
    }

    public double get(int i){
        if(i >= 0 && i < coeff.length)
            return coeff[i];
        return Double.NaN;
    }

    public Polynomial plus(Polynomial other){
        double [] nc = new double[Math.max(this.coeff.length,other.coeff.length)];
        var minl = Math.min(this.coeff.length,other.coeff.length);
        for(int i = 0; i < minl; i++){
            nc[i] = this.coeff[i] + other.coeff[i];
        }
        var longPol = (this.coeff.length > other.coeff.length) ? this : other;
        System.arraycopy(longPol.coeff, minl, nc, minl, nc.length - minl);
        return new Polynomial(nc);
    }

    public Polynomial times(double num){
        double [] nc = new double[coeff.length];
        for (int i = 0; i < coeff.length; i++)
            nc[i]=num*coeff[i];
        return new Polynomial(nc);
    }

    public Polynomial minus(Polynomial other){
        return plus(other.times(-1));
    }

    public Polynomial div(double num) throws Exception {
        if(Math.abs(num) >= EPS) return times(1.0/num);
        else throw new Exception("Division by 0");
    }

    public Polynomial times(Polynomial other){
        double [] nc = new double[other.getPower() + getPower() + 1];
        for(int i = 0; i < other.coeff.length; i++){
            for(int j = 0; j < coeff.length; j++) {
                nc [i + j] += other.coeff[i]* coeff[j];
            }
        }
        return new Polynomial(nc);
    }

    public double invoke(double x){
        if(coeff.length == 0)
            return 0;
        double res = coeff[0];
        double p = x;
        for(int i = 1; i < coeff.length;i++) {
            res += coeff[i] * p;
            p *= x;
        }
        return res;
    }

    public Polynomial der(){
        double [] nc = new double[getPower()];
        for (int i = 1; i < coeff.length; i++)
        {
            nc[i - 1] = i * coeff[i];
        }
        return new Polynomial(nc);
    }

    public int getPower(){
        return coeff.length - 1;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = coeff.length - 1; i > 0; i--){
            String sign = (coeff[i-1] >= EPS)? "+" : "";
            s.append((Math.abs(coeff[i] - 1) < EPS) || (Math.abs(coeff[i]) < EPS)
                    ? "" : ((Math.abs(coeff[i] + 1) < EPS) ? "-" : coeff[i]));
            s.append((Math.abs(coeff[i]) < EPS) ? "" :"x");
            s.append((Math.abs(coeff[i]) < EPS)||(i == 1) ? "" : "^" + i).append(sign);
        }
        s.append(((Math.abs(coeff[0]) >= EPS)|| coeff.length == 1) ? coeff[0] : "");
        return s.toString();
    }

}
