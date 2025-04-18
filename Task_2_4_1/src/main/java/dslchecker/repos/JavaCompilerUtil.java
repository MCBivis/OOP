package dslchecker.repos;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaCompilerUtil {

    public static boolean compileSources(File sourceRoot, File outputDir, String extraClasspath) {
        List<File> javaFiles = findJavaFiles(sourceRoot);
        if (javaFiles.isEmpty()) {
            System.out.println("Нет файлов для компиляции в " + sourceRoot.getAbsolutePath());
            return false;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Не удалось получить компилятор.");
            return false;
        }

        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(outputDir.getAbsolutePath());

        if (extraClasspath != null) {
            options.add("-classpath");
            options.add(extraClasspath);
        }

        List<String> fileNames = javaFiles.stream()
                .map(File::getAbsolutePath)
                .toList();

        List<String> args = new ArrayList<>(options);
        args.addAll(fileNames);

        int result = compiler.run(null, null, null, args.toArray(new String[0]));

        if (result == 0) {
            System.out.println("Компиляция прошла успешно: " + sourceRoot.getName());
            return true;
        } else {
            System.err.println("Ошибка компиляции в " + sourceRoot.getName());
            return false;
        }
    }

    private static List<File> findJavaFiles(File dir) {
        List<File> result = new ArrayList<>();
        if (!dir.exists()) return result;

        File[] files = dir.listFiles();
        if (files == null) return result;

        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(findJavaFiles(file));
            } else if (file.getName().endsWith(".java")) {
                result.add(file);
            }
        }
        return result;
    }
}
