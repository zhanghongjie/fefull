package com.frame.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
public class CompressPic {
	private File file = null; 
	private String inputDir; 
	private String outputDir;
	private String inputFileName;
	private String outputFileName; 
	private int outputWidth = 100; 
	private int outputHeight = 100;
	private boolean proportion = true;

	public CompressPic() { 
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}

	public void setWidthAndHeight(int width, int height) {
		this.outputWidth = width;
		this.outputHeight = height;
	}

	/*
	 */
	public long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}

	public String compressPic() {
		FileOutputStream out = null;
		try {
			file = new File(inputDir + inputFileName);
			if (!file.exists()) {
				return "";
			}
			Image img = ImageIO.read(file);
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return "no";
			} else {
				int newWidth;
				int newHeight;
				if (this.proportion == true) {
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = img.getWidth(null); 
					newHeight = img.getHeight(null);
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_ARGB);

				 
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				
				ImageIO.write(tag, "png", new File(outputDir+ outputFileName)); 
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "ok";
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName) {
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		return compressPic();
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp) {
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		setWidthAndHeight(width, height);
		this.proportion = gp;
		return compressPic();
	}

	public String compressPic(String mpath){
		String outputFileName = "";
		if(mpath!=null && !mpath.equals("")){
			String FS = System.getProperty("file.separator");
			mpath = mpath.replace("\\", FS).replace("/", FS);
			int index = mpath.lastIndexOf(FS);
			String inputDir = mpath.substring(0,index);
			String outputDir = inputDir;
			String inputFileName = mpath.substring(index);
			int dotIndex = inputFileName.lastIndexOf(".");
			outputFileName = inputFileName.substring(0,dotIndex)+"_small"+inputFileName.substring(dotIndex);
			CompressPic mypic = new CompressPic();
			mypic.compressPic(inputDir, outputDir, inputFileName, outputFileName);
			mpath = outputDir+FS+outputFileName;
		}
		return outputFileName;
	}
	public static void main(String[] arg) {
		CompressPic mypic = new CompressPic();
		mypic.compressPic("E:\\payspace\\satisfyReport\\WebRoot\\upload\\2015\\20150201\\20150201134811.png");

	}
}