package com.frame.utils;

import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 *
 * @ClassName: VerifiedCodeGenerator
 * @Description: 验证码生成器
 * @author ZhangHJ
 * @date 2014-11-20 下午02:25:45
 * @version V1.0
 */
public class VerifiedCodeGenerator {
	/**
	 * 图片宽度
	 */
	private int imgWidth = 210;

	/**
	 * 图片高度
	 */
	private int imgHeight = 20;

	/*-------------------- construtors --------------------*/

	public VerifiedCodeGenerator() {

	}

	/*----------------- public methods --------------------*/

	/**
	 *
	 * @param imgHeight
	 */
	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	/**
	 *
	 * @return
	 */
	public int getImgWidth() {
		return imgWidth;
	}

	/**
	 *
	 * @param imgWidth
	 */
	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public BufferedImage createImage(String str) {

		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = image.createGraphics();

		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, imgWidth, imgHeight);

		g2d.setComposite(AlphaComposite.Src);
		g2d.setColor(new Color(183, 183, 183));
		Random rand = new Random();
		float factor = 0.4f;
		for (int i = 0; i < 3; i++) {
			int startX = (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % (factor * imgWidth));
			int startY = (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % (factor * imgHeight));
			int endX = startX + (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % imgWidth);
			int endY = startY + (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % imgHeight);
			g2d.drawLine(startX, startY, endX, endY);
		}

		g2d.setColor(new Color(255, 255, 255));
		g2d.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		g2d.drawString(str, 2, 20);
		g2d.dispose();

		return image;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public BufferedImage createImage2(String str) {

		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		g.setColor(new Color(225, 225, 225));
		g.fillRect(0, 0, imgWidth, imgHeight);

		g.setColor(new Color(183, 183, 183));
		Random rand = new Random();
		float factor = 0.4f;
		for (int i = 0; i < 3; i++) {
			int startX = (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % (factor * imgWidth));
			int startY = (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % (factor * imgHeight));
			int endX = startX + (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % imgWidth);
			int endY = startY + (int) Math.floor(rand.nextInt(Integer.MAX_VALUE) % imgHeight);
			g.drawLine(startX, startY, endX, endY);
		}

		g.setColor(new Color(89, 0, 0));
		g.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		g.drawString(str, 2, 20);

		return image;
	}

	/**
	 * 输出图片
	 *
	 * @param out
	 * @param formatName
	 * @param img
	 * @throws IOException
	 */
	public void output(OutputStream out, String formatName, BufferedImage img) throws IOException {

		ImageIO.write(img, formatName, out);
	}

	/**
	 *
	 * @return
	 */
	public int getImgHeight() {
		return imgHeight;
	}

}
