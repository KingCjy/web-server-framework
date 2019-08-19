package com.kingcjy.was.core;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WasApplication {

    private static final Logger logger = LoggerFactory.getLogger(WasApplication.class);

    private static File getRootFolder(Class<?> clazz) {
        try {
            File root;
            String runningJarPath = clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            logger.info("com.kingcjy.was.application resolved root folder: " + root.getAbsolutePath());
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void run(Class<?> clazz, String[] args) {
        try {
            File root = getRootFolder(clazz);
            System.setProperty("was.basePackage", clazz.getPackage().getName());
            System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
            Tomcat tomcat = new Tomcat();
            Path tempPath = Files.createTempDirectory("tomcat-base-dir");
            tomcat.setBaseDir(tempPath.toString());

            //The port that we should run on can be set into an environment variable
            //Look for that variable and default to 8080 if it isn't there.
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }

            tomcat.setPort(Integer.valueOf(webPort));
            File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
            if (!webContentFolder.exists()) {
                webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
            }
            StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
            //Set execution independent of current thread context classloader (compatibility with exec:java mojo)
            ctx.setParentClassLoader(WasApplication.class.getClassLoader());

            logger.info("configuring app with basedir: " + webContentFolder.getAbsolutePath());

            // Declare an alternative location for your "WEB-INF/classes" dir
            // Servlet 3.0 annotation will work

            File root2 = getRootFolder(WasApplication.class);
            File additionWebInfClassesFolder = new File(root2.getAbsolutePath(), "target/classes");
            File additionWebInfClassesFolder2 = new File(root2.getAbsolutePath(), "src/main/resources");
            File additionWebInfClassesFolder3 = new File(root.getAbsolutePath(), "target/classes");
            File additionWebInfClassesFolder4 = new File(root.getAbsolutePath(), "src/main/resources");
            WebResourceRoot resources = new StandardRoot(ctx);

            WebResourceSet resourceSet;
            WebResourceSet resourceSet2;
            WebResourceSet resourceSet3;
            WebResourceSet resourceSet4;
            if (additionWebInfClassesFolder.exists()) {
                resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
                resourceSet2 = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder2.getAbsolutePath(), "/");
                resourceSet3 = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder3.getAbsolutePath(), "/");
                resourceSet4 = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder4.getAbsolutePath(), "/");
                logger.info("loading WEB-INF resources from as '" + additionWebInfClassesFolder.getAbsolutePath() + "'");
            } else {
                resourceSet = new EmptyResourceSet(resources);
                resourceSet2 = new EmptyResourceSet(resources);
                resourceSet3 = new EmptyResourceSet(resources);
                resourceSet4 = new EmptyResourceSet(resources);
            }
            resources.addPreResources(resourceSet);
            resources.addPreResources(resourceSet2);
            resources.addPreResources(resourceSet3);
            resources.addPreResources(resourceSet4);
            ctx.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
