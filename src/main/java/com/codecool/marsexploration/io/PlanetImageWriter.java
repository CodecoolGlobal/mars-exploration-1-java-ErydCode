package com.codecool.marsexploration.io;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlanetImageWriter {
    public void generateImage(Planet planet, String[][] terrainProvider, int counterImgName) {
        int lengthYX = planet.xyLength();
        List<Area> areas = planet.areas();
        List<Resource> resources = planet.resources();
        BufferedImage planetImage = new BufferedImage(lengthYX, lengthYX, BufferedImage.TYPE_INT_RGB);
        replaceSymbolWithRGB(terrainProvider, areas, resources, planetImage);
        try {
            String filePath = getFilePath(planet, counterImgName);
            writeRescaledImg(lengthYX, planetImage, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaceSymbolWithRGB(String[][] terrainProvider, List<Area> areas, List<Resource> resources, BufferedImage planetImage) {
        for (int y = 0; y < terrainProvider.length; y++) {
            for (int x = 0; x < terrainProvider[y].length; x++) {
                String terrain = terrainProvider[y][x];
                for (Area area : areas) {
                    if (terrain.equals(area.symbol())) {
                        planetImage.setRGB(x, y, new Color(area.rgb()[0], area.rgb()[1], area.rgb()[2]).getRGB());
                    }
                }
                for (Resource resource : resources) {
                    if (terrain.equals(resource.symbol())) {
                        planetImage.setRGB(x, y, new Color(resource.rgb()[0], resource.rgb()[1], resource.rgb()[2]).getRGB());
                    }
                }
            }
        }
    }

    private String getFilePath(Planet planet, int counter) {
        String folderName = counter == 0 ? planet.name() : planet.name() + counter;
        String folderPath = "src/main/outputPlanets/" + folderName;
        File folder = new File(folderPath);
        folder.mkdirs();
        String fileName = planet.name() + counter + ".jpg";
        String filePath = folderPath + File.separator + fileName;
        return filePath;
    }

    private void writeRescaledImg(int lengthYX, BufferedImage planetImage, String filePath) throws IOException {
        Image scaledImage = planetImage.getScaledInstance(800 + lengthYX, 800 + lengthYX, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(800 + lengthYX, 800 + lengthYX, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = outputImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();
        ImageIO.write(outputImage, "jpg", new File(filePath));
    }
}