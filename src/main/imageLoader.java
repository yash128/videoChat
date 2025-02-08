package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class imageLoader {
    public static char folderName = 'E';
    private File file = new File("/media/kali/Vol A/IdeaProjects/client-server/Clone/src/main/"+folderName+"/");
    private ByteArrayOutputStream stream = new ByteArrayOutputStream();
    public static BufferedImage reader;
    private File[] list = Objects.requireNonNull(file.listFiles());
    private int totalFiles = list.length-1;
    public byte[] imageLoaders() {
        if (--totalFiles == -1){
            totalFiles = list.length;
        }else {
            try {
                stream.flush();
                stream.reset();
                reader = ImageIO.read(list[totalFiles]);
                ImageIO.write(reader, "png", stream);
            }catch (IOException e){
                System.out.println(e.getMessage() + "error loading file");
            }
        }
        return stream.toByteArray();
    }
}
