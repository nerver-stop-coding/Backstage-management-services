import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class codeLine {
    public static void main(String[] args) throws Exception {
        long count = Files.walk(Paths.get("C:\\JetBrains\\IDEAProject\\netty\\transport-udt"))    // 递归获得项目目录下的所有文件
                .filter(file -> !Files.isDirectory(file))   // 筛选出文件
                .filter(file -> file.toString().endsWith(".java"))  // 筛选出 java 文件
                .flatMap(Try.of(Files::lines, Stream.empty()))     // 将会抛出受检异常的 Lambda 包装为 抛出非受检异常的 Lambda
                .filter(line -> !line.trim().isEmpty())         // 过滤掉空行
                .filter(line -> !line.trim().startsWith("//"))  //过滤掉 //之类的注释
                .filter(line -> !(line.trim().startsWith("/*") && line.trim().endsWith("*/")))  //过滤掉/* */之类的注释
                .filter(line -> !(line.trim().startsWith("/*") && !line.trim().endsWith("*/")))     //过滤掉以 /* 开头的注释（去除空格后的开头）
                .filter(line -> !(!line.trim().startsWith("/*") && line.trim().endsWith("*/")))     //过滤掉已 */ 结尾的注释
                .filter(line -> !line.trim().startsWith("*"))   //过滤掉 javadoc 中的文字注释
                .filter(line -> !line.trim().startsWith("@Override"))   //过滤掉方法上含 @Override 的
                .count();
        System.out.println("代码行数：" + count);
    }
}
