package com.sun.overweight.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SqlParser {
    private static final Pattern PATTERN =
            Pattern.compile("<foreach[^>]*?open=\"([^\"]+)\".*?close=\"([^\"]+)\".*?>(.*?)</foreach>", Pattern.DOTALL);

    // 新的 PATTERN2 用于删除指定内容
    private static final Pattern PATTERN0 =
            Pattern.compile("<select[^>]*?databaseId=\"mysql\"[^>]*?>(.*?)</select>", Pattern.DOTALL);


    public static void main(String[] args) {
        // 替换mapper的地址 TODO
        String folderPath = "E:\\echo\\overweight\\src\\main\\resources\\mybatis\\mapper";

        try {
            List<Path> xmlFiles = Files.walk(Paths.get(folderPath))
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".xml"))
                    .collect(Collectors.toList());

            for (Path xmlFile : xmlFiles) {
                String content = new String(Files.readAllBytes(xmlFile));

                // 替换 <where> 为 where 1=1
                content = content.replace("<where>", "where 1=1");

                Matcher matcher0 = PATTERN0.matcher(content);

                if (matcher0.find()) {
                    String replacement = ""; // 将匹配到的内容替换为空字符串
                    content = matcher0.replaceAll(replacement);
                }

                // 使用 StringBuffer 以便于修改内容
                StringBuffer resultBuffer = new StringBuffer();
                Matcher matcher = PATTERN.matcher(content);

                while (matcher.find()) {
                    String open = matcher.group(1);
                    String close = matcher.group(2);
                    String body = matcher.group(3).trim();

                    // 修改或构建新的部分
                    String modifiedString = open + body + close;

                    // 将修改后的内容添加到结果中
                    matcher.appendReplacement(resultBuffer, modifiedString);
                }
                matcher.appendTail(resultBuffer); // 添加剩余部分

                // 更新内容
                content = resultBuffer.toString();
                content = content.replace("</select>", ";");


                // 替换 '#{xxx}' 为 '1'
                content = content.replaceAll("#\\{[^{}]+}", "'1'");

                // 移除相邻 '<' 和 '>' 之间的内容
                content = content.replaceAll("<[^<>]+>", "");

                // 替换多个空格为一个空格
                content = content.replaceAll("\\s{2,}", " ");
                content = content.replaceAll("&gt;", ">");
                content = content.replaceAll("&lt;", "<");
                content = content.replaceAll("( )", "1");
                content = content.replaceAll(";", ";\n");
                content = content.replaceAll("0\">", "");


                String fileName = xmlFile.getFileName().toString();
                content = "-- " + fileName + "\n" + content;
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
