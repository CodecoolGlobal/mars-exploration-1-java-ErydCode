package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.data.SymbolColorProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class MapImageGenerator {
    public void generateImage(Planet planet, List<Area> areas, List<Resource> resources, String[][] terrainProvider) {

        int width = terrainProvider[0].length;
        int height = terrainProvider.length;

        BufferedImage planetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                String terrain = terrainProvider[y][x];

                for (Area area : areas) {
                    if (terrain.equals(area.symbol())) {
                        planetImage.setRGB(x, y, new Color(area.rgb()[0], area.rgb()[1], area.rgb()[2]).getRGB());
                    }
                }
                for(Resource resource : resources) {
                    if( terrain.equals(resource.symbol())) {
                        planetImage.setRGB(x,y,new Color(resource.rgb()[0], resource.rgb()[1], resource.rgb()[2]).getRGB());
                    }
                }
            }
        }
        try {
            int counter = 1;
            String fileName = planet.name() + counter + ".jpg";
            String folderName = planet.name() + counter;
            String folderPath = "src/main/outputPlanets/" + folderName;
            String filePath = folderPath + File.separator + fileName;

            File folder = new File(folderPath);
            folder.mkdirs();

            // Scale the image to the desired dimensions
            Image scaledImage = planetImage.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = outputImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();

            ImageIO.write(outputImage, "jpg", new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

//public class MapImageGenerator {
//    public void generateImage(Planet planet, List<SymbolColorProvider> entities, String[][] terrainProvider) {
//
//        int width = terrainProvider[0].length;
//        int height = terrainProvider.length;
//
//        BufferedImage planetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        for (int y = 0; y < width; y++) {
//            for (int x = 0; x < height; x++) {
//                String terrain = terrainProvider[y][x];
//
//                for (SymbolColorProvider entity : entities) {
//                    if (terrain.equals(entity.symbol())) {
//                        planetImage.setRGB(x, y, new Color(entity.rgb()[0], entity.rgb()[1], entity.rgb()[2]).getRGB());
//                    }
//                }
//            }
//        }
//        try {
//            int counter = 1;
//            String fileName = planet.name() + counter + ".jpg";
//            String folderName = planet.name() + counter;
//            String folderPath = "src/main/outputPlanets/" + folderName;
//            String filePath = folderPath + File.separator + fileName;
//
//            File folder = new File(folderPath);
//            folder.mkdirs();
//
//            // Scale the image to the desired dimensions
//            Image scaledImage = planetImage.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
//            BufferedImage outputImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
//
//            Graphics2D graphics = outputImage.createGraphics();
//            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            graphics.drawImage(scaledImage, 0, 0, null);
//            graphics.dispose();
//
//            ImageIO.write(outputImage, "jpg", new File(filePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}











