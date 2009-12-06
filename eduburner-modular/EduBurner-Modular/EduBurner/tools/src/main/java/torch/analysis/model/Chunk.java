package torch.analysis.model;

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
}
