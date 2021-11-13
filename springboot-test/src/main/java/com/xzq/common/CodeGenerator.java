package com.xzq.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author xzq
 * @Description //mp 代码生成器
 * @Date 2021/11/11 21:23
 * @Version 1.0.0
 **/
public class CodeGenerator {
//    private static String url = "jdbc:mysql://121.40.49.80:3306/ihrm?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static String url = "jdbc:mysql://121.40.49.80:3306/bugstack?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static String username = "root";
    private static String password = "123456";
    private static String tableName = "user";

    private static String projectPath;
    private static String xmlPath;

    static {
        String projectPath = System.getProperty("user.dir");
        CodeGenerator.projectPath = projectPath + "/src/main/java";
        CodeGenerator.xmlPath=projectPath+ "/src/main/resources/mapper";
    }

    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("xzq") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xzq") // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, xmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .entityBuilder()
                            .enableTableFieldAnnotation();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
