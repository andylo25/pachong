package cn.zf.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.swetake.util.Qrcode;

public class QrcodeDis {

	private static Logger logger = Logger.getLogger(QrcodeDis.class);

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * @param imgPath
	 *            生成二维码图片完整的路径
	 * @param ccbpath
	 *            二维码图片中间的logo路径
	 * @param version
	 *            二维码图片大小尺寸
	 */
	public static int createQRCode(String content, String imgPath, String ccbPath, int version) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			// N代表数字,A代表字符a-Z,B代表其他字符
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(version);
			// 图片尺寸
			int imgSize = 67 + 12 * (version - 1);

			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage image = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = image.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 130) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				logger.error("QRCode content bytes length = " + contentBytes.length + " not in [ 0,125]. ");
				return -1;
			}
			Image logo = ImageIO.read(new File(ccbPath)); // 实例化一个Image对象。
			int widthLogo = logo.getWidth(null) > image.getWidth() / 3 ? (image.getWidth() / 3) : logo.getWidth(null),
					heightLogo = logo.getHeight(null) > image.getHeight() / 3 ? (image.getHeight() / 3) : logo.getWidth(null);

			/**
			 * logo放在中心
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;
			gs.drawImage(logo, x, y, widthLogo, heightLogo, null);
			gs.dispose();
			image.flush();

			// 生成二维码QRCode图片
			File imgFile = new File(imgPath);
			ImageIO.write(image, "png", imgFile);

		} catch (Exception e) {
			logger.error("生成二维码QRCode图片出错", e);
			return -100;
		}
		return 0;
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * @param ccbpath
	 *            二维码图片中间的logo路径
	 * @param version
	 *            二维码图片大小尺寸
	 */
	public static int createQRCodeBytes(String content, String ccbPath, int version, OutputStream ops) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			// N代表数字,A代表字符a-Z,B代表其他字符
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(version);
			// 图片尺寸
			int imgSize = 67 + 12 * (version - 1);

			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage image = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = image.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 130) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				logger.error("QRCode content bytes length = " + contentBytes.length + " not in [ 0,125]. ");
				return -1;
			}
			Image logo = ImageIO.read(new File(ccbPath)); // 实例化一个Image对象。
			int widthLogo = logo.getWidth(null) > image.getWidth() / 3 ? (image.getWidth() / 3) : logo.getWidth(null),
					heightLogo = logo.getHeight(null) > image.getHeight() / 3 ? (image.getHeight() / 3) : logo.getWidth(null);

			/**
			 * logo放在中心
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;
			gs.drawImage(logo, x, y, widthLogo, heightLogo, null);
			gs.dispose();
			image.flush();

			// 生成二维码QRCode图片
			ImageIO.write(image, "png", ops);

		} catch (Exception e) {
			logger.error("生成二维码QRCode图片出错", e);
			return -100;
		}
		return 0;
	}
}
