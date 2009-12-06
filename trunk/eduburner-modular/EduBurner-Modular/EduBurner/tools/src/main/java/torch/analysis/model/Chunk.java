package torch.analysis.model;

import java.util.Arrays;

public class Chunk {

    private Word[] words = null;
    private double averageLength = -1D;
    private double variance = -1D;
    private double degreeMorphemicFreedom = -1D;
    private int length = -1;

    public Chunk(Word[] words) {
        this.words = words;
    }

    public double getAverageLength() {
        if (averageLength == -1D) {
            averageLength = (double) getLength() / (double) words.length;
        }

        return averageLength;
    }

    public double getVariance() {
        if (variance == -1D) {
            double tempVariance = 0D;
            for (int i = 0; i < words.length; i++) {
                double temp = (double) words[i].getLength() - getAverageLength();
                tempVariance += temp * temp;
            }

            variance = Math.sqrt(tempVariance / (double) words.length);
        }
        return variance;
    }


    public Word[] getWords() {
        return words;
    }

    public int getLength() {
        if (length == -1) {
            length = 0;
            for (int i = 0; i < words.length; i++) {
                length += words[i].getLength();
            }
        }

        return length;
    }

    public double getDegreeOfMorphemicFreedom() {
        if (degreeMorphemicFreedom == -1D) {
            degreeMorphemicFreedom = 0D;
            for (int i = 0; i < words.length; i++) {
                if (words[i].getLength() == 1) {
                    degreeMorphemicFreedom += Math.log((double) words[i]
                            .getFrequency());
                }
            }
        }
        return degreeMorphemicFreedom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chunk chunk = (Chunk) o;

        if (Double.compare(chunk.averageLength, averageLength) != 0) return false;
        if (Double.compare(chunk.degreeMorphemicFreedom, degreeMorphemicFreedom) != 0) return false;
        if (length != chunk.length) return false;
        if (Double.compare(chunk.variance, variance) != 0) return false;
        if (!Arrays.equals(words, chunk.words)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = words != null ? Arrays.hashCode(words) : 0;
        temp = averageLength != +0.0d ? Double.doubleToLongBits(averageLength) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = variance != +0.0d ? Double.doubleToLongBits(variance) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = degreeMorphemicFreedom != +0.0d ? Double.doubleToLongBits(degreeMorphemicFreedom) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + length;
        return result;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "words=" + (words == null ? null : Arrays.asList(words)) +
                ", averageLength=" + averageLength +
                ", variance=" + variance +
                ", degreeMorphemicFreedom=" + degreeMorphemicFreedom +
                ", length=" + length +
                '}';
    }
}
