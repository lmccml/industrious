package io.nio;

import com.sun.imageio.plugins.common.ImageUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author lmc
 * @date 2020/1/5
 */
public class PathAndFilesTest {
    public static void main(String[] args) throws Exception {
        String project_path = System.getProperty("user.dir") + "/file/";

        //final类Paths的两个static方法，从一个路径字符串来构造Path对象
        Path path = Paths.get(project_path, "test.txt");
        System.out.println(path);
        Path path2 = Paths.get(project_path + "test.txt");
        URI u = URI.create("file:///D:/work_space/java-a2z/file/test.txt");
        Path p = Paths.get(u);
        System.out.println(p);

        //FileSystems构造：
        Path path3 = FileSystems.getDefault().getPath("D:/work_space/java-a2z/file/", "test.txt");
        System.out.println(path3);

        //File和Path之间的转换，File和URI之间的转换：
        File file = new File("D:/work_space/java-a2z/file/test.txt");
        Path p1 = file.toPath();
        System.out.println(p1);
        p1.toFile();
        URI uu = file.toURI();
        System.out.println(uu); //file:/D:/work_space/java-a2z/file/test.txt

        //创建一个文件：
        Path target2 = Paths.get("D:/work_space/java-a2z/file/test_duplicate.txt");
        //windows下不支持PosixFilePermission来指定rwx权限。
        //Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
        //FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);
        if (!Files.exists(target2)) {
            Files.createFile(target2);
        }

        Files.createDirectories(Paths.get("D:/work_space/java-a2z/file"));
        if(!Files.exists(Paths.get("D:/work_space/java-a2z/file"))) {
            Files.createFile(Paths.get("D:/work_space/java-a2z/file/test.txt"));
        }
        Files.copy(Paths.get("D:/work_space/java-a2z/file/test.txt"), System.out);
        Files.copy(Paths.get("D:/work_space/java-a2z/file/test.txt"), Paths.get("D:/work_space/java-a2z/file/test_duplicate.txt"), StandardCopyOption.REPLACE_EXISTING);
        //Files.copy(System.in, Paths.get("D:/work_space/java-a2z/file/test.txt"), StandardCopyOption.REPLACE_EXISTING);

        //读取文件属性：
        Path zip = Paths.get(uu);
        System.out.println(Files.getLastModifiedTime(zip));
        System.out.println(Files.size(zip));
        System.out.println(Files.isSymbolicLink(zip));
        System.out.println(Files.isDirectory(zip));
        System.out.println(Files.readAttributes(zip, "*"));

        //读取和设置文件权限,windows下不支持PosixFilePermission来指定rwx权限。
    /*    Path profile = Paths.get("D:/work_space/java-a2z/file/test.txt");
        PosixFileAttributes attrs = Files.readAttributes(profile, PosixFileAttributes.class);// 读取文件的权限
        Set<PosixFilePermission> posixPermissions = attrs.permissions();
        posixPermissions.clear();
        String owner = attrs.owner().getName();
        String perms = PosixFilePermissions.toString(posixPermissions);
        System.out.format("%s %s%n", owner, perms);

        posixPermissions.add(PosixFilePermission.OWNER_READ);
        posixPermissions.add(PosixFilePermission.GROUP_READ);
        posixPermissions.add(PosixFilePermission.OTHERS_READ);
        posixPermissions.add(PosixFilePermission.OWNER_WRITE);

        Files.setPosixFilePermissions(profile, posixPermissions);    // 设置文件的权限*/

        //Charset.forName("GBK")
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("D:/work_space/java-a2z/file/test.txt"), StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("D:/work_space/java-a2z/file/out.txt"), StandardCharsets.UTF_8);

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            if (str != null && str.indexOf(", CAST(0x") != -1 && str.indexOf("AS DateTime)") != -1) {
                String newStr = str.substring(0, str.indexOf(", CAST(0x")) + ")";
                bufferedWriter.write(newStr);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();
        bufferedWriter.close();

        //遍历一个文件夹：
        Path dir = Paths.get("D:\\work_space");
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        for(Path e : stream){
            System.out.println(e.getFileName());
        }

        Stream<Path> stream2 = Files.list(Paths.get("D:/work_space/java-a2z/file"));
        Iterator<Path> ite = stream2.iterator();
        while(ite.hasNext()){
            Path pp = ite.next();
            System.out.println(pp.getFileName());
        }

        //遍历整个文件目录
        Path startingDir = Paths.get("D:/work_space/java-a2z");
        List<Path> result = new LinkedList<Path>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("result.size()=" + result.size());

    }

    /*
    遍历整个文件目录
     */
    private static class FindJavaVisitor extends SimpleFileVisitor<Path>{
        private List<Path> result;
        public FindJavaVisitor(List<Path> result){
            this.result = result;
        }
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            if(file.toString().endsWith(".java")){
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }

    /*
    遍历整个文件目录
     */
    private static class FindJavaVisitor2 extends SimpleFileVisitor<Path>{
        private List<Path> result;
        public FindJavaVisitor2(List<Path> result){
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            String filePath = file.toFile().getAbsolutePath();
            if(filePath.matches(".*_[1|2]{1}\\.(?i)(jpg|jpeg|gif|bmp|png)")){
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result.add(file.getFileName());
            } return FileVisitResult.CONTINUE;
        }
    }

}