package com.fh.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

public final class ImageUtils
{

	private int width;

	private int height;

	private int scaleWidth;

	double support = (double) 3.0;

	double PI = (double) 3.14159265358978;

	double[] contrib;

	double[] normContrib;

	double[] tmpContrib;

	int startContrib, stopContrib;

	int nDots;

	int nHalfDots;

	public ImageUtils()
	{
	}

	/**
	 * public final static String getPressImgPath() { return ApplicationContext
	 * .getRealPath("/template/data/util/shuiyin.gif"); }
	 */
	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg --
	 *            水印文件
	 * @param targetImg --
	 *            目标文件
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 */
	public final static void pressImage(String pressImg, String targetImg, int x, int y)
	{
		try
		{
			// 目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			// int wideth_biao = src_biao.getWidth(null);
			// int height_biao = src_biao.getHeight(null);
			// 播放按钮太大 高宽各取50
			int wideth_biao = 50;
			int height_biao = 50;
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param targetImg --
	 *            目标文件
	 * @return
	 */
	public static int getImgWidth(String targetImg)
	{
		// 目标文件
		try
		{
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			return wideth;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param _file --
	 *            目标文件
	 * @return
	 */
	public static int getImgWidth(File _file)
	{
		// 目标文件
		try
		{
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			return wideth;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取图片高度
	 * 
	 * @param targetImg --
	 *            目标文件
	 * @return
	 */
	public static int getImgHeight(String targetImg)
	{
		// 目标文件
		try
		{
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int height = src.getHeight(null);
			return height;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取图片高度
	 * 
	 * @param _file --
	 *            目标文件
	 * @return
	 */
	public static int getImgHeight(File _file)
	{
		// 目标文件
		try
		{
			Image src = ImageIO.read(_file);
			int height = src.getHeight(null);
			return height;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg --
	 *            目标图片
	 * @param fontName --
	 *            字体名
	 * @param fontStyle --
	 *            字体样式
	 * @param color --
	 *            字体颜色
	 * @param fontSize --
	 *            字体大小
	 * @param x --
	 *            偏移量
	 * @param y
	 */
	public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, int color,
			int fontSize, int x, int y)
	{
		try
		{
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(Color.RED);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.drawString(pressText, wideth - fontSize - x, height - fontSize / 2 - y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param originalFile
	 *            --目标图片
	 * @param resizedFile
	 *            --生产图片
	 * @param newWidth
	 *            --新的宽度
	 * @param quality
	 *            --缩放比例
	 * @throws IOException
	 */
	public static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException
	{

		if (quality > 1)
		{
			throw new IllegalArgumentException("Quality has to be between 0 and 1");
		}

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = null;

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);

		if (iWidth > iHeight)
		{
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
		}
		else
		{

			System.out.println((newWidth * iWidth) / iHeight);
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
				BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray =
		{
			0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0
		};
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.
		FileOutputStream out = new FileOutputStream(resizedFile);

		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);

		param.setQuality(quality, true);

		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	}
	/**
     * 
     * @param originalFile
     *            --目标图片
     * @param resizedFile
     *            --生产图片
     * @param newWidth
     *            --新的宽度
     * @param quality
     *            --缩放比例
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile, int newWidth,int newHeight, float quality) throws IOException
    {

        if (quality > 1)
        {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        /*int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
         */
        resizedImage = i.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray =
        {
            0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0
        };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);

        param.setQuality(quality, true);

        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }
	/**
	 * 图片等比例缩放
	 * 
	 * @param fromFileStr
	 *            --原图片地址
	 * @param saveToFileStr
	 *            --生成缩略图地址
	 * @param formatWideth
	 *            --生成图片宽度
	 * @param formatHeight
	 *            --生成图片高度
	 * @throws Exception
	 */
	public void saveImageAsJpg(String fromFileStr, String saveToFileStr, int formatWideth, int formatHeight)
		throws Exception
	{
		BufferedImage srcImage;
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = javax.imageio.ImageIO.read(fromFile); // construct
		// image
		int imageWideth = srcImage.getWidth(null);
		int imageHeight = srcImage.getHeight(null);
		int changeToWideth = 0;
		int changeToHeight = 0;
		if (imageWideth > 0 && imageHeight > 0)
		{
			// flag=true;
			if (imageWideth / imageHeight >= formatWideth / formatHeight)
			{
				if (imageWideth > formatWideth)
				{
					changeToWideth = formatWideth;
					changeToHeight = (imageHeight * formatWideth) / imageWideth;
				}
				else
				{
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			}
			else
			{
				if (imageHeight > formatHeight)
				{
					changeToHeight = formatHeight;
					changeToWideth = (imageWideth * formatHeight) / imageHeight;
				}
				else
				{
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			}
		}

		srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
		ImageIO.write(srcImage, "JPEG", saveFile);
	}

	public BufferedImage imageZoomOut(BufferedImage srcBufferImage, int w, int h)
	{
		width = srcBufferImage.getWidth();
		height = srcBufferImage.getHeight();
		scaleWidth = w;
		if (DetermineResultSize(w, h) == 1)
		{
			return srcBufferImage;
		}
		CalContrib();
		BufferedImage pbOut = HorizontalFiltering(srcBufferImage, w);
		BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);
		return pbFinalOut;
	}

	private int DetermineResultSize(int w, int h)
	{
		double scaleH, scaleV;
		scaleH = (double) w / (double) width;
		scaleV = (double) h / (double) height;
		// 需要判断一下scaleH，scaleV，不做放大操作
		if (scaleH >= 1.0 && scaleV >= 1.0)
		{
			return 1;
		}
		return 0;

	} // end of DetermineResultSize()

	private double Lanczos(int i, int inWidth, int outWidth, double Support)
	{
		double x;

		x = (double) i * (double) outWidth / (double) inWidth;

		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support) / (x * PI / Support);

	}

	private void CalContrib()
	{
		nHalfDots = (int) ((double) width * support / (double) scaleWidth);
		nDots = nHalfDots * 2 + 1;
		try
		{
			contrib = new double[nDots];
			normContrib = new double[nDots];
			tmpContrib = new double[nDots];
		}
		catch (Exception e)
		{
			System.out.println("init   contrib,normContrib,tmpContrib" + e);
		}

		int center = nHalfDots;
		contrib[center] = 1.0;

		double weight = 0.0;
		int i = 0;
		for (i = 1; i <= center; i++)
		{
			contrib[center + i] = Lanczos(i, width, scaleWidth, support);
			weight += contrib[center + i];
		}

		for (i = center - 1; i >= 0; i--)
		{
			contrib[i] = contrib[center * 2 - i];
		}

		weight = weight * 2 + 1.0;

		for (i = 0; i <= center; i++)
		{
			normContrib[i] = contrib[i] / weight;
		}

		for (i = center + 1; i < nDots; i++)
		{
			normContrib[i] = normContrib[center * 2 - i];
		}
	} // end of CalContrib()

	// 处理边缘
	private void CalTempContrib(int start, int stop)
	{
		double weight = 0;

		int i = 0;
		for (i = start; i <= stop; i++)
		{
			weight += contrib[i];
		}

		for (i = start; i <= stop; i++)
		{
			tmpContrib[i] = contrib[i] / weight;
		}

	} // end of CalTempContrib()

	private int GetRedValue(int rgbValue)
	{
		int temp = rgbValue & 0x00ff0000;
		return temp >> 16;
	}

	private int GetGreenValue(int rgbValue)
	{
		int temp = rgbValue & 0x0000ff00;
		return temp >> 8;
	}

	private int GetBlueValue(int rgbValue)
	{
		return rgbValue & 0x000000ff;
	}

	private int ComRGB(int redValue, int greenValue, int blueValue)
	{

		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	// 行水平滤波
	private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX, int start, int stop, int y,
			double[] pContrib)
	{
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;

		for (i = startX, j = start; i <= stopX; i++, j++)
		{
			valueRGB = bufImg.getRGB(i, y);

			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}

		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		return valueRGB;

	} // end of HorizontalFilter()

	// 图片水平滤波
	private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW)
	{
		int dwInW = bufImage.getWidth();
		int dwInH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iOutW, dwInH, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < iOutW; x++)
		{

			int startX;
			int start;
			int X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);
			int y = 0;

			startX = X - nHalfDots;
			if (startX < 0)
			{
				startX = 0;
				start = nHalfDots - X;
			}
			else
			{
				start = 0;
			}

			int stop;
			int stopX = X + nHalfDots;
			if (stopX > (dwInW - 1))
			{
				stopX = dwInW - 1;
				stop = nHalfDots + (dwInW - 1 - X);
			}
			else
			{
				stop = nHalfDots * 2;
			}

			if (start > 0 || stop < nDots - 1)
			{
				CalTempContrib(start, stop);
				for (y = 0; y < dwInH; y++)
				{
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			}
			else
			{
				for (y = 0; y < dwInH; y++)
				{
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}

		return pbOut;

	} // end of HorizontalFiltering()

	private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY, int start, int stop, int x,
			double[] pContrib)
	{
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;

		for (i = startY, j = start; i <= stopY; i++, j++)
		{
			valueRGB = pbInImage.getRGB(x, i);

			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
			// System.out.println(valueRed+"->"+Clip((int)valueRed)+"<-");
			//  
			// System.out.println(valueGreen+"->"+Clip((int)valueGreen)+"<-");
			// System.out.println(valueBlue+"->"+Clip((int)valueBlue)+"<-"+"-->");
		}

		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		// System.out.println(valueRGB);
		return valueRGB;

	} // end of VerticalFilter()

	private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH)
	{
		int iW = pbImage.getWidth();
		int iH = pbImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iW, iOutH, BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < iOutH; y++)
		{

			int startY;
			int start;
			int Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);

			startY = Y - nHalfDots;
			if (startY < 0)
			{
				startY = 0;
				start = nHalfDots - Y;
			}
			else
			{
				start = 0;
			}

			int stop;
			int stopY = Y + nHalfDots;
			if (stopY > (int) (iH - 1))
			{
				stopY = iH - 1;
				stop = nHalfDots + (iH - 1 - Y);
			}
			else
			{
				stop = nHalfDots * 2;
			}

			if (start > 0 || stop < nDots - 1)
			{
				CalTempContrib(start, stop);
				for (int x = 0; x < iW; x++)
				{
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			}
			else
			{
				for (int x = 0; x < iW; x++)
				{
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}

		}

		return pbOut;

	} // end of VerticalFiltering()

	int Clip(int x)
	{
		if (x < 0)
			return 0;
		if (x > 255)
			return 255;
		return x;
	}

	public static String getImageType(String path) throws IOException
	{
		FileInputStream fis = new FileInputStream(path);

		int leng = fis.available();
		BufferedInputStream buff = new BufferedInputStream(fis);
		byte[] mapObj = new byte[leng];
		buff.read(mapObj, 0, leng);

		String type = "";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try
		{
			bais = new ByteArrayInputStream(mapObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext())
			{
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader)
				{
					type = "gif";
				}
				else if (reader instanceof JPEGImageReader)
				{
					type = "jpg";
				}
				else if (reader instanceof PNGImageReader)
				{
					type = "png";
				}
				else if (reader instanceof BMPImageReader)
				{
					type = "bmp";
				}
			}
		}
		finally
		{
			if (bais != null)
			{
				try
				{
					bais.close();
				}
				catch (IOException ioe)
				{

				}
			}

			if (mcis != null)
			{
				try
				{
					mcis.close();
				}
				catch (IOException ioe)
				{

				}
			}
		}
		return type;
	}

	public static void main(String[] args)
	{
		System.out.println("1111");

		try
		{
			//resize(new File("F:/BF01.png"), new File("F:/BF01-1.png"), 170, (float) 1);
			resize(new File("F:/BF01.png"), new File("F:/BF01-3.png"), 170,270,(float) 1);
			/*ImageUtils imageUtils = new ImageUtils();
			imageUtils.saveImageAsJpg("F:/水槽.png", "F:/水槽1.png", 260, 160);*/
			//System.out.println(getImageType("D:/IMG_20141009_225100.jpg"));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// pressImage("c:/play.png", "c:/1.jpg", 0, 0);
	}
}
