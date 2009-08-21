package eduburner.util;


public class ImageUtils {

	/*public static void resizeImage(File source, File resized, int newWidth,
			int newHeight) throws IOException {
		// read in the original image from a file
		PlanarImage originalImage = JAI.create("stream",
				new FileSeekableStream(source));

		final int originalWidth = originalImage.getWidth();
		final int originalHeight = originalImage.getHeight();
		double hRatio = ((double) newHeight / (double) originalHeight);
		double wRatio = ((double) newWidth / (double) originalWidth);
		double scale = Math.min(hRatio, wRatio);

		final ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(originalImage);
		parameterBlock.add(scale);
		parameterBlock.add(scale);
		parameterBlock.add(Interpolation
				.getInstance(Interpolation.INTERP_BICUBIC_2));

		final RenderingHints renderingHints = new RenderingHints(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		final PlanarImage newImage = JAI.create("SubsampleAverage",
				parameterBlock, renderingHints);

		// output stream, in a specific encoding
		OutputStream outputStream = new FileOutputStream(resized);
		JAI.create("encode", newImage, outputStream, "PNG", null);

		outputStream.close();
	}*/
}
