package Project1;

/**
 * Created by Yoona on 7/17/2017.
 * Basically an ARMY fangirl version of project0
 */

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class BTSstarSystem {

    public double readRadius(String fileName) {
        double r = 0.0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader textReader = new BufferedReader(fr);
            if (textReader != null) {
                textReader.readLine();
                String str = textReader.readLine();
                r = Double.valueOf(str);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return r;


    }

    public Planet[] readMembers(String fileName) {

        int count = 0;
        double xxPos; // its current x position
        double yyPos; // its current y position
        double xxVel; // its current velocity in the x direction
        double yyVel; // its current velocity in the y direction
        double mass; // its mass
        String imgFileName; // name of image in directory that depicts the member
        Planet[] starSystem1 = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader textReader = new BufferedReader(fr);
            if (textReader != null) {
                count = Integer.valueOf(textReader.readLine());
                starSystem1 = new Planet[count];
                textReader.readLine();
                for (int i = 0; i < count; i++) {
                    String st = textReader.readLine();
                    StringTokenizer str = new StringTokenizer(st, " \t");
                    String ss = str.nextToken();
                    xxPos = Double.valueOf(ss);
                    ss = str.nextToken();
                    yyPos = Double.valueOf(ss);
                    ss = str.nextToken();
                    xxVel = Double.valueOf(ss);
                    ss = str.nextToken();
                    yyVel = Double.valueOf(ss);
                    ss = str.nextToken();
                    mass = Double.valueOf(ss);
                    ss = str.nextToken();
                    imgFileName = ss;
                    starSystem1[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);

                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return starSystem1;
    }

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
        NBody test = new NBody();
        String filename = currentDir + "/src/Project1/Bangtan.txt";
        int T = 5000;
        double r = test.readRadius(filename);
        StdDraw.setScale(-r, r);


        Planet[] sts = test.readPlanet(filename);

        ArrayList<String> jpgFiles = new ArrayList<String>();

        int imgNum = 1;
        // ---- draw members by time
        for (int t = 0; t < T; t++) {
            double[] xForces = new double[sts.length];
            double[] yForces = new double[sts.length];
            for (int i = 0; i < sts.length; i++) {
                xForces[i] = sts[i].calcNetForceExertedByX(sts);
                yForces[i] = sts[i].calcNetForceExertedByY(sts);
            }
            StdDraw.clear();
            // ---- draw the updated members positions and velocities
            StdDraw.picture(0, 0, currentDir + "/BTS/btsNYC.jpg");
            for (int i = 0; i < sts.length; i++) {
                sts[i].update(t, xForces[i], yForces[i]);
                StdDraw.picture(sts[i].xxPos, sts[i].yyPos, "BTS/" + sts[i].imgFileName);
            }
            // ---- show the members
            StdDraw.show(1);
            if( t % 150 == 0 ) {
                String fileName = "BTSImages/" + "BTS_image" + imgNum + ".jpg";
                StdDraw.save(fileName);
                jpgFiles.add(fileName);
                imgNum++;
            }

        }
        try {
            String jpgFile = currentDir + "/BTSImages/BTS_image1.jpg";
            String outputGifFile = "BTSStarSystem.gif";

            // grab the output image type from the first image in the sequence
            BufferedImage firstImage = ImageIO.read(new File(jpgFile));

            // create a new BufferedOutputStream with the last argument
            ImageOutputStream output =
                    new FileImageOutputStream(new File(outputGifFile));

            // create a gif sequence with the type of the first image, 1 second
            // between frames, which loops continuously
            GifSequenceWriter writer =
                    new GifSequenceWriter(output, firstImage.getType(), 1, true);

            // write out the first image to our sequence...
            writer.writeToSequence(firstImage);
            for(int i = 1; i < jpgFiles.size(); i++) {
                BufferedImage nextImage = ImageIO.read(new File(jpgFiles.get(i)));
                writer.writeToSequence(nextImage);
            }
/*
            for(int i = jpgFiles.size(); i >= 0; i--) {
                BufferedImage nextImage = ImageIO.read(new File(jpgFiles.get(i)));
                writer.writeToSequence(nextImage);
            }
*/
            writer.close();
            output.close();

        } catch( Exception ie ) {
            System.out.println(ie.getMessage());
        }
    }
}
