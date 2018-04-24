package com.jsf2184.utility;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceUtility {

    public static String getResourcePath(String name) throws Exception {
        File file = getResourceFile(name);
        if (file == null) {
            return null;
        }
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }


    public static File getResourceFile(String name) throws Exception {
        ResourceUtility resourceUtility = new ResourceUtility();
        ClassLoader classLoader = resourceUtility.getClass().getClassLoader();
        URL resource =  classLoader.getResource(name);
        if (resource == null) {
            throw new Exception(String.format("Unable to find resource with name: %s", name));
        }
        String fileName = resource.getFile();
        File res = new File(fileName);
        return res;
    }
}
