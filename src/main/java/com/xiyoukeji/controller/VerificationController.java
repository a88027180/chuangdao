package com.xiyoukeji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Matilda on 2016/12/27.
 */

@Controller
public class VerificationController {

    public static final int WIDTH = 120;    // 生成图片的宽度
    public static final int HEIGHT = 30;    // 生成图片的高度

    // 生成验证码图片
    @RequestMapping("/getCode")
    public void getCodePicture(String type, HttpServletResponse response, HttpSession session) throws IOException {
        // 1.在内存中创建一张图片
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.得到图片
        Graphics g = bi.getGraphics();
        // 3.设置图片的背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);    // 0,0是左上角坐标
        // 4.设置图片的边框
        g.setColor(Color.BLUE);
        g.drawRect(1, 1, WIDTH-2, HEIGHT-2);
        // 5.在图片上画干扰线
        drawRandomLine(g);
        // 6.在图片上写随机字符
        String code = drawRandomCharacter((Graphics2D)g, type);
        // 7.将随机数存在session中
        session.setAttribute("code", code);
        // 8.设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");
        // 9.设置响应头控制浏览器不要缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        // 10.将图片写给浏览器
        ImageIO.write(bi, "jpg", response.getOutputStream());
    }

    // 在图片上画随机线条
    private void drawRandomLine(Graphics g) {
        g.setColor(Color.GREEN);

        // 设置线条数量并绘制
        int lineNum = 10;
        for(int i=0; i<lineNum; i++) {
            int x1 = new Random().nextInt(WIDTH);
            int y1 = new Random().nextInt(HEIGHT);
            int x2 = new Random().nextInt(WIDTH);
            int y2 = new Random().nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

    }

    // 在图片上画随机字符
    private String drawRandomCharacter(Graphics2D g, String flag) {
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("华文新魏", Font.BOLD, 20));
        // 数字和字母
        String baseNumLetter = "0123456789ABCDEFHIJKLMNOPQRSTUVWXYZ";
        // 纯数字
        String baseNum = "0123456789";
        // 纯字母
        String baseLetter = "ABCDEFHIJKLMNOPQRSTUVWXYZ";
        if(flag == null)
            return createRandomChar(g, baseNumLetter);
        if (flag.equals("nl"))    // number&letter
            return createRandomChar(g, baseNumLetter);
        else if (flag.equals("n"))     // number
            return createRandomChar(g, baseNum);
        else if (flag.equals("l"))     // letter
            return createRandomChar(g, baseLetter);

        return createRandomChar(g, baseNumLetter);
    }

    // 创建随机字符
    private String createRandomChar(Graphics2D g, String baseChar) {
        StringBuilder sb = new StringBuilder();
        int x = WIDTH / 8 - 10;
        int deltaX = WIDTH / 4;
        String ch;

        for(int i=0; i<4; i++) {
            //设置字符旋转角度(最大30度)
            int degree = new Random().nextInt()%30;
            ch = baseChar.charAt(new Random().nextInt(baseChar.length()))+"";
            sb.append(ch);

            g.rotate(Math.toRadians(degree), x, 20); // 不是绕中心点旋转
            g.drawString(ch, x, 20);

            g.rotate(-Math.toRadians(degree), x, 20);
            x += deltaX;
        }
        return sb.toString();

    }

}
