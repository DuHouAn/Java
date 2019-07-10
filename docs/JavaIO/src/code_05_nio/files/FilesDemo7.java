package code_05_nio.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo7 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("demo3\\demo2");

        List<Path> paths=new ArrayList<>();
        Files.walkFileTree(path,new FileVisitor(paths));
        System.out.println("paths:"+paths);
    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {
        private List<Path> paths;

        public FileVisitor(List<Path> paths){
            this.paths=paths;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if(file.toString().endsWith(".txt")){
                paths.add(file.getFileName());
            }
            return super.visitFile(file, attrs);
        }
    }
}