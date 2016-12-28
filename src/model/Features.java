package model;

/**
 * Created by Thomas on 14/11/2016.
 */
public class Features {
    private boolean unicar;
    private boolean bicar;
    private boolean tricar;
    private boolean quadricar;
    private boolean unigram;
    private boolean bigram;

    public Features(){
        this.unicar = false;
        this.bicar = false;
        this.tricar = false;
        this.quadricar = false;
        this.unigram = false;
        this.bigram = false;

    }

    public boolean isUnicar() {
        return unicar;
    }

    public void setUnicar(boolean unicar) {
        this.unicar = unicar;
    }

    public boolean isBicar() {
        return bicar;
    }

    public void setBicar(boolean bicar) {
        this.bicar = bicar;
    }

    public boolean isTricar() {
        return tricar;
    }

    public void setTricar(boolean tricar) {
        this.tricar = tricar;
    }

    public boolean isQuadricar() {
        return quadricar;
    }

    public void setQuadricar(boolean quadricar) {
        this.quadricar = quadricar;
    }

    public boolean isUnigram() {
        return unigram;
    }

    public void setUnigram(boolean unigram) {
        this.unigram = unigram;
    }

    public boolean isBigram() {
        return bigram;
    }

    public void setBigram(boolean bigram) {
        this.bigram = bigram;
    }
}
