# Parkanoid

***

School project made in 2006. Code uploaded to Github for archive purposes only.
This is not how I would create code nowadays :)

***

Arkanoid type game created with Java. Basic gameplay consists of bouncing balls against blocks to destroy
them. When all the blocks are destroyed, the next level starts. Game ends when ball falls out of the
bottom of the sceen.

Java Swing and AWT were used to render the game. Game was originally created as a Java Applet
since it was easy to share the game to others. It also included a level editor that saved the
levels as a list of blocks serialized using Javas object serialization tools.

Features

* 10 different levels
* 7 different powerups / powerdowns
* score keeping

## Modernization effort

Game was originally written in Java 5 and Applets. Since those technologies are no longer runnable, a few
changes had to be made to support running the game in modern computers.

* Game should be runnable as a standalone Java application
* Game should be easily distributable 

As much of the original code (and bugs) have been preserved as possible.

Changes made to original code

* `parkanoid.ParkanoidSwing` overrides Applet logic and allows the game to be run as standalone application
* `parkanoid.ClasspathURLStreamHandlerProvider` is used to add support for loading resources from
classpath using URLs. Original game loaded the resources from a web page using URLs.
* Project has been migrated to Maven to allow building with modern software tools
* jpackage is used to build the distribution. Creates an installer that bundles the game
with JRE

## Development instructions

This repository uses Maven as build tools. If you have a compatible IDE, you should be able
to do basic development tasks without much configuration.

### Requirements

* Maven
* Java 17

#### Build the application using maven
`mvn clean package`

#### Run Application
Run class `parkanoid.ParkanoidSwing` using your IDE

#### Create distribution
You must have JAVA_HOME environment variable set to JDK directory

Run `mvn package -Pjpackage`

## Created By
Toni Paloniemi & Severi Vidnäs