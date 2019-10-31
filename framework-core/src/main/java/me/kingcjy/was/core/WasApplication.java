package me.kingcjy.was.core;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WasApplication {
    private static final Logger logger = LoggerFactory.getLogger(WasApplication.class);

    public static void run(Class<?> baseClass, String[] args) {
        try {
            File applicationRoot = getRootFolder(baseClass);

            System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
            System.setProperty("was.basePackage", baseClass.getPackage().getName());
            System.setProperty("was.baseClass", baseClass.getName());

            Tomcat tomcat = new Tomcat();
            Path tempPath = Files.createTempDirectory("tomcat-base-dir");
            tomcat.setBaseDir(tempPath.toString());

            tomcat.setPort(Integer.valueOf(getPort()));

            File webContentFolder = new File(applicationRoot.getAbsolutePath(), "src/main/webapp/");

            if (!webContentFolder.exists()) {
                webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
            }

            StandardContext standardContext = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
            standardContext.setParentClassLoader(WasApplication.class.getClassLoader());

            WebResourceRoot resources = getResources(standardContext, baseClass);

            standardContext.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File getRootFolder(Class<?> targetClass) {
        try {
            File root;
            String runningJarPath = targetClass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Integer getPort() {
        String webPort = System.getenv("PORT");

        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        return Integer.valueOf(webPort);
    }

    private static WebResourceRoot getResources(StandardContext standardContext, Class<?> baseClass) {
        WebResourceRoot resourceRoot = new StandardRoot(standardContext);

        File applicationRoot = getRootFolder(baseClass);
        File coreRoot = getRootFolder(WasApplication.class);
        File dataJpaRoot = getDataJpaRootFolder();

        List<WebResourceSet> resources = findResources(resourceRoot, applicationRoot, coreRoot, dataJpaRoot);

        for (WebResourceSet resource : resources) {
            resourceRoot.addPreResources(resource);
        }

        return resourceRoot;
    }

    private static File getDataJpaRootFolder() {
        try {
            Class dataJpaClass = ClassLoader.getSystemClassLoader().loadClass("me.kingcjy.was.data.core.RepositoryComponentProvider");

            return getRootFolder(dataJpaClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static List<WebResourceSet> findResources(WebResourceRoot resourceRoot, File ...files) {
        List<WebResourceSet> resources = new ArrayList<>();

        for (File file : files) {

            if(file == null) {
                continue;
            }

            File classFolder = new File(file.getAbsolutePath(), "target/classes");
            File resourceFolder = new File(file.getAbsolutePath(), "src/main/resources");

            if(classFolder.exists()) {
                resources.add(new DirResourceSet(resourceRoot, "/WEB-INF/classes", classFolder.getAbsolutePath(), "/"));
            }

            if(resourceFolder.exists()) {
                resources.add(new DirResourceSet(resourceRoot, "/WEB-INF/classes", resourceFolder.getAbsolutePath(), "/"));
            }
        }

        return resources;
    }
}
