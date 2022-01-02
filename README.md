# Parkanoid

Arkanoid type game created with Java. Basic gameplay consists of bouncing balls against blocks to destroy
them. When all the blocks are destroyed, the next level starts. Game ends when ball falls out of the
bottom of the sceen.

Java Swing and AWT were used to render the game. Game was originally created as a Java Applet
since it was easy to share the game to others.

Features

* 10 different levels
* 7 different powerups / powerdowns
* score keeping

## Modernization

Game was originally written in Java 5 and Applets. Since those technologies are no longer runnable, a few
changes had to be made to support running the game in modern computers.

* Game should be runnable as a standalone Java application
* Game should be easily distributable 

As much of the original code (and bugs) have been preserved as possible.

* `parkanoid.ParkanoidSwing` overrides Applet logic and allows the game to be run as standalone application
* Project has been migrated to Maven to allow building with modern software tools
* jpackage is used to build the distribution

## Created By
Toni Paloniemi & Severi Vidnäs

