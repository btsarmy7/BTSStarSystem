package Project1;

/**
 * Created by Yoona on 7/14/17.
 */

import java.lang.Math;

public class Planet {

    double xxPos; // its current x position
    double yyPos; // its current y position
    double xxVel; // its current velocity in the x direction
    double yyVel; // its current velocity in the y direction
    double mass; // its mass
    String imgFileName; // name of image in directory that depicts the planet

    double G = 6.67 * Math.pow(10, -11); // Gravitational Constant

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        //System.out.println(p.xxPos);
        //System.out.println(this.xxPos);
        double r = Math.sqrt(Math.pow((p.xxPos - this.xxPos), 2) + Math.pow((p.yyPos - this.yyPos), 2)); // calculates distance between two planets
        return r;
    }

    public double calcForceExertedBy(Planet p) {

        double F = (G * this.mass * p.mass) / Math.pow(this.calcDistance(p), 2); // Calculates Force

        //System.out.println("The total force is :" + F);
        return F;
    }

    public double calculateForceExertedByX(Planet p) {

        double r = this.calcDistance(p);        // calculate the radius between the planets

        //System.out.println(r);

        double F = this.calcForceExertedBy(p);  // Calculates Force
        //System.out.println(F);

        double Fx = F * (p.xxPos - this.xxPos) / r; // Calculates Force in X direction

        return Fx;
    }

    public double calculateForceExertedByY(Planet p) {

        double r = this.calcDistance(p);        // calculate the radius between the planets

        double F = this.calcForceExertedBy(p);  // Calculates Force

        double Fy = F * (p.yyPos - this.yyPos) / r; // Calculates Force in Y direction

        return Fy;
    }

    public double calcNetForceExertedByX(Planet[] starSystem) {

        int count = 0;
        double totalFx = 0;

        while (count < starSystem.length) {
            if (this.equals(starSystem[count]))
                count = count + 1;
            else {
                totalFx = totalFx + this.calculateForceExertedByX(starSystem[count]);
                count = count + 1;
            }
        }
        return totalFx;
    }

    // total force Y
    public double calcNetForceExertedByY(Planet[] starSystem) {
        int count = 0;
        double totalFy = 0;

        while (count < starSystem.length) {
            if (this.equals(starSystem[count]))
                count = count + 1;
            else {
                totalFy = totalFy + this.calculateForceExertedByY(starSystem[count]);
                count = count + 1;
            }
        }
        return totalFy;
    }


    public void update(double dt, double fX, double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        double vX = this.xxVel + dt*aX;
        double vY = this.yyVel + dt*aY;
        double pNewX = this.xxPos + dt*vX;
        double pNewY = this.yyPos + dt*vY;

        this.xxVel = vX;
        this.xxPos = pNewX;
        this.yyPos = pNewY;
        this.yyVel = vY;
    }

//public update(double dt, double fX, double fY){

    public static void main(String[] args){

        Planet Saturn = new Planet(2.3 * Math.pow(10,12), 9.5 * Math.pow(10,11), 1,
        1, 6.0 * Math.pow(10,26), "N/A");

        Planet Sun = new Planet(1.0 * Math.pow(10,12), 2.0 * Math.pow(10,11), 1,
                1, 2.0 * Math.pow(10,30), "N/A");

        Planet Squirrel = new Planet(0,0,3,5,1, "bunny" );

        Planet[] starSys = new Planet[2];
        starSys[0] = Saturn;
        starSys[1] = Sun;


        double a = Sun.calcNetForceExertedByX(starSys);
        //System.out.println(a);

        Squirrel.update(1, -5, -2);
        //System.out.println("The new position of Squirrel is :" + Squirrel.xxVel);
        //System.out.println("The new velocity in the x direction is :" + Squirrel.yyVel);
    }
}
