package com.sun.overweight.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CreateBaseScript {
    private static final Pattern CDATA_PATTERN =
            Pattern.compile("<!\\[CDATA\\[(.*?)\\]\\]>", Pattern.DOTALL);

    public static void main(String[] args) {
        String folderPath = "E:\\echo\\overweight\\src\\main\\resources\\mybatis\\mapper";

        try {
            List<Path> xmlFiles = Files.walk(Paths.get(folderPath))
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".xml"))
                    .collect(Collectors.toList());

            for (Path xmlFile : xmlFiles) {
                String content = new String(Files.readAllBytes(xmlFile));

                // 替换 CDATA 中的内容以提取“符号”
                Matcher cdataMatcher = CDATA_PATTERN.matcher(content);
                while (cdataMatcher.find()) {
                    String symbol = cdataMatcher.group(1);
                    content = content.replace(cdataMatcher.group(0), symbol);
                }

                // Replace '<where>' with 'where 1=1'
                content = content.replace("<where>", "where 1=1");

                // Replace '</select>' with ';' followed by a newline
                content = content.replaceAll("</select>", ";\n");

                // Sample code to wrap the 'open' and 'close' values as symbols on both sides of the content not enclosed in <>
                content = content.replaceAll(
                        "<foreach\\s+[^>]*open=\"([^\"]+)\"[^>]*close=\"([^\"]+)\"[^>]*>(.*?)</foreach>",
                        "$1 $3 $2"
                );

                // Replace '#{xxx}' with '1'
                content = content.replaceAll("#\\{[^{}]+\\}", "'1'");

                // Remove data between adjacent '<' and '>'
                content = content.replaceAll("<[^<>]+>", "");

                // Replace multiple spaces with a single space
                content = content.replaceAll("\\s{2,}", " ");

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